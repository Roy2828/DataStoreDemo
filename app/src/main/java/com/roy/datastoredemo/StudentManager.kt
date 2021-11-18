package com.roy.datastoredemo

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.io.IOException

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "key_data")

class StudentManager(val dataStore: DataStore<Preferences>) {


    companion object {
        val  AGE = stringPreferencesKey("age")
        val  NAME  = stringPreferencesKey("name")
    }


    suspend fun storeUser(age: String, fname: String) {
        dataStore.edit {
            it[ AGE ] = age
            it[ NAME ] = fname
        }
    }


    val studentAgeFlow: Flow<String?> = dataStore.data.map {
        it[ AGE ]
    }


    val studentNameFlow: Flow<String?> = dataStore.data.map {
        it[ NAME ]
    }



    suspend fun readAgeString(): String {
        val value = dataStore.data.catch {
            if (it is IOException) {
                it.printStackTrace()
                emit(emptyPreferences())
            } else {
                emit(emptyPreferences())
            }
        }.map { preferences ->
            preferences[ AGE ] ?: "0"
        }
        return value.first()
    }

}