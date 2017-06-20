package com.alexanderivanets.filmie.MVPAttempt.popular;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.alexanderivanets.filmie.CardAdapter;
import com.alexanderivanets.filmie.CheckInternetAvailibiality;
import com.alexanderivanets.filmie.MVPAttempt.CardInfo;
import com.alexanderivanets.filmie.MVPAttempt.settings.SettingsActivity;
import com.alexanderivanets.filmie.R;

import java.util.List;


public class PopularView extends Fragment implements PopularMVP.VtoPInterface {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    PopularMVP.PInterface pInterfaceImpl;

    RecyclerView popular_recyclerView;
    CardAdapter cardAdapter;
    ProgressBar progressBar_popular;

    private String page = "1";

    private SharedPreferences sharedPreferences;
    private String mLang;
    private String mImageQuality;


    public PopularView() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PopularView.
     */
    // TODO: Rename and change types and number of parameters
    public static PopularView newInstance(String param1, String param2) {
        PopularView fragment = new PopularView();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);

        }


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_fragment_popular, container, false);
        progressBar_popular = (ProgressBar)v.findViewById(R.id.progressbar_popular);
        progressBar_popular.setVisibility(View.VISIBLE);

        popular_recyclerView = (RecyclerView) v.findViewById(R.id.popular_recyclerView);
        popular_recyclerView.setHasFixedSize(true);

        //handle different orientation film per line count
        int mOrientation = getActivity().getResources().getConfiguration().orientation;
        if(mOrientation == Configuration.ORIENTATION_LANDSCAPE){
            popular_recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
        }
        else{
            popular_recyclerView.setLayoutManager(new GridLayoutManager(getContext(),1));
        }

        popular_recyclerView.setAdapter(new RecyclerView.Adapter() {
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                return null;
            }
            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            }
            @Override
            public int getItemCount() {
                return 0;
            }
        });
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
                        pInterfaceImpl.onGetInfo(page, mLang, mImageQuality);
                    }
            }
        });

        getSettingsPreference();

        pInterfaceImpl = new PopularPresenter(this);

        pInterfaceImpl.onGetInfo(page, mLang, mImageQuality);


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


    @Override
    public void onResume() {
        super.onResume();
        }


    @Override
    public void onShowNewInfo(List<CardInfo>cardInfoList,String page) {

        if(cardAdapter == null){
            cardAdapter = new CardAdapter(cardInfoList,getContext(),"popular");
            popular_recyclerView.setAdapter(cardAdapter);
        }

        else{
            cardAdapter.addInfoCardAdapter(cardInfoList);
            cardAdapter.notifyDataSetChanged();
        }

        this.page= page;
        progressBar_popular.setVisibility(View.INVISIBLE);
    }
}
