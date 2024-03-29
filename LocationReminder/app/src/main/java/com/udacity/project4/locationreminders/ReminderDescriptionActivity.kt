package com.udacity.project4.locationreminders

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.udacity.project4.R
import com.udacity.project4.databinding.ActivityReminderDescriptionBinding
import com.udacity.project4.locationreminders.data.local.RemindersLocalRepository
import com.udacity.project4.locationreminders.reminderslist.ReminderDataItem
import org.koin.android.ext.android.inject

/**
 * Activity that displays the reminder details after the user clicks on the notification
 */
class ReminderDescriptionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityReminderDescriptionBinding

    companion object {
        private const val EXTRA_ReminderDataItem = "EXTRA_ReminderDataItem"

        // Receive the reminder object after the user clicks on the notification
        fun newIntent(context: Context, reminderDataItem: ReminderDataItem): Intent {
            val intent = Intent(context, ReminderDescriptionActivity::class.java)
            intent.putExtra(EXTRA_ReminderDataItem, reminderDataItem)
            return intent
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val layoutId = R.layout.activity_reminder_description
        binding = DataBindingUtil.setContentView(this, layoutId)
        val item= intent.getSerializableExtra(EXTRA_ReminderDataItem,ReminderDataItem::class.java)
        binding.reminderDataItem=item

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
       when(item.itemId){
           android.R.id.home->{startActivity(Intent(this,RemindersActivity::class.java))
           finish()

           }


       }


        return true

    }


    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this, RemindersActivity::class.java))
        finish()
    }

}