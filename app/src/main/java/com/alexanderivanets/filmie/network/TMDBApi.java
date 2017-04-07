package com.alexanderivanets.filmie.network;

import com.alexanderivanets.filmie.network.popularmovies.MovieListPopular;
import com.alexanderivanets.filmie.network.selectedmovie.SelectedFilmInfo;
import com.alexanderivanets.filmie.network.upcoming.MovieListUpcoming;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Саня on 15.03.2017.
 */

public interface TMDBApi {

    @GET("/3/movie/popular")
    Call<MovieListPopular> getPopularList(@Query("api_key") String mApiKey, @Query("page") String mPage);

    @GET("/3/movie/upcoming")
    Call<MovieListUpcoming>getUpcomingList(@Query("api_key") String mApiKey,@Query("page") String mPage);

    @GET("/3/movie/{mFilmId}")
    Call<SelectedFilmInfo>getSelectedFilmInfo(@Path("mFilmId")String mFIlmId, @Query("api_key") String mApiKey);



}
