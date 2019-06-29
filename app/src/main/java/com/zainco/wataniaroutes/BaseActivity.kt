package com.zainco.wataniaroutes

import android.app.AlertDialog
import android.content.Context
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import org.koin.java.standalone.KoinJavaComponent


abstract class BaseActivity : AppCompatActivity() {

    protected fun initToolbar(
        toolbar: Toolbar,
        title: String,
        withBack: Boolean = false
    ) {
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayShowHomeEnabled(withBack)
        supportActionBar!!.setDisplayHomeAsUpEnabled(withBack)
        supportActionBar!!.title = title
    }

    override fun attachBaseContext(base: Context?) {
        val localeManager: LocaleManager = KoinJavaComponent.get(LocaleManager::class.java)
        super.attachBaseContext(localeManager.setLocale(localeManager.getLanguage()))
    }

    interface DialogClickListener {
        fun onDialogButtonClick()
    }
    protected fun generateMessageAlert(
        msg: String,
        positiveButtonText: String?,
        nagativeButtonText: String?,
        view: View?,
        onDialogClickListener: DialogClickListener?,
        onDialogCancelClickListener: DialogClickListener?,
        displayKeyboard: Boolean = false
    ) {
        if (msg.isEmpty())
            return
        val mDialogBuilder = AlertDialog.Builder(this)
        mDialogBuilder.setMessage(msg)
        view?.let {
            mDialogBuilder.setView(view)
        }
        mDialogBuilder.setPositiveButton(positiveButtonText) { _, _ -> onDialogClickListener?.onDialogButtonClick() }
        mDialogBuilder.setNegativeButton(nagativeButtonText) { _, _ -> onDialogCancelClickListener?.onDialogButtonClick() }
        try {
            val mDailog = mDialogBuilder.create()
            if (displayKeyboard) mDailog.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE)
            mDailog.show()

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}