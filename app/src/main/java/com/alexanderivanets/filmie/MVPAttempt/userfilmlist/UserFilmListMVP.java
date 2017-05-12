package com.alexanderivanets.filmie.MVPAttempt.userfilmlist;

import com.alexanderivanets.filmie.MVPAttempt.CardInfo;

import java.util.List;

/**
 * Created by root on 08.05.17.
 */

public interface UserFilmListMVP {

    interface VtoPInterface{
        void onShowInfo(List<CardInfo> cardInfoList);
    }

    interface PInterface{
        void onAskInfo();
    }
}
