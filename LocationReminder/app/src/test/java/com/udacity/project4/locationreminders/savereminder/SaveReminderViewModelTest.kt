package com.udacity.project4.locationreminders.savereminder

import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.udacity.project4.locationreminders.data.FakeDataSource
import com.udacity.project4.locationreminders.data.dto.ReminderDTO
import com.udacity.project4.locationreminders.data.dto.Result
import com.udacity.project4.locationreminders.getOrAwaitValue
import com.udacity.project4.locationreminders.reminderslist.ReminderDataItem

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@Config(sdk=[29])
@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class SaveReminderViewModelTest {
    val fake=FakeDataSource()
    val viewModelTest=SaveReminderViewModel(ApplicationProvider.getApplicationContext(),fake)

    @Before
    fun before(){
        viewModelTest.reminderTitle.value="testTitle"
        viewModelTest.reminderDescription.value="testDescription"
        viewModelTest.reminderSelectedLocationStr.value="testLoc"
        viewModelTest.latitude.value=23.0
        viewModelTest.longitude.value=23.0

    }


@Test
fun setData_SaveData(){
       val item=ReminderDataItem(

           viewModelTest.reminderTitle.getOrAwaitValue(),
                   viewModelTest.reminderDescription.getOrAwaitValue(),
                   viewModelTest.reminderSelectedLocationStr.getOrAwaitValue(),
                   viewModelTest.latitude.getOrAwaitValue(),
                   viewModelTest.longitude.getOrAwaitValue {  }
       )

     viewModelTest.validateAndSaveReminder(item)

            runBlockingTest{
                val result= fake.getReminder(item.id) as Result.Success<ReminderDTO>
                val itemFromRes=result.data
                assertThat(item.id,`is`(itemFromRes.id))
            }


}


}