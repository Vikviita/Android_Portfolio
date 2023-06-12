package com.udacity.project4.locationreminders.savereminder.selectreminderlocation

import android.Manifest
import android.annotation.TargetApi
import android.app.AlertDialog
import android.content.Intent
import android.content.IntentSender
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.navigation.fragment.findNavController
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PointOfInterest
import com.google.android.material.snackbar.Snackbar
import com.udacity.project4.R
import com.udacity.project4.base.BaseFragment
import com.udacity.project4.base.BaseViewModel
import com.udacity.project4.databinding.FragmentSelectLocationBinding
import com.udacity.project4.locationreminders.reminderslist.REQUEST_FOREGROUND_ONLY_PERMISSION
import com.udacity.project4.locationreminders.savereminder.REQUEST_TURN_DEVICE_LOCATION_ON
import com.udacity.project4.locationreminders.savereminder.SaveReminderViewModel
import com.udacity.project4.utils.setDisplayHomeAsUpEnabled
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class SelectLocationFragmet:BaseFragment() ,OnMapReadyCallback{
    override val _viewModel:SaveReminderViewModel by sharedViewModel()
    private lateinit var binding:FragmentSelectLocationBinding
    private lateinit var map: GoogleMap
    private lateinit var locationManager: LocationManager
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding= FragmentSelectLocationBinding.inflate(layoutInflater,container,false)
        binding.lifecycleOwner = this

        setHasOptionsMenu(true)
        setDisplayHomeAsUpEnabled(true)



        (childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment).getMapAsync { googleMap ->
            map = googleMap


            map.setOnMapLongClickListener {
                map.clear()
                map.addMarker(MarkerOptions().position(it))
                confirmLocation(latlng = it)
            }
            map.setOnPoiClickListener {
                map.clear()
                map.addMarker(
                    MarkerOptions().position(it.latLng)
                )
                confirmLocation(poi = it)

            }
            map.setOnMarkerClickListener {
                it.remove()
                true
            }
            map.setMapStyle(
                MapStyleOptions.loadRawResourceStyle(
                    requireContext(),
                    R.raw.my_map_style
                )

            )
            checkPermisiionAndFindMe()
        }





        return binding.root
    }



    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.map_options, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        // TODO: Change the map type based on the user's selection.
        R.id.normal_map -> {
            map.mapType=GoogleMap.MAP_TYPE_NORMAL
            true
        }
        R.id.hybrid_map -> {
            map.mapType=GoogleMap.MAP_TYPE_HYBRID
            true
        }
        R.id.satellite_map -> {
            map.mapType=GoogleMap.MAP_TYPE_SATELLITE
            true
        }
        R.id.terrain_map -> {
            map.mapType=GoogleMap.MAP_TYPE_SATELLITE
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(grantResults.isEmpty()||grantResults[0]!=PackageManager.PERMISSION_GRANTED){
            Snackbar.make(
                this.requireView(),
                R.string.location_required_error, Snackbar.LENGTH_INDEFINITE
            ).setAction(android.R.string.ok) {
                requestForPermissions()
            }.show()
        }


    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_TURN_DEVICE_LOCATION_ON) {
            checkDeviceLocationSettingsAndFindCurrentLocation(false)
        }
    }

    private fun checkDeviceLocationSettingsAndFindCurrentLocation(resolve:Boolean = true) {
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
                        checkDeviceLocationSettingsAndFindCurrentLocation()

                    }.show()

            }
        }

        locationSettingsResponseTask.addOnCompleteListener {
            if ( it.isSuccessful ) {
                map.isMyLocationEnabled=true
                findMyLoc()
            }
        }
    }

    private fun confirmLocation(latlng: LatLng?=null, poi: PointOfInterest?=null){
        AlertDialog.Builder(requireContext())
            .setMessage(getString(R.string.do_you_want_save_this_location))
            .setPositiveButton("ok"){p0,p1->
                if(latlng==null){
                    onLocationSelected(poi=poi)
                }
                else if(poi==null){
                    onLocationSelected(latlng=latlng)

                }

            }
            .setNegativeButton("Cancel"){p0,p1->
                map.clear()
            }
            .show()
    }



    private fun onLocationSelected(latlng:LatLng?=null,poi:PointOfInterest?=null) {
        _viewModel.latitude.value=(latlng?.latitude)?: (poi?.latLng?.latitude )
        _viewModel.longitude.value=(latlng?.longitude)?: (poi?.latLng?.longitude )

        if(latlng==null){
            _viewModel.reminderSelectedLocationStr.value=poi?.name
        }
        else if(poi==null){
            _viewModel.reminderSelectedLocationStr.value="(lat:${latlng.latitude},\n long:${latlng.longitude})"
        }





        findNavController().popBackStack()





    }


    private fun findMyLoc() {
        if (ActivityCompat.checkSelfPermission(requireContext(),Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            locationManager = requireContext().getSystemService(LocationManager::class.java)
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 10f) {
                map.moveCamera(
                    CameraUpdateFactory.newLatLngZoom(
                        LatLng(it.latitude, it.longitude),
                        15f
                    )
                )
            }
        }
    }





    @TargetApi(29)
    private fun allPermissionsApproved(): Boolean {
        val foregroundLocationApproved = (
                PackageManager.PERMISSION_GRANTED ==
                        ActivityCompat.checkSelfPermission(
                            requireContext(),
                            Manifest.permission.ACCESS_FINE_LOCATION
                        ))


        return foregroundLocationApproved

    }

    private fun checkPermisiionAndFindMe(){
        if(allPermissionsApproved()){
            checkDeviceLocationSettingsAndFindCurrentLocation()
        }
        else{
            requestForPermissions()
        }
    }
    @TargetApi(29)
    fun requestForPermissions() {

        if (allPermissionsApproved()) return
        var arrayOfPermission = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION)
        var resultCode = REQUEST_FOREGROUND_ONLY_PERMISSION
        ActivityCompat.requestPermissions(
            requireActivity(),
            arrayOfPermission,
            resultCode
        )

    }

    override fun onMapReady(p0: GoogleMap) {
        TODO("Not yet implemented")
    }
}