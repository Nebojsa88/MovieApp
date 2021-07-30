package com.radanov.movieapp10.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName ="movie_offline")
public class MovieModelOffline implements Parcelable {
        @PrimaryKey(autoGenerate = true)
        private int movieId;

        private int id;
        private String title;
        private String poster_path;
        private String release_date;
        private float vote_average;
        private String overview;
        private String original_language;

        public MovieModelOffline(String title, String poster_path, float vote_average, String overview) {
                this.title = title;
                this.poster_path = poster_path;
                this.vote_average = vote_average;
                this.overview = overview;
        }
        @Ignore
        public MovieModelOffline(int id, String title, String poster_path, String release_date, float vote_average, String overview, String original_language) {
                this.id = id;
                this.title = title;
                this.poster_path = poster_path;
                this.release_date = release_date;
                this.vote_average = vote_average;
                this.overview = overview;
                this.original_language = original_language;
        }

        protected MovieModelOffline(Parcel in) {
                id = in.readInt();
                title = in.readString();
                poster_path = in.readString();
                release_date = in.readString();
                vote_average = in.readFloat();
                overview = in.readString();
                original_language = in.readString();
        }

        public static final Creator<MovieModelOffline> CREATOR = new Creator<MovieModelOffline>() {
                @Override
                public MovieModelOffline createFromParcel(Parcel in) {
                        return new MovieModelOffline(in);
                }

                @Override
                public MovieModelOffline[] newArray(int size) {
                        return new MovieModelOffline[size];
                }
        };

        @Override
        public String toString() {
                return "MovieModelOffline{" +
                        "title='" + title + '\'' +
                        '}';
        }

        public int getMovieId() {
                return movieId;
        }

        public void setMovieId(int movieId) {
                this.movieId = movieId;
        }

        public int getId() {
                return id;
        }

        public void setId(int id) {
                this.id = id;
        }

        public void setTitle(String title) {
                this.title = title;
        }

        public void setPoster_path(String poster_path) {
                this.poster_path = poster_path;
        }

        public void setRelease_date(String release_date) {
                this.release_date = release_date;
        }

        public void setVote_average(float vote_average) {
                this.vote_average = vote_average;
        }

        public void setOverview(String overview) {
                this.overview = overview;
        }

        public void setOriginal_language(String original_language) {
                this.original_language = original_language;
        }

        public String getTitle() {
                return title;
        }

        public String getPoster_path() {
                return poster_path;
        }

        public String getRelease_date() {
                return release_date;
        }

        public float getVote_average() {
                return vote_average;
        }

        public String getOverview() {
                return overview;
        }

        public String getOriginal_language() {
                return original_language;
        }

        @Override
        public int describeContents() {
                return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
                parcel.writeInt(id);
                parcel.writeString(title);
                parcel.writeString(poster_path);
                parcel.writeString(release_date);
                parcel.writeFloat(vote_average);
                parcel.writeString(overview);
                parcel.writeString(original_language);
        }
}