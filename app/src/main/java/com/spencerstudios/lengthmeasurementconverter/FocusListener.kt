package com.spencerstudios.lengthmeasurementconverter

import android.view.View
import android.widget.EditText

class FocusListener(private val arrayOfEditTexts : Array<EditText>) : View.OnFocusChangeListener {
    override fun onFocusChange(v: View?, hasFocus: Boolean) {
        if (v?.hasFocus()!!) arrayOfEditTexts.forEach { it.setText("") }
    }
}