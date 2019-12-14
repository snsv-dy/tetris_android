package app.tets.Preferences;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import app.tets.R;

/**
 * Created by Jacek on 2018-09-19.
 */

public class PreferencesFragment extends PreferenceFragment {
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.preferences);
    }
}
