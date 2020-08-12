package com.test.gitrepo_test

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.test.gitrepo_test.TestUtils.withRecyclerView
import com.test.gitrepo_test.viewadapter.ReposViewHolder
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class FirstFragmentTest{

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun test_isRepoDataVisible() {
        val strName: String = "Name -gh-unit"
        val strDescription: String = "Description -Test Framework for Objective-C"

        onView(ViewMatchers.withId(R.id.recycler_view))
            .perform(RecyclerViewActions.scrollToPosition<ReposViewHolder>(2))

        onView(withRecyclerView(R.id.recycler_view).atPositionOnView(2,R.id.tv_name)).check(matches(withText(strName)))
        onView(withRecyclerView(R.id.recycler_view).atPositionOnView(2,R.id.tv_desc)).check(matches(withText(strDescription)))
    }

}