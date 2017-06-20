package com.alexanderivanets.filmie;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by root on 18.05.17.
 */

public class CheckInternetAvailibiality implements Runnable {
    private Context context;
    private volatile boolean isConnected;

    public CheckInternetAvailibiality(Context context){
        this.context = context;
    }

    private boolean checkInternetState(){
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        if(activeNetwork!= null &&  activeNetwork.isConnected()){
            /*
            try {
                URL url = new URL("http://www.google.com/");
                HttpURLConnection urlcon = (HttpURLConnection)url.openConnection();
                urlcon.setRequestProperty("User-Agent","test");
                urlcon.setRequestProperty("Connection","close");
                urlcon.setConnectTimeout(1000);
                urlcon.connect();

                if(urlcon.getResponseCode()==200){
                    return true;
                }
                else{
                    return false;
                }
            } catch (IOException e) {
                Log.v("warning", "Error checking internet connection", e);
                return false;
            }
            */
            return true;
        }

        return false;
    }

    @Override
    public void run() {
        isConnected = checkInternetState();
    }

    public boolean getState(){
        return isConnected;
    }
}
