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

public class AddNewMovieActivity extends AppCompatActivity {

    EditText editTextAddNewTitle, editTextAddNewOverview, editTextAddNewImagePath;
    Button btnCancel, btnSave;

    MovieViewModelDb movieViewModelDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_movie);

        getSupportActionBar().setTitle("Add New Movie");

        //omogucava koriscenje movieViewModela
        movieViewModelDb = ViewModelProviders.of(this).get(MovieViewModelDb.class);


        editTextAddNewTitle = findViewById(R.id.editTextUpdateMovieTitle);
        editTextAddNewOverview = findViewById(R.id.editTextUpdateMovieOverview);
        editTextAddNewImagePath = findViewById(R.id.editTextAddNewImage);

        btnCancel = findViewById(R.id.buttonUpdateCancel);
        btnSave = findViewById(R.id.buttonUpdateSave);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Movie is not saved", Toast.LENGTH_LONG).show();
                finish();
                Intent intent = new Intent(AddNewMovieActivity.this, WatchlistActivity.class);
                startActivity(intent);
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveMovie();
            }
        });



    }
    private void saveMovie() {

        String movieTitle = editTextAddNewTitle.getText().toString();
        String movieOverview = editTextAddNewOverview.getText().toString();
        String imagePath = editTextAddNewImagePath.getText().toString();


        MovieModelDb movieModelDb = new MovieModelDb(movieTitle, movieOverview, imagePath);
        //movieViewModelDb.insert(movieModelDb);
        Toast.makeText(getApplicationContext(), "Add new activity saved", Toast.LENGTH_LONG).show();
        Intent i = new Intent(AddNewMovieActivity.this, WatchlistActivity.class);
        startActivity(i);
        finish();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent = new Intent(AddNewMovieActivity.this, WatchlistActivity.class);
        startActivity(intent);
    }
}