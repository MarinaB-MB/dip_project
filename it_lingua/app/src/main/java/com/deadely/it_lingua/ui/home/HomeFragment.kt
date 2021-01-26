package com.deadely.it_lingua.ui.home

import android.graphics.Color
import by.kirich1409.viewbindingdelegate.viewBinding
import com.deadely.it_lingua.R
import com.deadely.it_lingua.base.BaseFragment
import com.deadely.it_lingua.databinding.FragmentHomeBinding
import com.deadely.it_lingua.model.User
import com.deadely.it_lingua.utils.formatLongToString
import com.deadely.it_lingua.utils.formatString
import com.deadely.it_lingua.utils.setActivityTitle
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

    override fun setUserData(activeUser: User?) {
        activeUser?.let { user ->
            val values = arrayListOf<Entry>()
            user.stats?.let {
                for (stat in it) {
                    values.add(
                        Entry(
                            formatString(stat?.date ?: ""),
                            stat?.countTests?.toFloat() ?: 0F
                        )
                    )
                }
                val dataSet = LineDataSet(values, getString(R.string.tests_count_label)).apply {
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
            }
            tvLessonCount.text = user.stats?.last()?.countLessons.toString()
            tvTestsCount.text = user.stats?.last()?.countTests.toString()
        }
    }

    override fun initView() {
        setActivityTitle(R.string.title_home)
        with(viewBinding) {
            lcStats.apply {
                description.isEnabled = false
                setMaxVisibleValueCount(100)
                setPinchZoom(false)
                isDragEnabled = false
                setTouchEnabled(true)
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
