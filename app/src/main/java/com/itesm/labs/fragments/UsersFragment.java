package com.itesm.labs.fragments;


import android.app.ActivityOptions;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
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
import com.itesm.labs.activities.SignupActivity;
import com.itesm.labs.activities.UserDetailActivity;
import com.itesm.labs.adapters.UsersModelAdapter;
import com.itesm.labs.async_tasks.GetUsersInfo;
import com.itesm.labs.rest.models.User;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class UsersFragment extends Fragment {

    private ListView mListView;
    private ArrayList<User> usersList = new ArrayList<User>();
    private String ENDPOINT;
    private Context mContext;
    private Toolbar mSubToolbar;
    private ProgressBar mProgressBar;

    private final static int SIGNUP_USER_REQUEST = 1;

    public UsersFragment() {
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
        return inflater.inflate(R.layout.fragment_users, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mContext = view.getContext();

        mProgressBar = (ProgressBar) view.findViewById(R.id.fragment_users_progressbar);
        mProgressBar.setIndeterminate(true);

        mListView = (ListView) view.findViewById(R.id.fragment_users_list);

        GetUsersInfo getUsersInfo = new GetUsersInfo() {
            @Override
            protected void onPostExecute(ArrayList<User> users) {
                super.onPostExecute(users);
                usersList = users;
                mListView.setAdapter(new UsersModelAdapter(mContext, usersList));

                mProgressBar.setVisibility(View.INVISIBLE);
            }
        };
        getUsersInfo.setContext(getActivity().getApplicationContext());
        getUsersInfo.execute(ENDPOINT);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(mContext, UserDetailActivity.class);
                intent.putExtra("USERNAME", usersList.get(position).getFullName());
                intent.putExtra("USERID", usersList.get(position).getUserId());
                intent.putExtra("USERCAREER", usersList.get(position).getUserCareer());
                intent.putExtra("USERCOLOR", usersList.get(position).getUserColor());
                ActivityOptions activityOptions = ActivityOptions.makeSceneTransitionAnimation(
                        getActivity(),
                        Pair.create(view.findViewById(R.id.user_item_user_name), getResources().getString(R.string.users_fragment_transition_name)),
                        Pair.create(view.findViewById(R.id.user_item_user_id), getResources().getString(R.string.users_fragment_transition_id)),
                        Pair.create(view.findViewById(R.id.user_item_user_carrer), getResources().getString(R.string.users_fragment_transition_career))
                );
                startActivity(intent, activityOptions.toBundle());
            }
        });

        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(mContext, SignupActivity.class);
                intent.putExtra("ENDPOINT", ENDPOINT);
                intent.putExtra("ISEDIT", true);
                intent.putExtra("USERNAME", usersList.get(position).getUserName());
                intent.putExtra("USERLASTNAME1", usersList.get(position).getUserLastName1());
                intent.putExtra("USERLASTNAME2", usersList.get(position).getUserLastName2());
                intent.putExtra("USERID", usersList.get(position).getUserId());
                intent.putExtra("USERCAREER", usersList.get(position).getUserCareer());

                startActivityForResult(intent, SIGNUP_USER_REQUEST);

                return true;
            }
        });

        mSubToolbar = (Toolbar) view.findViewById(R.id.fragment_users_subtoolbar);
        mSubToolbar.setTitle("Usuarios");
    }

    public String getENDPOINT() {
        return ENDPOINT;
    }

    public void setENDPOINT(String ENDPOINT) {
        this.ENDPOINT = ENDPOINT;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_users, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.fragment_users_menu_add_user:
                Intent intent = new Intent(mContext, SignupActivity.class);
                intent.putExtra("ENDPOINT", ENDPOINT);
                intent.putExtra("ISEDIT", false);
                startActivityForResult(intent, SIGNUP_USER_REQUEST);
                break;
            case R.id.fragment_users_menu_reload:
                mProgressBar.setVisibility(View.VISIBLE);
                GetUsersInfo getUsersInfo = new GetUsersInfo() {
                    @Override
                    protected void onPostExecute(ArrayList<User> users) {
                        super.onPostExecute(users);
                        usersList = users;
                        mListView.setAdapter(new UsersModelAdapter(mContext, usersList));

                        mProgressBar.setVisibility(View.INVISIBLE);
                    }
                };
                getUsersInfo.setContext(getActivity().getApplicationContext());
                getUsersInfo.execute(ENDPOINT);
        }

        return true;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("ENDPOINT", ENDPOINT);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        mProgressBar.setVisibility(View.VISIBLE);
        GetUsersInfo getUsersInfo = new GetUsersInfo() {
            @Override
            protected void onPostExecute(ArrayList<User> users) {
                super.onPostExecute(users);
                usersList = users;
                mListView.setAdapter(new UsersModelAdapter(mContext, usersList));

                mProgressBar.setVisibility(View.INVISIBLE);
            }
        };
        getUsersInfo.setContext(getActivity().getApplicationContext());
        getUsersInfo.execute(ENDPOINT);
    }
}
