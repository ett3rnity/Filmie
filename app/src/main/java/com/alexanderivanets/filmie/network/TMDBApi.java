package com.alexanderivanets.filmie.network;

import com.alexanderivanets.filmie.network.popularmovies.MovieListPopular;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Саня on 15.03.2017.
 */

public interface TMDBApi {

    @GET("/3/movie/popular")
    Call<MovieListPopular>getPopularList(@Query("api_key") String mApiKey);

}
