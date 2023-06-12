package com.udacity.project4.locationreminders.reminderslist

import android.Manifest
import android.annotation.TargetApi
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.firebase.ui.auth.AuthUI
import com.google.android.gms.location.LocationServices
import com.google.android.material.snackbar.Snackbar
import com.udacity.project4.BuildConfig
import com.udacity.project4.R
import com.udacity.project4.base.BaseFragment
import com.udacity.project4.base.NavigationCommand
import com.udacity.project4.databinding.FragmentRemindersBinding
import com.udacity.project4.locationreminders.ReminderDescriptionActivity

import com.udacity.project4.utils.setDisplayHomeAsUpEnabled
import com.udacity.project4.utils.setTitle
import com.udacity.project4.utils.setup
import org.koin.androidx.viewmodel.ext.android.viewModel

class ReminderListFragment : BaseFragment() {

    // Use Koin to retrieve the ViewModel instance
    override val _viewModel: RemindersListViewModel by viewModel()
    private lateinit var binding: FragmentRemindersBinding
    private lateinit var adapter: RemindersListAdapter
    private val runningQOrLater = Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q
    private val runningTirammisuOrLater = Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_reminders, container, false
        )
        binding.viewModel = _viewModel

        setHasOptionsMenu(true)
        setDisplayHomeAsUpEnabled(false)
        setTitle(getString(R.string.app_name))
        binding.refreshLayout.setOnRefreshListener {
            _viewModel.loadReminders()
        binding.refreshLayout.isRefreshing=false
        }
        _viewModel.remindersList.observe(viewLifecycleOwner){
            adapter.submitList(it)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = this
        setupRecyclerView()
        binding.addReminderFAB.setOnClickListener {
            navigateToAddReminder()
        }


    }

    override fun onStart() {
        super.onStart()
        requestForPermissions()
        
    }

    override fun onResume() {
        super.onResume()
        // Load the reminders list on the ui
        _viewModel.loadReminders()
    }

    private fun navigateToAddReminder() {
        // Use the navigationCommand live data to navigate between the fragments
        _viewModel.navigationCommand.postValue(
            NavigationCommand.To(ReminderListFragmentDirections.toSaveReminder())
        )
    }




    private fun setupRecyclerView() {
        adapter = RemindersListAdapter{
             val intent=ReminderDescriptionActivity.newIntent(requireContext(),it)
            startActivity(intent)
        }
        // Setup the recycler view using the extension function
        binding.reminderssRecyclerView.adapter = adapter
        binding.reminderssRecyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)


    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.logout -> {
                AuthUI.getInstance().signOut(requireContext())
            }
            R.id.deleteAll->{
                _viewModel.deleteAll()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        // Display logout as menu item
        inflater.inflate(R.menu.main_menu, menu)
    }


    @TargetApi(29)
    private fun allPermissionsApproved(): Boolean {
        val notificationPermission =
            if (runningTirammisuOrLater) {
                PackageManager.PERMISSION_GRANTED == ActivityCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.POST_NOTIFICATIONS
                )
            } else {
                true
            }

        return notificationPermission

    }


    @TargetApi(29)
    fun requestForPermissions() {

        if (allPermissionsApproved()) return
        var arrayOfPermission = arrayOf(Manifest.permission.POST_NOTIFICATIONS)
        var resultCode = NOTIFICATION_PERMISSION_RESULT_CODE

        requestPermissions(
            arrayOfPermission,
            resultCode
        )
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(grantResults[0]!=PackageManager.PERMISSION_GRANTED){
            Snackbar.make(
                this.requireView(),
                "Notification must be anabled", Snackbar.LENGTH_INDEFINITE
            ).setAction(android.R.string.ok) {
                requestForPermissions()
            }.show()
        }
    }


}
 const val NOTIFICATION_PERMISSION_RESULT_CODE = 33
 const val REQUEST_BACKGROUND_ONLY_PERMISSIONS_REQUEST_CODE = 32
 const val REQUEST_FOREGROUND_ONLY_PERMISSION=34



