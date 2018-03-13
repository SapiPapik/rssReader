package aspirity.afor.sapik.rssreader.repository.mapper

import aspirity.afor.sapik.rssreader.model.News
import aspirity.afor.sapik.rssreader.model.RssResource
import aspirity.afor.sapik.rssreader.model.db.NewsDb
import aspirity.afor.sapik.rssreader.model.db.RssResourceDb


fun NewsDb.toNewsApp() = News(
        id = this.id,
        title = this.title,
        link = this.link,
        description = this.description,
        pubDate = this.pubDate,
        rssResourceId = this.rssResourceId
)

fun RssResourceDb.toRssResourceApp() = RssResource(
        id = this.id,
        link = this.link,
        name = this.name,
        news = this.newsIds
)
