package com.spencerstudios.lengthmeasurementconverter

import android.widget.EditText
import java.util.*

fun calc(selected: EditText, arrayOfEditTexts : Array<EditText>) {
    val sVal = selected.text.toString()
    if(sVal.isNotEmpty()) {
        val dVal = sVal.toDouble()
        when (selected) {
            arrayOfEditTexts[EDIT_TEXT_MM] -> {
                arrayOfEditTexts[EDIT_TEXT_CM].setText(formatValue(dVal / CM))
                arrayOfEditTexts[EDIT_TEXT_INCH].setText(formatValue(dVal / INCH))
                arrayOfEditTexts[EDIT_TEXT_FOOT].setText(formatValue(dVal / FOOT))
            }
            arrayOfEditTexts[EDIT_TEXT_CM] -> {
                val mm = dVal * CM
                arrayOfEditTexts[EDIT_TEXT_MM].setText(formatValue(mm))
                arrayOfEditTexts[EDIT_TEXT_INCH].setText(formatValue(mm / INCH))
                arrayOfEditTexts[EDIT_TEXT_FOOT].setText(formatValue(mm / FOOT))
            }
            arrayOfEditTexts[EDIT_TEXT_INCH] -> {
                val mm = dVal * INCH
                arrayOfEditTexts[EDIT_TEXT_MM].setText(formatValue(mm))
                arrayOfEditTexts[EDIT_TEXT_CM].setText(formatValue(mm / CM))
                arrayOfEditTexts[EDIT_TEXT_FOOT].setText(formatValue(mm / FOOT))
            }
            arrayOfEditTexts[EDIT_TEXT_FOOT] -> {
                val mm = dVal * FOOT
                arrayOfEditTexts[EDIT_TEXT_MM].setText(formatValue(mm))
                arrayOfEditTexts[EDIT_TEXT_CM].setText(formatValue(mm / CM))
                arrayOfEditTexts[EDIT_TEXT_INCH].setText(formatValue(mm / INCH))
            }
        }
    }
}

private fun formatValue(value : Double) : String{
    val stringValue = String.format(Locale.getDefault(), "%.2f", value)
    val doub = stringValue.toDouble()
    return when {doub % 1 > 0.0 -> stringValue else -> "${doub.toLong()}" }
}