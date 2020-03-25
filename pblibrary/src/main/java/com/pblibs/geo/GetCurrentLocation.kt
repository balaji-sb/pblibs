package com.pblibs.geo

import android.Manifest
import android.app.Activity
import android.location.Location
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.pblibs.base.PBBaseActivity
import com.pblibs.utility.PBConstants
import com.pblibs.utility.PBSessionManager


/**
 * Created by Compaq on 25/3/20
 */


class GetCurrentLocation(activity: Activity) {

    private var mActivity: Activity = activity
    private var fusedLocationClient: FusedLocationProviderClient? = null
    private var mLocation: Location? = null
    private var locationPermissionArray =
        arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION)


    init {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(mActivity)
    }

    companion object {

        private var INSTANCE: GetCurrentLocation? = null

        fun getInstance(activity: Activity): GetCurrentLocation {
            INSTANCE = INSTANCE ?: synchronized(GetCurrentLocation::class.java) {
                GetCurrentLocation(activity)
            }
            return INSTANCE!!
        }
    }

    fun isPermissionGranted(): Boolean {
        val lActivity = mActivity as PBBaseActivity
        if (lActivity.checkPermission(locationPermissionArray)) {
            return true
        } else {
            lActivity.askPermission(locationPermissionArray)
        }
        return false
    }

    /**
     * To get last location
     */

    fun getLastLocation(): Location {
        if (isPermissionGranted()) {
            fusedLocationClient?.lastLocation?.addOnSuccessListener { location ->
                if (location != null) {
                    mLocation = location
                    PBSessionManager.setString(PBConstants.LATITUDE, location.latitude.toString())
                    PBSessionManager.setString(PBConstants.LONGITUDE, location.longitude.toString())
                }
            }
        }
        return mLocation!!
    }

}