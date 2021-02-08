package com.deadely.it_lingua.navigation

import android.content.Intent
import com.deadely.it_lingua.model.Lesson
import com.deadely.it_lingua.model.Test
import com.deadely.it_lingua.ui.account.AccountFragment
import com.deadely.it_lingua.ui.dictionary.DictionaryFragment
import com.deadely.it_lingua.ui.home.HomeFragment
import com.deadely.it_lingua.ui.lessons.LessonsFragment
import com.deadely.it_lingua.ui.lessons.lessondetail.LessonDetailFragment
import com.deadely.it_lingua.ui.main.MainActivity
import com.deadely.it_lingua.ui.reg.RegistrationActivity
import com.deadely.it_lingua.ui.splash.SplashActivity
import com.deadely.it_lingua.ui.testdetail.TestDetailFragment
import com.deadely.it_lingua.ui.tests.TestsFragment
import com.github.terrakok.cicerone.androidx.ActivityScreen
import com.github.terrakok.cicerone.androidx.FragmentScreen

object Screens {
    fun SPLASH_SCREEN() = ActivityScreen { Intent(it, SplashActivity::class.java) }
    fun MAIN_SCREEN() = ActivityScreen { Intent(it, MainActivity::class.java) }

    fun REGISTRATION_SCREEN() = ActivityScreen { Intent(it, RegistrationActivity::class.java) }

    fun TEST_DETAIL_SCREEN(test: Test) = FragmentScreen { TestDetailFragment.newInstance(test) }
    fun LESSON_DETAIL_SCREEN(lesson: Lesson) =
        FragmentScreen { LessonDetailFragment.newInstance(lesson) }

    fun ACCOUNT_SCREEN() = FragmentScreen { AccountFragment.newInstance() }
    fun DICTIONARY_SCREEN() = FragmentScreen { DictionaryFragment.newInstance() }
    fun HOME_SCREEN() = FragmentScreen { HomeFragment.newInstance() }
    fun LESSONS_SCREEN() = FragmentScreen { LessonsFragment.newInstance() }
    fun TESTS_SCREEN() = FragmentScreen { TestsFragment.newInstance() }
}
