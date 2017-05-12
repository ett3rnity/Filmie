package com.alexanderivanets.filmie.MVPAttempt.popular;

import com.alexanderivanets.filmie.MVPAttempt.CardInfo;

import java.util.List;

/**
 * Created by root on 26.04.17.
 */

public interface PopularMVP {

    interface VtoPInterface {
        void onShowNewInfo(List<CardInfo>cardInfoList,String page);
    }

    interface PInterface {
        void onGetInfo(String page, String lang,String imageQuality);
    }

}
