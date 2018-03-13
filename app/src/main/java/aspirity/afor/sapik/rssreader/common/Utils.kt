package aspirity.afor.sapik.rssreader.common

import android.text.format.DateFormat
import java.text.SimpleDateFormat
import java.util.*

const val DATE_MASK = "HH:mm - dd.MM.yyyy"

fun String.formatDate(mask: String): String =
        try {
            val date = SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale("eng"))
                    .parse(this)
            DateFormat.format(mask, date).toString()
        } catch (e: Exception) {
            ""
        }

fun String.formatDate(): Date =
        SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale("eng"))
                .parse(this)
