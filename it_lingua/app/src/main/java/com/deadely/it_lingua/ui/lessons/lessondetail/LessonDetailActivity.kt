package com.deadely.it_lingua.ui.lessons.lessondetail

import android.webkit.WebView
import android.webkit.WebViewClient
import by.kirich1409.viewbindingdelegate.viewBinding
import com.deadely.it_lingua.R
import com.deadely.it_lingua.base.BaseActivity
import com.deadely.it_lingua.databinding.FragmentLessonDetailBinding
import com.deadely.it_lingua.model.Lesson
import com.deadely.it_lingua.utils.TITLE_LESSON_DETAIL
import com.deadely.it_lingua.utils.makeGone
import com.deadely.it_lingua.utils.makeVisible
import com.deadely.it_lingua.utils.snack
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_lesson_detail.*
import moxy.ktx.moxyPresenter
import javax.inject.Inject
import javax.inject.Provider

@AndroidEntryPoint
class LessonDetailActivity :
    BaseActivity(R.layout.fragment_lesson_detail),
    LessonDetailView,
    BaseActivity.ExitPressedListener {
    private val viewBinding by viewBinding(
        FragmentLessonDetailBinding::bind
    )

    @Inject
    lateinit var provider: Provider<LessonDetailPresenter>
    private val presenter by moxyPresenter {
        provider.get().apply { lesson = intent?.extras?.getParcelable(TITLE_LESSON_DETAIL) }
    }

    override fun initView(lesson: Lesson?) {
        showProgress(true)
        lesson?.let {
            title = it.title
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

    override fun exit() {
        presenter.exit()
    }
}