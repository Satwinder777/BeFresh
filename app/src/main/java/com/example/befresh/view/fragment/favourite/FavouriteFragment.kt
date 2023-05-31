package com.example.befresh.view.fragment.favourite

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.location.Address
import android.location.Geocoder
import android.location.LocationRequest
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.befresh.R
import com.example.befresh.baseView.BaseFragment
import com.example.befresh.databinding.FragmentFavouriteBinding
import com.example.befresh.utils.AppConstants
import com.example.befresh.utils.AppConstants.REQUEST_CODE.AUTOCOMPLETE_REQUEST_CODE
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.GoogleMap.*
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.PlacesClient
import com.google.android.libraries.places.ktx.api.net.awaitFetchPlace
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.coroutines.launch

import java.util.*


const val API_KEY = "AIzaSyAsSMYb29gSo5bq2Ip-3lwx5mhqToxd5W8"
const val API_KEY_ONE = "AIzaSyAA8J4FwN3vqvltGu5osXTtKeexjjq_CkA"
const val NEW_API_KEY = "AIzaSyAosFREQUBhAQBLT7NTR7FBc2JwhL9smbs"
const val NEW1_API_KEY = "AIzaSyBiWumyUM7nfC4_SlLP1j-FIsDuyLpb390"
const val NEW2_API_KEY = "AIzaSyBBgOn1FtmzyjUnX0Xl6xMFqXYFmSEgdZg"

