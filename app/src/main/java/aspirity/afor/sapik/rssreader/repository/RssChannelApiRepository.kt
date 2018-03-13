package aspirity.afor.sapik.rssreader.repository

import aspirity.afor.sapik.rssreader.common.getDataXml
import aspirity.afor.sapik.rssreader.model.News
import aspirity.afor.sapik.rssreader.model.Rss
import com.stanfy.gsonxml.GsonXml
import io.reactivex.Single
import org.xmlpull.v1.XmlPullParserFactory
import com.stanfy.gsonxml.GsonXmlBuilder
import com.stanfy.gsonxml.XmlParserCreator
import io.reactivex.Observable


class RssChannelApiRepository {
    companion object {
        fun getData(link: String?): Observable<News> = Observable.create { subs ->
            link?.getDataXml()?.let {
                val model = getGsonXml().fromXml<Rss>(it, Rss::class.java)
                model.channel.news.forEach(subs::onNext).let { subs.onComplete() }
            } ?: subs.onError(Exception())
        }

        private fun getGsonXml(): GsonXml {
            val parserCreator = XmlParserCreator {
                try {
                    return@XmlParserCreator XmlPullParserFactory.newInstance().newPullParser()
                } catch (e: Exception) {
                    throw RuntimeException(e)
                }
            }

            return GsonXmlBuilder()
                    .setSameNameLists(true)
                    .setXmlParserCreator(parserCreator)
                    .create()
        }
    }

}