package com.udacity.project4.locationreminders.reminderslist

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.udacity.project4.locationreminders.MainCoroutineRule
import com.udacity.project4.locationreminders.data.FakeDataSource
import com.udacity.project4.locationreminders.data.dto.ReminderDTO
import com.udacity.project4.locationreminders.data.dto.Result
import com.udacity.project4.locationreminders.getOrAwaitValue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.pauseDispatcher
import kotlinx.coroutines.test.resumeDispatcher
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.notNullValue
import org.hamcrest.MatcherAssert.assertThat


import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.stopKoin

@RunWith(AndroidJUnit4::class)
@ExperimentalCoroutinesApi
class RemindersListViewModelTest {
@get:Rule
var instantTaskExecutorRule=InstantTaskExecutorRule()

    @get:Rule
    var mainRele=MainCoroutineRule()
    private lateinit var dataSource:FakeDataSource
    private lateinit var viewModel:RemindersListViewModel

    @Before
    fun init() {
        stopKoin()
        dataSource = FakeDataSource()
        viewModel = RemindersListViewModel(ApplicationProvider.getApplicationContext(), dataSource)
    }



    @Test
    fun loadedReminders() = mainRele.runBlockingTest {

        val reminder = ReminderDTO("test",
            "test", "test", 23.0, 23.0)
        dataSource.saveReminder(reminder)

        mainRele.pauseDispatcher()

        viewModel.loadReminders()
        assertThat(
            viewModel.showLoading.getOrAwaitValue(), `is`(true)
        )

        mainRele.resumeDispatcher()
        assertThat(
            viewModel.showLoading.getOrAwaitValue(), `is`(false)
        )
    }

    @Test
    fun shouldReturnError() = mainRele.runBlockingTest {
        dataSource.setReturnsError(true)
        viewModel.loadReminders()

        assertThat(
           viewModel.showSnackBar.getOrAwaitValue(), `is`(notNullValue())
        )
    }

}