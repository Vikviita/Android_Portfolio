package com.udacity.project4.locationreminders.savereminder

import android.Manifest
import android.annotation.TargetApi
import android.app.AlertDialog
import android.app.PendingIntent
import android.content.Intent
import android.content.IntentSender
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingClient
import com.google.android.gms.location.GeofencingRequest
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.material.snackbar.Snackbar
import com.udacity.project4.R
import com.udacity.project4.base.BaseFragment
import com.udacity.project4.base.NavigationCommand
import com.udacity.project4.databinding.FragmentSaveReminderBinding
import com.udacity.project4.locationreminders.geofence.GeofenceBroadcastReceiver
import com.udacity.project4.locationreminders.reminderslist.REQUEST_BACKGROUND_ONLY_PERMISSIONS_REQUEST_CODE

import com.udacity.project4.locationreminders.reminderslist.REQUEST_FOREGROUND_ONLY_PERMISSION
import com.udacity.project4.locationreminders.reminderslist.ReminderDataItem

import com.udacity.project4.utils.setDisplayHomeAsUpEnabled
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.core.context.loadKoinModules

class SaveReminderFragment : BaseFragment() {

    // Get the view model this time as a single to be shared with the another fragment
    override val _viewModel: SaveReminderViewModel by sharedViewModel()
    private lateinit var binding: FragmentSaveReminderBinding
    private lateinit var geofencingClient: GeofencingClient
    private lateinit var reminderDataItem: ReminderDataItem
    private val runningQOrLater = Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q
    private val runningTirammisuOrLater = Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU

    private val geofencePendingIntent: PendingIntent by lazy {
        val intent = Intent(requireContext(), GeofenceBroadcastReceiver::class.java)
        intent.action = ACTION_GEOFENCE_EVENT
        PendingIntent.getBroadcast(requireContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_MUTABLE)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        val layoutId = R.layout.fragment_save_reminder
        binding = DataBindingUtil.inflate(inflater, layoutId, container, false)

        setDisplayHomeAsUpEnabled(true)
        binding.viewModel = _viewModel
       geofencingClient=LocationServices.getGeofencingClient(requireActivity())

        return binding.root

    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.lifecycleOwner = this
        binding.selectLocation.setOnClickListener {
            // Navigate to another fragment to get the user location
            val directions =SaveReminderFragmentDirections.actionSaveReminderFragmentToSelectLocationFragment()
            _viewModel.navigationCommand.value = NavigationCommand.To(directions)
        }


        binding.saveReminder.setOnClickListener {
            val title = _viewModel.reminderTitle.value
            val description = _viewModel.reminderDescription.value
            val location = _viewModel.reminderSelectedLocationStr.value
            val latitude = _viewModel.latitude.value
            val longitude = _viewModel.longitude.value
             reminderDataItem=ReminderDataItem(
                 title=title,
                 description=description,
                 location=location,
                 latitude=latitude,
                 longitude= longitude
             )
            checkPermisiionAndCreateGeofence()






        }
    }

    override fun onDestroy() {
        super.onDestroy()

        _viewModel.onClear()
    }


    private fun addGeofenceReminder(item:ReminderDataItem){
        val geofence=Geofence.Builder()
            .setRequestId(item.id)
            .setCircularRegion(item.latitude!!,item.longitude!!,100f)
            .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER)
            .setExpirationDuration(Geofence.NEVER_EXPIRE)
            .build()
        val geofenceRequest=GeofencingRequest.Builder()
            .addGeofence(geofence)
            .build()
        if(ActivityCompat.checkSelfPermission(requireContext(),Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED)
        {geofencingClient.addGeofences(geofenceRequest,geofencePendingIntent)}
        _viewModel.validateAndSaveReminder(reminderDataItem)


    }



    @TargetApi(29)
    private fun allPermissionsApproved(): Boolean {
        val foregroundLocationApproved = (
                PackageManager.PERMISSION_GRANTED ==
                        ActivityCompat.checkSelfPermission(
                            requireContext(),
                            Manifest.permission.ACCESS_FINE_LOCATION
                        ))
        val backgroundPermissionApproved =
            if (runningQOrLater) {
                PackageManager.PERMISSION_GRANTED ==
                        ActivityCompat.checkSelfPermission(
                            requireContext(), Manifest.permission.ACCESS_BACKGROUND_LOCATION
                        )
            } else {
                true
            }


        return foregroundLocationApproved && backgroundPermissionApproved

    }

private fun checkPermisiionAndCreateGeofence(){
    if(allPermissionsApproved()){
        checkDeviceLocationSettingsAndStartGeofence()
    }
    else{
        requestForPermissions()
    }
}
    @TargetApi(29)
    fun requestForPermissions() {
        if (allPermissionsApproved()) return


        var permissionsArray = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION)

        val resultCode = when {
            runningQOrLater -> {

                permissionsArray += Manifest.permission.ACCESS_BACKGROUND_LOCATION
               BACKGROUND_AND_FOREGROUND_PEMISSION
            }
            else -> REQUEST_FOREGROUND_ONLY_PERMISSION
        }


        requestPermissions(
            permissionsArray,
            resultCode
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(grantResults.isEmpty()||grantResults[0]!=PackageManager.PERMISSION_GRANTED||grantResults[1]!=PackageManager.PERMISSION_GRANTED)
        {
            Snackbar.make(
                this.requireView(),
                R.string.location_required_error, Snackbar.LENGTH_INDEFINITE
            ).setAction(android.R.string.ok) {
                requestForPermissions()
            }.show()
        }
    }

    private fun checkDeviceLocationSettingsAndStartGeofence(resolve:Boolean = true) {
        val locationRequest = LocationRequest.create().apply {
            priority = LocationRequest.PRIORITY_LOW_POWER
        }
        val builder = LocationSettingsRequest.Builder().addLocationRequest(locationRequest)
        val settingsClient = LocationServices.getSettingsClient(requireActivity())
        val locationSettingsResponseTask =
            settingsClient.checkLocationSettings(builder.build())
        locationSettingsResponseTask.addOnFailureListener { exception ->
            if (exception is ResolvableApiException && resolve){
                try {
                    startIntentSenderForResult(exception.resolution.intentSender, REQUEST_TURN_DEVICE_LOCATION_ON, null, 0, 0, 0, null);
                } catch (sendEx: IntentSender.SendIntentException) {

                }
            } else {

                AlertDialog.Builder(context)
                    .setMessage(R.string.location_required_error)
                    .setPositiveButton(android.R.string.ok){p0,p1->
                        checkDeviceLocationSettingsAndStartGeofence()

                    }.show()

            }
        }

        locationSettingsResponseTask.addOnCompleteListener {
            if ( it.isSuccessful ) {
                addGeofenceReminder(reminderDataItem)
            }
        }
    }
}

const val ACTION_GEOFENCE_EVENT="ACTION_GEOFENCE_EVENT"
const val BACKGROUND_AND_FOREGROUND_PEMISSION=36
const val REQUEST_TURN_DEVICE_LOCATION_ON=40