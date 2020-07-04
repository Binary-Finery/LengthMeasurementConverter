package com.spencerstudios.lengthmeasurementconverter.utilities

import android.content.Context
import android.content.Intent
import android.preference.PreferenceManager
import android.widget.EditText
import com.spencerstudios.lengthmeasurementconverter.constants.labels

fun share(ctx: Context, editTexts : Array<EditText>){

    val prefs = PreferenceManager.getDefaultSharedPreferences(ctx)
    val idx = prefs.getString("units", "111111111")!!
    val sb = StringBuilder()

    for(i in editTexts.indices){
        if(idx[i] == '1') sb.append(editTexts[i].text.toString()).append(" ").append(
            labels[i]).append("\n")
    }

    val i = Intent().apply {
        action = Intent.ACTION_SEND
        type = "text/plain"
        putExtra(Intent.EXTRA_TEXT, "$sb")
    }
    ctx.startActivity(Intent.createChooser(i, "share to..."))

    sb.setLength(0)
}