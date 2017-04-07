package com.alexanderivanets.filmie.network.popularmovies;

import android.content.Context;
import android.graphics.BitmapFactory;
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentPopular.OnFragmentPopualarInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentPopular#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentPopular extends Fragment  {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentPopualarInteractionListener mListener;

    RecyclerView popular_recyclerView;
    CardAdapter cardAdapter;
    ProgressBar progressBar_popular;

    private TMDBApi tmdbApi;
    private Retrofit retrofit;

    private MovieListPopular movieListPopular;
    private String page = "1";


    public FragmentPopular() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentPopular.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentPopular newInstance(String param1, String param2) {
        FragmentPopular fragment = new FragmentPopular();
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


            //
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_fragment_popular, container, false);
        progressBar_popular = (ProgressBar)v.findViewById(R.id.progressbar_popular);
        progressBar_popular.setVisibility(View.VISIBLE);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);


        popular_recyclerView = (RecyclerView) v.findViewById(R.id.popular_recyclerView);
        popular_recyclerView.setHasFixedSize(true);
        popular_recyclerView.setLayoutManager(llm);
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
                    GetPopularMoviesInfo getPopularMoviesInfo = new GetPopularMoviesInfo();
                    getPopularMoviesInfo.execute();
                }
            }
        });

        GetPopularMoviesInfo getPopularMoviesInfo = new GetPopularMoviesInfo();
        getPopularMoviesInfo.execute();


        return v;
    }



    public class GetPopularMoviesInfo extends AsyncTask<Void, MovieListPopular, MovieListPopular> {
        @Override
        protected MovieListPopular doInBackground(Void... voids) {
            retrofit = new Retrofit.Builder()
                    .baseUrl("https://api.themoviedb.org/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            tmdbApi = retrofit.create(TMDBApi.class);

            Response<MovieListPopular> response = null;
            try {
                response = tmdbApi.getPopularList("1cf389af1a0ead5ea09eb1849d88a44a",page).execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
            movieListPopular = response.body();
            return movieListPopular;
        }

        @Override
        protected void onPostExecute(MovieListPopular movieListPopular) {
            super.onPostExecute(movieListPopular);
            ArrayList<String> urlList = createUrlList(movieListPopular.getResults().size(),movieListPopular);

            if(cardAdapter==null) {
                cardAdapter = new CardAdapter(createList(movieListPopular.getResults().size(), movieListPopular), urlList,
                        getContext());
                popular_recyclerView.setAdapter(cardAdapter);
            }
            else{
                cardAdapter.addInfoCardAdapter(createList(movieListPopular.getResults().size(), movieListPopular), urlList);
                cardAdapter.notifyDataSetChanged();
            }


            progressBar_popular.setVisibility(View.INVISIBLE);
            int pageval = Integer.valueOf(page);
            pageval++;
            page = String.valueOf(pageval);
        }
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
        if (context instanceof OnFragmentPopualarInteractionListener) {
            mListener = (OnFragmentPopualarInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentPopualarInteractionListener");
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
    public interface OnFragmentPopualarInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction();
    }


    @Override
    public void onResume() {
        super.onResume();
        mListener.onFragmentInteraction();
    }

    //set info into card function
    public List<CardInfo> createList(int size, MovieListPopular movieListPopular) {
        List<CardInfo> list = new ArrayList<CardInfo>();

        for (int i = 0; i < size; i++) {
            CardInfo ci = new CardInfo();


            ci.setmFilmName(movieListPopular.getResults().get(i).getOriginalTitle());
            ci.setmFilmId(movieListPopular.getResults().get(i).getId().toString());

            ci.setmFilmStat(BitmapFactory.decodeResource(getResources(), R.drawable.star_notclicked));

            list.add(ci);
        }
        return list;
    }


    public ArrayList<String> createUrlList(int size, MovieListPopular movieListPopular) {

        ArrayList<String> list = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            String picturePath = "http://image.tmdb.org/t/p/w500/" + movieListPopular.getResults().get(i)
                    .getBackdropPath();

            list.add(picturePath);
        }
        return list;
    }

}
