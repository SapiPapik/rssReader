package aspirity.afor.sapik.rssreader.repository

import android.util.Log
import aspirity.afor.sapik.rssreader.model.db.RssResourceDb
import aspirity.afor.sapik.rssreader.repository.RssChannelDbRepository.Companion.db
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.rxkotlin.subscribeBy


/** Класс-репозиторий для работы со списком каналов */
class RssResourcesDbRepository {
    companion object {
        private const val TAG = "[RRSR]"

        fun add(rssResourceDb: RssResourceDb): Completable = Completable.create { subs ->
            db.insert(rssResourceDb)
                    .doOnSubscribe { Log.d(TAG, "Start inserting $rssResourceDb") }
                    .subscribeBy(
                            onSuccess = { item ->
                                Log.d(TAG, "Inserted $item")
                                subs.onComplete()
                            },
                            onError = { e ->
                                Log.d(TAG, "Error happened while inserting $rssResourceDb")
                                e.printStackTrace()
                                subs.onError(e)
                            }
                    )
        }

        fun getAll(): Observable<RssResourceDb> =
                (db.select(RssResourceDb::class)).get().observable() ?: io.reactivex.Observable.empty()

    }
}