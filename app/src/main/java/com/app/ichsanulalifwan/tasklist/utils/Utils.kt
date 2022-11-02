package com.app.ichsanulalifwan.tasklist.utils

import java.text.SimpleDateFormat
import java.util.*

object Utils {

    fun dateFormatter(date: Long): String =
        SimpleDateFormat("MM/dd/yyyy", Locale.getDefault()).format(date)
}