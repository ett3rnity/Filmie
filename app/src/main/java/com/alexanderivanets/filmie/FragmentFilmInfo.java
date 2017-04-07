package com.alexanderivanets.filmie;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alexanderivanets.filmie.network.TMDBApi;
import com.alexanderivanets.filmie.network.selectedmovie.SelectedFilmInfo;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentFilmInfo.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentFilmInfo#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentFilmInfo extends Fragment implements YouTubePlayer.OnInitializedListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    String filmId;
    TextView tv_filminfo_filmid;
    RecyclerView filminfo_recyclerview;
    LinearLayoutManager llm_filminfo;
    FilmBlockAdapter filmBlockAdapter;

    private TMDBApi tmdbApi;
    private Retrofit retrofit;

    private SelectedFilmInfo selectedFilmInfo;

    YouTubePlayerSupportFragment youTubePlayerSupportFragment;
    private YouTubePlayer.OnInitializedListener onInitializedListener;
    String videoId;


    public FragmentFilmInfo() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentFilmInfo.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentFilmInfo newInstance(String param1, String param2) {
        FragmentFilmInfo fragment = new FragmentFilmInfo();
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
        View v = inflater.inflate(R.layout.fragment_fragment_film_info, container, false);

        Bundle bundle = this.getArguments();
        filmId = bundle.getString("id");
        llm_filminfo = new LinearLayoutManager(getActivity());
        llm_filminfo.setOrientation(LinearLayoutManager.VERTICAL);

        filminfo_recyclerview = (RecyclerView)v.findViewById(R.id.filminfo_recyclerview);
        filminfo_recyclerview.setLayoutManager(llm_filminfo);
        filminfo_recyclerview.setNestedScrollingEnabled(false);

        filminfo_recyclerview.setAdapter(new RecyclerView.Adapter() {
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



        GetAllFilmInfo getAllFilmInfo = new GetAllFilmInfo();
        getAllFilmInfo.execute();

        onInitializedListener = this;

        return v;
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
        Random random = new Random();
        int cases = random.nextInt(4);
        switch (cases){
            case 1:
                youTubePlayer.cueVideo("5aopMm7UGYA");
                break;
            case 2:
                youTubePlayer.cueVideo("TCCJOTY7uRI");
                break;
            case 3:
                youTubePlayer.cueVideo("TcLxlkc2pA");
                break;
            case 4:
                youTubePlayer.cueVideo("e097tSVkaQY");
                break;
        }
        //youTubePlayer.cueVideo("5aopMm7UGYA");//"TCCJOTY7uRI","TcLxlkc2pA","e097tSVkaQY"

    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        Toast.makeText(getActivity(),"fail", Toast.LENGTH_SHORT).show();
    }

    public class GetAllFilmInfo extends AsyncTask<Void, SelectedFilmInfo, SelectedFilmInfo> {

        @Override
        protected SelectedFilmInfo doInBackground(Void... params) {
            Retrofit retrofit = new Retrofit.Builder().baseUrl("https://api.themoviedb.org/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            tmdbApi = retrofit.create(TMDBApi.class);
            Response<SelectedFilmInfo> response = null;

            try {
                response = tmdbApi.getSelectedFilmInfo(filmId,"1cf389af1a0ead5ea09eb1849d88a44a").execute();

            } catch (IOException e) {
                e.printStackTrace();
            }
            selectedFilmInfo =response.body();

            return selectedFilmInfo;
        }

        @Override
        protected void onPostExecute(SelectedFilmInfo selectedFilmInfo) {
            super.onPostExecute(selectedFilmInfo);
            ArrayList<FilmBlock> list = createFilmBlockList(selectedFilmInfo);
            filmBlockAdapter = new FilmBlockAdapter(list, getContext());
            filminfo_recyclerview.setAdapter(filmBlockAdapter);

            CollapsingToolbarLayout collapsingToolbar =
                    (CollapsingToolbarLayout) getActivity().findViewById(R.id.toolbar_layout);
            collapsingToolbar.setTitle(selectedFilmInfo.getTitle());
            ImageView toolbarpic = (ImageView) getActivity().findViewById(R.id.iv_filminfo_toolbarpic);
            String toolbarPicPath = "http://image.tmdb.org/t/p/w780/"+ selectedFilmInfo.getBackdropPath();
            Picasso.with(getContext()).load(toolbarPicPath).fit().into(toolbarpic);

            //videoId = SelectedFilmInfo.
            //youTubePlayerSupportFragment = new YouTubePlayerSupportFragment();

            youTubePlayerSupportFragment.initialize(Config.YOUTUBE_API_KEY,onInitializedListener);
            FragmentManager fm = getFragmentManager();
            fm.beginTransaction().replace(R.id.frame_youtube,youTubePlayerSupportFragment).commit();
        }
    }

    public ArrayList<FilmBlock> createFilmBlockList(SelectedFilmInfo selectedFilmInfo){
        ArrayList<FilmBlock> list = new ArrayList<>();
        FilmBlock filmBlock0 = new FilmBlock();
        FilmBlock filmBlock1 = new FilmBlock();
        FilmBlock filmBlock2 = new FilmBlock();
        FilmBlock filmBlock3 = new FilmBlock();
        FilmBlock filmBlock4 = new FilmBlock();
        FilmBlock filmBlock5 = new FilmBlock();
        FilmBlock filmBlock6 = new FilmBlock();
        FilmBlock filmBlock7 = new FilmBlock();
        FilmBlock filmBlock8 = new FilmBlock();
        FilmBlock filmBlock9 = new FilmBlock();


            filmBlock0.setmBlockHead("Title");
            filmBlock0.setmBlockBody(selectedFilmInfo.getOriginalTitle());
            list.add(filmBlock0);

            filmBlock1.setmBlockHead("Genres");
            int genresSize = selectedFilmInfo.getGenres().size();
            StringBuilder stringBuilder = new StringBuilder();
            for (int i=0;i<genresSize; i++){
                stringBuilder.append(selectedFilmInfo.getGenres().get(i).getName());
                if(i!=genresSize-1){
                    stringBuilder.append(",");
                }
            }

            filmBlock1.setmBlockBody(stringBuilder.toString());
            list.add(filmBlock1);

            filmBlock2.setmBlockHead("Overview");
            filmBlock2.setmBlockBody(selectedFilmInfo.getOverview());
            list.add(filmBlock2);

            filmBlock3.setmBlockHead("Duration");
            stringBuilder.delete(0,stringBuilder.length());
            stringBuilder.append(selectedFilmInfo.getRuntime().toString());
            stringBuilder.append(" minutes");
            filmBlock3.setmBlockBody(stringBuilder.toString());

            list.add(filmBlock3);

            filmBlock4.setmBlockHead("Release Date");
            filmBlock4.setmBlockBody(selectedFilmInfo.getReleaseDate());
            list.add(filmBlock4);



        return list;
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
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
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
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction();
    }
}
