package com.korkel.mc.navigation

import android.content.Intent
import com.github.terrakok.cicerone.androidx.ActivityScreen
import com.github.terrakok.cicerone.androidx.FragmentScreen
import com.korkel.mc.ui.frag.AFragment
import com.korkel.mc.ui.main.MainActivity
import com.korkel.mc.ui.sec.SecActivity

object Screens {
    fun MainActivity() = ActivityScreen { Intent(it, MainActivity::class.java) }
    fun SecActivity() = ActivityScreen { Intent(it, SecActivity::class.java) }

    fun FragmentExample() = FragmentScreen { AFragment() }
}
