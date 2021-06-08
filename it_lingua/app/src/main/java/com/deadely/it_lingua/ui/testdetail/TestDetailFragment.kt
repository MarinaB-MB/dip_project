package com.deadely.it_lingua.ui.testdetail

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.view.LayoutInflater
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.view.children
import by.kirich1409.viewbindingdelegate.viewBinding
import com.deadely.it_lingua.R
import com.deadely.it_lingua.base.BaseFragment
import com.deadely.it_lingua.databinding.ActivityTestDetailBinding
import com.deadely.it_lingua.model.Ask
import com.deadely.it_lingua.model.Test
import com.deadely.it_lingua.utils.TEST
import com.deadely.it_lingua.utils.makeGone
import com.deadely.it_lingua.utils.makeVisible
import com.deadely.it_lingua.utils.snack
import dagger.hilt.android.AndroidEntryPoint
import moxy.ktx.moxyPresenter
import javax.inject.Inject
import javax.inject.Provider

@AndroidEntryPoint
class TestDetailFragment :
    BaseFragment(R.layout.activity_test_detail),
    TestDetailView {

    @Inject
    lateinit var provider: Provider<TestDetailPresenter>
    private val presenter by moxyPresenter {
        provider.get().apply { test = arguments?.getParcelable(TEST) }
    }
    private val viewBinding by viewBinding(
        ActivityTestDetailBinding::bind,
        R.id.nsvTestDetailContainer
    )

    companion object {
        fun newInstance(test: Test) =
            TestDetailFragment().apply { arguments = bundleOf(TEST to test) }
    }

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
        context?.let {
            Dialog(it).apply {
                setContentView(R.layout.dialog_result_test)
                window?.setBackgroundDrawableResource(R.drawable.bg_dialog)
                setCanceledOnTouchOutside(false)
                findViewById<TextView>(R.id.tvBad).text = badCount
                findViewById<TextView>(R.id.tvGood).text = goodCount
                if (badCount > goodCount) {
                    findViewById<TextView>(R.id.tvTitle).text = getString(R.string.unfortunately)
                }
                findViewById<ImageView>(R.id.ivClose).setOnClickListener { dismiss() }
                setOnDismissListener {
                    presenter.exit()
                }
            }.show()
        }
    }

    override fun showEndConfirmDialog() {
        val dialogView =
            LayoutInflater.from(context).inflate(R.layout.dialog_confirm_close_test, null)
        val builder = AlertDialog.Builder(context).apply { setView(dialogView) }.show()
        dialogView.findViewById<Button>(R.id.btnYes).setOnClickListener {
            builder.dismiss()
            presenter.exit(true)
        }
        dialogView.findViewById<Button>(R.id.btnNo).setOnClickListener {
            builder.dismiss()
        }
    }

    override fun blockButtons() {
        viewBinding.llAnswers.children.forEach { (it as TextView).isEnabled = false }
    }

    override fun showProgress(isShow: Boolean) {
        if (isShow) {
            viewBinding.pvLoad.makeVisible()
            viewBinding.rlContent.makeGone()
        } else {
            viewBinding.pvLoad.makeGone()
            viewBinding.rlContent.makeVisible()
        }
    }

    override fun showErrorDialog() {
        AlertDialog.Builder(context)
            .setCancelable(false)
            .setMessage(getString(R.string.unexpected_error))
            .setPositiveButton(getString(R.string.ok)) { dialog, _ ->
                dialog.dismiss()
                presenter.exit(true)
            }
            .show()
    }

    override fun setListeners() {}
    override fun onBackButtonPressed() {
        presenter.exit()
    }

    override fun exitPressed() {
        presenter.exit()
    }

    override fun showError(error: String) {
        viewBinding.nsvTestDetailContainer.snack(error)
    }
}
