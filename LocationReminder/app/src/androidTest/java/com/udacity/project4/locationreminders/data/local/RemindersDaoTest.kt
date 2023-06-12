package com.udacity.project4.locationreminders.data.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.SmallTest;
import com.udacity.project4.RemindersActivityTest
import com.udacity.project4.locationreminders.data.dto.ReminderDTO
import com.udacity.project4.locationreminders.savereminder.SaveReminderViewModel

import org.junit.Before;
import org.junit.Rule;
import org.junit.runner.RunWith;

import kotlinx.coroutines.ExperimentalCoroutinesApi;
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.notNullValue
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Test
import org.koin.java.KoinJavaComponent.inject
import org.koin.test.inject


@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
//Unit test the DAO
@SmallTest
class RemindersDaoTest {
    private lateinit var database: RemindersDatabase
    private lateinit var reminder:ReminderDTO

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun initDb() {
        // Using an in-memory database so that the information stored here disappears when the
        // process is killed.
        database = Room.inMemoryDatabaseBuilder(
            getApplicationContext(),
            RemindersDatabase::class.java
        ).allowMainThreadQueries().build()

        reminder = ReminderDTO(
            title = "test",
            description = "test",
            location = "test",
            latitude = 23.0,
            longitude = 23.0,
        )
        runBlockingTest {
            database.reminderDao().saveReminder(reminder)
        }
    }

    @After
    fun closeDb() = database.close()


    @Test
    fun remindersDaoTest() {
        runBlockingTest {
        val loaded= database.reminderDao().getReminderById(reminder.id)


            assertThat(loaded as ReminderDTO, notNullValue())
            assertThat(loaded.id, `is`(reminder.id))
            assertThat(loaded.title, `is`(reminder.title))
            assertThat(loaded.description, `is`(reminder.description))
            assertThat(loaded.latitude, `is`(reminder.latitude))
            assertThat(loaded.longitude, `is`(reminder.longitude))

        }
    }
}

