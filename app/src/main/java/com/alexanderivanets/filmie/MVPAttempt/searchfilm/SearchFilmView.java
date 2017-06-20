package com.alexanderivanets.filmie.MVPAttempt.searchfilm;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
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


public class SearchFilmView extends Fragment implements SearchFilmMVP.VtoPInterface{

    private String mQuery;
    private String page = "1";
    private SearchFilmPresenter presenter;

    private SharedPreferences sharedPreferences;
    private String mLang;
    private String mImageQuality;

    RecyclerView popular_recyclerView;
    CardAdapter cardAdapter;
    ProgressBar progressBar_popular;

    public SearchFilmView() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mQuery = getArguments().getString("filmName");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

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
                    System.out.println("loli bragon");
                }
            }
        });

        getSettingsPreference();

        presenter = new SearchFilmPresenter(this);
        presenter.onGetInfo(page,mLang,mImageQuality,mQuery);

        return v;
    }

    void getSettingsPreference(){
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity().getBaseContext());
        mLang = sharedPreferences.getString("pref_language",null);
        mImageQuality = sharedPreferences.getString("pref_poster_quality",null);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public Fragment newInstance(String input) {
        SearchFilmView fragment = new SearchFilmView();
        Bundle args = new Bundle();
        args.putString("filmName",input);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onShowNewInfo(List<CardInfo> cardInfoList, String page) {
        if(cardAdapter==null){
            cardAdapter = new CardAdapter(cardInfoList,getContext(),"search");
            popular_recyclerView.setAdapter(cardAdapter);
        }
        else {
            cardAdapter.addInfoCardAdapter(cardInfoList);
            cardAdapter.notifyDataSetChanged();
        }
        int intPage = Integer.valueOf(page)+1;
        page = String.valueOf(intPage);
        progressBar_popular.setVisibility(View.INVISIBLE);
    }


}
