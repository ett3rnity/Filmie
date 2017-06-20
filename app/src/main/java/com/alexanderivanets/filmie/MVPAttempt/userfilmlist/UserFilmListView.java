package com.alexanderivanets.filmie.MVPAttempt.userfilmlist;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.alexanderivanets.filmie.CardAdapter;
import com.alexanderivanets.filmie.MVPAttempt.CardInfo;
import com.alexanderivanets.filmie.R;

import java.util.List;

/**
 * Created by root on 09.05.17.
 */

public class UserFilmListView extends Fragment implements UserFilmListMVP.VtoPInterface{

    RecyclerView popular_recyclerView;
    CardAdapter cardAdapter;
    ProgressBar progressBar_popular;

    UserFilmListPresenter presenterImpl;

    public UserFilmListView(){

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_fragment_popular, container, false);
        progressBar_popular = (ProgressBar)v.findViewById(R.id.progressbar_popular);
        progressBar_popular.setVisibility(View.VISIBLE);

        popular_recyclerView = (RecyclerView) v.findViewById(R.id.popular_recyclerView);
        popular_recyclerView.setHasFixedSize(true);

        //handle different orientation film per line count
        if(getActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            popular_recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
        }
        else{
            popular_recyclerView.setLayoutManager(new GridLayoutManager(getContext(),1));
        }

        assert popular_recyclerView != null;
        //hardcode

        popular_recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager llm1 = (LinearLayoutManager)recyclerView.getLayoutManager();
                int lastvisiblepos = llm1.findLastCompletelyVisibleItemPosition();
                if (lastvisiblepos !=RecyclerView.NO_POSITION && lastvisiblepos == cardAdapter.getItemCount() -1){
                }
            }
        });


        presenterImpl = new UserFilmListPresenter(this,getContext());
        presenterImpl.onAskInfo();


        return v;
    }


    @Override
    public void onShowInfo(List<CardInfo> cardInfoList) {
        cardAdapter = new CardAdapter(cardInfoList,getContext(),"myDB");
        popular_recyclerView.setAdapter(cardAdapter);
        progressBar_popular.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if(popular_recyclerView !=null){
            cardAdapter.notifyDataSetChanged();
        }

    }



}
