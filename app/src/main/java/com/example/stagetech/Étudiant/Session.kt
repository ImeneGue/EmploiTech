package com.example.stagetech.Étudiant

import android.content.Context
import android.content.SharedPreferences

class Session(context : Context) {

    private val preferences: SharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    private val editor: SharedPreferences.Editor = preferences.edit()

    companion object {
        private const val PREF_NAME = "sessionEtudiant"
        private const val PRIVATE_MODE = 0
        private const val KEY_USER_ID = "idEtudiant"
    }

    var idEtudiant: Int?


        get() = preferences.getInt(KEY_USER_ID, -1).takeIf { it != -1 }
        set(value) {
            editor.putInt(KEY_USER_ID, value ?: -1)
            editor.apply()
        }

    fun fermerLaSession() {
        editor.clear()
        editor.apply()
    }
}