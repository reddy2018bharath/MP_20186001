package com.example.android.sunshine;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

/**
 * Loads the SettingsFragment and handles the proper behavior of the up button.
 */
public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_settings);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // done (2) Create an xml resource directory
        // done (3) Add a PreferenceScreen with an EditTextPreference and ListPreference within the newly created xml resource directory

        // done (4) Create SettingsFragment and extend PreferenceFragmentCompat

        // Do steps 5 - 11 within SettingsFragment
        // done (10) Implement OnSharedPreferenceChangeListener from SettingsFragment

        // done (8) Create a method called setPreferenceSummary that accepts a Preference and an Object and sets the summary of the preference

        // done (5) Override onCreatePreferences and add the preference xml file using addPreferencesFromResource

        // Do step 9 within onCreatePreference
        // done (9) Set the preference summary on each preference that isn't a CheckBoxPreference

        // done (13) Unregister SettingsFragment (this) as a SharedPreferenceChangedListener in onStop

        // done (12) Register SettingsFragment (this) as a SharedPreferenceChangedListener in onStart

        // done (11) Override onSharedPreferenceChanged to update non CheckBoxPreferences when they are changed
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }
}



