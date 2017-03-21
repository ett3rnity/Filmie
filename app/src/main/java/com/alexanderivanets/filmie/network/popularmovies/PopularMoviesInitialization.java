package com.alexanderivanets.filmie.network.popularmovies;

import android.os.AsyncTask;

import com.alexanderivanets.filmie.R;
import com.alexanderivanets.filmie.network.TMDBApi;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Саня on 15.03.2017.
 */

public class PopularMoviesInitialization {

    private TMDBApi tmdbApi;
    private Retrofit retrofit;

    private MovieListPopular movieListPopular;

    public PopularMoviesInitialization() {
        retrofit = new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        tmdbApi = retrofit.create(TMDBApi.class);

       // GetPopularMoviesInfo getPopularMoviesInfo = new GetPopularMoviesInfo();
        /*
        try {
            getPopularMoviesInfo.execute().get(); ///v etom meste gruzit
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        */
        //getPopularMoviesInfo.execute();

    }

    public MovieListPopular getMovieListPopular() {
        GetPopularMoviesInfo getPopularMoviesInfo = new GetPopularMoviesInfo();
        try {
            getPopularMoviesInfo.execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return this.movieListPopular;

    }

    public class GetPopularMoviesInfo extends AsyncTask<Void, MovieListPopular, MovieListPopular> {
        @Override
        protected MovieListPopular doInBackground(Void... voids) {
            Response<MovieListPopular> response = null;
            try {
                response = tmdbApi.getPopularList("1cf389af1a0ead5ea09eb1849d88a44a").execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
            movieListPopular = response.body();
            return movieListPopular;
        }

        @Override
        protected void onPostExecute(MovieListPopular movieListPopular) {
            super.onPostExecute(movieListPopular);
        }
    }

}
