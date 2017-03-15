package alc.javadevslagos.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import alc.javadevslagos.R;
import alc.javadevslagos.adapters.ListAdapter;
import alc.javadevslagos.interfaces.JavaDevsAPI;
import alc.javadevslagos.models.GitHubAPIResponse;
import alc.javadevslagos.models.JavaDeveloper;
import alc.javadevslagos.network.ServiceGenerator;
import alc.javadevslagos.utils.ConnectUtils;
import alc.javadevslagos.utils.RecyclerTouchListener;
import alc.javadevslagos.utils.SnackbarUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private ArrayList<JavaDeveloper> javaDeveloperArrayList;
    private RecyclerView recyclerView;
    private ListAdapter listAdapter;
    private JavaDeveloper mJavaDeveloper;
    private JavaDevsAPI service;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);


        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                prepareJavaDevelopersList();
            }
        });


//        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
//        itemAnimator.setAddDuration(1000);
//        recyclerView.setItemAnimator(itemAnimator);

        javaDeveloperArrayList = new ArrayList<>();
        listAdapter = new ListAdapter(javaDeveloperArrayList, getApplicationContext());

        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext()
                .getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(listAdapter);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext().getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {

                showDeveloperDetails(position);

            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        prepareJavaDevelopersList();


    }


    private void prepareJavaDevelopersList() {

        // check for network connectivity
        if (!ConnectUtils.isConnected(getApplicationContext())) {

            SnackbarUtils.show(recyclerView.getRootView(), getString(R.string.error_network_connection), getString(R.string.action_reload), new ReloadListener());
            swipeRefreshLayout.setRefreshing(false);

            return;

        }

        swipeRefreshLayout.setRefreshing(true);


        service = ServiceGenerator.createService(JavaDevsAPI.class);

        getJavaDevelopers();

    }

    private void getJavaDevelopers() {
        Call<GitHubAPIResponse> gitHubAPIResponseCall = service.getDevelopers();

        gitHubAPIResponseCall.enqueue(new Callback<GitHubAPIResponse>() {
            @Override
            public void onResponse(Call<GitHubAPIResponse> call, Response<GitHubAPIResponse> response) {

//                String test = String.valueOf(response.body().gettotal_count());
//                Log.d("test", test);


                if (response.isSuccessful()) {

                    swipeRefreshLayout.setRefreshing(false);


                    GitHubAPIResponse gitHubAPIResponse = response.body();
                    List<JavaDeveloper> javaDevelopers = gitHubAPIResponse.getItems();

                    for (int i = 0; i < javaDevelopers.size(); i++) {
                        mJavaDeveloper = javaDevelopers.get(i);
                        javaDeveloperArrayList.add(mJavaDeveloper);
                    }
                    listAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<GitHubAPIResponse> call, Throwable t) {
                Log.d("onFailure", t.toString());
                swipeRefreshLayout.setRefreshing(false);
                SnackbarUtils.show(recyclerView.getRootView(), getString(R.string.error_sockettimeout));

            }
        });
    }

    private void showDeveloperDetails(int position) {

        mJavaDeveloper = javaDeveloperArrayList.get(position);

        String username = mJavaDeveloper.getLogin();
        String avatarUrl = mJavaDeveloper.getAvatarUrl();
        String githubLink = mJavaDeveloper.getHtmlUrl();

        Intent detailIntent = new Intent(MainActivity.this, DeveloperDetailsActivity.class);
        detailIntent.putExtra("username", username);
        detailIntent.putExtra("avatar_url", avatarUrl);
        detailIntent.putExtra("link", githubLink);
        startActivity(detailIntent);
    }

    public class ReloadListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            prepareJavaDevelopersList();
        }
    }


}
