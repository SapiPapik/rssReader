package aspirity.afor.sapik.rssreader.model.db

import android.graphics.LinearGradient
import android.os.Parcelable
import aspirity.afor.sapik.rssreader.model.News
import io.requery.*

@Entity
interface RssResourceDb: Parcelable, Persistable {
    @get:Key
    @get:Generated
    var id: Long
    var link: String
    var name: String

    @get:Nullable
    var newsIds: MutableList<Long>?
}