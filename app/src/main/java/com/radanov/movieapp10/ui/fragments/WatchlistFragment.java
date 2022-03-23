package com.radanov.movieapp10.ui.fragments;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.radanov.movieapp10.R;
import com.radanov.movieapp10.ui.adapters.MovieDbAdapter;
import com.radanov.movieapp10.databinding.FragmentWatchlistBinding;
import com.radanov.movieapp10.io.models.MovieModel;
import com.radanov.movieapp10.viewmodels.MovieViewModel;


import java.util.List;


public class WatchlistFragment extends Fragment {

    FragmentWatchlistBinding binding;
    Context context;

    private MovieViewModel movieViewModel;

    private NavController navController;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context = getContext();

        binding = FragmentWatchlistBinding.inflate(inflater, container, false);
        navController = NavHostFragment.findNavController(this);

        movieViewModel = new ViewModelProvider(this).get(MovieViewModel.class);

        RecyclerView recyclerView = binding.recyclerViewWatchlist;

        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setHasFixedSize(true);

        final MovieDbAdapter adapter = new MovieDbAdapter();
        recyclerView.setAdapter(adapter);


        movieViewModel.getWatchlistMovies().observe(getViewLifecycleOwner(), new Observer<List<MovieModel>>() {
            @Override
            public void onChanged(List<MovieModel> movieModels) {
                adapter.setMoviesDb(movieModels);
            }
        });


        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                movieViewModel.deleteWatchlist(adapter.getMoviesDb(viewHolder.getAdapterPosition()));

            }
        }).attachToRecyclerView(recyclerView);

        adapter.setOnItemClickListener(new MovieDbAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(MovieModel movieModelDb) {

                Bundle args = new Bundle();
                args.putSerializable("movie", movieModelDb);
                args.putBoolean("is_watchlist", true);
                navController.navigate(R.id.action_dashboardFragment_to_movieDetailsFragment, args);

            }
        });

        return binding.getRoot();
    }

}
