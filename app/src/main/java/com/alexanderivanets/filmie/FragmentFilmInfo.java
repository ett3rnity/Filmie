package com.alexanderivanets.filmie;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alexanderivanets.filmie.MVPAttempt.api.TMDBApi;
import com.alexanderivanets.filmie.network.selectedmovie.Genre;
import com.alexanderivanets.filmie.network.selectedmovie.SelectedFilmInfo;
import com.alexanderivanets.filmie.network.youtuberesponse.YouTubeResponse;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

    boolean isIntoDb;
    ImageView toolbarpic;
    String filmId;
    TextView tv_filminfo_filmid;
    RecyclerView filminfo_recyclerview;
    LinearLayoutManager llm_filminfo;
    FilmBlockAdapter filmBlockAdapter;

    private TMDBApi tmdbApi;
    private  TMDBApi tmdbApi1;
    private Retrofit retrofit;

    private SelectedFilmInfo selectedFilmInfo;
    private YouTubeResponse youTubeResponse;

    YouTubePlayerSupportFragment youTubePlayerSupportFragment;
    private YouTubePlayer.OnInitializedListener onInitializedListener;
    String videoId;

    GetAllFilmInfo getAllFilmInfo;
    AddTrailerToList addTrailerToList;

    SharedPreferences sharedPreferences;
    String mLang;



    public FragmentFilmInfo() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     *
     * @return A new instance of fragment FragmentFilmInfo.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentFilmInfo newInstance() {
        FragmentFilmInfo fragment = new FragmentFilmInfo();
        Bundle args = new Bundle();
        //args.putString(ARG_PARAM1, param1);
        //args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            //mParam1 = getArguments().getString(ARG_PARAM1);
            //mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_fragment_film_info, container, false);


        Bundle bundle = this.getArguments();
        filmId = bundle.getString("id");
        isIntoDb = bundle.getBoolean("isIntoDb");
        llm_filminfo = new LinearLayoutManager(getActivity());
        llm_filminfo.setOrientation(LinearLayoutManager.VERTICAL);

        filminfo_recyclerview = (RecyclerView)v.findViewById(R.id.filminfo_recyclerview);
        filminfo_recyclerview.setLayoutManager(llm_filminfo);
        filminfo_recyclerview.setNestedScrollingEnabled(false);


        if(getActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            filminfo_recyclerview.setLayoutManager(new GridLayoutManager(getContext(),2));
        }
        else{
            filminfo_recyclerview.setLayoutManager(new StaggeredGridLayoutManager(1,Configuration.ORIENTATION_PORTRAIT));
        }


        //try assert recyclerview !=null
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

        getSettingsPreference();


        if(!isIntoDb) {
            getAllFilmInfo = new GetAllFilmInfo();
            getAllFilmInfo.execute();

            addTrailerToList = new AddTrailerToList();
            addTrailerToList.execute();
        }
        else{
            filmIsAlreadyInDb();
        }

        onInitializedListener = this;

        return v;
    }

    void filmIsAlreadyInDb(){
        DBHelper dbHelper = new DBHelper(getContext());
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM filmList;",null);
        selectedFilmInfo = new SelectedFilmInfo();

        if(cursor != null){
            if(cursor.moveToFirst()){

                do{
                    Log.v("--IN DB--",cursor.getString(cursor.getColumnIndex("title")));
                    if(cursor.getInt(cursor.getColumnIndex("id")) == Integer.valueOf(filmId)){
                        selectedFilmInfo.setTitle(cursor.getString(cursor.getColumnIndex("title")));
                        selectedFilmInfo.setOverview(cursor.getString(cursor.getColumnIndex("overview")));
                        List<Genre>genreList = new ArrayList<>();
                        String genres = cursor.getString(cursor.getColumnIndex("genres"));
                        String[] splitGenres = genres.split(",");
                        for (String genre:splitGenres) {
                            Genre newGenre = new Genre();
                            newGenre.setName(genre);
                            genreList.add(newGenre);
                        }
                        selectedFilmInfo.setGenres(genreList);

                        selectedFilmInfo.setRuntime(Integer.valueOf(cursor.getString(cursor.getColumnIndex("duration")).replaceAll("\\D+","")));

                        selectedFilmInfo.setReleaseDate(cursor.getString(cursor.getColumnIndex("releaseDate")));

                        toolbarpic = (ImageView) getActivity().findViewById(R.id.iv_filminfo_toolbarpic);

                        File imgFile = new File(Environment.getExternalStorageDirectory()+ "/filmieDbPics/" +
                                filmId +".png");
                        Picasso.with(getContext()).load(imgFile).fit().into(toolbarpic);
                    }
                }while(cursor.moveToNext());
            }
            cursor.close();

            ArrayList<Object> list = createFilmBlockList(selectedFilmInfo);
            filmBlockAdapter = new FilmBlockAdapter(list, getContext());
            filminfo_recyclerview.setAdapter(filmBlockAdapter);
        }
    }

    void getSettingsPreference(){
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity().getBaseContext());
        mLang = sharedPreferences.getString("pref_language",null);
    }


    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {

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
                response = tmdbApi.getSelectedFilmInfo(filmId,"1cf389af1a0ead5ea09eb1849d88a44a",mLang).execute();

            } catch (IOException e) {
                e.printStackTrace();
            }
            selectedFilmInfo =response.body();

            return selectedFilmInfo;
        }

        @Override
        protected void onPostExecute(SelectedFilmInfo selectedFilmInfo) {
            super.onPostExecute(selectedFilmInfo);
            ArrayList<Object> list = createFilmBlockList(selectedFilmInfo);
            filmBlockAdapter = new FilmBlockAdapter(list, getContext());
            filminfo_recyclerview.setAdapter(filmBlockAdapter);

            CollapsingToolbarLayout collapsingToolbar =
                    (CollapsingToolbarLayout) getActivity().findViewById(R.id.toolbar_layout);

            collapsingToolbar.setTitle(selectedFilmInfo.getTitle());

            toolbarpic = (ImageView) getActivity().findViewById(R.id.iv_filminfo_toolbarpic);

            String toolbarPicPath = "http://image.tmdb.org/t/p/w780/"+ selectedFilmInfo.getBackdropPath();

            Picasso.with(getContext()).load(toolbarPicPath).fit().into(toolbarpic);

            //pls fix this hardcode for god
            ArrayList<String> listForDB = new ArrayList<>();
            for(int i=0;i<5;i++){
                FilmBlock fb = (FilmBlock) list.get(i);
                listForDB.add(fb.getmBlockBody());
            }

            mListener.onInfoDownloaded(Integer.valueOf(filmId),listForDB.get(0),
                    listForDB.get(1),listForDB.get(2),listForDB.get(3),listForDB.get(4),selectedFilmInfo.getBackdropPath());
        }
    }

    public class AddTrailerToList extends AsyncTask<Void,YouTubeResponse,YouTubeResponse>{
        @Override
        protected YouTubeResponse doInBackground(Void... params) {
            Retrofit retrofit = new Retrofit.Builder().baseUrl("https://api.themoviedb.org/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            tmdbApi1 = retrofit.create(TMDBApi.class);

            Response<YouTubeResponse> response2 = null;
            try {
                response2 = tmdbApi1.getTrailerInfo(filmId,"1cf389af1a0ead5ea09eb1849d88a44a").execute();

            } catch (IOException e) {
                e.printStackTrace();
            }

            return response2.body();
        }

        @Override
        protected void onPostExecute(YouTubeResponse trailersResponse) {
            super.onPostExecute(trailersResponse);
            ArrayList<Object> trailer = createYouTubeBlock(trailersResponse);
            filmBlockAdapter.addToAdapter(trailer);
            filmBlockAdapter.notifyDataSetChanged();
        }
    }


    public ArrayList<Object>createYouTubeBlock(YouTubeResponse trailersResponse){
        ArrayList<Object> list = new ArrayList<>();
        YoutubeBlock ytb1 = new YoutubeBlock();
        ytb1.setmYTHead("Trailer");
        ytb1.setmYTVideoId(trailersResponse.getResults().get(0).getKey());

        list.add(ytb1);
        return list;
    }


    public ArrayList<Object> createFilmBlockList(SelectedFilmInfo selectedFilmInfo){
        ArrayList<Object> list = new ArrayList<>();

        FilmBlock filmBlock0 = new FilmBlock();
        FilmBlock filmBlock1 = new FilmBlock();
        FilmBlock filmBlock2 = new FilmBlock();
        FilmBlock filmBlock3 = new FilmBlock();
        FilmBlock filmBlock4 = new FilmBlock();


            filmBlock0.setmBlockHead("Title");
            filmBlock0.setmBlockBody(selectedFilmInfo.getTitle());
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(!isIntoDb) {
            getAllFilmInfo.cancel(true);
            addTrailerToList.cancel(true);
        }
        else{

        }
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

        void onInfoDownloaded(int mId, String mTitle, String mGenres, String mOverview, String mDuration,
                              String mReleaseDate,String backDropPath);
    }


}
