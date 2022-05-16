package com.radanov.movieapp10.ui.fragments;


import android.content.Context;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.radanov.movieapp10.R;
import com.radanov.movieapp10.io.models.MovieModel;
import com.radanov.movieapp10.ui.adapters.MovieRecycleView;
import com.radanov.movieapp10.ui.adapters.OnMovieListener;
import com.radanov.movieapp10.databinding.FragmentHomeBinding;

import com.radanov.movieapp10.utils.ViewUtils;
import com.radanov.movieapp10.viewmodels.MovieViewModel;


import java.util.List;

public class HomeFragment extends Fragment implements OnMovieListener {

    private Context context;

    private FragmentHomeBinding binding;

    private NavController navController;

    //RecycleView
    private RecyclerView recyclerView;
    private MovieRecycleView movieRecycleAdapter;

    //Live data View Model
   // private MovieListViewModel movieListViewModel;
    private MovieViewModel movieViewModel;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getContext();


       // movieListViewModel = new ViewModelProvider(this).get(MovieListViewModel.class);
        //movieViewModelDb = new ViewModelProvider(this).get(MovieViewModelDb.class);
        movieViewModel = new ViewModelProvider(this).get(MovieViewModel.class);
      //    MovieApiClient.getInstance().searchMoviesPop(1);

        //Objects.requireNonNull(context.getSupportActionBar()).setTitle("Movies");
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        navController = NavHostFragment.findNavController(this);
        //movieViewModelDb = new ViewModelProvider(this).get(MovieViewModelDb.class);

        recyclerView = binding.recyclerView;
        configureRecyclerView();

        if (!haveNetworkConnection()) {

            /*recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
            recyclerView.setHasFixedSize(true);

            final MovieOfflineAdapter adapter = new MovieOfflineAdapter();
            recyclerView.setAdapter(adapter);*/

            //movieViewModelOffline = ViewModelProviders.of(this).get(MovieViewModelOffline.class);
            movieViewModel.getAllOfflineMovies().observe(getViewLifecycleOwner(), new Observer<List<MovieModel>>() {
                @Override
                public void onChanged(List<MovieModel> movieModel) {
                    //update recycle
                    movieRecycleAdapter.setmMovies(movieModel);
                    //adapter.setOfflineMovies(movieModelOffline);
                }

            });

            /*adapter.setOnItemClickListener(new MovieOfflineAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(MovieModelOffline movieModelOffline) {
                    Bundle args = new Bundle();
                    args.putSerializable("movieOff", movieModelOffline);
                    navController.navigate(R.id.action_homeFragment_to_movieDetailsFragment);
                }
            });
*/
        } else {
            //RecycleView
            //configureRecyclerView();
            //ViewUtils.showProgressDialog(context);
            observePopularMovie();
            //ViewUtils.hideProgressDialog();
        }

        //Calling the observers
        // observingAnyChange();


        //getting popular movies
        //movieListViewModel.searchMoviePop(1);


        // Inflate the layout for this fragment
        return binding.getRoot();


    }


    private void configureRecyclerView() {
        //LiveData can't be passed via the constructor
        movieRecycleAdapter = new MovieRecycleView(this);
        recyclerView.setAdapter(movieRecycleAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));


        //Recycle View pagination
        //Loading next page of api result
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (!recyclerView.canScrollVertically(1)) {
                    //Here we need to display next search results on next api page
                    //movieListViewModel.searchNextPage();

                }
            }
        });
    }

    private void observePopularMovie() {
        ViewUtils.showProgressDialog(context);
        movieViewModel.getPopularMovies().observe(getViewLifecycleOwner(), new Observer<List<MovieModel>>() {
            @Override
            public void onChanged(List<MovieModel> movieList) {
                ViewUtils.hideProgressDialog();
                //Observing for any data change
                if (movieList != null) {
                    movieViewModel.deleteAll();
                    for (int i = 0; i < movieList.size(); i++) {
                        movieViewModel.insert(movieList.get(i));
                        movieRecycleAdapter.setmMovies(movieList);
                        Log.v("Tag", "Saved to database: " + movieList.get(i).getTitle());
                    }
                }
            }
        });

    }

    private boolean haveNetworkConnection() {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }
        return haveConnectedWifi || haveConnectedMobile;
    }

    @Override
    public void onMovieClick(int position) {
        Bundle args = new Bundle();
        args.putSerializable("movie", movieRecycleAdapter.getSelectedMovie(position));
        navController.navigate(R.id.action_homeFragment_to_movieDetailsFragment, args);
    }

    @Override
    public void onCategoryClick(String category) {

    }
}
