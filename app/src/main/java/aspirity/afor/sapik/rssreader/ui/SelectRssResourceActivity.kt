package aspirity.afor.sapik.rssreader.ui

import android.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import aspirity.afor.sapik.rssreader.R
import aspirity.afor.sapik.rssreader.model.RssResource
import aspirity.afor.sapik.rssreader.presentation.selectRssResource.SelectRssResourcePresenter
import aspirity.afor.sapik.rssreader.presentation.selectRssResource.SelectRssResourceView
import aspirity.afor.sapik.rssreader.ui.base.BaseMvpActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import kotlinx.android.synthetic.main.activity_select_rss_resource.*
import org.jetbrains.anko.find
import org.jetbrains.anko.intentFor


class SelectRssResourceActivity : BaseMvpActivity(), SelectRssResourceView {

    @InjectPresenter
    lateinit var presenter: SelectRssResourcePresenter

    private var dialog: AlertDialog? = null

    private lateinit var adapter: RssResourceListAdapter

    override val layoutRes: Int = R.layout.activity_select_rss_resource

    override fun initContent() {
        title = resources.getString(R.string.select_channel)
        btnAddNewResource.setOnClickListener { presenter.onClickBtnAdd() }
        adapter = RssResourceListAdapter(ArrayList())
        rvListResources.layoutManager = LinearLayoutManager(this)
        rvListResources.adapter = adapter
    }

    override fun showAdditionForm() {
        if (dialog != null) return

        val dialogLayout =
                LayoutInflater.from(this).inflate(R.layout.additional_form_dialog, null)

        val etNameResource: EditText = dialogLayout.findViewById(R.id.etNameResource)
        val etLinkResource: EditText = dialogLayout.findViewById(R.id.etLinkResource)

        resetError(etNameResource)
        resetError(etLinkResource)

        dialog = with(AlertDialog.Builder(this)) {
            setTitle(R.string.add_new_rss_channel)
            setView(dialogLayout)
            setNegativeButton(R.string.cancel, null)
            setPositiveButton(R.string.add, null)
            create()
            show()
        }
        dialog?.getButton(AlertDialog.BUTTON_POSITIVE)?.setOnClickListener {
            if (etNameResource.text.isEmpty()) {
                etNameResource.error = resources.getString(R.string.obligatory_field)
                return@setOnClickListener
            }
            if (etLinkResource.text.isEmpty()) {
                etLinkResource.error = resources.getString(R.string.obligatory_field)
                return@setOnClickListener
            }
            presenter.onAddNewResource(etNameResource.text.toString(), etLinkResource.text.toString())
        }
    }

    private fun resetError(editText: EditText) {
        editText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {}

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (editText.text.isNotEmpty())
                    editText.error = null
            }
        })
    }

    override fun dismissAdditionForm() {
        dialog?.dismiss()
        dialog = null
    }

    override fun showListResources(list: List<RssResource>) {
        adapter.addAll(list)
    }

    override fun startMainActivity() {
        startActivity(intentFor<MainActivity>())
    }

    override fun showLinkError(msg: String) {
        val etResourceLink = dialog?.find<EditText>(R.id.etLinkResource)
        etResourceLink?.error = msg
    }

    inner class RssResourceListAdapter(private val list: ArrayList<RssResource>)
        : RecyclerView.Adapter<RssResourceListAdapter.ViewHolder>() {

        fun addAll(list: List<RssResource>) {
            this.list.clear()
            this.list.addAll(list)
            notifyDataSetChanged()
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context)
                    .inflate(android.R.layout.simple_list_item_1, parent, false)
            return ViewHolder(view)
        }

        override fun getItemCount(): Int = list.size

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.bind(list[position])
        }

        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            private val link = itemView.find<TextView>(android.R.id.text1)

            fun bind(rssResource: RssResource) {
                link.text = rssResource.name
                link.setOnClickListener { presenter.onSelectResource(rssResource) }
            }
        }
    }
}
