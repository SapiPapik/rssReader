package aspirity.afor.sapik.rssreader.model

import com.google.gson.annotations.SerializedName

/** Класс-контейнер для распарсивания */
data class Channel(@SerializedName("item") var news: List<News>)