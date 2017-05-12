package com.alexanderivanets.filmie.MVPAttempt.settings;

import android.os.Bundle;
import android.os.Environment;
import android.preference.CheckBoxPreference;
import android.preference.ListPreference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;

import com.alexanderivanets.filmie.R;

import java.io.File;

/**
 * Created by root on 01.05.17.
 */

public class SettingsActivity extends PreferenceActivity {

    ListPreference pref_language;
    ListPreference pref_poster_quality;
    CheckBoxPreference pref_adult;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preference);


        pref_language = (ListPreference)findPreference("pref_language");
        pref_poster_quality = (ListPreference)findPreference("pref_poster_quality");
        pref_adult = (CheckBoxPreference)findPreference("pref_adult");

        if(pref_language.getValue()==null){
            setDefaultLang();
        }

        if(pref_poster_quality.getValue()==null){
            setDefaultPosterQuality();
        }

        checkExistingDirectory();

    }

    public void setDefaultLang(){
        String defaultLang = "en-US";
        pref_language.setValue(defaultLang);
        //pref_language.setDefaultValue(defaultLang);
    }

    public void setDefaultPosterQuality(){
        String defaultQuality = "w500/";
        pref_poster_quality.setValue(defaultQuality);
    }

    public void checkExistingDirectory() {
        File myDirectory = new File(Environment.getExternalStorageDirectory() + "/tempDirForFilmie");
        if(!myDirectory.exists()){
            myDirectory.mkdirs();
        }
    }

}
