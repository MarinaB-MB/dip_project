package com.deadely.it_lingua.navigation

import android.content.Intent
import com.deadely.it_lingua.model.Lesson
import com.deadely.it_lingua.ui.dictionary.DictionaryFragment
import com.deadely.it_lingua.ui.home.HomeFragment
import com.deadely.it_lingua.ui.lessons.LessonsFragment
import com.deadely.it_lingua.ui.lessons.lessondetail.LessonDetailFragment
import com.deadely.it_lingua.ui.main.MainActivity
import com.deadely.it_lingua.ui.reg.RegistrationActivity
import com.deadely.it_lingua.ui.splash.SplashActivity
import com.deadely.it_lingua.ui.tests.TestsFragment
import com.github.terrakok.cicerone.androidx.ActivityScreen
import com.github.terrakok.cicerone.androidx.FragmentScreen

object Screens {
    fun SPLASH_SCREEN() = ActivityScreen { Intent(it, SplashActivity::class.java) }
    fun MAIN_SCREEN() = ActivityScreen { Intent(it, MainActivity::class.java) }
    fun REGISTRATION_SCREEN() = ActivityScreen { Intent(it, RegistrationActivity::class.java) }

    fun DICTIONARY_SCREEN() = FragmentScreen { DictionaryFragment.newInstance() }
    fun HOME_SCREEN() = FragmentScreen { HomeFragment.newInstance() }
    fun LESSONS_SCREEN() = FragmentScreen { LessonsFragment.newInstance() }
    fun LESSON_DETAIL(data: Lesson) = FragmentScreen { LessonDetailFragment.newInstance(data) }
    fun TESTS_SCREEN() = FragmentScreen { TestsFragment.newInstance() }
}
