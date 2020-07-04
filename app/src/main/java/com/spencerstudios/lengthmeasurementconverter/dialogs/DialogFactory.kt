package com.spencerstudios.lengthmeasurementconverter.dialogs

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import com.spencerstudios.lengthmeasurementconverter.R
import com.spencerstudios.lengthmeasurementconverter.activities.MainActivity
import com.spencerstudios.lengthmeasurementconverter.utilities.PrefUtils
import kotlinx.android.synthetic.main.select_units.view.*

class DialogFactory(private val ctx: Context) {

    @SuppressLint("InflateParams")
    fun unitDialog() {
        val v = LayoutInflater.from(ctx).inflate(R.layout.select_units, null)
        val units = arrayOf(v.cb_mm, v.cb_cm, v.cb_inch, v.cb_ft, v.cb_yd, v.cb_m, v.cb_km, v.cb_mi, v.cb_nmi)
        val prefs = PrefUtils(
            ctx
        ).getUnits()

        for (i in prefs.indices) {
            units[i].isChecked = prefs[i] == '1'
        }

        AlertDialog.Builder(ctx).apply {
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
                    PrefUtils(
                        ctx
                    ).setUnits("$sb")
                    val activity : MainActivity = ctx as MainActivity
                    activity.displayUnits()
                } else infoDialog()
            }
            create().show()
        }
    }

    private fun infoDialog() {
        AlertDialog.Builder(ctx).apply {
            setTitle("For Your Information")
            setMessage("you must select a minimum of two units")
            setPositiveButton("select units") { d, _ ->
                unitDialog()
                d.dismiss()
            }
            setNegativeButton("cancel") { d, _ -> d.dismiss() }
            create().show()
        }
    }
}