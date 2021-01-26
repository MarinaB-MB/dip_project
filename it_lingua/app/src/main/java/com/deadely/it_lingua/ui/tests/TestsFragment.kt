package com.deadely.it_lingua.ui.tests

import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.deadely.it_lingua.R
import com.deadely.it_lingua.base.BaseFragment
import com.deadely.it_lingua.databinding.FragmentTestsBinding
import com.deadely.it_lingua.model.Test
import com.deadely.it_lingua.utils.makeGone
import com.deadely.it_lingua.utils.makeVisible
import com.deadely.it_lingua.utils.setActivityTitle
import com.deadely.it_lingua.utils.snack
import dagger.hilt.android.AndroidEntryPoint
import moxy.ktx.moxyPresenter
import javax.inject.Inject
import javax.inject.Provider

@AndroidEntryPoint
class TestsFragment : BaseFragment(R.layout.fragment_tests), TestsView {
    @Inject
    lateinit var provider: Provider<TestsPresenter>
    private val presenter by moxyPresenter { provider.get() }
    private val testsAdapter = TestsAdapter()

    private val viewBinding by viewBinding(
        FragmentTestsBinding::bind
    )

    companion object {
        fun newInstance() = TestsFragment()
    }

    override fun initView() {
        setActivityTitle(R.string.title_tests)
    }

    override fun setListeners() {
        testsAdapter.setOnClickListener(object : TestsAdapter.OnItemClickListener {
            override fun onItemClick(test: Test) {
                presenter.openDetailTest(test)
            }
        })
        viewBinding.rvTests.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = testsAdapter
        }
    }

    override fun setTestsList(data: List<Test>) {
        testsAdapter.setData(data)
    }

    override fun showProgress(isShow: Boolean) {
        if (isShow) {
            viewBinding.pvLoad.makeVisible()
            viewBinding.rvTests.makeGone()
        } else {
            viewBinding.pvLoad.makeGone()
            viewBinding.rvTests.makeVisible()
        }
    }

    override fun showError(error: String) {
        viewBinding.rlTestsContainer.snack(error)
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
