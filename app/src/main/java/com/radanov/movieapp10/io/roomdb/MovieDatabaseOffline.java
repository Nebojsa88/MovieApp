package com.radanov.movieapp10.io.roomdb;

import android.content.Context;
import android.os.AsyncTask;


import com.radanov.movieapp10.io.models.MovieModel;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = MovieModel.class, version = 2)
public abstract class MovieDatabaseOffline extends RoomDatabase {

    private static MovieDatabaseOffline instance;

    public abstract MovieDaoOffline movieDaoOffline();

    public static synchronized MovieDatabaseOffline getInstance(Context context)
    {
        if(instance == null)
        {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    MovieDatabaseOffline.class, "movie_database_offline").fallbackToDestructiveMigration()
                    .addCallback(roomCallBack)
                    .build();
        }

        return instance;

    }

    private static Callback roomCallBack = new Callback()
    {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void>
    {
        private MovieDaoOffline movieDaoOffline;

        private PopulateDbAsyncTask(MovieDatabaseOffline database)
        {
            movieDaoOffline = database.movieDaoOffline();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            //movieDao.Insert(new MovieModelDb("Film 1", "Opis filma 1", "hahahha"));
           // movieDao.Insert(new MovieModelDb("Film 2", "Opis filma 2"));
            //noteDao.Insert(new Note("Title 3", "Description 3"));
          // noteDao.Insert(new Note("Title 4", "Description 4"));
           // noteDao.Insert(new Note("Title 5", "Description 5"));
            return null;
        }
    }

}
