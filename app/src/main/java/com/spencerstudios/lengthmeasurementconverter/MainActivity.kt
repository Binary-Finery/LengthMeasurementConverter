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

    private lateinit var editTexts: Array<EditText>
    private lateinit var values: Array<BigDecimal>
    private lateinit var arrayOfTextInputLayouts: Array<TextInputLayout>
    private val scale: Int = 10

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        editTexts = arrayOf(etMm, etCm, etInch, etFt, etYd, etM)
        arrayOfTextInputLayouts = arrayOf(tilMm, tilCm, tilIn, tilFt, tilYd, tilM)
        values = arrayOf(MM, CM, INCH, FOOT, YD, M)

        editTexts.forEach { it.onFocusChangeListener = this }
        btnReset.setOnClickListener { reset() }
        displayUnits()
    }

    private fun calc(idx: Int, s: String, input: String) {
        if (s.isNotEmpty()) {
            val mm: BigDecimal = if (idx == 0) BigDecimal(input)
            else BigDecimal(input).multiply(values[idx])

            for (i in 0 until editTexts.size) {
                if (i == idx) continue
                if (i == 0) editTexts[i].setText(mm.setScale(scale, HALF_EVEN).stripTrailingZeros().toPlainString())
                else editTexts[i].setText(mm.divide(values[i], scale, HALF_EVEN).stripTrailingZeros().toPlainString())
            }
        } else for (i in 0 until editTexts.size)
            if (i != idx) editTexts[i].setText("")
    }

    override fun afterTextChanged(editable: Editable?) {}
    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        var input = "$s"
        if (input == "." || input == ",") input = "0"
        for (i in 0 until editTexts.size) {
            if (editTexts[i].hasFocus()) {
                calc(i, "$s", input)
                break
            }
        }
    }

    override fun onFocusChange(v: View?, hasFocus: Boolean) {
        if (v?.hasFocus()!!) {
            editTexts.forEach {
                if (v as EditText == it) it.addTextChangedListener(this)
                else it.removeTextChangedListener(this)
            }
        }
    }

    private fun reset() {
        var x = 0
        (0 until editTexts.size).forEach { i ->
            editTexts[i].removeTextChangedListener(this)
            editTexts[i].setText("")
            if (editTexts[i].hasFocus()) x = i
        }
        editTexts[x].addTextChangedListener(this)
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
        val v = LayoutInflater.from(this).inflate(R.layout.select_units, null)
        val units = arrayOf(v.cb_mm, v.cb_cm, v.cb_inch, v.cb_ft, v.cb_yd, v.cb_m)
        val prefs = PrefUtils(this@MainActivity).getUnits()

        for (i in 0 until prefs.length) {
            units[i].isChecked = prefs[i] == '1'
        }

        AlertDialog.Builder(this).apply {
            setTitle("Units")
            setView(v)
            setNegativeButton("cancel") { d, _ -> d.dismiss() }
            setPositiveButton("apply") { _, _ ->
                var qty = 0
                units.forEach { if (it.isChecked) qty++ }
                if (qty >= 2) {
                    val sb = StringBuilder()
                    units.forEach {
                        if (it.isChecked) sb.append("1") else sb.append("0")
                    }
                    PrefUtils(this@MainActivity).setUnits("$sb")
                    sb.setLength(0)
                    displayUnits()
                } else infoDialog()
            }
            create().show()
        }
    }

    private fun displayUnits() {
        val prefs = PrefUtils(this).getUnits()
        for (i in 0 until prefs.length) {
            val show: Boolean = prefs[i] == '1'
            editTexts[i].visibility = if (show) View.VISIBLE else View.GONE
            arrayOfTextInputLayouts[i].visibility = if (show) View.VISIBLE else View.GONE
        }
    }

    private fun infoDialog() {
        AlertDialog.Builder(this).apply {
            setTitle("For Your Info")
            setMessage("you must select a minimum of two units")
            setPositiveButton("select units") { d, _ ->
                unitDialog()
                d.dismiss()
            }
            setNegativeButton("cancel") { d, _ ->
                d.dismiss()
            }
            create().show()
        }
    }
}