class FavouriteFragment : BaseFragment<FragmentFavouriteBinding>(),
    GoogleMap.OnMapLongClickListener, GoogleMap.OnMarkerDragListener, OnMapReadyCallback  {

    //    private lateinit var location: Location
//    private lateinit var gac: GoogleApiClient
    private lateinit var marker: Marker
    private lateinit var supportMapFragment: SupportMapFragment
    private lateinit var mMap: GoogleMap
    //    private lateinit var geocoder: Geocoder
    private lateinit var mLocationRequest: LocationRequest
    private lateinit var newAddress: Address
    private lateinit var latLng: LatLng
    private lateinit var placesClient: PlacesClient
    var city = ""
    var state = ""
    var sub_add = ""
    var address_line2 = ""
    var zip_code = ""
    var address = ""
    var places = ""
    var country = ""
    var wayLatitude: Double = 0.0
    var wayLongitude: Double = 0.0
    var markerlist = ArrayList<Marker>()
    lateinit var line: Polyline


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }


    override fun setUpUi(binding: FragmentFavouriteBinding) {
        var layer = false
//        searchLocation()
        val mapFragment =
            childFragmentManager.findFragmentById(binding.map1.id) as SupportMapFragment
//        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL)


        binding.savebtn.setOnClickListener {
            val dialog = this.context?.let { it1 -> BottomSheetDialog(it1) }
//             Log.e("thevalueis", "btn event listen"+cartAdapter.sumTotal.toString() )

            val view = layoutInflater.inflate(R.layout.fav_pop_up, null)
            val closebtn = view.findViewById<ImageView>(R.id.closedialog)
            closebtn.setOnClickListener { dialog?.dismiss() }

            dialog?.setCancelable(false)
            dialog?.setContentView(view)
            dialog?.show()

        }
//        mMap.setOnMapLongClickListener(onLocationChanged())

        mapFragment.getMapAsync {
            mMap = it
            mMap!!.isMyLocationEnabled = true
            mMap.setOnMapLongClickListener(this)


//            val sydney = LatLng(30.719, 76.707)
//            mMap!!.addMarker(
//                MarkerOptions().position(sydney).title("ToXSl-Mobile App Development And....")
//                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
//            )
//            mMap!!.moveCamera(CameraUpdateFactory.newLatLng(sydney))


        }
//            var arr = arrayOf(R.id.sc1, R.id.sc2, R.id.sc3, R.id.sc4, R.id.sc5, R.id.sc6, R.id.sc7)


//        var Address = arrayOf(
//            Address("Mohali", 30.704649, 76.717873),
//            Address("Kharar", 30.719870, 76.648180),
//            Address("Amritsar", 31.633980, 74.872261),
//            Address("Chandighar", 21.135580, 86.643913),
//            Address("NewDelhi", 40.741895, -73.989308)
//        )
        binding.sc1.setOnClickListener {
//            Log.e("newtest", "binding.sc1.setOnClickListener ")
//            mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL)
//            var Mohali = LatLng(30.704649, 76.717873)
//            mMap!!.addMarker(
//                MarkerOptions().position(Mohali)
//                    .title("ToXSl-Mobile App Development And....")
//            )
//            mMap.moveCamera(CameraUpdateFactory.newLatLng(Mohali))
//            mMap.animateCamera(CameraUpdateFactory.zoomTo(11F));Log.e("sc", "sc1 call")
            addPath()
        }
        binding.sc2.setOnClickListener {

            if (markerlist.count() == 2) {
                getDirectionURL()
                mMap.animateCamera(
                    CameraUpdateFactory.newLatLngZoom(
                        markerlist[0].position, 14F
                    )
                )

            }
//            getDirectionURL()

            Log.e("newtest", "binding.sc2.setOnClickListener ")
//            try{
//                searchLocation()
//            }
//            catch (e:Exception){
//                Log.e("newsc", "setUpUi: "+e.message )
//            }
//            getlatlng()

            var Kharar = LatLng(30.719870, 76.648180)
            mMap!!.addMarker(
                MarkerOptions().position(Kharar).title("Kharar")
            )
            mMap!!.moveCamera(CameraUpdateFactory.newLatLng(Kharar))
            mMap.animateCamera(CameraUpdateFactory.zoomTo(11F));
            Log.e("newsc", "sc2 call")
        }
        binding.sc3.setOnClickListener {
            var Amritsar = LatLng(31.633980, 74.872261)
            mMap!!.addMarker(
                MarkerOptions().position(Amritsar).title("Kharar")
            )
            mMap!!.moveCamera(CameraUpdateFactory.newLatLng(Amritsar))
            mMap.animateCamera(CameraUpdateFactory.zoomTo(11F));
            Log.e("newsc", "sc3 call")
        }
        binding.sc4.setOnClickListener {
            var Chandighar = LatLng(21.135580, 86.643913)
            mMap!!.addMarker(
                MarkerOptions().position(Chandighar).title("Kharar")
            )
            mMap!!.moveCamera(CameraUpdateFactory.newLatLng(Chandighar))
            mMap.animateCamera(CameraUpdateFactory.zoomTo(11F));
            Log.e("newsc", "sc4 call")
        }
        binding.sc5.setOnClickListener {
            var NewDelhi = LatLng(40.741895, -73.989308)
            mMap!!.addMarker(
                MarkerOptions().position(NewDelhi).title("Kharar")
            )
            mMap!!.moveCamera(CameraUpdateFactory.newLatLng(NewDelhi))
            mMap.moveCamera(CameraUpdateFactory.newLatLng(NewDelhi));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(11F));
            Log.e("newsc", "sc5 call")
        }
        binding.sc6.setOnClickListener {
            var Kharar = LatLng(30.719870, 76.648180)
            mMap!!.addMarker(
                MarkerOptions().position(Kharar).title("Kharar")
            )
            mMap!!.moveCamera(CameraUpdateFactory.newLatLng(Kharar))
            mMap.animateCamera(CameraUpdateFactory.zoomTo(11F));
            Log.e("newsc", "sc6 call")
        }
        binding.sc7.setOnClickListener {
            var NewDelhi = LatLng(40.741895, -73.989308)
            mMap!!.addMarker(
                MarkerOptions().position(NewDelhi).title("Kharar")
            )
            mMap!!.moveCamera(CameraUpdateFactory.newLatLng(NewDelhi))
            mMap.moveCamera(CameraUpdateFactory.newLatLng(NewDelhi));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(11F));
            Log.e("newsc", "sc7 call")
        }
        binding.closeBtn.setOnClickListener {
            Toast.makeText(this.context, "click", Toast.LENGTH_SHORT).show()
        }
        binding.layer1.setOnClickListener {

            if (layer == false) {
//                mMap.clear()
                mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL)

                Log.e("newsc", "if block executes")
                layer = true
            } else {
//                mMap.clear()
//                mMap.setMapStyle(GoogleMap.MAP_TYPE_NORMAL)
                mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE)
                layer = false
                Log.e("newsc", "else block executes")

            }


        }

        binding.searchBtn.setOnClickListener {

//            binding.searchBtn.visibility = View.GONE

            addAddress()

         }


    }

    private fun initView() {


        if (!Places.isInitialized()) {
            Places.initialize(
                requireActivity(), getString(R.string.google_maps_key), Locale.US
            )
        }
        placesClient = Places.createClient(requireActivity())
    }


    override fun getLayoutResID(): Int {
        return R.layout.fragment_favourite
    }


    override fun onMarkerDrag(p0: Marker) {
        try {
            if (line.isVisible){
                line.remove()
            }
        }
        catch (e:Exception){
            Log.e("newsx", "onMarkerDrag: "   )
        }
        addPath()
    }

    override fun onMarkerDragEnd(p0: Marker) {
        latLng = LatLng(marker.position.latitude, marker.position.longitude)


    }

    override fun onMarkerDragStart(p0: Marker) {

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == AUTOCOMPLETE_REQUEST_CODE) {
            when (resultCode) {
                Activity.RESULT_OK -> {
                    data?.let {
                        val place = Autocomplete.getPlaceFromIntent(data)
                        val placeId = place.id
                        val placeFields = listOf(
                            Place.Field.NAME,
                            Place.Field.ID,
                            Place.Field.LAT_LNG,
                            Place.Field.ADDRESS
                        )

                        lifecycleScope.launch {
                            try {
                                val response = placesClient.awaitFetchPlace(placeId, placeFields)
                                getloactionData(
                                    latitude = response.place.latLng.latitude,
                                    longitude = response.place.latLng.longitude,
                                    place.name

                                )
                            } catch (e: Exception) {
                                Log.e("activityrslt", "onActivityResult:" + e.message)
                            }
                        }
                    }
                }

            }
        }
    }

    //////
    private fun addAddress() {


        val fields = listOf(Place.Field.ID, Place.Field.NAME)

        // Start the autocomplete intent.
        val intent = Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY, fields)
            .build(requireActivity())
        startActivityForResult(intent, AppConstants.REQUEST_CODE.AUTOCOMPLETE_REQUEST_CODE)
        try {
            if (marker.isVisible ) {

                marker.remove()
//                if (markerlist.s){
//                    markerlist.remove(markerlist[0])
//                }
            }
        } catch (e: Exception) {
            Log.e("newsc", "setUpUi: " + e.message)
        }
    }

    private fun getloactionData(latitude: Double, longitude: Double, placeName: String) {

        val geocoder: Geocoder = Geocoder(requireActivity(), Locale.getDefault())

        //////
        var placeName = LatLng(latitude, longitude)

        var a = mMap!!.addMarker(MarkerOptions().position(placeName).draggable(true))!!

        mMap.moveCamera(CameraUpdateFactory.newLatLng(placeName));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(5F))
        if (markerlist.size<2){
            markerlist.add(a)
        }
        else{
            markerlist.remove(markerlist[0])
        }

        Log.e("markerList", "markerList empty works" + markerlist)


        val addresses: List<Address> = geocoder.getFromLocation(
            latitude, longitude, 1
        )

        wayLatitude = latitude
        wayLongitude - longitude


