package aspirity.afor.sapik.rssreader.ui.base

import android.app.Dialog
import android.os.Bundle
import android.os.PersistableBundle
import aspirity.afor.sapik.rssreader.R
import com.arellomobile.mvp.MvpAppCompatActivity
import org.jetbrains.anko.indeterminateProgressDialog
import org.jetbrains.anko.toast


abstract class BaseMvpActivity: MvpAppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutRes)
        initContent()
    }

    abstract fun initContent()

    abstract val layoutRes: Int

    private var progressDialog: Dialog? = null

    fun dismissProgress() {
        progressDialog?.dismiss()
    }

    fun showMessage(msg: String) {
        toast(msg)
    }

    fun showProgress() {
        progressDialog = indeterminateProgressDialog(
                R.string.loading,
                R.string.please_wait
        ).apply { setCancelable(false) }
    }
}