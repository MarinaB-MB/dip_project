package com.deadely.it_lingua.ui.lessons.lessondetail

import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.core.os.bundleOf
import by.kirich1409.viewbindingdelegate.viewBinding
import com.deadely.it_lingua.R
import com.deadely.it_lingua.base.BaseFragment
import com.deadely.it_lingua.databinding.FragmentLessonDetailBinding
import com.deadely.it_lingua.model.Lesson
import com.deadely.it_lingua.utils.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_lesson_detail.*
import moxy.ktx.moxyPresenter
import javax.inject.Inject
import javax.inject.Provider

@AndroidEntryPoint
class LessonDetailFragment :
    BaseFragment(R.layout.fragment_lesson_detail),
    LessonDetailView {

    @Inject
    lateinit var provider: Provider<LessonDetailPresenter>
    private val presenter by moxyPresenter {
        provider.get().apply { lesson = arguments?.getParcelable<Lesson>(TITLE_LESSON_DETAIL) }
    }
    private val viewBinding by viewBinding(
        FragmentLessonDetailBinding::bind
    )

    companion object {
        fun newInstance(lesson: Lesson) =
            LessonDetailFragment().apply { arguments = bundleOf(TITLE_LESSON_DETAIL to lesson) }
    }

    override fun initView(lesson: Lesson?) {
        showProgress(true)
        lesson?.let {
            setActivityTitle(it.title)
            showBottomNavView(false)
            webView.apply {
                settings.javaScriptEnabled = true
                settings.defaultTextEncodingName = "utf-8"
                loadData(it.content, "text/html; charset=UTF-8", "UTF-8")
            }
        }
    }

    override fun setListeners() {

        viewBinding.webView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                showProgress(false)
            }
        }
    }

    override fun showError(error: String) {
        viewBinding.webView.snack(error)
    }

    override fun showProgress(isShow: Boolean) {
        if (isShow) {
            viewBinding.pvLoad.makeVisible()
            viewBinding.webView.makeGone()
        } else {
            viewBinding.pvLoad.makeGone()
            viewBinding.webView.makeVisible()
        }
    }

    override fun onBackButtonPressed() {
        presenter.exit()
    }

    override fun exitPressed() {
        presenter.exit()
    }
}
