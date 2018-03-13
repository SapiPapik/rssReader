package aspirity.afor.sapik.rssreader.interactor

import aspirity.afor.sapik.rssreader.model.RssResource
import aspirity.afor.sapik.rssreader.model.db.RssResourceDb
import aspirity.afor.sapik.rssreader.repository.RssChannelApiRepository
import aspirity.afor.sapik.rssreader.repository.RssResourcesDbRepository
import aspirity.afor.sapik.rssreader.repository.mapper.toRssResourceApp
import aspirity.afor.sapik.rssreader.repository.mapper.toRssResourceDb
import io.reactivex.Completable
import io.reactivex.rxkotlin.subscribeBy

class RssResourceInteractor {
    companion object {

        fun getRssResources() = RssResourcesDbRepository.getAll().map(RssResourceDb::toRssResourceApp)

        fun checkResource(link: String): Completable = Completable.create { subs ->
            RssChannelApiRepository.getData(link)
                    .subscribeBy(
                            onComplete = { subs.onComplete() },
                            onError = { subs.onError(Exception()) }
                    )
        }

        fun addResource(rrsResource: RssResource): Completable =
                RssResourcesDbRepository.add(rrsResource.toRssResourceDb())
    }
}