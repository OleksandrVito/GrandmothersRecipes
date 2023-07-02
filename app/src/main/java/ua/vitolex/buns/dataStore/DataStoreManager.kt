package ua.vitolex.buns.dataStore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("data_store")

class DataStoreManager(val context: Context) {
    suspend fun saveFavorite(favoriteRecipeTitle: String) {
        context.dataStore.edit { pref ->
            pref[stringPreferencesKey("favorite_title")] = favoriteRecipeTitle
        }
    }

    fun getFavorite() = context.dataStore.data.map { pref ->
        pref[stringPreferencesKey("favorite_title")]
    }
}