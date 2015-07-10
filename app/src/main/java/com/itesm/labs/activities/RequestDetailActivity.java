package com.itesm.labs.activities;

import android.app.Activity;
import android.nfc.NfcAdapter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mrengineer13.snackbar.SnackBar;
import com.itesm.labs.R;
import com.itesm.labs.adapters.RequestItemModelAdapter;
import com.itesm.labs.rest.models.CartItem;
import com.itesm.labs.rest.models.Category;
import com.itesm.labs.rest.models.Component;
import com.itesm.labs.rest.models.History;
import com.itesm.labs.rest.models.User;
import com.itesm.labs.rest.queries.CartQuery;
import com.itesm.labs.rest.queries.CategoryQuery;
import com.itesm.labs.rest.queries.ComponentQuery;
import com.itesm.labs.rest.queries.DetailHistoryQuery;
import com.itesm.labs.rest.queries.UserQuery;
import com.itesm.labs.util.NfcHandler;
import com.itesm.labs.util.NfcHandler.UidCallback;
import com.melnykov.fab.FloatingActionButton;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.ExecutionException;

public class RequestDetailActivity extends AppCompatActivity implements UidCallback {

    private TextView userNameTextView, userIdTextView;
    private ListView userRequestList;
    private FloatingActionButton mFab;
    private boolean mRequestStatus;
    private String userName, userId;
    private long UID;
    private String ENDPOINT, USERS_ENDPOINT = "http://labs.chi.itesm.mx:8080";
    private Activity mActivity;

    public NfcHandler nfcHandler;

    private ArrayList<CartItem> mCartItemsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_detail);

        mActivity = this;

        ENDPOINT = getIntent().getStringExtra("ENDPOINT");
        userNameTextView = (TextView) findViewById(R.id.activity_request_detail_user_name);
        userName = getIntent().getStringExtra("USERNAME");
        userNameTextView.setText(userName);
        userIdTextView = (TextView) findViewById(R.id.activity_request_detail_user_id);
        userId = getIntent().getStringExtra("USERID");
        userIdTextView.setText(userId);
        mRequestStatus = getIntent().getBooleanExtra("REQUESTSTATUS", false);

        getWindow().setStatusBarColor(getResources().getColor(R.color.primary_dark));

        userRequestList = (ListView) findViewById(R.id.activity_request_detail_list);

        mFab = (FloatingActionButton) findViewById(R.id.activity_request_detail_fab);
        mFab.attachToListView(userRequestList);
        if (mRequestStatus) mFab.setImageResource(R.drawable.ic_uid_white);
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mRequestStatus)
                    validateRequest();
                else
                    readyRequest();
            }
        });

        new AsyncTask<Void, Void, ArrayList<CartItem>>() {
            ArrayList<Category> categories = new ArrayList<>();
            ArrayList<Component> components = new ArrayList<>();

            @Override
            protected ArrayList<CartItem> doInBackground(Void... params) {
                ArrayList<CartItem> cartItems = new CartQuery(ENDPOINT).getCartOf(userId);
                for (CartItem cartItem : cartItems) {
                    categories.add(new CategoryQuery(ENDPOINT).getCategoryOf(cartItem));
                    components.add(new ComponentQuery(ENDPOINT).getComponent(cartItem.getComponentId()));
                }

                return cartItems;
            }

            @Override
            protected void onPostExecute(ArrayList<CartItem> cartItems) {
                super.onPostExecute(cartItems);

                mCartItemsList = cartItems;

                userRequestList.setAdapter(new RequestItemModelAdapter(getApplicationContext(),
                        mCartItemsList, categories, components));
            }
        }.execute();

        try {
            nfcHandler = new NfcHandler(this);
            enableReader();
        } catch (Exception ex) {
            Toast.makeText(this, "No NFC adapter available", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Updates a cart to make an user cart ready.
     */
    private void readyRequest() {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                for (CartItem cartItem : mCartItemsList) {
                    cartItem.setReady(true);
                    new CartQuery(ENDPOINT).updateCartItem(cartItem);
                }

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);

                new SnackBar.Builder(mActivity)
                        .withMessage("Pedido listo.")
                        .withTextColorId(R.color.primary_text_light)
                        .withVisibilityChangeListener(new SnackBar.OnVisibilityChangeListener() {
                            @Override
                            public void onShow(int i) {

                            }

                            @Override
                            public void onHide(int i) {
                                finish();
                            }
                        })
                        .show();
            }
        }.execute();
    }

    /**
     * Validate an user cart with the UID of the user credential.
     */
    private void validateRequest() {
        User user;

        class GetUidUser extends AsyncTask<Void, Void, User> {
            @Override
            protected User doInBackground(Void... params) {
                return new UserQuery(USERS_ENDPOINT).getUser(userId);
            }
        }

        try {
            user = new GetUidUser().execute().get();

            if (UID == user.getUserUid()) {

                new AsyncTask<Void, Void, Void>() {
                    @Override
                    protected Void doInBackground(Void... params) {
                        for (CartItem cartItem : mCartItemsList) {
                            DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
                            df.setTimeZone(TimeZone.getTimeZone("UTC"));
                            History tempHistory = new History();
                            tempHistory.setStudentId(userId);
                            tempHistory.setComponentId(cartItem.getComponentId());
                            tempHistory.setQuantity(cartItem.getQuantity());
                            tempHistory.setDateOut(df.format(new Date()));
                            tempHistory.setDateIn(null);
                            new DetailHistoryQuery(ENDPOINT).postDetailHistory(tempHistory);

                            new CartQuery(ENDPOINT).deleteCart(cartItem.getCartId());
                        }

                        return null;
                    }

                    @Override
                    protected void onPostExecute(Void aVoid) {
                        super.onPostExecute(aVoid);

                        new SnackBar.Builder(mActivity)
                                .withMessage("Pedido verificado.")
                                .withTextColorId(R.color.primary_text_light)
                                .withVisibilityChangeListener(new SnackBar.OnVisibilityChangeListener() {
                                    @Override
                                    public void onShow(int i) {
                                    }

                                    @Override
                                    public void onHide(int i) {
                                        finish();
                                    }
                                })
                                .show();
                    }
                }.execute();
            } else {
                new SnackBar.Builder(mActivity)
                        .withMessage("Credencial no válida")
                        .withTextColorId(R.color.primary_text_light)
                        .show();
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        disableReader();
    }

    @Override
    protected void onResume() {
        super.onResume();
        enableReader();
    }

    private void enableReader() {
        Activity activity = this;
        NfcAdapter nfcAdapter = NfcAdapter.getDefaultAdapter(activity);
        if (nfcAdapter != null)
            nfcAdapter.enableReaderMode(activity, nfcHandler, NfcAdapter.FLAG_READER_NFC_A, null);
    }

    private void disableReader() {
        Activity activity = this;
        NfcAdapter nfcAdapter = NfcAdapter.getDefaultAdapter(activity);
        if (nfcAdapter != null)
            nfcAdapter.disableReaderMode(activity);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_request_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void getUid(long uid) {
        this.UID = uid;
    }
}
