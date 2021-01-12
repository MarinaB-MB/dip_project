package com.deadely.it_lingua.navigation

import android.content.Intent
import com.deadely.it_lingua.model.Lesson
import com.deadely.it_lingua.ui.dictionary.DictionaryFragment
import com.deadely.it_lingua.ui.home.HomeFragment
import com.deadely.it_lingua.ui.lessons.LessonsFragment
import com.deadely.it_lingua.ui.lessons.lessondetail.LessonDetailFragment
import com.deadely.it_lingua.ui.splash.SplashActivity
import com.deadely.it_lingua.ui.tests.TestsFragment
import com.github.terrakok.cicerone.Screen
import com.github.terrakok.cicerone.androidx.ActivityScreen
import com.github.terrakok.cicerone.androidx.FragmentScreen
import com.readystatesoftware.chuck.internal.ui.MainActivity

object Screens {

    const val SPLASH_SCREEN = "splash_screen"
    const val MAIN_SCREEN = "main_screen"

    const val DICTIONARY_SCREEN = "dictionary_screen"
    const val LESSON_DETAIL = "lesson_detail"
    const val HOME_SCREEN = "home_screen"
    const val LESSONS_SCREEN = "lessons_screen"
    const val TESTS_SCREEN = "tests_screen"

    fun getScreenByKey(screenKey: String?, data: Any? = null): Screen = when (screenKey) {
        SPLASH_SCREEN -> ActivityScreen { Intent(it, SplashActivity::class.java) }
        MAIN_SCREEN -> ActivityScreen { Intent(it, MainActivity::class.java) }
        DICTIONARY_SCREEN -> FragmentScreen { DictionaryFragment() }
        HOME_SCREEN -> FragmentScreen { HomeFragment() }
        LESSONS_SCREEN -> FragmentScreen { LessonsFragment() }
        LESSON_DETAIL -> FragmentScreen { LessonDetailFragment.newInstance(data as Lesson) }
        TESTS_SCREEN -> FragmentScreen { TestsFragment() }
        else -> throw Exception("Unknown key of fragment")
    }
}
