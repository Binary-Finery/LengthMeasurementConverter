package com.spencerstudios.lengthmeasurementconverter.constants

import java.math.BigDecimal

const val scale : Int = 10

val MM = BigDecimal(0.0)
val CM = BigDecimal(10.0)
val INCH = BigDecimal(25.4)
val FOOT = BigDecimal(304.8)
val YD = BigDecimal(914.4)
val M = BigDecimal(1000)
val KM = BigDecimal(1000000)
val MI = BigDecimal(1609344)
val NMI = BigDecimal(1852000)

val labels = arrayOf("millimeters", "centimeters", "inches", "feet", "yards", "meters", "kilometers", "miles", "nautical miles")

val values  = arrayOf(
    MM,
    CM,
    INCH,
    FOOT,
    YD,
    M,
    KM,
    MI,
    NMI
)

