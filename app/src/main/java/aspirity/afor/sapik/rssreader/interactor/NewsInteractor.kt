package aspirity.afor.sapik.rssreader.interactor

import aspirity.afor.sapik.rssreader.App
import aspirity.afor.sapik.rssreader.model.News
import aspirity.afor.sapik.rssreader.model.RssResource
import aspirity.afor.sapik.rssreader.model.db.NewsDb
import aspirity.afor.sapik.rssreader.repository.RssChannelApiRepository
import aspirity.afor.sapik.rssreader.repository.RssChannelDbRepository
import aspirity.afor.sapik.rssreader.repository.mapper.toNewsApp
import aspirity.afor.sapik.rssreader.repository.mapper.toNewsDb
import aspirity.afor.sapik.rssreader.repository.mapper.toRssResourceDb
import io.reactivex.Completable
import io.reactivex.Observable

class NewsInteractor {
    companion object {
        fun getNews(idRes: Long?): Observable<News> {
            val apiRequest = RssChannelApiRepository.getData(App.instance.rssResource?.link)
                    .doOnNext(this::saveToDb)
            val dbRequest = RssChannelDbRepository.getAll(idRes).map(NewsDb::toNewsApp)

            return apiRequest.onErrorResumeNext(dbRequest)
        }

        private fun saveToDb(news: News) {
            //TODO: badstyle
            news.toNewsDb(App.instance.rssResource?.toRssResourceDb()!!).let { RssChannelDbRepository.add(it) }
        }
    }

}