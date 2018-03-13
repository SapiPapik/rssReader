package aspirity.afor.sapik.rssreader.ui

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import aspirity.afor.sapik.rssreader.App
import aspirity.afor.sapik.rssreader.R
import aspirity.afor.sapik.rssreader.model.News
import aspirity.afor.sapik.rssreader.presentation.main.MainPresenter
import aspirity.afor.sapik.rssreader.presentation.main.MainView
import aspirity.afor.sapik.rssreader.ui.base.BaseMvpActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import kotlinx.android.synthetic.main.main_activity.*
import org.jetbrains.anko.find
import org.jetbrains.anko.intentFor


class MainActivity : BaseMvpActivity(), MainView {

    @InjectPresenter
    lateinit var presenter: MainPresenter

    private lateinit var adapter: NewsListAdapter

    override val layoutRes: Int = R.layout.main_activity

    override fun initContent() {
        title = resources.getString(R.string.channel_postfix).format(App.instance.rssResource?.name)
        initAdapter()
        srlUploadNews.isEnabled = true
        srlUploadNews.setOnRefreshListener {
            presenter.refreshListNews()
        }
    }

    private fun initAdapter() {
        rvListNews.layoutManager = LinearLayoutManager(this)
        adapter = NewsListAdapter(ArrayList())
        rvListNews.adapter = adapter
    }

    override fun showListNews(list: List<News>) {
        adapter.setNewList(list)
    }

    override fun openDetailsNews(itemNews: News) {
        startActivity(intentFor<DetailsNewsActivity>(
                DetailsNewsActivity.NEWS_TITLE to itemNews.title,
                DetailsNewsActivity.NEWS_DESCRIPTION to itemNews.description,
                DetailsNewsActivity.NEWS_PUB_DATE to itemNews.pubDate,
                DetailsNewsActivity.NEWS_LINK to itemNews.link
        ))
    }

    override fun dismissSwipeProgress() {
        srlUploadNews.isRefreshing = false
    }

    inner class NewsListAdapter(private val list: ArrayList<News>)
        : RecyclerView.Adapter<NewsListAdapter.ViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_news, parent, false)
            return ViewHolder(view)
        }

        override fun getItemCount(): Int = list.size

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.bind(list[position])
        }

        fun setNewList(list: List<News>) {
            this.list.clear()
            this.list.addAll(list)
            notifyDataSetChanged()
        }

        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            private val newsTitle = itemView.find<TextView>(R.id.tvNewsTitle)
            private val newsDescription = itemView.find<TextView>(R.id.tvNewsDescription)
            private val itemNews = itemView.find<LinearLayout>(R.id.llNewsContainer)
            private val newsPubDate = itemView.find<TextView>(R.id.tvNewsPubDate)

            fun bind(item: News) {
                newsDescription.text = item.description
                newsTitle.text = item.title
                itemNews.setOnClickListener { presenter.onClickItemList(item) }
                newsPubDate.text = item.pubDate
            }
        }

    }
}
