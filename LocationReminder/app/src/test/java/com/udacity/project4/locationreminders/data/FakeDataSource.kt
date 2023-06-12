package com.udacity.project4.locationreminders.data

import com.udacity.project4.locationreminders.data.dto.ReminderDTO
import com.udacity.project4.locationreminders.data.dto.Result
import com.udacity.project4.locationreminders.reminderslist.ReminderDataItem

//Use FakeDataSource that acts as a test double to the LocalDataSource
class FakeDataSource : ReminderDataSource {

private val list= mutableListOf<ReminderDTO>()

    private var returnsError = false

    fun setReturnsError(value: Boolean) {
        returnsError = value
    }

    override suspend fun getReminders(): Result<List<ReminderDTO>> {
        if (returnsError) return Result.Error("error")
        else {
            list.let { return Result.Success(ArrayList(it)) }

        }
    }

    override suspend fun saveReminder(reminder: ReminderDTO) {
      list.add(reminder)
    }

    override suspend fun getReminder(id: String): Result<ReminderDTO> {
        if (returnsError) return Result.Error("error")
        else {
                for (reminder in list) {
                    if (reminder.id == id) return Result.Success(reminder)
                }
            }
            return Result.Error(
                "error there is not such reminder"
            )
        }

    override suspend fun deleteAllReminders() {
        list.clear()
    }
}



