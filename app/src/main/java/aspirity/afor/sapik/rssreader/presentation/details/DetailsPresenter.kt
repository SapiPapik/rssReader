package aspirity.afor.sapik.rssreader.presentation.details

import aspirity.afor.sapik.rssreader.App
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter


@InjectViewState
class DetailsPresenter(
        private val title: String,
        private val pubDate: String,
        private val description: String,
        private val link: String
) : MvpPresenter<DetailsView>() {
    companion object {
        private const val NO_CONNECTION = "Отсутвует подключение к интернету"
    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.showNewsDetails(title, pubDate, description)
    }

    fun onClickOpenInBrowser() {
        if (!App.instance.isConnected) return viewState.showMessage(NO_CONNECTION)
        viewState.openLinkInBrowser(link)
    }
}