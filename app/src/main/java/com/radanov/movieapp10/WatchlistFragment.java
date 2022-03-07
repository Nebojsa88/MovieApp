package com.radanov.movieapp10;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.radanov.movieapp10.adapters.MovieDbAdapter;
import com.radanov.movieapp10.databinding.FragmentWatchlistBinding;
import com.radanov.movieapp10.models.MovieModelDb;
import com.radanov.movieapp10.viewmodels.MovieViewModelDb;

import java.util.List;


public class WatchlistFragment extends Fragment {


    FragmentWatchlistBinding binding;
    private MovieViewModelDb movieViewModelDb;
    Context context;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context = getContext();

        binding = FragmentWatchlistBinding.inflate(inflater, container, false);

        movieViewModelDb = new ViewModelProvider(this).get(MovieViewModelDb.class);

        RecyclerView recyclerView = binding.recyclerViewWatchlist;

        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setHasFixedSize(true);

        final MovieDbAdapter adapter = new MovieDbAdapter();
        recyclerView.setAdapter(adapter);

        movieViewModelDb.getAllDbMovies().observe(getViewLifecycleOwner(), new Observer<List<MovieModelDb>>() {
            @Override
            public void onChanged(List<MovieModelDb> movieModelDbs) {
                //update recycle
                adapter.setMoviesDb(movieModelDbs);
            }

        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                movieViewModelDb.delete(adapter.getMoviesDb(viewHolder.getAdapterPosition()));
            }
        }).attachToRecyclerView(recyclerView);

        adapter.setOnItemClickListener(new MovieDbAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(MovieModelDb movieModelDb) {
                /*Intent intent = new Intent(WatchlistActivity.this, com.radanov.movieapp10.UpdateMovieActivity.class);
                intent.putExtra("id", movieModelDb.getId());
                intent.putExtra("titleUpdate", movieModelDb.getMovieDbTitle());
                intent.putExtra("descriptionUpdate",movieModelDb.getMovieDbDescription());
                intent.putExtra("imagePath",movieModelDb.getImagePath());
                startActivity(intent);*/

            }
        });



        // Inflate the layout for this fragment
        return binding.getRoot();
    }

}
