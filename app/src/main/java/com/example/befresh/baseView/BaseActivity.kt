package com.example.befresh.baseView

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.Rect
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.*
import androidx.lifecycle.LiveData
import androidx.viewbinding.ViewBinding
import com.example.befresh.R

import java.util.*

abstract class BaseActivity<T : ViewBinding> : AppCompatActivity() {
    var binding: T? = null


    private var isGPSEnabled = false

    /**
     * MutableLiveData private field to get/save location updated values
     */
  //  private val locationData = LocationLiveData(MonoPlugApplication.instance.applicationContext)

    /**
     * LiveData a public field to observe the changes of location
     */
 //   val getLocationData: LiveData<Location> = locationData
    private val locationPermissions = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION
    )


    @LayoutRes
    abstract fun getLayoutResID(): Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this@BaseActivity, getLayoutResID())
    }

    /** Keyboard Remove on Edit text**/
    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            val v = currentFocus
            if (v is EditText) {
                val outRect = Rect()
                v.getGlobalVisibleRect(outRect)
                if (!outRect.contains(event.rawX.toInt(), event.rawY.toInt())) {
                    v.clearFocus()
                    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(v.windowToken, 0)
                }
            }
        }
        return super.dispatchTouchEvent(event)
    }

    /** show snack bar toast **/
    /*   fun showSnackBar(string: String) {
           try {
               val snackBar: Snackbar = Snackbar.make(findViewById(android.R.id.content), string, Snackbar.LENGTH_SHORT)
               val snackBarView = snackBar.view
               snackBarView.setBackgroundColor(ContextCompat.getColor(this, R.color.black))
               snackBar.show()
           }
           catch (e: java.lang.Exception) {
           }
       }*/

    fun changeStatusBarFullScreen() {
        val w = window
        val v: View = w.decorView
        w.navigationBarColor = resources.getColor(android.R.color.transparent)
        v.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        w.statusBarColor = Color.TRANSPARENT
        w.navigationBarColor = ContextCompat.getColor(this, android.R.color.black)
    }


    fun changeStatusBarColor() {
        val window: Window = window
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        window.statusBarColor = ContextCompat.getColor(this, R.color.purple_200)
    }

//    fun showMainProgress() {
//        MyProgress.show(this)
//    }
//
//    fun hideMainProgress() {
//        MyProgress.hide(this)
//    }

    override fun onResume() {
        super.onResume()

    }

//    fun turnONGPS() {
//        LocationUtil(this).turnGPSOn(object :
//            LocationUtil.OnLocationOnListener {
//
//            override fun locationStatus(isLocationOn: Boolean) {
//                this@BaseActivity.isGPSEnabled = isLocationOn
//            }
//        })
//    }

    fun hasOpenedDialogs(activity: FragmentActivity): Boolean {
        val fragments: List<Fragment> = activity.supportFragmentManager.fragments
        for (fragment in fragments) {
            if (fragment is DialogFragment) {
                return true
            }
        }
        return false
    }


    //Location Realted Work

    /**
     * Observe LocationViewModel LiveData to get updated location
     */
//    private fun observeLocationUpdates() {
//        getLocationData.observe(this) {
//            Prefs.init().longitude = it.longitude.toString()
//            Prefs.init().latitude = it.latitude.toString()
//            println("Location ${Prefs.init().longitude}")
//
//            CoroutineScope(Dispatchers.IO).launch {
//                try {
//                    val geocoder = Geocoder(this@BaseActivity, Locale.getDefault())
//                    val addresses: List<Address> =
//                        geocoder.getFromLocation(it.latitude, it.longitude, 1)
//                    var currentLocation = ""
//                    if (addresses.isNotEmpty()) {
//                        for (adr in addresses) {
//                            val address =
//                                addresses[0].getAddressLine(0) // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
//
//                            val locality = addresses[0].locality
//                            val subLocality = addresses[0].subLocality
//                            if (subLocality != null) {
//                                currentLocation = "$locality,$subLocality"
//                            } else {
//                                currentLocation = locality
//                            }
//                        }
//                    }
//
//
//                    Prefs.init().currentLocation = "$currentLocation"
//                    println("GeoCoderCurrentLocation ${Prefs.init().currentLocation}")
//                } catch (e: Exception) {
//                    println("GeoCoderExceptionn ${e.message}")
//                    e.printStackTrace()
//                }
//            }
//
//
//        }
//    }


//    fun startLocationUpdates() {
//        val transaction = supportFragmentManager.beginTransaction()
//
//        when {
//            isLocationPermissionsGranted() && LocationUtil(this).checkGPS() -> {
//
//                observeLocationUpdates()
//            }
//            else -> {
//                if (!hasOpenedDialogs(this)) {
//                    EnableLocationDialog(
//                        isGPSEnabled = isGPSEnabled,
//                        onDismissDialog = {
//                            startLocationUpdates()
//                        },
//                        onEnableLocation = {
//                            if (it.startsWith("You have")) {
//                                startActivity(
//                                    EzPermission.appDetailSettingsIntent(
//                                        this
//                                    )
//                                )
//
//                            } else if (!isLocationPermissionsGranted()) {
//                                askLocationPermission()
//                            } else if (!isGPSEnabled) {
//                                turnONGPS()
//                            }
//                        }
//                    ).show(transaction, "SOME_TAG")
//                }
//
//            }
//        }
//    }


    /**
     * Check the availability of location permissions
     */
//    fun isLocationPermissionsGranted(): Boolean {
//        return (EzPermission.isGranted(this, locationPermissions[0])
//                && EzPermission.isGranted(this, locationPermissions[1]))
//    }

    /**
     *
     */
//    fun askLocationPermission() {
//        EzPermission
//            .with(this)
//            .permissions(locationPermissions[0], locationPermissions[1])
//            .request { granted, denied, permanentlyDenied ->
//                if (granted.contains(locationPermissions[0]) &&
//                    granted.contains(locationPermissions[1])
//                ) { // Granted
//                    startLocationUpdates()
//
//                } else if (denied.contains(locationPermissions[0]) ||
//                    denied.contains(locationPermissions[1])
//                ) { // Denied
//
//                    askLocationPermission()
//
//                } else if (permanentlyDenied.contains(locationPermissions[0]) ||
//                    permanentlyDenied.contains(locationPermissions[1])
//                ) { // Permanently denied
//
//                    val transaction = supportFragmentManager.beginTransaction()
//                    EnableLocationDialog(
//                        subTitle = getString(R.string.denied_permission),
//                        isGPSEnabled = isGPSEnabled,
//                        onDismissDialog = {
//                            startLocationUpdates()
//                        },
//                        onEnableLocation = {
//                            if (it.startsWith("You have")) {
//                                startActivity(
//                                    EzPermission.appDetailSettingsIntent(
//                                        this
//                                    )
//                                )
//
//                            } else if (!isLocationPermissionsGranted()) {
//                                askLocationPermission()
//                            } else if (!isGPSEnabled) {
//                                turnONGPS()
//                            }
//                        }
//                    ).show(transaction, "SOME_TAG")
//                } else if (!isGPSEnabled) {
//                    turnONGPS()
//                } else {
//                    observeLocationUpdates()
//                }
//
//            }
//    }


//    @Deprecated("Deprecated in Java")
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if (resultCode == Activity.RESULT_OK) {
//            if (requestCode == 101) {
//                isGPSEnabled = true
//                startLocationUpdates()
//            }
//        } else {
//
//        }
//
//    }


}