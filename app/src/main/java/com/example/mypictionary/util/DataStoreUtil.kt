package com.example.mypictionary.util

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore

val Context.dataStore by preferencesDataStore(Keys.SETTINGS)