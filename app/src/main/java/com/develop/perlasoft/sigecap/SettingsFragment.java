package com.develop.perlasoft.sigecap;

import android.os.Bundle;

import com.develop.perlasoft.model.Usuarios;
import com.develop.perlasoft.utils.SPHandler;

import androidx.fragment.app.Fragment;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

public class SettingsFragment extends PreferenceFragmentCompat {

    SPHandler spHandler;
    Usuarios userLogged;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preferences,rootKey);

        spHandler = new SPHandler(getActivity());
        userLogged = spHandler.getUserDetails();

        Preference user = findPreference("user_logged");
        user.setTitle(userLogged.getNombres() +" "+userLogged.getApellidos());
    }
}
