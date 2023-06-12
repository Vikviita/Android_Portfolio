package com.udacity.project4.locationreminders.reminderslist

import android.content.Context
import android.os.Bundle
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.onView

import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed

import androidx.test.espresso.matcher.ViewMatchers.withId

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.udacity.project4.R

import kotlinx.coroutines.ExperimentalCoroutinesApi

import org.junit.Assert.*

import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

import org.mockito.Mockito.mock
import org.mockito.Mockito.verify


@RunWith(AndroidJUnit4::class)
@ExperimentalCoroutinesApi
//UI Testing
@MediumTest
class ReminderListFragmentTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()












    @Test
fun clickAddreminder_NavigateToSaveReminderFragment(){

    val scenario = launchFragmentInContainer<ReminderListFragment>(Bundle(), R.style.AppTheme)
    val navController=mock(NavController::class.java)

    scenario.onFragment { Navigation.setViewNavController(it.view!!, navController) }

    onView(withId(R.id.addReminderFAB)).perform(click())

    // THEN - Verify that we navigate to the add screen
    verify(navController).navigate(
        ReminderListFragmentDirections.toSaveReminder()
    )

        onView(withId(R.id.reminderssRecyclerView)).check(matches(isDisplayed()))



}


}