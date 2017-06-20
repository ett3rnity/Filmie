package com.alexanderivanets.filmie.MVPAttempt.api;

import com.alexanderivanets.filmie.MVPAttempt.popular.PopularModel;
import com.alexanderivanets.filmie.MVPAttempt.searchfilm.SearchFilmResponse;
import com.alexanderivanets.filmie.MVPAttempt.similar.SimilarMovies;
import com.alexanderivanets.filmie.network.selectedmovie.SelectedFilmInfo;
import com.alexanderivanets.filmie.MVPAttempt.upcoming.UpcomingModel;
import com.alexanderivanets.filmie.network.youtuberesponse.YouTubeResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
  Created by Саня->root on 15.03.2017.->17.04.2017
 */

public interface TMDBApi {

    @GET("/3/movie/popular")
    Call<PopularModel> getPopularList(@Query("api_key") String mApiKey, @Query("page") String mPage,@Query("language") String mLang);

    @GET("/3/movie/upcoming")
    Call<UpcomingModel>getUpcomingList(@Query("api_key") String mApiKey, @Query("page") String mPage,@Query("language") String mLang);

    @GET("/3/movie/{mFilmId}")
    Call<SelectedFilmInfo>getSelectedFilmInfo(@Path("mFilmId")String mFIlmId, @Query("api_key") String mApiKey,@Query("language") String mLang);

    @GET("/3/movie/{mFilmId}/videos")
    Call<YouTubeResponse>getTrailerInfo(@Path("mFilmId")String mFIlmId, @Query("api_key")String mApiKey);

    @GET("/3/search/movie/")
    Call<SearchFilmResponse>getSearchFilmList(@Query("query") String mQuery, @Query("api_key") String mApiKey);

    @GET("/3/movie/{mFilmId}/similar")
    Call<SimilarMovies>getSimilarList(@Path("mFilmId") String mFilmId, @Query("api_key") String mApiKey, @Query("language") String mLang);

    @GET("/3/movie/{mFilmId}/recommendations")
    Call<SimilarMovies>getRecomendationsList(@Path("mFilmId") String mFilmId, @Query("api_key") String mApiKey, @Query("language") String mLang);

    @GET("/genre/movie/list")
    Call<>getGenresList();

}
