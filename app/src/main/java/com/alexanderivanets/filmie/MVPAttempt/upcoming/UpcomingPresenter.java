package com.alexanderivanets.filmie.MVPAttempt.upcoming;

import android.os.AsyncTask;

import com.alexanderivanets.filmie.Config;
import com.alexanderivanets.filmie.MVPAttempt.CardInfo;
import com.alexanderivanets.filmie.MVPAttempt.api.TMDBApi;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by root on 28.04.17.
 */

public class UpcomingPresenter implements UpcomingMVP.PInterface {
    
    UpcomingMVP.VtoPInterface view;
    private TMDBApi tmdbApi;
    private Retrofit retrofit;
    private String page = "1";
    private UpcomingModel upcomingModel;
    private String lang;
    
    public UpcomingPresenter(UpcomingView view){
        this.view = view;
    }
    
    @Override
    public void onGetInfo(String page,String lang) {
        this.page = page;
        this.lang = lang;
        GetUpcomingMoviesInfo getUpcomingMoviesInfo = new GetUpcomingMoviesInfo();
        getUpcomingMoviesInfo.execute();
    }

    public class GetUpcomingMoviesInfo extends AsyncTask<Void, UpcomingModel, UpcomingModel> {
        @Override
        protected UpcomingModel doInBackground(Void... voids) {
            retrofit = new Retrofit.Builder()
                    .baseUrl("https://api.themoviedb.org/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            tmdbApi = retrofit.create(TMDBApi.class);

            Response<UpcomingModel> response = null;
            try {
                response = tmdbApi.getUpcomingList(Config.TMDB_API_KEY, page, lang).execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
            upcomingModel = response.body();
            return upcomingModel;
        }

        @Override
        protected void onPostExecute(UpcomingModel upcomingModel) {
            super.onPostExecute(upcomingModel);
            List<CardInfo> returnList = createList(upcomingModel.getResults().size(), upcomingModel);
            int pagebuf = Integer.valueOf(page);
            pagebuf++;
            page = String.valueOf(pagebuf);
            view.onShowNewInfo(returnList, page);
        }

        public List<CardInfo> createList(int size, UpcomingModel UpcomingModel) {
            List<CardInfo> list = new ArrayList<>();

            for (int i = 0; i < size; i++) {
                CardInfo ci = new CardInfo();

                ci.setmFilmName(UpcomingModel.getResults().get(i).getOriginalTitle());
                ci.setmFilmId(UpcomingModel.getResults().get(i).getId().toString());

                ci.setmTrailerPath("http://image.tmdb.org/t/p/w500/" + UpcomingModel.getResults().get(i)
                        .getBackdropPath());
                list.add(ci);
            }
            return list;
        }
    }
    
}
