package aspirity.afor.sapik.rssreader.repository.mapper

import aspirity.afor.sapik.rssreader.model.News
import aspirity.afor.sapik.rssreader.model.RssResource
import aspirity.afor.sapik.rssreader.model.db.NewsDbEntity
import aspirity.afor.sapik.rssreader.model.db.RssResourceDb
import aspirity.afor.sapik.rssreader.model.db.RssResourceDbEntity


fun News.toNewsDb(rssResourceDb: RssResourceDb) = NewsDbEntity().also { newsDb ->
    newsDb.title = title
    newsDb.description = description
    newsDb.pubDate = pubDate
    newsDb.link = link
    newsDb.rssResourceId = rssResourceDb.id
}

fun RssResource.toRssResourceDb() = RssResourceDbEntity().also { rssResourceDb ->
    rssResourceDb.id = id ?: 0
    rssResourceDb.link = link
    rssResourceDb.name = name
    rssResourceDb.newsIds = null
}