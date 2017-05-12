package com.alexanderivanets.filmie.MVPAttempt.upcoming;

import com.alexanderivanets.filmie.MVPAttempt.CardInfo;

import java.util.List;

/**
 * Created by root on 28.04.17.
 */

public interface UpcomingMVP {

    interface VtoPInterface {
        void onShowNewInfo(List<CardInfo> cardInfoList, String page);
    }

    interface PInterface {
        void onGetInfo(String page, String lang);
    }

}
