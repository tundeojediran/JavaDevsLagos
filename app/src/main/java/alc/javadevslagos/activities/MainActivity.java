package alc.javadevslagos.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.github.amlcurran.showcaseview.ShowcaseView;
import com.github.amlcurran.showcaseview.targets.ActionViewTarget;

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

    public static final int PAGE_SIZE = 30;

    private ArrayList<JavaDeveloper> javaDeveloperArrayList;
    private RecyclerView recyclerView;
    private ListAdapter listAdapter;
    private JavaDeveloper mJavaDeveloper;
    private JavaDevsAPI service;
    private SwipeRefreshLayout swipeRefreshLayout;

    private FloatingActionButton floatingActionButton;
    private int pageNo = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        floatingActionButton = (FloatingActionButton) findViewById(R.id.fab);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadMoreDevelopers();
            }
        });


        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pageNo = 1;
                prepareJavaDevelopersList();
            }
        });

        javaDeveloperArrayList = new ArrayList<>();
        listAdapter = new ListAdapter(javaDeveloperArrayList, getApplicationContext());

        recyclerView.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext()
                .getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(listAdapter);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext().getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {

                showDeveloperDetails(position);
                Log.d("position", String.valueOf(position));

            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        prepareJavaDevelopersList();
    }


    //check for network connectivity
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

    // load more developers from Github API by page number
    private void loadMoreDevelopers() {

        // check for network connectivity
        if (!ConnectUtils.isConnected(getApplicationContext())) {

            SnackbarUtils.show(recyclerView.getRootView(), getString(R.string.error_network_connection), getString(R.string.action_reload), new ReloadListener());
            swipeRefreshLayout.setRefreshing(false);

            return;
        }

        getPageNumber();
        swipeRefreshLayout.setRefreshing(true);
        service = ServiceGenerator.createService(JavaDevsAPI.class);


        Call<GitHubAPIResponse> gitHubAPIResponseCall = service.getDevelopers(pageNo);

        gitHubAPIResponseCall.enqueue(new Callback<GitHubAPIResponse>() {
            @Override
            public void onResponse(Call<GitHubAPIResponse> call, Response<GitHubAPIResponse> response) {


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

    private void getJavaDevelopers() {
        Call<GitHubAPIResponse> gitHubAPIResponseCall = service.getDevelopers(pageNo);

        gitHubAPIResponseCall.enqueue(new Callback<GitHubAPIResponse>() {
            @Override
            public void onResponse(Call<GitHubAPIResponse> call, Response<GitHubAPIResponse> response) {

                if (response.isSuccessful()) {
                    Log.d("page no", String.valueOf(pageNo));

                    javaDeveloperArrayList.clear();


                    GitHubAPIResponse gitHubAPIResponse = response.body();
                    List<JavaDeveloper> javaDevelopers = gitHubAPIResponse.getItems();

                    for (int i = 0; i < javaDevelopers.size(); i++) {
                        mJavaDeveloper = javaDevelopers.get(i);
                        javaDeveloperArrayList.add(mJavaDeveloper);
                    }
                    listAdapter.notifyDataSetChanged();
                    swipeRefreshLayout.setRefreshing(false);

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

    // show a developer's github details
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

    // set page numbers to allow for pagination
    private void getPageNumber() {
        if (javaDeveloperArrayList.size() == PAGE_SIZE) {
            pageNo = 2;
        } else if (javaDeveloperArrayList.size() == PAGE_SIZE * 2) {
            pageNo = 3;
        } else if (javaDeveloperArrayList.size() == PAGE_SIZE * 3) {
            pageNo = 4;
        } else if (javaDeveloperArrayList.size() >= PAGE_SIZE * 4) {
            pageNo = 5;
        } else {
            pageNo = 1;
        }
    }

    // reload listener on network connection failure
    public class ReloadListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            prepareJavaDevelopersList();
        }
    }


}