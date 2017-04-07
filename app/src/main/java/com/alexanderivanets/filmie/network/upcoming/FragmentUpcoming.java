package com.alexanderivanets.filmie.network.upcoming;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.alexanderivanets.filmie.CardAdapter;
import com.alexanderivanets.filmie.CardInfo;
import com.alexanderivanets.filmie.R;
import com.alexanderivanets.filmie.network.TMDBApi;
import com.alexanderivanets.filmie.network.popularmovies.FragmentPopular;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentUpcoming.OnFragmentUpcomingInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentUpcoming#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentUpcoming extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    RecyclerView upcoming_recyclerView;
    CardAdapter cardAdapter;
    ProgressBar progressBar_upcoming;

    private TMDBApi tmdbApi;
    private Retrofit retrofit;

    private MovieListUpcoming movieListUpcoming;
    private String page = "1";

    private OnFragmentUpcomingInteractionListener mListener;

    public FragmentUpcoming() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentUpcoming.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentUpcoming newInstance(String param1, String param2) {
        FragmentUpcoming fragment = new FragmentUpcoming();
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
                    GetUpcomingMoviesInfo getUpcomingMoviesInfo = new GetUpcomingMoviesInfo();
                    getUpcomingMoviesInfo.execute();
                }
            }
        });



        GetUpcomingMoviesInfo getUpcomingMoviesInfo = new GetUpcomingMoviesInfo();
        getUpcomingMoviesInfo.execute();

        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed() {
        if (mListener != null) {
            mListener.onFragmentInteraction();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentUpcomingInteractionListener) {
            mListener = (OnFragmentUpcomingInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentUpcomingInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction();
    }

    public class GetUpcomingMoviesInfo extends AsyncTask<Void, MovieListUpcoming, MovieListUpcoming> {
        @Override
        protected MovieListUpcoming doInBackground(Void... voids) {
            retrofit = new Retrofit.Builder()
                    .baseUrl("https://api.themoviedb.org/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            tmdbApi = retrofit.create(TMDBApi.class);

            Response<MovieListUpcoming> response = null;
            try {
                response = tmdbApi.getUpcomingList("1cf389af1a0ead5ea09eb1849d88a44a",page).execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
            movieListUpcoming = response.body();
            return movieListUpcoming;
        }

        @Override
        protected void onPostExecute(MovieListUpcoming movieListUpcoming) {
            super.onPostExecute(movieListUpcoming);
            ArrayList<String> urlList = createUrlList(movieListUpcoming.getResults().size(),movieListUpcoming);

            if(cardAdapter==null){
            cardAdapter = new CardAdapter(createList(movieListUpcoming.getResults().size(),movieListUpcoming), urlList,
                    getContext());
                upcoming_recyclerView.setAdapter(cardAdapter);
            }
            else{
                cardAdapter.addInfoCardAdapter(createList(movieListUpcoming.getResults().size(),movieListUpcoming), urlList);
                cardAdapter.notifyDataSetChanged();
            }

            progressBar_upcoming.setVisibility(View.INVISIBLE);
        }
    }

    public List<CardInfo> createList(int size, MovieListUpcoming movieListUpcoming) {
        List<CardInfo> list = new ArrayList<CardInfo>();

        for (int i = 0; i < size; i++) {
            CardInfo ci = new CardInfo();


            ci.setmFilmName(movieListUpcoming.getResults().get(i).getOriginalTitle());
            ci.setmFilmId(movieListUpcoming.getResults().get(i).getId().toString());

            //CardAdapter adapter = (CardAdapter) main_recyclerView.getAdapter();//hzhz

            ci.setmFilmStat(BitmapFactory.decodeResource(getResources(), R.drawable.star_notclicked));

            list.add(ci);
        }
        return list;
    }


    public ArrayList<String> createUrlList(int size, MovieListUpcoming movieListUpcoming) {

        ArrayList<String> list = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            String picturePath = "http://image.tmdb.org/t/p/w500/" + movieListUpcoming.getResults().get(i)
                    .getBackdropPath();

            list.add(picturePath);
        }
        return list;
    }
}
