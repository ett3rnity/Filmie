package com.alexanderivanets.filmie.MVPAttempt.searchfilm;

import android.os.AsyncTask;

import com.alexanderivanets.filmie.Config;
import com.alexanderivanets.filmie.MVPAttempt.CardInfo;
import com.alexanderivanets.filmie.MVPAttempt.api.TMDBApi;
import com.alexanderivanets.filmie.MVPAttempt.popular.PopularModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by root on 14.05.17.
 */

public class SearchFilmPresenter implements SearchFilmMVP.PInterface {

    SearchFilmMVP.VtoPInterface view;

    private String input;
    private String page;

    private String lang;
    private String imageQuality;

    private GetSearchFIlmInfo getSearchFIlmInfo;


    Retrofit retrofit;
    TMDBApi tmdbApi;


    public SearchFilmPresenter(SearchFilmView view){
        this.view = view;
    }

    @Override
    public void onGetInfo(String page, String lang, String imageQuality, String input) {

        //проверка страницы,т.к. по запросу у нас обычно страниц мало,то таким образом можем
        //передать в запрос номер сраницы,которой нет

        if(getSearchFIlmInfo!=null){
            if (getSearchFIlmInfo.getStatus() == AsyncTask.Status.RUNNING) {
                getSearchFIlmInfo.cancel(true);
            }
        }


        if(this.page==null || checkForLastPage(page,this.page)) {
            this.input = input;
            this.page = page;
            this.lang = lang;
            this.imageQuality = imageQuality;

            getSearchFIlmInfo = new GetSearchFIlmInfo();
            getSearchFIlmInfo.execute();
        }

    }


    public class GetSearchFIlmInfo extends AsyncTask<Void,SearchFilmResponse,SearchFilmResponse>{

        @Override
        protected SearchFilmResponse doInBackground(Void... params) {
            retrofit = new Retrofit.Builder()
                    .baseUrl("https://api.themoviedb.org/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            tmdbApi = retrofit.create(TMDBApi.class);

            Response<SearchFilmResponse> response = null;

            try {
                response = tmdbApi.getSearchFilmList(input, Config.TMDB_API_KEY).execute();

            } catch (IOException e) {
                e.printStackTrace();
            }

            return response.body();
        }

        @Override
        protected void onPostExecute(SearchFilmResponse searchFilmResponse) {
            super.onPostExecute(searchFilmResponse);

            List<CardInfo> returnList = createList(searchFilmResponse.getResults().size(), searchFilmResponse);
            int pagebuf = Integer.valueOf(page);
            pagebuf++;
            page = String.valueOf(pagebuf);
            view.onShowNewInfo(returnList,page);

        }
    }

    List<CardInfo> createList(int size, SearchFilmResponse searchFilmResponse) {
        List<CardInfo> list = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            CardInfo ci = new CardInfo();

            ci.setmFilmName(searchFilmResponse.getResults().get(i).getTitle());
            ci.setmFilmId(searchFilmResponse.getResults().get(i).getId().toString());

            ci.setmTrailerPath("http://image.tmdb.org/t/p/w780/" + searchFilmResponse.getResults().get(i)
                    .getBackdropPath());
            list.add(ci);
        }
        return list;
    }

    public boolean checkForLastPage(String currentPage, String prevPage){
        if (Integer.valueOf(currentPage)>Integer.valueOf(prevPage)) return false;
        else  return true;
    }
}
