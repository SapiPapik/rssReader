package aspirity.afor.sapik.rssreader.model.db

import android.os.Parcelable
import aspirity.afor.sapik.rssreader.model.RssResource
import io.requery.*

@Entity
interface NewsDb: Parcelable, Persistable {
    @get:Key
    @get:Generated
    val id: Long
    var title: String
    var link: String
    var description: String
    var pubDate: String

    var rssResourceId: Long
}