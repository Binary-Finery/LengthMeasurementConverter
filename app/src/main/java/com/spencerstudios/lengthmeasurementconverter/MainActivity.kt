package com.spencerstudios.lengthmeasurementconverter

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import java.util.*

class MainActivity : AppCompatActivity(), View.OnFocusChangeListener, TextWatcher {

    private lateinit var arrayOfEditTexts: Array<EditText>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        arrayOfEditTexts = arrayOf(etMm, etCm, etInch, etFt)

        0.until(arrayOfEditTexts.size).forEach { i ->
            arrayOfEditTexts[i].onFocusChangeListener = this
        }
        btnReset.setOnClickListener { reset() }
    }

    override fun afterTextChanged(editable: Editable?) {
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

        var input = "$s"
        if (input == "." || input == ",")
            input = "0"

        if (etMm.hasFocus()) {
            if ("$s".isNotEmpty()) {
                val v = input.toDouble()
                etCm.setText(formatValue(v / CM, etCm))
                etInch.setText(formatValue(v / INCH,etInch))
                etFt.setText(formatValue(v / FOOT,etFt))
            } else {
                etCm.setText("")
                etInch.setText("")
                etFt.setText("")
            }
        }
        if (etCm.hasFocus()) {
            if ("$s".isNotEmpty()) {
                val v = input.toDouble()
                val mm = v * CM
                etMm.setText(formatValue(mm, etMm))
                etInch.setText(formatValue(mm / INCH, etInch))
                etFt.setText(formatValue(mm / FOOT, etFt))
            } else {
                etMm.setText("")
                etInch.setText("")
                etFt.setText("")
            }
        }

        if (etInch.hasFocus()) {
            if ("$s".isNotEmpty()) {
                val v = input.toDouble()
                val mm = v * INCH
                etMm.setText(formatValue(mm, etMm))
                etCm.setText(formatValue(mm / CM, etCm))
                etFt.setText(formatValue(mm / FOOT, etFt))
            } else {
                etMm.setText("")
                etCm.setText("")
                etFt.setText("")
            }
        }
        if (etFt.hasFocus()) {
            if ("$s".isNotEmpty()) {
                val v = input.toDouble()
                val mm = v * FOOT
                etMm.setText(formatValue(mm, etMm))
                etCm.setText(formatValue(mm / CM, etCm))
                etInch.setText(formatValue(mm / INCH, etInch))
            } else {
                etMm.setText("")
                etCm.setText("")
                etInch.setText("")
            }
        }
    }

    override fun onFocusChange(v: View?, hasFocus: Boolean) {
        if (v?.hasFocus()!!) {
            arrayOfEditTexts.forEach { i ->
                if (v as EditText == i) {
                    i.addTextChangedListener(this)
                } else i.removeTextChangedListener(this)
            }
        }
    }

    private fun formatValue(value: Double, et : EditText): String {
        val stringValue = String.format(Locale.getDefault(), "%f", value)


        val doub = stringValue.toDouble()

        if(doub >= Double.MAX_VALUE){
            et.error = "number too big!"
        }

        if (doub % 1 > 0.0) {
            return if (stringValue[stringValue.length - 1] == '0') {
                removeTrailingZeros(stringValue)
            } else stringValue
        }
        return "${doub.toLong()}"
    }

    private fun removeTrailingZeros(n: String): String {
        var idx = 1
        for (i in n.length - 1 downTo 0) {
            if (n[i] == '0')
                idx++
            else break
        }
        return n.substring(0..n.length - idx)
    }

    private fun reset() {
        var x = 0
        for (i in 0 until arrayOfEditTexts.size) {
            arrayOfEditTexts[i].removeTextChangedListener(this)
            arrayOfEditTexts[i].setText("")
            if (arrayOfEditTexts[i].hasFocus()) x = i
        }
        arrayOfEditTexts[x].addTextChangedListener(this)
    }
}
