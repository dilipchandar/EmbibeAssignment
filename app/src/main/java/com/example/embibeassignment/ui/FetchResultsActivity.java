package com.example.embibeassignment.ui;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.example.embibeassignment.MyRepo;
import com.example.embibeassignment.R;
import com.example.embibeassignment.database.AppDatabase;
import com.example.embibeassignment.database.ResultDao;
import com.example.embibeassignment.di.NetworkComponent;
import com.example.embibeassignment.model.Result;
import com.example.embibeassignment.model.Movies;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class FetchResultsActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    Call<Movies> myRepoList;
    @Inject Retrofit retrofit;
    RepoListAdapter adapter;
    Map<String, String> map = new HashMap<>();
    MyRepo myRepo;
    BookmarkViewModel bookmarkViewModel;
    ResultDao resultDao;
    private ExecutorService executorService;
    private int currentPage = 1;
    private ProgressBar progressBar;
    private List<Result> totalResultList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fetchresults);
        progressBar = findViewById(R.id.progressBar);
        ((App) getApplication()).getComponent().inject(this);

        AppDatabase appDatabase = AppDatabase.getAppDatabase(this);
        resultDao = appDatabase.resultDao();
        executorService = Executors.newSingleThreadExecutor();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // toolbar fancy stuff
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Title");

        recyclerView = findViewById(R.id.recycler_view);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        myRepo = retrofit.create(MyRepo.class);

        doApiCall();

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int visibleItemCount = layoutManager.getChildCount();
                int totalItemCount = layoutManager.getItemCount();
                int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();

                    if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                            && firstVisibleItemPosition >= 0
                            && totalItemCount >= 20) {
                        progressBar.setVisibility(View.VISIBLE);
                        currentPage++;
                        doApiCall();
                    }

            }
        });

    }

    public void doApiCall() {
        map.put("page", Integer.toString(currentPage));
        myRepoList = myRepo.getUsers(map);

        myRepoList.enqueue(new Callback<Movies>() {
            @Override
            public void onResponse(Call<Movies> call, Response<Movies> response) {
                totalResultList.addAll(response.body().getResult());
                if(currentPage > 1) {
                    adapter = new RepoListAdapter(FetchResultsActivity.this, totalResultList);
                    progressBar.setVisibility(View.GONE);
                } else {
                    generateDataList(totalResultList);
                }
            }

            @Override
            public void onFailure(Call<Movies> call, Throwable t) {
                System.out.println();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search)
                .getActionView();
        searchView.setSearchableInfo(searchManager
                .getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);

        // listening to search query text change
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // filter recycler view when query submitted
                adapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                // filter recycler view when text is changed
                adapter.getFilter().filter(query);
                return false;
            }
        });
        return true;
    }

    private void generateDataList(List<Result> photoList) {
        adapter = new RepoListAdapter(this,photoList);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        progressBar.setVisibility(View.GONE);
    }

    public class RepoListAdapter extends RecyclerView.Adapter<RepoListAdapter.RepoViewHolder> implements Filterable {


        private List<Result> data;
        private Context context;
        public static final String IMAGE_URL_BASE_PATH = "http://image.tmdb.org/t/p/w342//";
        private List<Result> resultListFiltered;

        RepoListAdapter(Context context, List<Result> list) {

            this.context = context;
            this.data = list;
            setHasStableIds(true);
            this.resultListFiltered = list;
        }

        @NonNull
        @Override
        public RepoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.view_repo_list_item, parent, false);
            return new RepoViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RepoViewHolder holder, int position) {
            holder.bind(resultListFiltered.get(position));
        }

        @Override
        public int getItemCount() {
            return resultListFiltered.size();
        }

        @Override
        public int getItemViewType(int position) {
            return position;
        }

        @Override
        public Filter getFilter() {
            return new Filter() {
                @Override
                protected FilterResults performFiltering(CharSequence charSequence) {
                    String charString = charSequence.toString();
                    if (charString.isEmpty()) {
                        resultListFiltered = data;
                    } else {
                        final List<Result> filteredList = new ArrayList<>();
//                        for (Result row : data) {
//
//                            // name match condition. this might differ depending on your requirement
//                            // here we are looking for name or phone number match
//                            if (row.getTitle().toLowerCase().contains(charString.toLowerCase())) {
//                                filteredList.add(row);
//                            }
//                        }
                        map.put("page", "1");
                        map.put("query", charString);
                        myRepoList = myRepo.getQuery(map);

                        myRepoList.enqueue(new Callback<Movies>() {
                            @Override
                            public void onResponse(Call<Movies> call, Response<Movies> response) {

                                filteredList.addAll(response.body().getResult());
                                resultListFiltered = filteredList;
                            }

                            @Override
                            public void onFailure(Call<Movies> call, Throwable t) {
                                System.out.println();
                            }
                        });



                    }

                    FilterResults filterResults = new FilterResults();
                    filterResults.values = resultListFiltered;
                    return filterResults;
                }

                @Override
                protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                    if(!charSequence.toString().isEmpty()) {
                        resultListFiltered = (ArrayList<Result>) filterResults.values;
                    }
                    notifyDataSetChanged();
                }
            };
        }


        class RepoViewHolder extends RecyclerView.ViewHolder {

            TextView textTitle;
            ImageView imagePoster;
            ImageView imageBookmark;


            RepoViewHolder(View itemView) {
                super(itemView);
                textTitle = itemView.findViewById(R.id.text_title);
                imagePoster = itemView.findViewById(R.id.image_poster);
                imageBookmark = itemView.findViewById(R.id.image_bookmark);
            }

            void bind(final Result result) {
                textTitle.setText(result.getTitle());
                String imageUrl = IMAGE_URL_BASE_PATH + result.getPoster_path();
                Glide.with(context).load(imageUrl).override(200, 100).into(imagePoster);

                imageBookmark.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        executorService.execute(new Runnable() {
                            @Override
                            public void run() {
                                resultDao.insertAll(result);
                            }
                        });
                    }
                });
            }
        }
    }

}
