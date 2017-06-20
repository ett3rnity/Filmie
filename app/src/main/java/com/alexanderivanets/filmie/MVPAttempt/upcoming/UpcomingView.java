package com.alexanderivanets.filmie.MVPAttempt.upcoming;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.alexanderivanets.filmie.CardAdapter;
import com.alexanderivanets.filmie.MVPAttempt.CardInfo;
import com.alexanderivanets.filmie.MVPAttempt.popular.PopularMVP;
import com.alexanderivanets.filmie.R;

import java.util.List;


public class UpcomingView extends Fragment  implements UpcomingMVP.VtoPInterface{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    UpcomingMVP.PInterface pInterfaceImpl;

    RecyclerView upcoming_recyclerView;
    CardAdapter cardAdapter;
    ProgressBar progressBar_upcoming;

    private String page = "1";
    SharedPreferences sharedPreferences;
    private  String mLang;


    public UpcomingView() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UpcomingView.
     */
    // TODO: Rename and change types and number of parameters
    public static UpcomingView newInstance(String param1, String param2) {
        UpcomingView fragment = new UpcomingView();
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
        View v = inflater.inflate(R.layout.fragment_fragment_upcoming, container, false);
        progressBar_upcoming = (ProgressBar)v.findViewById(R.id.progressbar_upcoming);
        progressBar_upcoming.setVisibility(View.VISIBLE);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);


        upcoming_recyclerView = (RecyclerView) v.findViewById(R.id.upcoming_recyclerView);
        upcoming_recyclerView.setHasFixedSize(true);
        upcoming_recyclerView.setLayoutManager(llm);

        upcoming_recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
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

                    pInterfaceImpl.onGetInfo(page,mLang);
                }
            }
        });

        getSettingsPreference();

        pInterfaceImpl = new UpcomingPresenter(this);

        pInterfaceImpl.onGetInfo(page,mLang);

        return v;
    }

    void getSettingsPreference(){
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity().getBaseContext());
        mLang = sharedPreferences.getString("pref_language",null);
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
    public void onShowNewInfo(List<CardInfo> cardInfoList, String page) {
        if(cardAdapter == null){
            cardAdapter = new CardAdapter(cardInfoList,getContext(),"upcoming");
            upcoming_recyclerView.setAdapter(cardAdapter);
        }

        else{
            cardAdapter.addInfoCardAdapter(cardInfoList);
            cardAdapter.notifyDataSetChanged();
        }

        this.page= page;
        progressBar_upcoming.setVisibility(View.INVISIBLE);
    }
}
