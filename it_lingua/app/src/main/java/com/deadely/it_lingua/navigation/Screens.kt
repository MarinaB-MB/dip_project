package com.deadely.it_lingua.navigation

import android.content.Intent
import com.deadely.it_lingua.model.Lesson
import com.deadely.it_lingua.model.Test
import com.deadely.it_lingua.ui.account.AccountActivity
import com.deadely.it_lingua.ui.dictionary.DictionaryFragment
import com.deadely.it_lingua.ui.home.HomeFragment
import com.deadely.it_lingua.ui.lessons.LessonsFragment
import com.deadely.it_lingua.ui.lessons.lessondetail.LessonDetailActivity
import com.deadely.it_lingua.ui.main.MainActivity
import com.deadely.it_lingua.ui.reg.RegistrationActivity
import com.deadely.it_lingua.ui.splash.SplashActivity
import com.deadely.it_lingua.ui.testdetail.TestDetailActivity
import com.deadely.it_lingua.ui.tests.TestsFragment
import com.deadely.it_lingua.utils.TEST
import com.deadely.it_lingua.utils.TITLE_LESSON_DETAIL
import com.github.terrakok.cicerone.androidx.ActivityScreen
import com.github.terrakok.cicerone.androidx.FragmentScreen

object Screens {
    fun SPLASH_SCREEN() = ActivityScreen { Intent(it, SplashActivity::class.java) }
    fun MAIN_SCREEN() = ActivityScreen { Intent(it, MainActivity::class.java) }
    fun REGISTRATION_SCREEN() = ActivityScreen { Intent(it, RegistrationActivity::class.java) }
    fun TEST_DETAIL_SCREEN(test: Test) =
        ActivityScreen {
            Intent(it, TestDetailActivity::class.java).apply {
                putExtra(
                    TEST,
                    test
                )
            }
        }

    fun LESSON_DETAIL_SCREEN(lesson: Lesson) = ActivityScreen {
        Intent(it, LessonDetailActivity::class.java).apply {
            putExtra(TITLE_LESSON_DETAIL, lesson)
        }
    }

    fun ACCOUNT_SCREEN() = ActivityScreen { Intent(it, AccountActivity::class.java).apply { } }

    fun DICTIONARY_SCREEN() = FragmentScreen { DictionaryFragment.newInstance() }
    fun HOME_SCREEN() = FragmentScreen { HomeFragment.newInstance() }
    fun LESSONS_SCREEN() = FragmentScreen { LessonsFragment.newInstance() }
    fun TESTS_SCREEN() = FragmentScreen { TestsFragment.newInstance() }
}
