package edu.farmingdale.datastoresimplestoredemo

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import edu.farmingdale.datastoresimplestoredemo.data.AppPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.io.FileOutputStream
import java.io.PrintWriter

class AppStorage (
    private val context: Context
) {
    //singleton - can create one instance of an object - used often in programming - boiler plare
    //companion object can&cannot have a name - no name - referring to AppStorage
    companion object{
        private val Context.dataStore by
        preferencesDataStore(name = "app_preferences")

        private object PreferencesKeys {
            val USERNAME = stringPreferencesKey("user_name")
            val HIGHSCORE = intPreferencesKey("high_score")
            val DARK_MODE = booleanPreferencesKey("dark_mode")
        }
    }

    //class 311 -flow is the stream(java) of data
        //we have hte usernam, [async], highscore then the darkmode
        //can deal data 2 ways, live data or flow
    val appPreferenceFlow: Flow<AppPreferences> = context.dataStore.data
        //map -
        .map { preferences ->
            val userName = preferences[PreferencesKeys.USERNAME] ?: ""
            val highScore = preferences[PreferencesKeys.HIGHSCORE] ?: 0
            val darkMode = preferences[PreferencesKeys.DARK_MODE] ?: false
            AppPreferences(userName, highScore, darkMode)
        }

    //because we are dealing with multi threading
    suspend fun saveUsername(username: String) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.USERNAME] = username
        }
    }
    suspend fun saveHighScore(highscore: Int) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.HIGHSCORE] = highscore
        }
    }
    suspend fun saveDarkMode( darkmode: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.DARK_MODE] = darkmode
        }
    }


}