package aspirity.afor.sapik.rssreader.model

import aspirity.afor.sapik.rssreader.common.formatDate
import com.google.gson.annotations.SerializedName
import java.util.*

data class News(
        var id: Long? = null,
        var title: String,
        var link: String,
        var description: String,
        var pubDate: String,
        var rssResourceId: Long? = null
) {
    val publishedDate: Date
        get() = pubDate.formatDate()
}