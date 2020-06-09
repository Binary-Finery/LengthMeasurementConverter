package com.spencerstudios.lengthmeasurementconverter

import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var arrayOfEditTexts: Array<EditText>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        arrayOfEditTexts = arrayOf(etMm, etCm, etInch, etFt)

        (0 until arrayOfEditTexts.size).forEach { i ->
            arrayOfEditTexts[i].onFocusChangeListener = FocusListener(arrayOfEditTexts)
            arrayOfEditTexts[i].setOnEditorActionListener { _, id, _ ->
                when (id) {
                    EditorInfo.IME_ACTION_DONE -> {
                        calc(arrayOfEditTexts[i], arrayOfEditTexts)
                        true
                    }
                    else -> false
                }
            }
        }
    }
}
