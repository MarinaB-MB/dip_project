package com.deadely.it_lingua.ui.splash

import android.os.Handler
import com.deadely.it_lingua.R
import com.deadely.it_lingua.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint
import moxy.ktx.moxyPresenter
import javax.inject.Inject
import javax.inject.Provider

@AndroidEntryPoint
class SplashActivity : BaseActivity(R.layout.activity_splash), SplashView {
    @Inject
    lateinit var provider: Provider<SplashPresenter>
    private val presenter by moxyPresenter { provider.get() }

    override fun initView(isUserActive: Boolean) {
        supportActionBar?.hide()
        Handler().postDelayed(
            {
                if (isUserActive) {
                    presenter.openMainScreen()
                } else {
                    presenter.openRegScreen()
                }
            },
            1500
        )
    }

    override fun setListeners() {}
}
