package com.deadely.it_lingua.ui.lessons

import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.deadely.it_lingua.R
import com.deadely.it_lingua.base.BaseFragment
import com.deadely.it_lingua.databinding.FragmentLessonsBinding
import com.deadely.it_lingua.model.Lesson
import com.deadely.it_lingua.utils.makeGone
import com.deadely.it_lingua.utils.makeVisible
import com.deadely.it_lingua.utils.setActivityTitle
import com.deadely.it_lingua.utils.snack
import dagger.hilt.android.AndroidEntryPoint
import moxy.ktx.moxyPresenter
import javax.inject.Inject
import javax.inject.Provider

@AndroidEntryPoint
class LessonsFragment : BaseFragment(R.layout.fragment_lessons), LessonsView {
    @Inject
    lateinit var provider: Provider<LessonsPresenter>
    private val presenter by moxyPresenter { provider.get() }

    private val lessonsAdapter = LessonsAdapter()
    private val viewBinding by viewBinding(
        FragmentLessonsBinding::bind
    )

    companion object {
        fun newInstance() = LessonsFragment()
    }

    override fun initView() {
        setActivityTitle(R.string.title_lessons)
    }

    override fun setListeners() {
        lessonsAdapter.setOnClickListener(object : LessonsAdapter.OnItemClickListener {
            override fun onItemClick(lesson: Lesson) {
                presenter.openDetailLesson(lesson)
            }
        })
        viewBinding.rvLessons.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = lessonsAdapter
        }
    }

    override fun setLessonsList(data: List<Lesson>) {
        lessonsAdapter.setData(data)
    }

    override fun showProgress(isShow: Boolean) {
        if (isShow) {
            viewBinding.pvLoad.makeVisible()
            viewBinding.rvLessons.makeGone()
        } else {
            viewBinding.pvLoad.makeGone()
            viewBinding.rvLessons.makeVisible()
        }
    }

    override fun showError(error: String) {
        viewBinding.rlLessonsContainer.snack(error)
    }

    override fun getExtras() {
    }

    override fun onBackButtonPressed() {
        presenter.exit()
    }

    override fun exitPressed() {
        presenter.exit()
    }
}
