package com.i2e1.linq.Ui.Activities;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.grampower.menuapplication.data.network.AppApiHelper;
import com.i2e1.linq.R;
import com.i2e1.linq.Ui.Adapters.PersonAdapter;
import com.i2e1.linq.Utils.UtilFunctions;
import com.i2e1.linq.data.Databases.Room.DataBaseHelper;
import com.i2e1.linq.data.Pojo.PersonWrapper;
import com.i2e1.linq.data.Pojo.PersonsApiResponse;
import com.i2e1.linq.data.Pojo.Result;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class MainActivity extends AppCompatActivity {

    private static String TAG;
    private RecyclerView mPersonsView;
    private Context context;
    private AVLoadingIndicatorView mProgressDialog;
    private CoordinatorLayout mMainLayout;
    private Toolbar mToolbar;
    private List<PersonWrapper> listOfPersons;
    private PersonAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        TAG = getClass().getSimpleName();

        bindView();


        listOfPersons = new ArrayList<>();
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }


    /**
     * function to bind view with java variables
     */
    private void bindView() {
        mToolbar = (Toolbar) findViewById(R.id.tb_main);
        mMainLayout = (CoordinatorLayout) findViewById(R.id.crdl_main);
        mProgressDialog = (AVLoadingIndicatorView) findViewById(R.id.avi_progress_dialog);
        mPersonsView = (RecyclerView) findViewById(R.id.rv_persons);
        mPersonsView.setLayoutManager(new LinearLayoutManager(context));
    }


    @Override
    protected void onResume() {
        super.onResume();

        if (new DataBaseHelper(context).getPersons().size() < 1) {
            fetchPersonsDetailsFromServer();
        } else {
            fetchDataFromDatabase();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (id) {

            /**
             * sorted by name
             */
            case R.id.action_sort_by_name:

                if (listOfPersons != null && adapter != null) {
                    Collections.sort(listOfPersons, new Comparator<PersonWrapper>() {
                        @Override
                        public int compare(PersonWrapper o1, PersonWrapper o2) {
                            return o1.getFullName().compareTo(o2.getFullName());
                        }
                    });
                    adapter.notifyDataSetChanged();

                }
                break;

            /**
             * sorted by mobile Number
             */
            case R.id.action_sort_by_mobile:

                if (listOfPersons != null && adapter != null) {
                    Collections.sort(listOfPersons, new Comparator<PersonWrapper>() {
                        @Override
                        public int compare(PersonWrapper o1, PersonWrapper o2) {
                            return o1.getPhoneNumber().compareTo(o2.getPhoneNumber());
                        }
                    });

                    adapter.notifyDataSetChanged();
                }
                break;


            /**
             * sorted by email
             */
            case R.id.action_sort_by_email:
                if (listOfPersons != null && adapter != null) {
                    Collections.sort(listOfPersons, new Comparator<PersonWrapper>() {
                        @Override
                        public int compare(PersonWrapper o1, PersonWrapper o2) {
                            return o1.getEmail().compareTo(o2.getEmail());
                        }
                    });

                    adapter.notifyDataSetChanged();
                }
                break;

            /**
             * sorted by date of birth
             */
            case R.id.action_sort_by_dob:
                if (listOfPersons != null && adapter != null) {
                    Collections.sort(listOfPersons, new Comparator<PersonWrapper>() {
                        @Override
                        public int compare(PersonWrapper o1, PersonWrapper o2) {
                            return o1.getDob().compareTo(o2.getDob());
                        }
                    });

                    adapter.notifyDataSetChanged();
                }
                break;
        }

        return super.onOptionsItemSelected(item);
    }


    /**
     * function to fetch profile data of persons from local database
     */
    private void fetchDataFromDatabase() {

        listOfPersons = new DataBaseHelper(context).getPersons();
        initView(listOfPersons);
    }


    /**
     * function to initiate the view
     *
     * @param listOfPersons list of persons to show in recyclerView
     */
    private void initView(List<PersonWrapper> listOfPersons) {
        adapter = new PersonAdapter(context, listOfPersons);
        mPersonsView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }


    /**
     * function for api call using retrofit to fetch data from server
     */
    private void fetchPersonsDetailsFromServer() {

        if (UtilFunctions.isNetworkAvailable(context)) {


            mProgressDialog.setVisibility(View.VISIBLE);
            Call<PersonsApiResponse> call = new AppApiHelper().getPersons();
            call.enqueue(new Callback<PersonsApiResponse>() {
                @Override
                public void onResponse(Call<PersonsApiResponse> call, retrofit2.Response<PersonsApiResponse> response) {
                    List<Result> listOFPersons = response.body().getResults();

                    /**
                     * inserting persons data in local database
                     */

                    List<PersonWrapper> personWrapperList = new ArrayList<>();
                    for (int i = 0; i < listOFPersons.size(); i++) {
                        Result result = listOFPersons.get(i);
                        PersonWrapper person = new PersonWrapper(0, result.getName().getFirst() + " " + result.getName().getFirst(),
                                result.getEmail(), result.getDob().getDate(), UtilFunctions.getPhoneNumberFormat(result.getPhone()), result.getPicture().getMedium(), "");
                        personWrapperList.add(person);
                    }

                    new DataBaseHelper(context).insertPersonList(personWrapperList);

                    /**
                     * downloading profile pics to store them in database
                     */
                    downloadingIssueHistoryImages(listOFPersons);

                    mProgressDialog.setVisibility(View.GONE);
                    /**
                     * fetching data from database
                     */
                    fetchDataFromDatabase();
                }

                @Override
                public void onFailure(Call<PersonsApiResponse> call, Throwable t) {
                    mProgressDialog.setVisibility(View.GONE);
                    Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });


        } else {

            Snackbar snackbar = Snackbar.make(mMainLayout, "No Connections", Snackbar.LENGTH_LONG);
            View sbView = snackbar.getView();
            TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
            textView.setTextColor(Color.YELLOW);
            snackbar.setAction("RETRY", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    fetchPersonsDetailsFromServer();
                }
            }).setActionTextColor(getResources().getColor(R.color.white));

            snackbar.show();

        }


    }


    /**
     * function to download profile Images from server  to store in local database
     *
     * @param listOfPersons list of persons to be stored
     */
    private void downloadingIssueHistoryImages(final List<Result> listOfPersons) {

        ImageLoader imageLoader = ImageLoader.getInstance();

        for (int i = 0; i < listOfPersons.size(); i++) {
            final String imageUrl = listOfPersons.get(i).getPicture().getMedium();

            imageLoader.loadImage(imageUrl, new SimpleImageLoadingListener() {
                @Override
                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                    if (loadedImage != null) {

                        String encodedImageString = UtilFunctions.bitmapToString(loadedImage);
                        /**
                         * Calling function to updating profile pic string data in local database
                         */
                        //AppDatabase.getInstance(context).updateProfileImages(imageUri, encodedImageString);
                        new DataBaseHelper(context).updateImageData(imageUri, encodedImageString);
                    } else {
                        Log.d("az", "image bitmap  is null with url " + imageUri);
                    }
                }
            });
        }

    }


}
