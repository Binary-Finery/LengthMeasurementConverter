package com.spencerstudios.lengthmeasurementconverter

import java.math.BigDecimal

const val scale : Int = 10

val MM = BigDecimal(0.0)
val CM = BigDecimal(10.0)
val INCH = BigDecimal(25.4)
val FOOT = BigDecimal(304.8)
val YD = BigDecimal(914.4)
val M = BigDecimal(1000)

val values = arrayOf(MM, CM, INCH, FOOT, YD, M)

