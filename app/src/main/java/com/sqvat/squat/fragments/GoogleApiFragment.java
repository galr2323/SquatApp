package com.sqvat.squat.fragments;

import android.app.Fragment;
import android.os.Bundle;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.games.Games;

/**
 * Created by GAL on 2/12/2015.
 */
public class GoogleApiFragment extends Fragment {
    public GoogleApiClient googleApiClient;
    public GoogleApiFragment() {
        super();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.googleApiClient = new GoogleApiClient.Builder(getActivity())
                .addApi(Games.API).addScope(Games.SCOPE_GAMES)
                        // add other APIs and scopes here as needed
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();
        googleApiClient.connect();
    }

    @Override
    public void onStop() {
        super.onStop();
        googleApiClient.disconnect();
    }
}
