package aspirity.afor.sapik.rssreader.model

data class RssResource(
        var id: Long? = null,
        var link: String,
        var name: String,
        var news: List<Long>? = null
)