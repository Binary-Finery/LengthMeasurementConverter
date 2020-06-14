package com.spencerstudios.lengthmeasurementconverter

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.select_units.view.*
import java.math.BigDecimal
import java.math.RoundingMode.HALF_EVEN

class MainActivity : AppCompatActivity(), View.OnFocusChangeListener, TextWatcher {

    private lateinit var arrayOfEditTexts: Array<EditText>
    private lateinit var arrayOfValues: Array<BigDecimal>
    private lateinit var arrayOfTil : Array<TextInputLayout>
    private var scale = 10

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        arrayOfEditTexts = arrayOf(etMm, etCm, etInch, etFt, etYd, etM)
        arrayOfTil = arrayOf(tilMm, tilCm, tilIn, tilFt, tilYd, tilM)
        arrayOfValues = arrayOf(MM, CM, INCH, FOOT, YD, M)

        0.until(arrayOfEditTexts.size).forEach { i ->
            arrayOfEditTexts[i].onFocusChangeListener = this
        }

        btnReset.setOnClickListener { reset() }

        displayUnits()
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
                etYd.setText(formatValue(v.divide(YD, scale, HALF_EVEN)))
                etM.setText(formatValue(v.divide(M, scale, HALF_EVEN)))
            } else {
                etCm.setText("")
                etInch.setText("")
                etFt.setText("")
                etYd.setText("")
                etM.setText("")
            }
        }
        if (etCm.hasFocus()) {
            if ("$s".isNotEmpty()) {
                val v = BigDecimal(input)
                val mm = v.multiply(CM)
                etMm.setText(formatValue(mm.setScale(scale, HALF_EVEN)))
                etInch.setText(formatValue(mm.divide(INCH, scale, HALF_EVEN)))
                etFt.setText(formatValue(mm.divide(FOOT, scale, HALF_EVEN)))
                etYd.setText(formatValue(mm.divide(YD, scale, HALF_EVEN)))
                etM.setText(formatValue(mm.divide(M, scale, HALF_EVEN)))
            } else {
                etMm.setText("")
                etInch.setText("")
                etFt.setText("")
                etYd.setText("")
                etM.setText("")
            }
        }

        if (etInch.hasFocus()) {
            if ("$s".isNotEmpty()) {
                val v = BigDecimal(input)
                val mm = v.multiply(INCH)
                etMm.setText(formatValue(mm.setScale(scale, HALF_EVEN)))
                etCm.setText(formatValue(mm.divide(CM, scale, HALF_EVEN)))
                etFt.setText(formatValue(mm.divide(FOOT, scale, HALF_EVEN)))
                etYd.setText(formatValue(mm.divide(YD, scale, HALF_EVEN)))
                etM.setText(formatValue(mm.divide(M, scale, HALF_EVEN)))
            } else {
                etMm.setText("")
                etCm.setText("")
                etFt.setText("")
                etYd.setText("")
                etM.setText("")
            }
        }
        if (etFt.hasFocus()) {
            if ("$s".isNotEmpty()) {
                val v = BigDecimal(input)
                val mm = v.multiply(FOOT)
                etMm.setText(formatValue(mm.setScale(scale, HALF_EVEN)))
                etCm.setText(formatValue(mm.divide(CM, scale, HALF_EVEN)))
                etInch.setText(formatValue(mm.divide(INCH, scale, HALF_EVEN)))
                etYd.setText(formatValue(mm.divide(YD, scale, HALF_EVEN)))
                etM.setText(formatValue(mm.divide(M, scale, HALF_EVEN)))
            } else {
                etMm.setText("")
                etCm.setText("")
                etInch.setText("")
                etYd.setText("")
                etM.setText("")
            }
        }

        if (etYd.hasFocus()) {
            if ("$s".isNotEmpty()) {
                val v = BigDecimal(input)
                val mm = v.multiply(YD)
                etMm.setText(formatValue(mm.setScale(scale, HALF_EVEN)))
                etCm.setText(formatValue(mm.divide(CM, scale, HALF_EVEN)))
                etInch.setText(formatValue(mm.divide(INCH, scale, HALF_EVEN)))
                etFt.setText(formatValue(mm.divide(FOOT, scale, HALF_EVEN)))
                etM.setText(formatValue(mm.divide(M, scale, HALF_EVEN)))
            } else {
                etMm.setText("")
                etCm.setText("")
                etInch.setText("")
                etFt.setText("")
                etM.setText("")
            }
        }

        if (etM.hasFocus()) {
            if ("$s".isNotEmpty()) {
                val v = BigDecimal(input)
                val mm = v.multiply(M)
                etMm.setText(formatValue(mm.setScale(scale, HALF_EVEN)))
                etCm.setText(formatValue(mm.divide(CM, scale, HALF_EVEN)))
                etInch.setText(formatValue(mm.divide(INCH, scale, HALF_EVEN)))
                etFt.setText(formatValue(mm.divide(FOOT, scale, HALF_EVEN)))
                etYd.setText(formatValue(mm.divide(YD, scale, HALF_EVEN)))
            } else {
                etMm.setText("")
                etCm.setText("")
                etInch.setText("")
                etFt.setText("")
                etYd.setText("")
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

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> {
                unitDialog()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    @SuppressLint("InflateParams")
    private fun unitDialog() {
        val dialog = AlertDialog.Builder(this)
        dialog.setTitle("Units")
        val v = LayoutInflater.from(this).inflate(R.layout.select_units, null)
        val units = arrayOf(v.cb_mm, v.cb_cm, v.cb_inch, v.cb_ft, v.cb_yd, v.cb_m)
        dialog.setView(v)

        val prefs = PrefUtils(this@MainActivity).getUnits()
        for (i in 0 until prefs.length) {
            units[i].isChecked = prefs[i] == '1'
        }

        dialog.setNegativeButton("cancel") { d, _ ->
            d.dismiss()
        }

        dialog.setPositiveButton("apply") { d, _ ->

            var qty = 0

            units.forEach {
                if (it.isChecked) qty++
            }

            if (qty >= 2) {
                val sb = StringBuilder()
                units.forEach {
                    if (it.isChecked) sb.append("1") else sb.append("0")
                }
                PrefUtils(this@MainActivity).setUnits("$sb")
                sb.setLength(0)
                displayUnits()
                d.dismiss()
            } else {
                infoDialog()
                d.dismiss()
            }
        }
        dialog.create().show()
    }

    private fun displayUnits() {
        val prefs = PrefUtils(this).getUnits()
        for (i in 0 until prefs.length) {
            val show : Boolean = prefs[i] == '1'
            arrayOfEditTexts[i].visibility = if (show) View.VISIBLE else View.GONE
            arrayOfTil[i].visibility = if (show) View.VISIBLE else View.GONE
        }
    }

    private fun infoDialog(){
        AlertDialog.Builder(this).apply {
            setTitle("For Your Info")
            setMessage("you must select a minimum of two units")
            setPositiveButton("select units"){d,_->
                unitDialog()
                d.dismiss()
            }
            setNegativeButton("cancel"){d,_->
                d.dismiss()
            }
            create().show()
        }
    }
}
