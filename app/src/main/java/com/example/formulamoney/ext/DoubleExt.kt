package com.example.formulamoney.ext

import java.text.DecimalFormat

fun Double.toFormattedStringCurrency(): String {
    val format = DecimalFormat("#,###.00")
    format.isDecimalSeparatorAlwaysShown = true

    return format.format(this).toString()
}

fun Double.toFormattedTransactionItem(): String {
    val format = DecimalFormat("####.00")
    format.isDecimalSeparatorAlwaysShown = true

    return format.format(this).toString()
}