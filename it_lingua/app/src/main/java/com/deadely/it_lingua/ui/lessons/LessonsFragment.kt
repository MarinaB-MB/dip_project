package com.deadely.it_lingua.ui.lessons

import android.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.deadely.it_lingua.R
import com.deadely.it_lingua.base.BaseFragment
import com.deadely.it_lingua.databinding.FragmentLessonsBinding
import com.deadely.it_lingua.model.Lesson
import com.deadely.it_lingua.utils.*
import dagger.hilt.android.AndroidEntryPoint
import moxy.ktx.moxyPresenter
import javax.inject.Inject
import javax.inject.Provider

@AndroidEntryPoint
class LessonsFragment : BaseFragment(R.layout.fragment_lessons), LessonsView {
    @Inject
    lateinit var provider: Provider<LessonsPresenter>
    private val presenter by moxyPresenter { provider.get() }
    private val viewBinding by viewBinding(
        FragmentLessonsBinding::bind
    )
    private val lessonsAdapter = LessonsAdapter()

    companion object {
        fun newInstance() = LessonsFragment()
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

    override fun initView(data: List<Lesson>) {
        setActivityTitle(R.string.title_lessons)
        showBottomNavView(true)
        lessonsAdapter.setData(data)
    }

    override fun showPreviewDialog() {
        AlertDialog.Builder(context)
            .setCancelable(false)
            .setMessage(getString(R.string.error_preview))
            .setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
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

    override fun onBackButtonPressed() {
        presenter.exit()
    }

    override fun exitPressed() {
        presenter.exit()
    }
}
