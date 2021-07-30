package com.radanov.movieapp10;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.radanov.movieapp10.adapters.MovieDbAdapter;
import com.radanov.movieapp10.models.MovieModelDb;
import com.radanov.movieapp10.viewmodels.MovieViewModelDb;

import java.util.List;

public class WatchlistActivity extends AppCompatActivity {

    private MovieViewModelDb movieViewModelDb;
    Button btnAddNewWatchlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watchlist);

        getSupportActionBar().setTitle("Watchlist");
        

        RecyclerView recyclerView = findViewById(R.id.recyclerViewWatchlist);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        btnAddNewWatchlist = findViewById(R.id.buttonAddNewMovie);
        btnAddNewWatchlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(WatchlistActivity.this, AddNewMovieActivity.class);
                startActivity(i);
                finish();

            }
        });

        final MovieDbAdapter adapter = new MovieDbAdapter();
        recyclerView.setAdapter(adapter);

        movieViewModelDb = ViewModelProviders.of(this).get(MovieViewModelDb.class);
        movieViewModelDb.getAllDbMovies().observe(this, new Observer<List<MovieModelDb>>() {
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
                Intent intent = new Intent(WatchlistActivity.this, com.radanov.movieapp10.UpdateMovieActivity.class);
                intent.putExtra("id", movieModelDb.getId());
                intent.putExtra("titleUpdate", movieModelDb.getMovieDbTitle());
                intent.putExtra("descriptionUpdate",movieModelDb.getMovieDbDescription());
                intent.putExtra("imagePath",movieModelDb.getImagePath());
                startActivity(intent);

            }
        });

        //addNewMovieWatchlist();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent = new Intent(WatchlistActivity.this, MovieListActivity.class);
        startActivity(intent);
    }

    public void addNewMovieWatchlist(){

        Intent data = new Intent();
        data = getIntent();
        String movieTitle =  data.getStringExtra("add new title");
        String movieDescription =  data.getStringExtra("add new overview");
        if(movieTitle != null && movieDescription != null ) {

            //  MovieModelDb movieDb = new MovieModelDb(movieTitle, movieDescription);
           //   movieViewModelDb.insert(movieDb);
            Toast.makeText(WatchlistActivity.this, "Main added movie ", Toast.LENGTH_LONG).show();
        }




    }
}