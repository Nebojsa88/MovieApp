package com.radanov.movieapp10;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.radanov.movieapp10.models.MovieModelDb;
import com.radanov.movieapp10.viewmodels.MovieViewModelDb;

public class UpdateMovieActivity extends AppCompatActivity {

    EditText updateTitle, updateOverview, updateImagePath;
    Button btnCancel, btnSave;

    int movieId;

    MovieViewModelDb movieViewModelDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_movie);

        getSupportActionBar().setTitle("Update Movie");

        movieViewModelDb = ViewModelProviders.of(this).get(MovieViewModelDb.class);

        updateTitle = findViewById(R.id.editTextUpdateMovieTitle);
        updateOverview = findViewById(R.id.editTextUpdateMovieOverview);
        updateImagePath = findViewById(R.id.editTextUpdateImagePath);
        btnCancel = findViewById(R.id.buttonUpdateCancel);
        btnSave = findViewById(R.id.buttonUpdateSave);

        getData();


        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Nothing Updated", Toast.LENGTH_LONG).show();
                finish();

            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateMovie();

            }
        });


    }

    private void updateMovie() {

        String titleLast = updateTitle.getText().toString();
        String descriptionLast = updateOverview.getText().toString();
        String imagePath = updateImagePath.getText().toString();

        MovieModelDb movieModelDb = new MovieModelDb(titleLast, descriptionLast, imagePath);
        if(movieId != -1){

            movieModelDb.setId(movieId);
           // movieViewModelDb.update(movieModelDb);
            Intent intent = new Intent(UpdateMovieActivity.this, WatchlistActivity.class);
            Toast.makeText(UpdateMovieActivity.this, "Movie is updated", Toast.LENGTH_SHORT).show();
            startActivity(intent);
            finish();
        }
    }


    private void getData() {

        Intent i = getIntent();
        String movieTitle = i.getStringExtra("titleUpdate");
        String movieDescription = i.getStringExtra("descriptionUpdate");
        String movieImage = i.getStringExtra("imagePath");
        movieId = i.getIntExtra("id", -1);

        updateTitle.setText(movieTitle);
        updateOverview.setText(movieDescription);
        updateImagePath.setText(movieImage);

    }
}