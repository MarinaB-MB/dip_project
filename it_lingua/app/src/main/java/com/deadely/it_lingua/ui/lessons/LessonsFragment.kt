package com.deadely.it_lingua.ui.lessons

import androidx.recyclerview.widget.LinearLayoutManager
import com.deadely.it_lingua.R
import com.deadely.it_lingua.base.BaseFragment
import com.deadely.it_lingua.model.Lesson
import com.deadely.it_lingua.model.getLessons
import com.deadely.it_lingua.utils.setActivityTitle
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_lessons.*
import moxy.ktx.moxyPresenter
import javax.inject.Inject
import javax.inject.Provider

@AndroidEntryPoint
class LessonsFragment : BaseFragment(R.layout.fragment_lessons), LessonsView {
    @Inject
    lateinit var provider: Provider<LessonsPresenter>
    private val presenter by moxyPresenter { provider.get() }

    private val lessonsAdapter = LessonsAdapter()

    override fun initView() {
        setActivityTitle(R.string.title_lessons)
    }

    override fun setListeners() {
        lessonsAdapter.setData(getLessons())
        lessonsAdapter.setOnClickListener(object : LessonsAdapter.OnItemClickListener {
            override fun onItemClick(lesson: Lesson) {
                presenter.openDetailLesson(lesson)
            }
        })
        rvLessons.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = lessonsAdapter
        }
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
