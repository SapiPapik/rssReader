package aspirity.afor.sapik.rssreader.presentation.selectRssResource

import aspirity.afor.sapik.rssreader.model.RssResource
import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType


interface SelectRssResourceView: MvpView {
    @StateStrategyType(SkipStrategy::class)
    fun showMessage(msg: String)
    fun showAdditionForm()
    fun dismissAdditionForm()
    fun showProgress()
    fun dismissProgress()
    fun showListResources(list: List<RssResource>)
    @StateStrategyType(SkipStrategy::class)
    fun startMainActivity()
    @StateStrategyType(SkipStrategy::class)
    fun showLinkError(msg: String)
}