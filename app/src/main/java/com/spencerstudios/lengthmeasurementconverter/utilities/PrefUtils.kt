package com.spencerstudios.lengthmeasurementconverter.utilities

import android.content.Context
import android.preference.PreferenceManager

class PrefUtils(c : Context) {

    private val prefs = PreferenceManager.getDefaultSharedPreferences(c)

    fun getUnits() : String{
        return prefs.getString("units", "111111111")!!
    }

    fun setUnits(s : String){
        prefs.edit().putString("units", s).apply()
    }

}