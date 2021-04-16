package com.deadely.it_lingua.ui.home

import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import by.kirich1409.viewbindingdelegate.viewBinding
import com.deadely.it_lingua.R
import com.deadely.it_lingua.base.BaseFragment
import com.deadely.it_lingua.databinding.FragmentHomeBinding
import com.deadely.it_lingua.model.User
import com.deadely.it_lingua.utils.*
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.components.YAxis.AxisDependency
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_home.*
import moxy.ktx.moxyPresenter
import javax.inject.Inject
import javax.inject.Provider

@AndroidEntryPoint
class HomeFragment : BaseFragment(R.layout.fragment_home), HomeView {
    @Inject
    lateinit var provider: Provider<HomePresenter>
    private val presenter by moxyPresenter { provider.get() }
    private val viewBinding by viewBinding(
        FragmentHomeBinding::bind
    )

    companion object {
        fun newInstance() = HomeFragment()
        const val LESSON_MODE = "HomeFragment.Lesson"
        const val TEST_MODE = "HomeFragment.Test"
    }

    object DateValueFormatter : ValueFormatter() {
        override fun getFormattedValue(value: Float): String {
            return formatLongToString(value.toLong())
        }
    }

    object IntValueFormatter : ValueFormatter() {
        override fun getFormattedValue(value: Float): String {
            return value.toInt().toString()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.account_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.mnu_account -> {
                presenter.openAccountScreen()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun initView() {
        setActivityTitle(R.string.title_home)
        showBottomNavView(true)
        with(viewBinding) {
            lcStats.apply {
                description.isEnabled = false
                setMaxVisibleValueCount(100)
                setPinchZoom(false)
                isDragEnabled = false
                setTouchEnabled(false)
                xAxis.apply {
                    position = XAxis.XAxisPosition.BOTTOM
                    textColor = Color.BLACK
                    valueFormatter = DateValueFormatter
                }
                axisLeft.apply {
                    setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART)
                    textColor = Color.BLACK
                }
                axisRight.isEnabled = false
            }
        }
    }

    override fun setUserData(activeUser: User?, graphMode: String) {
        activeUser?.let { user ->
            val values = arrayListOf<Entry>()
            if (!user.stats.isNullOrEmpty()) {
                for (stat in user.stats!!) {
                    val yCountValue =
                        if (graphMode == LESSON_MODE) stat?.countLessons?.toFloat() else stat?.countTests?.toFloat()
                    values.add(
                        Entry(formatString(stat?.date ?: ""), yCountValue ?: 0F)
                    )
                }
                val label =
                    if (graphMode == LESSON_MODE) getString(R.string.checked_lessons_count_label) else getString(
                        R.string.checked_words_count_label
                    )
                val dataSet = LineDataSet(values, label).apply {
                    axisDependency = AxisDependency.LEFT
                    color = ColorTemplate.getHoloBlue()
                    valueTextColor = ColorTemplate.getHoloBlue()
                }
                val data = LineData(dataSet).apply {
                    setValueTextColor(Color.BLACK)
                    setValueFormatter(IntValueFormatter)
                    setValueTextSize(9f)
                }
                lcStats.data = data
                lcStats.invalidate()
                tvLessonCount.text = user.stats?.last()?.countLessons.toString()
                tvTestsCount.text = user.stats?.last()?.countTests.toString()
            }
        }
    }

    override fun setListeners() {
        with(viewBinding) {
            llLessonsCount.setOnClickListener { presenter.changeGraphMode(LESSON_MODE) }
            llTestsCount.setOnClickListener { presenter.changeGraphMode(TEST_MODE) }
        }
    }

    override fun showError(error: String) {
        viewBinding.nsvHomeContainer.snack(error)
    }

    override fun showProgress(isShow: Boolean) {
        if (isShow) {
            viewBinding.apply {
                llContent.makeGone()
                pvLoad.makeVisible()
            }
        } else {
            viewBinding.apply {
                llContent.makeVisible()
                pvLoad.makeGone()
            }
        }
    }

    override fun onBackButtonPressed() {
        presenter.exit()
    }

    override fun exitPressed() {
        presenter.exit()
    }
}
