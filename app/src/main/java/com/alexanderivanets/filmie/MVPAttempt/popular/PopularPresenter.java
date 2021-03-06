package com.alexanderivanets.filmie.MVPAttempt.popular;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import com.alexanderivanets.filmie.CheckInternetAvailibiality;
import com.alexanderivanets.filmie.Config;
import com.alexanderivanets.filmie.MVPAttempt.CardInfo;
import com.alexanderivanets.filmie.MVPAttempt.api.TMDBApi;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by root on 26.04.17.
 */

public class PopularPresenter implements PopularMVP.PInterface {

    private PopularMVP.VtoPInterface view;
    private Context context;
    private TMDBApi tmdbApi;
    private Retrofit retrofit;
    private String page = "1";
    private String lastPage;
    private PopularModel popularModel;
    private String language;
    private String imageQuality;
    private CheckInternetAvailibiality checkInternetAvailibiality;
    private boolean internetIsAlive;


    public PopularPresenter(PopularView view){
        this.view = view;
        this.context = view.getContext();
    }


    @Override
    public void onGetInfo(String page, String lang,String imageQuality) {
        this.page = page;
        language = lang;
        this.imageQuality = imageQuality;
        checkInternetAvailibiality = new CheckInternetAvailibiality(context);
        Thread t = new Thread(checkInternetAvailibiality);
        t.start();
        try {
            t.join();
            internetIsAlive = checkInternetAvailibiality.getState();

            if(internetIsAlive) {
                GetPopularMoviesInfo getPopularMoviesInfo = new GetPopularMoviesInfo();
                getPopularMoviesInfo.execute();
            }
        } catch (InterruptedException e) {
            Log.v("Error"," while joining thread of checking the internet");
        }

    }

    public class GetPopularMoviesInfo extends AsyncTask<Void, PopularModel, PopularModel> {
        @Override
        protected PopularModel doInBackground(Void... voids) {


            retrofit = new Retrofit.Builder()
                    .baseUrl("https://api.themoviedb.org/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            tmdbApi = retrofit.create(TMDBApi.class);

            Response<PopularModel> response = null;


            try {
                    response = tmdbApi.getPopularList(Config.TMDB_API_KEY, page, language).execute();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                popularModel = response.body();
                return popularModel;

        }

        @Override
        protected void onPostExecute(PopularModel popularModel) {
            super.onPostExecute(popularModel);
            if(popularModel!=null) {
                List<CardInfo> returnList = createList(popularModel.getResults().size(), popularModel);
                int pagebuf = Integer.valueOf(page);
                pagebuf++;
                page = String.valueOf(pagebuf);
                view.onShowNewInfo(returnList, page);
            }
        }

         List<CardInfo> createList(int size, PopularModel popularModel) {
            List<CardInfo> list = new ArrayList<>();

            for (int i = 0; i < size; i++) {
                CardInfo ci = new CardInfo();

                ci.setmFilmName(popularModel.getResults().get(i).getTitle());
                ci.setmFilmId(popularModel.getResults().get(i).getId().toString());

                ci.setmTrailerPath("http://image.tmdb.org/t/p/"+imageQuality + popularModel.getResults().get(i)
                        .getBackdropPath());
                list.add(ci);
            }
            return list;
        }

    }


}
