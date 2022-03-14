package com.radanov.movieapp10;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.radanov.movieapp10.models.MovieModel;
import com.radanov.movieapp10.models.MovieModelDb;
import com.radanov.movieapp10.models.MovieModelOffline;
import com.radanov.movieapp10.roomdb.MovieDao;
import com.radanov.movieapp10.viewmodels.MovieViewModelDb;

public class MovieDetailsActivity extends AppCompatActivity {

    private ImageView imageViewDetails;
    private TextView titleDetails, descDetails;
    private RatingBar ratingBarDetails;

    MovieViewModelDb movieViewModelDb;

    MovieDao movieDao;
    Button buttonAdd;
    String imagePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        imageViewDetails = findViewById(R.id.imageView_details);
        titleDetails = findViewById(R.id.textView_title_details);
        descDetails = findViewById(R.id.textView_desc_details);
        ratingBarDetails = findViewById(R.id.ratingBar_details);

        buttonAdd = findViewById(R.id.buttonAddWatch);
        movieViewModelDb = new ViewModelProvider(this).get(MovieViewModelDb.class);

        getDataFromIntent();
        getOfflineDataFromIntent();

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String movieTitle = titleDetails.getText().toString();
                String movieDescription = descDetails.getText().toString();

                MovieModelDb movieModelDb = new MovieModelDb(movieTitle, movieDescription, imagePath);


                //movieViewModelDb.insert(movieModelDb);

                Toast.makeText(MovieDetailsActivity.this, "Movie Saved to your watchlist", Toast.LENGTH_SHORT).show();
                //Intent i = new Intent(MovieDetailsActivity.this, MovieListActivity.class);
                //startActivity(i);
                //finish();
            }
        });

    }

    private void getDataFromIntent() {

        if(getIntent().hasExtra("movie")){
            MovieModel movieModel = getIntent().getParcelableExtra("movie");

            titleDetails.setText(movieModel.getTitle());
            descDetails.setText(movieModel.getOverview());
            ratingBarDetails.setRating(movieModel.getVote_average()/2);
            imagePath = movieModel.getPoster_path();

            Glide.with(this).load("https://image.tmdb.org/t/p/w500/"+ movieModel.getPoster_path()).into(imageViewDetails);
        }
    }
    private void getOfflineDataFromIntent() {

        if(getIntent().hasExtra("movieOff")){
            MovieModelOffline movieModelOffline = getIntent().getParcelableExtra("movieOff");

            titleDetails.setText(movieModelOffline.getTitle());
            descDetails.setText(movieModelOffline.getOverview());
            ratingBarDetails.setRating(movieModelOffline.getVote_average()/2);
            imagePath = movieModelOffline.getPoster_path();

            Glide.with(this).load("https://image.tmdb.org/t/p/w500/"+ movieModelOffline.getPoster_path()).into(imageViewDetails);
        }
    }
}