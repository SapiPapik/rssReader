package aspirity.afor.sapik.rssreader.presentation.main

import aspirity.afor.sapik.rssreader.model.News
import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType


interface MainView: MvpView {
    @StateStrategyType(SkipStrategy::class)
    fun showMessage(msg: String)
    fun showProgress()
    fun dismissProgress()
    fun showListNews(list: List<News>)
    @StateStrategyType(SkipStrategy::class)
    fun openDetailsNews(itemNews: News)
    fun dismissSwipeProgress()
}