//        Log.e("latlong", "getloactionData: the lat long is:"+latitude+longitude )
        if (addresses != null && addresses.size > 0) {
            country = addresses[0].countryName
            city = addresses[0].locality
            state = addresses[0].adminArea
//            sub_add = addresses[0].thoroughfare
            if (addresses[0].thoroughfare != null) {
                sub_add = addresses[0].thoroughfare
            }
            val local = addresses[0].locale
            val f = addresses[0].featureName
            address_line2 = addresses[0].getAddressLine(0)
            zip_code = addresses[0].postalCode

//            binding.apply {
//                etState.setText(state)
//                etCity.setText(city)
//                etAddressLine1.setText(sub_add)
//                etAddressLine2.setText(address_line2)
//                etZipCode.setText(zip_code)
//            }

            binding.apply {
//                etAddressLine1.setText(f + " ")
//                etAddressLine1.append(sub_add)
//
//                etState.setText(state)
//                etAddressLine2.setText(address_line2)
//                etZipCode.setText(zip_code)
//                etCity.setText(city)
            }
//            et_signup_address1.setText(f + " ")
//            et_signup_address1.append(sub_add)
//
//            et_signup_State.setText(state)
//            et_signup_address2.setText(address_line2)
//            et_signup_zipcode.setText(zip_code)
//            et_signup_city.setText(city)


        }


    }


    override fun onMapLongClick(p0: LatLng) {


        try {
            var a = mMap.addMarker(MarkerOptions().position(p0).draggable(true))

            if (a != null) {
                markerlist.add(a)
            }


        } catch (e: Exception) {
            Log.e("newtest", "onMapLongClick: " + e.message)
        }


    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        addPath()
        getDirectionURL()

        Log.e("newtest", "on map ready ")

    }

    private fun addPath(

        firstMarker: LatLng = markerlist[0].position, secondMarker: LatLng = markerlist[1].position
    ) {
        var k:Boolean =  false
        val options = PolylineOptions().width(5f).color(Color.BLUE).geodesic(true)
        var firstMarker: LatLng = LatLng(firstMarker.latitude, firstMarker.longitude)
        var secondMarker: LatLng = LatLng(secondMarker.latitude, secondMarker.longitude)

        Log.e("newtest", "addPath: ")
        if (k==false){
            ///////
            try {
                if (markerlist.count() == 2) {
                    options.add(firstMarker, secondMarker)
                    line = mMap.addPolyline(options)

                    Log.e("newtest", "  if block addPath: ")
                }
                else{
                    line.remove()
                }
            }
            catch (e:Exception){
                Log.e("loge", "addPath: "+e.message )
            }
            k=true
        }
        else{
            //////
            if (markerlist.count() == 2) {
                options.add(firstMarker, secondMarker)
//                line = mMap.addPolyline(options)
//                line = mMap.

//                Log.e("newtest", "  if block addPath: ")
            }
            else{
                line.remove()
            }
            k=false
        }


  var a = "2+2+2"

    }




    private fun getDirectionURL(
        origin: LatLng = markerlist[0].position,
        dest: LatLng = markerlist[1].position,
        secret: Int = R.string.google_maps_key
    ): String {
        var url =
            "https://maps.googleapis.com/maps/api/directions/json?origin=${origin.latitude},${origin.longitude}" + "&destination=${dest.latitude},${dest.longitude}" + "&sensor=false" + "&mode=driving" + "&key=$secret"
        return url
    }







}










