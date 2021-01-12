package com.deadely.it_lingua.ui.lessons.lessondetail

import android.os.Bundle
import com.deadely.it_lingua.R
import com.deadely.it_lingua.base.BaseActivity
import com.deadely.it_lingua.base.BaseFragment
import com.deadely.it_lingua.model.Lesson
import com.deadely.it_lingua.utils.TITLE_LESSON_DETAIL
import com.deadely.it_lingua.utils.setActivityTitle
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_lesson_detail.*
import moxy.ktx.moxyPresenter
import javax.inject.Inject
import javax.inject.Provider

@AndroidEntryPoint
class LessonDetailFragment :
    BaseFragment(R.layout.fragment_lesson_detail),
    LessonDetailView,
    BaseActivity.ExitListener,
    BaseActivity.BackButtonPressed {
    companion object {
        fun newInstance(lesson: Lesson) = LessonDetailFragment().apply {
            arguments = Bundle().apply { putParcelable(TITLE_LESSON_DETAIL, lesson) }
        }
    }

    @Inject
    lateinit var provider: Provider<LessonDetailPresenter>
    private val presenter by moxyPresenter { provider.get() }

    private val lesson: Lesson?
        get() = arguments?.getParcelable<Lesson>(TITLE_LESSON_DETAIL)

    override fun initView() {
        lesson?.let {
            setActivityTitle(it.title)
            webView.apply {
                settings.builtInZoomControls = true
                loadData(it.content, "text/html; charset=utf-8", "UTF-8")
            }
        }
    }

    override fun getExtras() {
    }

    override fun setListeners() {
    }

    override fun onBackButtonPressed() {
        presenter.exit()
    }

    override fun exitPressed() {
        presenter.exit()
    }
}
