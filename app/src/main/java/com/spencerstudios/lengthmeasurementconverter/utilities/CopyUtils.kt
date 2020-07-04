package com.spencerstudios.lengthmeasurementconverter.utilities

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.EditText
import android.widget.Toast
import com.spencerstudios.lengthmeasurementconverter.constants.labels

fun copySingleValue(ctx : Context, editText: EditText, idx : Int){
    val value = editText.text.toString()
    if(value.isNotEmpty()){
        val text = value.plus(" ").plus(labels[idx])
        val clipboard: ClipboardManager? = ctx.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager?
        val clip = ClipData.newPlainText("copy value", text)
        clipboard?.primaryClip = clip
        Toast.makeText(ctx, "copied", Toast.LENGTH_SHORT).show()
    }
}