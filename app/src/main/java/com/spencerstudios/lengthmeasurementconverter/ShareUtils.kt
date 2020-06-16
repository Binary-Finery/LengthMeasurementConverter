package com.spencerstudios.lengthmeasurementconverter

import android.content.Context
import android.content.Intent
import android.preference.PreferenceManager
import android.widget.EditText

fun share(ctx: Context, editTexts : Array<EditText>){

    val labels = arrayOf("millimeters: ", "centimeters: ", "inches: ", "feet: ", "yards: ", "meters: ", "kilometers: ", "miles: ", "nautical miles: ")

    val prefs = PreferenceManager.getDefaultSharedPreferences(ctx)
    val idx = prefs.getString("units", "111111111")!!
    val sb = StringBuilder()

    for(i in 0 until editTexts.size){
        if(idx[i] == '1') sb.append(labels[i]).append(editTexts[i].text.toString()).append("\n")
    }

    val i = Intent().apply {
        action = Intent.ACTION_SEND
        type = "text/plain"
        putExtra(Intent.EXTRA_TEXT, "$sb")
    }
    ctx.startActivity(Intent.createChooser(i, "share to..."))

    sb.setLength(0)
}