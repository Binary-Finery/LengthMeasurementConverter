package com.spencerstudios.lengthmeasurementconverter

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import java.math.BigDecimal
import java.math.RoundingMode.HALF_EVEN

class MainActivity : AppCompatActivity(), View.OnFocusChangeListener, TextWatcher {

    private lateinit var arrayOfEditTexts: Array<EditText>
    private var scale = 10

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
        if (input == "." || input == ",") input = "0"

        if (etMm.hasFocus()) {
            if ("$s".isNotEmpty()) {
                val v = BigDecimal(input)
                etCm.setText(formatValue(v.divide(CM, scale, HALF_EVEN)))
                etInch.setText(formatValue(v.divide(INCH, scale, HALF_EVEN)))
                etFt.setText(formatValue(v.divide(FOOT, scale, HALF_EVEN)))
            } else {
                etCm.setText("")
                etInch.setText("")
                etFt.setText("")
            }
        }
        if (etCm.hasFocus()) {
            if ("$s".isNotEmpty()) {
                val v = BigDecimal(input)
                val mm = v.multiply(CM)
                etMm.setText(formatValue(mm.setScale(scale, HALF_EVEN)))
                etInch.setText(formatValue(mm.divide(INCH,scale, HALF_EVEN)))
                etFt.setText(formatValue(mm.divide(FOOT,scale, HALF_EVEN)))
            } else {
                etMm.setText("")
                etInch.setText("")
                etFt.setText("")
            }
        }

        if (etInch.hasFocus()) {
            if ("$s".isNotEmpty()) {
                val v = BigDecimal(input)
                val mm = v.multiply(INCH)
                etMm.setText(formatValue(mm.setScale(scale, HALF_EVEN)))
                etCm.setText(formatValue(mm.divide(CM, scale, HALF_EVEN)))
                etFt.setText(formatValue(mm.divide(FOOT,scale, HALF_EVEN)))
            } else {
                etMm.setText("")
                etCm.setText("")
                etFt.setText("")
            }
        }
        if (etFt.hasFocus()) {
            if ("$s".isNotEmpty()) {
                val v = BigDecimal(input)
                val mm = v.multiply(FOOT)
                etMm.setText(formatValue(mm.setScale(scale, HALF_EVEN)))
                etCm.setText(formatValue(mm.divide(CM,scale, HALF_EVEN)))
                etInch.setText(formatValue(mm.divide(INCH,scale, HALF_EVEN)))
            } else {
                etMm.setText("")
                etCm.setText("")
                etInch.setText("")
            }
        }
    }

    override fun onFocusChange(v: View?, hasFocus: Boolean) {
        if (v?.hasFocus()!!) {
            arrayOfEditTexts.forEach { editText ->
                if (v as EditText == editText) editText.addTextChangedListener(this)
                else editText.removeTextChangedListener(this)
            }
        }
    }

    private fun formatValue(value: BigDecimal): String = value.stripTrailingZeros().toPlainString()

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
