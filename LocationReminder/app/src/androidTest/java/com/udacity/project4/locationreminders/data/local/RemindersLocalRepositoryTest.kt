package com.udacity.project4.locationreminders.data.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest

import com.udacity.project4.locationreminders.data.dto.ReminderDTO
import com.udacity.project4.locationreminders.data.dto.Result
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.instanceOf
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
//Medium Test to test the repository
@MediumTest
class RemindersLocalRepositoryTest {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()


    private lateinit var localRepository: RemindersLocalRepository
    private lateinit var database: RemindersDatabase
    private lateinit var reminder:ReminderDTO


    @Before
    fun initDb() {

        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            RemindersDatabase::class.java
        ).allowMainThreadQueries().build()



        localRepository =
            RemindersLocalRepository(
                database.reminderDao(),
                Dispatchers.Main
            )

    }

    @Test
    fun addReminder()= runTest {
        val reminder = ReminderDTO("test", "test", "test", 23.0, 23.0)

        localRepository.saveReminder(reminder)
        val result = localRepository.getReminder(reminder.id) as Result.Success

        assertThat(result.data, `is`(reminder))
    }

    @Test
    fun checkError() = runTest {
        val reminder = ReminderDTO("test2", "test2", "test2", 23.0, 23.2)
        val id = reminder.id
        localRepository.saveReminder(reminder)
       localRepository.deleteAllReminders()

        val result =localRepository.getReminder(id) as Result.Error

        assertThat(result.message, `is`("Reminder not found!"))
    }





}