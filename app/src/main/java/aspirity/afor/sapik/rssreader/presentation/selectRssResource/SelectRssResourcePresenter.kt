package aspirity.afor.sapik.rssreader.presentation.selectRssResource

import aspirity.afor.sapik.rssreader.App
import aspirity.afor.sapik.rssreader.interactor.RssResourceInteractor
import aspirity.afor.sapik.rssreader.model.RssResource
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers


@InjectViewState
class SelectRssResourcePresenter : MvpPresenter<SelectRssResourceView>() {
    companion object {
        private const val ERROR_DOWNLOAD = "Не удалось загрузить данные"
        private const val SUCCESS_SAVING_RESOURCE = "Новый ресурс успешно сохранен"
        private const val NEED_CONNECTION_FOR_VALIDATION = "Для проверки ресурса необходимо подключение к интернету"
        private const val INCORRECT_LINK = "Указана некорректная ссылка"
    }

    private val listResources: ArrayList<RssResource> = ArrayList()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        getListRssResources()
    }

    private fun onGettingResources(list: List<RssResource>) {
        listResources.clear()
        listResources.addAll(list)
        viewState.showListResources(listResources)
    }

    private fun onError(t: Throwable) {
        viewState.showMessage(ERROR_DOWNLOAD)
    }

    fun onClickBtnAdd() {
        viewState.showAdditionForm()
    }

    fun onSelectResource(rssResource: RssResource) {
        App.instance.rssResource = rssResource
        viewState.startMainActivity()
    }

    fun onAddNewResource(name: String, link: String) {
        if (!App.instance.isConnected) return viewState.showLinkError(NEED_CONNECTION_FOR_VALIDATION)
        RssResourceInteractor.checkResource(link)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { viewState.showProgress() }
                .doAfterTerminate { viewState.dismissProgress() }
                .subscribeBy(
                        onComplete = { resourceSuccessValidation(name, link) },
                        onError = ::resourceFailedValidation
                )
    }

    private fun resourceFailedValidation(t: Throwable) {
        viewState.showLinkError(INCORRECT_LINK)
    }

    private fun resourceSuccessValidation(name: String, link: String) {
        val newResource = RssResource(name = name, link = link, news = null)
        RssResourceInteractor.addResource(newResource)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                        onComplete = {
                            getListRssResources()
                            viewState.dismissAdditionForm()
                            viewState.showMessage(SUCCESS_SAVING_RESOURCE)
                        },
                        onError = ::onError
                )
    }

    private fun getListRssResources() {
        RssResourceInteractor.getRssResources()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { viewState.showProgress() }
                .doAfterTerminate { viewState.dismissProgress() }
                .toList()
                .subscribeBy(
                        onSuccess = ::onGettingResources,
                        onError = ::onError
                )
    }
}