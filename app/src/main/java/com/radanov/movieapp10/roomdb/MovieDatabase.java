package com.radanov.movieapp10.roomdb;

import android.content.Context;
import android.os.AsyncTask;

import com.radanov.movieapp10.models.MovieModelDb;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = MovieModelDb.class, version = 2)
public abstract class MovieDatabase extends RoomDatabase {

    private static MovieDatabase instance;

    public abstract MovieDao movieDao();

    public static synchronized MovieDatabase getInstance(Context context)
    {
        if(instance == null)
        {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    MovieDatabase.class, "movie_database").fallbackToDestructiveMigration()
                    .addCallback(roomCallBack)
                    .build();
        }

        return instance;

    }

    private static final Callback roomCallBack = new Callback()
    {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void>
    {
        private final MovieDao movieDao;

        private PopulateDbAsyncTask(MovieDatabase database)
        {
            movieDao = database.movieDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            movieDao.Insert(new MovieModelDb("Film 1", "Opis filma 1", "hahahha"));
           // movieDao.Insert(new MovieModelDb("Film 2", "Opis filma 2"));
            //noteDao.Insert(new Note("Title 3", "Description 3"));
          // noteDao.Insert(new Note("Title 4", "Description 4"));
           // noteDao.Insert(new Note("Title 5", "Description 5"));
            return null;
        }
    }







}
