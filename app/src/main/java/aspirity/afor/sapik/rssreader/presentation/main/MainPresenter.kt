package aspirity.afor.sapik.rssreader.presentation.main

import aspirity.afor.sapik.rssreader.App
import aspirity.afor.sapik.rssreader.common.DATE_MASK
import aspirity.afor.sapik.rssreader.common.formatDate
import aspirity.afor.sapik.rssreader.interactor.NewsInteractor
import aspirity.afor.sapik.rssreader.model.News
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers


@InjectViewState
class MainPresenter : MvpPresenter<MainView>() {
    companion object {
        private const val ERROR_DOWNLOAD = "Не удалось загрузить данные"
        private const val NO_CONNECTION = "Отсутствует подключение к интернету"
    }

    private val listNews: ArrayList<News> = ArrayList()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        if (!App.instance.isConnected) viewState.showMessage(NO_CONNECTION)
        getNews().doOnSubscribe { viewState.showProgress() }
                .doAfterTerminate { viewState.dismissProgress() }
                .subscribeBy(
                        onSuccess = ::onDownloadNews,
                        onError = ::onError
                )
    }

    private fun onDownloadNews(list: List<News>) {
        with(listNews) {
            clear()
            addAll(list)
            sortBy { it.publishedDate }
            reverse()
            forEach { it.pubDate = it.pubDate.formatDate(DATE_MASK) }
            viewState.showListNews(listNews)
        }
    }

    private fun onError(t: Throwable) {
        viewState.showMessage(ERROR_DOWNLOAD)
    }

    fun onClickItemList(itemNews: News) {
        viewState.openDetailsNews(itemNews)
    }

    fun refreshListNews() {
        if (!App.instance.isConnected) {
            viewState.dismissSwipeProgress()
            return viewState.showMessage(NO_CONNECTION)
        }
        getNews().doAfterTerminate { viewState.dismissSwipeProgress() }
                .subscribeBy(
                        onSuccess = ::onDownloadNews,
                        onError = ::onError
                )
    }

    private fun getNews(): Single<List<News>> =
            NewsInteractor.getNews(App.instance.rssResource?.id)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .toList()
}