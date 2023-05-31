//package com.example.befresh.view.fragment.favourite
//
//import android.R
//import android.os.AsyncTask
//import android.os.Bundle
//import androidx.fragment.app.FragmentActivity
//import com.google.android.gms.maps.CameraUpdateFactory
//import com.google.android.gms.maps.GoogleMap
//import com.google.android.gms.maps.OnMapReadyCallback
//import com.google.android.gms.maps.SupportMapFragment
//import com.google.android.gms.maps.model.BitmapDescriptorFactory
//import com.google.android.gms.maps.model.LatLng
//import com.google.android.gms.maps.model.MarkerOptions
//import com.google.android.gms.maps.model.PolylineOptions
//import org.json.JSONObject
//
//
//class MapsActivity : FragmentActivity(), OnMapReadyCallback {
//    private var mMap: GoogleMap? = null
//    var markerPoints: ArrayList = ArrayList()
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_maps)
//        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
//        val mapFragment = supportFragmentManager
//            .findFragmentById(R.id.map) as SupportMapFragment?
//        mapFragment!!.getMapAsync(this)
//    }
//
//    override fun onMapReady(googleMap: GoogleMap) {
//        mMap = googleMap
//        val sydney = LatLng(-34.0, 151.0)
//        //mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        mMap!!.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 16f))
//        mMap!!.setOnMapClickListener { latLng ->
//            if (markerPoints.size() > 1) {
//                markerPoints.clear()
//                mMap!!.clear()
//            }
//
//            // Adding new item to the ArrayList
//            markerPoints.add(latLng)
//
//            // Creating MarkerOptions
//            val options = MarkerOptions()
//
//            // Setting the position of the marker
//            options.position(latLng)
//            if (markerPoints.size() === 1) {
//                options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
//            } else if (markerPoints.size() === 2) {
//                options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
//            }
//
//            // Add new marker to the Google Map Android API V2
//            mMap!!.addMarker(options)
//
//            // Checks, whether start and end locations are captured
//            if (markerPoints.size() >= 2) {
//                val origin = markerPoints.get(0) as LatLng
//                val dest = markerPoints.get(1) as LatLng
//
//                // Getting URL to the Google Directions API
//                val url = getDirectionsUrl(origin, dest)
//                val downloadTask: DownloadTask = DownloadTask()
//
//                // Start downloading json data from Google Directions API
//                downloadTask.execute(url)
//            }
//        }
//    }
//
//    private inner class DownloadTask :
//        AsyncTask<Any?, Any?, Any?>() {
//        protected fun doInBackground(vararg url: String?): String {
//            var data = ""
//            try {
//                data = downloadUrl(url[0])
//            } catch (e: Exception) {
//                Log.d("Background Task", e.toString())
//            }
//            return data
//        }
//
//        protected fun onPostExecute(result: String?) {
//            super.onPostExecute(result)
//            val parserTask: ParserTask = ParserTask()
//            parserTask.execute(result)
//        }
//    }
//
//    private inner class ParserTask :
//        AsyncTask<String?, Int?, List<List<HashMap>>?>() {
//        // Parsing the data in non-ui thread
//        protected override fun doInBackground(vararg jsonData: String): List<List<HashMap>>? {
//            val jObject: JSONObject
//            var routes: List<List<HashMap>>? = null
//            try {
//                jObject = JSONObject(jsonData[0])
//                val parser = DirectionsJSONParser()
//                routes = parser.parse(jObject)
//            } catch (e: Exception) {
//                e.printStackTrace()
//            }
//            return routes
//        }
//
//        override fun onPostExecute(result: List<List<HashMap>>?) {
//            var points: ArrayList? = null
//            var lineOptions: PolylineOptions? = null
//            val markerOptions = MarkerOptions()
//            for (i in result!!.indices) {
//                points = ArrayList()
//                lineOptions = PolylineOptions()
//                val path: List<HashMap> = result[i]
//                for (j in path.indices) {
//                    val point: HashMap = path[j]
//                    val lat: Double = point.get("lat").toDouble()
//                    val lng: Double = point.get("lng").toDouble()
//                    val position = LatLng(lat, lng)
//                    points.add(position)
//                }
//                lineOptions.addAll(points)
//                lineOptions.width(12f)
//                lineOptions.color(Color.RED)
//                lineOptions.geodesic(true)
//            }
//
//// Drawing polyline in the Google Map for the i-th route
//            mMap!!.addPolyline(lineOptions!!)
//        }
//    }
//
//    private fun getDirectionsUrl(origin: LatLng, dest: LatLng): String {
//
//        // Origin of route
//        val str_origin = "origin=" + origin.latitude + "," + origin.longitude
//
//        // Destination of route
//        val str_dest = "destination=" + dest.latitude + "," + dest.longitude
//
//        // Sensor enabled
//        val sensor = "sensor=false"
//        val mode = "mode=driving"
//
//        // Building the parameters to the web service
//        val parameters = "$str_origin&$str_dest&$sensor&$mode"
//
//        // Output format
//        val output = "json"
//
//        // Building the url to the web service
//        return "https://maps.googleapis.com/maps/api/directions/$output?$parameters"
//    }
//
//    @Throws(IOException::class)
//    private fun downloadUrl(strUrl: String?): String {
//        var data = ""
//        var iStream: InputStream? = null
//        var urlConnection: HttpURLConnection? = null
//        try {
//            val url = URL(strUrl)
//            urlConnection = url.openConnection() as HttpURLConnection
//            urlConnection.connect()
//            iStream = urlConnection.getInputStream()
//            val br = BufferedReader(InputStreamReader(iStream))
//            val sb = StringBuffer()
//            var line: String? = ""
//            while (br.readLine().also { line = it } != null) {
//                sb.append(line)
//            }
//            data = sb.toString()
//            br.close()
//        } catch (e: Exception) {
//            Log.d("Exception", e.toString())
//        } finally {
//            iStream.close()
//            urlConnection.disconnect()
//        }
//        return data
//    }
//}