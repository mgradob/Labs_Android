package com.itesm.labs.fragments;


import android.app.ActivityOptions;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.itesm.labs.R;
import com.itesm.labs.activities.RequestDetailActivity;
import com.itesm.labs.adapters.RequestsModelAdapter;
import com.itesm.labs.async_tasks.GetRequestsInfo;
import com.itesm.labs.rest.models.Cart;
import com.itesm.labs.rest.models.CartItem;
import com.itesm.labs.rest.models.User;
import com.itesm.labs.rest.queries.CartQuery;
import com.itesm.labs.rest.queries.UserQuery;
import com.itesm.labs.sqlite.LabsSqliteHelper;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class RequestsFragment extends Fragment {

    private ProgressBar mProgressBar;
    private ListView mListView;
    private ArrayList<Cart> cartsList = new ArrayList<>();
    private Toolbar mSubtoolbar;
    private String ENDPOINT;
    private Context mContext;

    private final static int REQUEST_DETAIL_REQUEST = 1;

    private LabsSqliteHelper labsSqliteHelper;

    public RequestsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);

        if (savedInstanceState != null) setENDPOINT(savedInstanceState.getString("ENDPOINT"));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_requests, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mContext = view.getContext();

        labsSqliteHelper = new LabsSqliteHelper(mContext);
        labsSqliteHelper.repopulate();

        mProgressBar = (ProgressBar) view.findViewById(R.id.fragment_requests_progressbar);
        mProgressBar.setIndeterminate(true);

        mListView = (ListView) view.findViewById(R.id.fragment_requests_list);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(mContext, RequestDetailActivity.class);
                intent.putExtra("ENDPOINT", ENDPOINT);
                intent.putExtra("USERNAME", cartsList.get(position).getUserName());
                intent.putExtra("USERID", cartsList.get(position).getUserId());
                intent.putExtra("REQUESTSTATUS", cartsList.get(position).isReady());

                ActivityOptions activityOptions = ActivityOptions.makeSceneTransitionAnimation(
                        getActivity(),
                        Pair.create(view.findViewById(R.id.request_item_user_name), getResources().getString(R.string.requests_fragment_transition_name)),
                        Pair.create(view.findViewById(R.id.request_item_user_id), getResources().getString(R.string.requests_fragment_transition_id))
                );
                startActivityForResult(intent, REQUEST_DETAIL_REQUEST, activityOptions.toBundle());
            }
        });

        mSubtoolbar = (Toolbar) view.findViewById(R.id.fragment_requests_subtoolbar);
        mSubtoolbar.setTitle("Pedidos");

        GetRequestsInfo getRequestsInfo = new GetRequestsInfo() {
            @Override
            protected void onPostExecute(ArrayList<CartItem> cartItems) {
                super.onPostExecute(cartItems);

                final ArrayList<CartItem> cartItemList = cartItems;

                class FillCarts extends AsyncTask<Void, Void, Void> {
                    @Override
                    protected Void doInBackground(Void... params) {

                        ArrayList<String> userIds = new ArrayList<>();
                        for (CartItem cartItem : cartItemList) {
                            if (!userIds.contains(cartItem.getStudentId()))
                                userIds.add(cartItem.getStudentId());
                        }

                        for (String userId : userIds) {
                            User user = new UserQuery("http://labs.chi.itesm.mx:8080").getUser(userId);
                            ArrayList<CartItem> tempCartItemsList = new CartQuery(ENDPOINT).getCartOf(userId);
                            for(CartItem item : tempCartItemsList){
                                if(!item.isReady()){
                                    for(CartItem item1 : tempCartItemsList){
                                        item1.setReady(false);
                                    }
                                    break;
                                }
                            }
                            cartsList.add(new Cart(user.getFullName(), user.getUserId(),
                                    tempCartItemsList.get(tempCartItemsList.size() - 1).getDateCheckout(),
                                    tempCartItemsList, tempCartItemsList.get(tempCartItemsList.size() - 1).isReady()));
                        }

                        return null;
                    }

                    @Override
                    protected void onPostExecute(Void aVoid) {
                        super.onPostExecute(aVoid);
                        mProgressBar.setVisibility(View.INVISIBLE);

                        mListView.setAdapter(new RequestsModelAdapter(mContext, cartsList));
                    }
                }
                new FillCarts().execute();
            }
        };
        getRequestsInfo.execute(ENDPOINT);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("ENDPOINT", ENDPOINT);
    }

    public String getENDPOINT() {
        return ENDPOINT;
    }

    public void setENDPOINT(String ENDPOINT) {
        this.ENDPOINT = ENDPOINT;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        mProgressBar.setIndeterminate(true);
        mProgressBar.setVisibility(View.VISIBLE);

        cartsList.clear();

        GetRequestsInfo getRequestsInfo = new GetRequestsInfo() {
            @Override
            protected void onPostExecute(ArrayList<CartItem> cartItems) {
                super.onPostExecute(cartItems);

                final ArrayList<CartItem> cartItemList = cartItems;

                class FillCarts extends AsyncTask<Void, Void, Void> {
                    @Override
                    protected Void doInBackground(Void... params) {

                        ArrayList<String> userIds = new ArrayList<>();
                        for (CartItem cartItem : cartItemList) {
                            if (!userIds.contains(cartItem.getStudentId()))
                                userIds.add(cartItem.getStudentId());
                        }

                        for (String userId : userIds) {
                            User user = new UserQuery("http://labs.chi.itesm.mx:8080").getUser(userId);
                            ArrayList<CartItem> tempCartItemsList = new CartQuery(ENDPOINT).getCartOf(userId);
                            for(CartItem item : tempCartItemsList){
                                if(!item.isReady()){
                                    for(CartItem item1 : tempCartItemsList){
                                        item1.setReady(false);
                                    }
                                    break;
                                }
                            }
                            cartsList.add(new Cart(user.getFullName(), user.getUserId(),
                                    tempCartItemsList.get(tempCartItemsList.size() - 1).getDateCheckout(),
                                    tempCartItemsList, tempCartItemsList.get(tempCartItemsList.size() - 1).isReady()));
                        }

                        return null;
                    }

                    @Override
                    protected void onPostExecute(Void aVoid) {
                        super.onPostExecute(aVoid);
                        mProgressBar.setVisibility(View.INVISIBLE);

                        mListView.setAdapter(new RequestsModelAdapter(mContext, cartsList));
                    }
                }
                new FillCarts().execute();
            }
        };
        getRequestsInfo.execute(ENDPOINT);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_request, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.fragment_users_menu_reload:
                cartsList.clear();
                GetRequestsInfo getRequestsInfo = new GetRequestsInfo() {
                    @Override
                    protected void onPostExecute(ArrayList<CartItem> cartItems) {
                        super.onPostExecute(cartItems);

                        final ArrayList<CartItem> cartItemList = cartItems;

                        class FillCarts extends AsyncTask<Void, Void, Void> {
                            @Override
                            protected Void doInBackground(Void... params) {

                                ArrayList<String> userIds = new ArrayList<>();
                                for (CartItem cartItem : cartItemList) {
                                    if (!userIds.contains(cartItem.getStudentId()))
                                        userIds.add(cartItem.getStudentId());
                                }

                                for (String userId : userIds) {
                                    User user = new UserQuery("http://labs.chi.itesm.mx:8080").getUser(userId);
                                    ArrayList<CartItem> tempCartItemsList = new CartQuery(ENDPOINT).getCartOf(userId);
                                    for(CartItem item : tempCartItemsList){
                                        if(!item.isReady()){
                                            for(CartItem item1 : tempCartItemsList){
                                                item1.setReady(false);
                                            }
                                            break;
                                        }
                                    }
                                    cartsList.add(new Cart(user.getFullName(), user.getUserId(),
                                            tempCartItemsList.get(tempCartItemsList.size() - 1).getDateCheckout(),
                                            tempCartItemsList, tempCartItemsList.get(tempCartItemsList.size() - 1).isReady()));
                                }

                                return null;
                            }

                            @Override
                            protected void onPostExecute(Void aVoid) {
                                super.onPostExecute(aVoid);
                                mProgressBar.setVisibility(View.INVISIBLE);

                                mListView.setAdapter(new RequestsModelAdapter(mContext, cartsList));
                            }
                        }
                        new FillCarts().execute();
                    }
                };
                getRequestsInfo.execute(ENDPOINT);
                break;
        }

        return true;
    }
}
