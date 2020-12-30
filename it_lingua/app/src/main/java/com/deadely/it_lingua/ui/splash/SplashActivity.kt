package com.deadely.it_lingua.ui.splash

import android.content.Intent
import android.os.Handler
import com.deadely.it_lingua.R
import com.deadely.it_lingua.base.BaseActivity
import com.deadely.it_lingua.ui.main.MainActivity

class SplashActivity : BaseActivity(R.layout.activity_splash) {

    override fun initView() {
        supportActionBar?.hide()
        openMainScreen()
    }
    private fun openMainScreen() {
        Handler().postDelayed(
            {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            },
            1500
        )
    }

    override fun setListeners() { }

    override fun getExtras() {

    }
}
