package com.deadely.it_lingua.ui.testdetail

import android.app.AlertDialog
import android.app.Dialog
import android.view.LayoutInflater
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.children
import by.kirich1409.viewbindingdelegate.viewBinding
import com.deadely.it_lingua.R
import com.deadely.it_lingua.base.BaseActivity
import com.deadely.it_lingua.databinding.ActivityTestDetailBinding
import com.deadely.it_lingua.model.Ask
import com.deadely.it_lingua.utils.TEST
import com.deadely.it_lingua.utils.snack
import dagger.hilt.android.AndroidEntryPoint
import moxy.ktx.moxyPresenter
import javax.inject.Inject
import javax.inject.Provider

@AndroidEntryPoint
class TestDetailActivity :
    BaseActivity(R.layout.activity_test_detail),
    TestDetailView,
    BaseActivity.ExitPressedListener {

    @Inject
    lateinit var provider: Provider<TestDetailPresenter>
    private val presenter by moxyPresenter {
        provider.get().apply {
            test = intent?.getParcelableExtra(
                TEST
            )
        }
    }

    private val viewBinding by viewBinding(
        ActivityTestDetailBinding::bind,
        R.id.nsvTestDetailContainer
    )

    override fun initView(ask: Ask?, answers: List<String>) {
        ask?.let {
            viewBinding.tvAsk.text = it.ask
            viewBinding.llAnswers.children.forEachIndexed { index, view ->
                (view as TextView).apply {
                    text = answers[index]
                    setOnClickListener { presenter.checkAnswer(index) }
                }
            }
        }
    }

    override fun showResultDialog(badCount: String, goodCount: String) {
        Dialog(this@TestDetailActivity).apply {
            setContentView(R.layout.dialog_result_test)
            window?.setBackgroundDrawableResource(R.drawable.bg_dialog)
            setCanceledOnTouchOutside(false)
            findViewById<TextView>(R.id.tvBad).text = badCount
            findViewById<TextView>(R.id.tvGood).text = goodCount
            findViewById<ImageView>(R.id.ivClose).setOnClickListener { dismiss() }
            setOnDismissListener {
                presenter.exit()
            }
        }.show()
    }

    override fun showEndConfirmDialog() {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_confirm_close_test, null)
        val builder = AlertDialog.Builder(this).apply { setView(dialogView) }.show()
        dialogView.findViewById<Button>(R.id.btnYes).setOnClickListener {
            builder.dismiss()
            this@TestDetailActivity.finish()
        }
        dialogView.findViewById<Button>(R.id.btnNo).setOnClickListener {
            builder.dismiss()
        }
    }

    override fun setListeners() {}

    override fun showError(error: String) {
        viewBinding.nsvTestDetailContainer.snack(error)
    }

    override fun closeScreen() {
        finish()
    }

    override fun exit() {
        presenter.exit()
    }
}
