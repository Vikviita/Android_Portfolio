package com.vikvita.wheatherapp.screens.selectLocation

import android.Manifest
import android.annotation.TargetApi
import android.app.AlertDialog
import android.content.Context
import android.content.IntentSender
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PointOfInterest
import com.google.android.material.snackbar.Snackbar
import com.vikvita.wheatherapp.R
import com.vikvita.wheatherapp.databinding.FragmentSelectionBinding

class SelectionFragment:Fragment() {
private lateinit var binding:FragmentSelectionBinding
    private lateinit var map: GoogleMap
    private lateinit var locationManager: LocationManager
    private lateinit var myPrefs:SharedPreferences
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        binding= FragmentSelectionBinding.inflate(inflater,container,false)
        myPrefs=requireActivity().getPreferences(Context.MODE_PRIVATE)


        (childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment).getMapAsync {
            map=it
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
                confirmLocation(it.latLng)

            }
            map.setOnMarkerClickListener {
                it.remove()
                true
            }
            checkPermisiionAndFindMe()
        }


        return binding.root
    }

    private fun confirmLocation(latlng: LatLng){
        AlertDialog.Builder(requireContext())
            .setMessage(getString(R.string.do_you_want_save_this_location))
            .setPositiveButton("ok"){p0,p1->
                with(myPrefs.edit()) {
                    putFloat("latitude", latlng.latitude.toFloat())
                    putFloat("longitude", latlng.longitude.toFloat())
                    apply()
                }
                findNavController().navigate(R.id.mainFragment)
            }
            .setNegativeButton("Cancel"){p0,p1->
                map.clear()
            }
            .show()
    }

    @TargetApi(29)
    private fun allPermissionsApproved(): Boolean {
        val foregroundLocationApproved = (
                PackageManager.PERMISSION_GRANTED ==
                        ActivityCompat.checkSelfPermission(
                            requireContext(),
                            Manifest.permission.ACCESS_COARSE_LOCATION
                        ))


        return foregroundLocationApproved

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
                if(allPermissionsApproved()){
                    checkDeviceLocationSettingsAndFindCurrentLocation()
                }
                else{
                    requestForPermissions()}
            }.show()
        }



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
        var arrayOfPermission = arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION)
        var resultCode = 29
        requestPermissions(
            arrayOfPermission,
            resultCode
        )

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
                    startIntentSenderForResult(exception.resolution.intentSender, 40, null, 0, 0, 0, null);
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

            }
        }
    }



}