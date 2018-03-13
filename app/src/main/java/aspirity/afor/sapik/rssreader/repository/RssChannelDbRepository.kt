package aspirity.afor.sapik.rssreader.repository

import android.util.Log
import aspirity.afor.sapik.rssreader.App
import aspirity.afor.sapik.rssreader.model.db.NewsDb
import io.reactivex.Observable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.rxkotlin.toSingle
import io.requery.Persistable
import io.requery.kotlin.eq
import io.requery.reactivex.KotlinReactiveEntityStore


class RssChannelDbRepository(private val db: KotlinReactiveEntityStore<Persistable>) {
    companion object {
        const val TAG = "[LDR]"
        val db = App.instance.db

        fun add(newsDb: NewsDb) {
            db.upsert(newsDb)
                    .doOnSubscribe { Log.d(TAG, "Start inserting $newsDb") }
                    .subscribeBy(
                            onSuccess = { item ->
                                Log.d(TAG, "Inserted $item")
                            },
                            onError = { e ->
                                Log.d(TAG, "Error happened while inserting $newsDb")
                                e.printStackTrace()
                            }
                    )
        }

        fun getAll(resId: Long?): Observable<NewsDb> =
                (db.select(NewsDb::class) where (NewsDb::rssResourceId eq resId)).get()
                        .observable() ?: io.reactivex.Observable.empty()

    }
}