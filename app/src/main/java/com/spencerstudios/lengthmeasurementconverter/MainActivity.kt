package com.spencerstudios.lengthmeasurementconverter

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import java.math.BigDecimal
import java.math.RoundingMode.HALF_EVEN

class MainActivity : AppCompatActivity(), View.OnFocusChangeListener, TextWatcher {

    private lateinit var editTexts: Array<EditText>
    private lateinit var arrayOfTextInputLayouts: Array<TextInputLayout>
    private var focusedEditText = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        editTexts = arrayOf(etMm, etCm, etInch, etFt, etYd, etM)
        arrayOfTextInputLayouts = arrayOf(tilMm, tilCm, tilIn, tilFt, tilYd, tilM)

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
        } else for (i in 0 until editTexts.size) if (i != idx) editTexts[i].setText("")
    }

    override fun afterTextChanged(editable: Editable?) {}
    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        calc(focusedEditText, "$s", if("$s" == "."  || "$s" == ",") "0" else "$s")
    }

    override fun onFocusChange(v: View?, hasFocus: Boolean) {
        if (v?.hasFocus()!!) {
            editTexts.forEach {
                if (v as EditText == it) {
                    it.addTextChangedListener(this)
                    focusedEditText = editTexts.indexOf(it)
                }
                else it.removeTextChangedListener(this)
            }
        }
    }

    private fun reset() {
        editTexts.forEach {
            it.removeTextChangedListener(this)
            it.setText("")
        }
        editTexts[focusedEditText].addTextChangedListener(this)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> {
                DialogFactory(this).unitDialog()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun displayUnits() {
        val prefs = PrefUtils(this).getUnits()
        for (i in 0 until prefs.length) {
            val show: Boolean = prefs[i] == '1'
            editTexts[i].visibility = if (show) View.VISIBLE else View.GONE
            arrayOfTextInputLayouts[i].visibility = if (show) View.VISIBLE else View.GONE
        }
    }
}
