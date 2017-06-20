package com.alexanderivanets.filmie;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;

/**
 * Created by root on 22.05.17.
 */

public class InfoDownloadDialogFragment extends DialogFragment {

    public  InfoDownloadDialogFragment newInstance() {

        Bundle args = new Bundle();
        InfoDownloadDialogFragment fragment = new InfoDownloadDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new ProgressDialog.Builder(getActivity())
                .setTitle("Loading info")
                .setMessage("wait till data is downloaded")
                .create();
    }
}
