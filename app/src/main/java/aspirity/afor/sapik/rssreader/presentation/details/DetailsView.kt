package aspirity.afor.sapik.rssreader.presentation.details

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType


interface DetailsView : MvpView {
    fun showNewsDetails(title: String, pubDate: String, description: String)
    @StateStrategyType(SkipStrategy::class)
    fun openLinkInBrowser(link: String)
    @StateStrategyType(SkipStrategy::class)
    fun showMessage(msg: String)
}