
package com.gocarhub.utils.ktx

import android.annotation.SuppressLint
import android.app.DownloadManager
import android.content.Context
import android.content.Context.DOWNLOAD_SERVICE
import android.content.ContextWrapper
import android.database.Cursor
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.OpenableColumns
import android.util.Log
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import com.example.befresh.R
import com.example.befresh.utils.LoadingDialog
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.jvm.Throws


internal fun Context.getColorCompat(@ColorRes color: Int) = ContextCompat.getColor(this, color)

internal fun Context.getDrawableCompat(@DrawableRes drawable: Int) =
    ContextCompat.getDrawable(this, drawable)

// function to get offset of current time zone
fun Context.getOffsetOfCurrentTimezone():String
{
    val calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"), Locale.getDefault())
    val currentLocalTime = calendar.time
    val date: DateFormat = SimpleDateFormat("Z")
    return date.format(currentLocalTime)
}

fun Context.downloadFile(url:String,name:String,type:String){

    var mimeType=""
    mimeType = when(type)
    {
        "pdf"->
        {
            "application/pdf"
        }
        else->
        {
            "application/excel"
        }
    }

    val manager = this.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
    val request = DownloadManager.Request(Uri.parse(url))
    request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
    request.setTitle(name)
    request.setMimeType(mimeType)
    request.allowScanningByMediaScanner()

    request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
    manager.enqueue(request)
}

fun Context.downloadFile2(url: String, fileName: String,type:String): Long {
    var downloadReference: Long = 0


    var mimeType=""
    mimeType = when(type)
    {
        "pdf"->
        {
            "application/pdf"
        }
        else->
        {
            "application/excel"
        }
    }

    val uri:Uri?
    var file: File?=null

 var    downloadManager = this.getSystemService(DOWNLOAD_SERVICE) as DownloadManager?
    try {
        val request = DownloadManager.Request(Uri.parse(url))

        //Setting title of request
        request.setTitle(fileName)

        //Setting description of request
        request.setDescription("Your file is downloading")

        request.setMimeType(mimeType)

        //set notification when download completed
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)

        //Set the local destination for the downloaded file to a path within the application's external files directory
          //  request.setDestinationInExternalPublicDir(Uri.fromFile(file!!).toString(), fileName)
            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName)
        request.allowScanningByMediaScanner()

        //Enqueue download and save the referenceId
        downloadReference = downloadManager!!.enqueue(request)
    } catch (e: IllegalArgumentException) {
      //  BaseUtils.showToast(mContext, "Download link is broken or not availale for download")
        Log.e("DOWNLOAD", "Line no: 146,Method: downloadFile: Download link is broken")
    }
    return downloadReference
}

