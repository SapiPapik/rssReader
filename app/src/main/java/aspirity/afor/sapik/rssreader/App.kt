package aspirity.afor.sapik.rssreader

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import aspirity.afor.sapik.rssreader.model.RssResource
import aspirity.afor.sapik.rssreader.model.db.Models
import io.requery.Persistable
import io.requery.android.sqlite.DatabaseSource
import io.requery.reactivex.KotlinReactiveEntityStore
import io.requery.sql.KotlinEntityDataStore
import io.requery.sql.TableCreationMode


class App: Application() {
    companion object {
        lateinit var instance: App
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    val isConnected: Boolean
        get() {
            val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val wifiInfo = cm.activeNetworkInfo
            return wifiInfo?.isConnected ?: false
        }

    val db: KotlinReactiveEntityStore<Persistable> by lazy {
        val source = DatabaseSource(this, Models.DEFAULT, 1)
        source.setTableCreationMode(TableCreationMode.DROP_CREATE)
        KotlinReactiveEntityStore<Persistable>(KotlinEntityDataStore(source.configuration))
    }

    var rssResource: RssResource? = null
}