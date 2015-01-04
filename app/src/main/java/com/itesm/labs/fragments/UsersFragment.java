package com.itesm.labs.fragments;


import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.itesm.labs.R;
import com.itesm.labs.adapters.UsersModelAdapter;
import com.itesm.labs.dialogs.SignupDialog;
import com.itesm.labs.models.UserModel;
import com.itesm.labs.rest.deserializers.UserDeserializer;
import com.itesm.labs.rest.models.User;
import com.itesm.labs.rest.models.UserWrapper;
import com.itesm.labs.rest.service.UserService;

import java.util.ArrayList;
import java.util.Random;

import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

/**
 * A simple {@link Fragment} subclass.
 */
public class UsersFragment extends Fragment {

    private ListView mListView;
    private ArrayList<UserModel> data = new ArrayList<UserModel>();
    private UsersFragmentComm mCallback;
    private String ENDPOINT;
    private Context context;
    private Toolbar mSubToolbar;
    ProgressBar mProgressBar;

    public UsersFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
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

        context = view.getContext();

        mProgressBar = (ProgressBar) view.findViewById(R.id.fragment_users_progressbar);
        mProgressBar.setIndeterminate(true);

        new GetDbInfo().execute();

        mListView = (ListView) view.findViewById(R.id.fragment_users_list);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mCallback.loadNewUsersDetail(data.get(position), data.get(position).getBackgroundColor());
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
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            mCallback = (UsersFragmentComm) activity;
        } catch (ClassCastException e ) {
            throw new ClassCastException(activity.toString() + " must implement interface.");
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_users, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.fragment_users_menu_add_user:
                SignupDialog signupDialog = new SignupDialog(context, ENDPOINT);
                signupDialog.show();
                break;
            case R.id.fragment_users_menu_reload:
                new GetDbInfo().execute();
        }

        return true;
    }

    public interface UsersFragmentComm {
        void loadNewUsersDetail(UserModel user, int colorCode);
    }

    private class GetDbInfo extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(UserWrapper.class, new UserDeserializer())
                    .create();

            RestAdapter restAdapter = new RestAdapter.Builder()
                    .setEndpoint(ENDPOINT)
                    .setConverter(new GsonConverter(gson))
                    .build();

            UserService service = restAdapter.create(UserService.class);

            UserWrapper userWrapper = service.getUsers();

            data = new ArrayList<UserModel>();

            int[] colors = context.getResources().getIntArray(R.array.material_colors);
            for (User user : userWrapper.userList) {
                UserModel mUserModel = new UserModel(
                        user.getUserName(),
                        user.getUserLastName1(),
                        user.getUserLastName2(),
                        user.getUserId(),
                        user.getUserCarrer(),
                        user.getUserMail(),
                        user.getUserUid()
                );

                int color = colors[new Random().nextInt(colors.length-1)];
                mUserModel.setBackgroundColor(color);

                data.add(mUserModel);
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            mProgressBar.setVisibility(View.INVISIBLE);

            mListView.setAdapter(new UsersModelAdapter(context, data));
        }
    }
}