@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
fun Context.checkInternetConnection(): Boolean {
    try {
        val connMgr = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val wifi = connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
        val mobile = connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
        if (mobile != null) {
            if (wifi != null) {
                return mobile.isConnected || wifi.isConnected
            }
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return false
}


// function  for changing the format of date
@SuppressLint("SimpleDateFormat")
fun Context.getDateInDesiredFormat(input: String, output: String, dateString: String): String {
    val df = SimpleDateFormat(input)
    df.timeZone = TimeZone.getTimeZone("UTC")
    val date = df.parse(dateString)
    val print = SimpleDateFormat(output)
    print.timeZone = TimeZone.getDefault()
    return print.format(date)
}

// function  for changing the format of date without conversion into default timeline
@SuppressLint("SimpleDateFormat")
fun Context.getDateInDesiredFormat3(input: String, output: String, dateString: String): String {
    val df = SimpleDateFormat(input)

    val date = df.parse(dateString)
    val print = SimpleDateFormat(output)

    return print.format(date)
}



//fun Context.getNewDate(date:String,timezone:String,format:String): String? {
//    if (date == null) {
//        return ""
//    }
//    val oldFormatter = SimpleDateFormat(ChronoConstants.DateFormats.SERVER_DATE_FORMAT.value)
//    oldFormatter.timeZone = TimeZone.getTimeZone("UTC")
//    var value: Date? = null
//    var dueDateAsNormal = ""
//    try {
//        value = oldFormatter.parse(date)
//        val newFormatter = SimpleDateFormat(format)
//       // newFormatter.timeZone = TimeZone.getDefault()
//        newFormatter.timeZone = TimeZone.getTimeZone(timezone)
//        dueDateAsNormal = newFormatter.format(value)
//    } catch (e: ParseException) {
//        e.printStackTrace()
//    }
//    return dueDateAsNormal
//}


// function to load json from asset folder
fun Context.loadJSONFromAsset(fileName: String): String {
    var json: String? = null
    json = try {
        val `is`: InputStream = this.assets.open(fileName)
        val size: Int = `is`.available()
        val buffer = ByteArray(size)
        `is`.read(buffer)
        `is`.close()
        String(buffer, Charsets.UTF_8)
    } catch (ex: IOException) {
        ex.printStackTrace()
        return ""
    }
    return json!!
}

fun Context.showToast(
    message: String?
) {
    message?.let {
        Toast.makeText(
            this,
            message, Toast.LENGTH_SHORT
        ).show()
    } ?: run {
        Toast.makeText(
            this,
            this.getString(R.string.error_something_went_wrong), Toast.LENGTH_SHORT
        ).show()
    }
}


// function for showing loader
fun Context.showLoader() {
    try {
        LoadingDialog.getLoader().showLoader(this)
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

// function for hiding loading dialog
fun Context.hideLoader() {
    try {
        LoadingDialog.getLoader().dismissLoader()
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

// function for the boilerplate of loader,i.e showing and hiding the loader
fun Context.loaderManager(visibleFlag: Boolean) {
    when (visibleFlag) {
        true ->
            showLoader()
        false ->
            hideLoader()
    }
}

fun Context.lifecycleOwner(): LifecycleOwner? {
    var context: Context? = this

    while (context != null && context !is LifecycleOwner) {
        val baseContext = (context as? ContextWrapper?)?.baseContext
        context = if (baseContext == context) null else baseContext
    }

    return if (context is LifecycleOwner) context else null
}

@Throws(IOException::class)
fun Context.getFile( uri: Uri): File? {
    val destinationFilename: File =
        File(filesDir.path + File.separatorChar + queryName( uri))
    try {
        contentResolver.openInputStream(uri).use { ins ->
            createFileFromStream(
                ins!!,
                destinationFilename
            )
        }
    } catch (ex: java.lang.Exception) {
        ex.printStackTrace()
    }
    return destinationFilename
}

// function for fetching the name from the URI
fun Context.queryName( uri: Uri): String {
    val returnCursor: Cursor = contentResolver.query(uri, null, null, null, null)!!
    val nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
    returnCursor.moveToFirst()
    val name = returnCursor.getString(nameIndex)
    returnCursor.close()
    return name
}

// function for creating file from stream
private fun createFileFromStream(ins: InputStream, destination: File?) {
    try {
        FileOutputStream(destination).use { os ->
            val buffer = ByteArray(4096)
            var length: Int
            while (ins.read(buffer).also { length = it } > 0) {
                os.write(buffer, 0, length)
            }
            os.flush()
        }
    } catch (ex: java.lang.Exception) {
        ex.printStackTrace()
    }
}

// function for creating file for storing any data ex: bitmap, image etc
fun Context.getFile(): File? {
    return try {
        val folder = File("${this.getExternalFilesDir(Environment.DIRECTORY_DCIM)}")
        folder.mkdirs()
        val file = File(folder, "Image_Tmp.jpg")
        if (file.exists())
            file.delete()
        file.createNewFile()
        file
    }catch (e: Exception){
        Log.d("checkXCamera:", "getFile $e")
        null
    }
}
