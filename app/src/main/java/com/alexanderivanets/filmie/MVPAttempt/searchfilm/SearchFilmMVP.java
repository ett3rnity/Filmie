package com.alexanderivanets.filmie.MVPAttempt.searchfilm;

import com.alexanderivanets.filmie.MVPAttempt.CardInfo;

import java.util.List;

/**
 * Created by root on 14.05.17.
 */

public interface SearchFilmMVP {

    interface VtoPInterface {
        void onShowNewInfo(List<CardInfo> cardInfoList, String page);
    }

    interface PInterface {
        void onGetInfo(String page, String lang,String imageQuality,String input);
    }
}
