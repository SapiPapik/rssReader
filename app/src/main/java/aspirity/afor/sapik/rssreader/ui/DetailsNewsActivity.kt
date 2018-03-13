package aspirity.afor.sapik.rssreader.ui

import aspirity.afor.sapik.rssreader.R
import aspirity.afor.sapik.rssreader.presentation.details.DetailsPresenter
import aspirity.afor.sapik.rssreader.presentation.details.DetailsView
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import kotlinx.android.synthetic.main.activity_details.*
import android.content.Intent
import android.net.Uri
import aspirity.afor.sapik.rssreader.ui.base.BaseMvpActivity


class DetailsNewsActivity : BaseMvpActivity(), DetailsView {
    companion object {
        const val NEWS_TITLE = "news_title"
        const val NEWS_PUB_DATE = "news_pub_date"
        const val NEWS_DESCRIPTION = "news_description"
        const val NEWS_LINK = "news_link"
    }

    @InjectPresenter
    lateinit var presenter: DetailsPresenter

    @ProvidePresenter
    fun provideDetailsPresenter() = DetailsPresenter(
            intent.getStringExtra(NEWS_TITLE),
            intent.getStringExtra(NEWS_PUB_DATE),
            intent.getStringExtra(NEWS_DESCRIPTION),
            intent.getStringExtra(NEWS_LINK)
    )

    override val layoutRes: Int = R.layout.activity_details

    override fun initContent() {
        title = resources.getString(R.string.more_info)
        btnOpenInBrowser.setOnClickListener { presenter.onClickOpenInBrowser() }
    }

    override fun showNewsDetails(title: String, pubDate: String, description: String) {
        tvDetailsNewsTitle.text = title
        tvDetailsNewsPubDate.text = pubDate
        tvDetailsNewsDescription.text = description
    }

    override fun openLinkInBrowser(link: String) {
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(link)))
    }
}
