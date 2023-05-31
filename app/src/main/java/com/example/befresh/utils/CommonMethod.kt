package com.example.befresh.utils

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.*
import android.graphics.drawable.ColorDrawable
import android.media.ExifInterface
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.OpenableColumns
import android.text.format.DateFormat
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.webkit.URLUtil
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.befresh.R
import com.example.befresh.application.MyApplicationClass
import java.io.*
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import java.util.regex.Pattern


@SuppressLint("StaticFieldLeak")
object CommonMethod {
    lateinit var mContext: Context
    operator fun invoke(applicationContext: Context?) {
        mContext = applicationContext!!
    }

    fun addFragmentActivity(
        fragmentManager: FragmentManager?,
        fragment: Fragment,
        container: Int
    ) {
        fragmentManager?.beginTransaction()
            ?.add(container, fragment)
            ?.commit()
    }

    fun checkIfHasNetwork(): Boolean {
        val connectivityManager =
            MyApplicationClass.getInstance()
                .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }

    private lateinit var mAlertDialog: AlertDialog
    fun showBaseProgressDialog(context: Context) {
        val mAlertDialogBuilder = AlertDialog.Builder(context)
        mAlertDialogBuilder.setCancelable(false)
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.view_progress_dialog, null)
        view.findViewById<ProgressBar>(R.id.progressBarPB).apply {
            // indeterminateDrawable = FoldingCube()
        }
        view.findViewById<TextView>(R.id.progressMessageTV).apply {
            text = ""
        }
        mAlertDialogBuilder.setView(view)
        mAlertDialog = mAlertDialogBuilder.create()
        mAlertDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        try {

            if (mAlertDialog.isShowing.not()) {
                mAlertDialog.show()
            }

        } catch (e: Exception) {
            e.printStackTrace()
            Log.e("appClosedHere", " AppClosed " + e.message)
        }

        val layoutParams = WindowManager.LayoutParams()
        val window = mAlertDialog.window
        window!!.setGravity(Gravity.CENTER)
        layoutParams.copyFrom(window.attributes)
        // show dialog on full screen
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT
        layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT
        window.attributes = layoutParams
    }

//    fun googleLogout(context: Context) {
//        val googleSignInOptions =
//            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).build()
//        val googleSignInClient = GoogleSignIn.getClient(context, googleSignInOptions)
//        googleSignInClient.signOut()
//    }

    fun hideBaseProgressDialog() {
        if (this::mAlertDialog.isInitialized && mAlertDialog.isShowing) {
            try {
                mAlertDialog.dismiss()
            } catch (e: Exception) {
                e.printStackTrace()
                Log.e("appClosedHere", " AppClosed " + e.message)
            }
        }
    }

    fun replaceFragmentActivityWithoutStack(
        fragmentManager: FragmentManager?,
        fragment: Fragment, container: Int
    ) {
        fragmentManager!!.beginTransaction()
            .replace(container, fragment)
            .commit()
    }

    // this method is used to validation in password field
//    fun isValidPassword(password: String): Boolean {
//        return Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!_])(?=\\S+$).{10,20}$")
//            .matcher(password).matches()
//    }

    fun replaceFragmentScreenWithBackStack(
        fragmentManager: FragmentManager?,
        fragment: Fragment, container: Int
    ) {
        fragmentManager!!.beginTransaction()
            .replace(container, fragment)
            .addToBackStack(null)
            .commit()
    }

    fun destroyFragment(
        fragmentManager: FragmentManager?,
        fragment: Fragment, container: Int
    ) {
        fragmentManager!!.beginTransaction().remove(fragment).commitAllowingStateLoss()

        fragmentManager.beginTransaction()
            .replace(container, fragment)
            .addToBackStack(null)
            .commit()
    }


    fun toastUnderDevelopment(context: Context) {
//        Toast.makeText(context, context.getString(R.string.under_development), Toast.LENGTH_SHORT)
//            .show()
    }

    fun showToast(context: Context, message: String) {
        if ((message.equals("Read timed out").not()) && message.equals("timeout").not()) {
            Toast.makeText(context, message, Toast.LENGTH_SHORT)
                .show()
        }
    }

    fun windowFlagLyout(activity: Activity) {
//            activity.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // activity!!.window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            activity.window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            activity.window.decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        } else {
            // < 6.0
        }
    }

//    fun showToast(context: String, message: String) {
//        if (message == AppConstants.UNAUTHENTICATED) {
//            Toast.makeText(context.(R.string.session_expired), Toast.LENGTH_SHORT)
//                .show()
////            Handler().postDelayed({
////                SharedPref.instance.clear()
////                context.startActivity(
////                    Intent(mContext, SplashScreenActivity::class.java)
////                        .setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
////                        .putExtra("logoutScreen", "logout")
////                )
////                context.finishAffinity()
////            }, 1000)
//        } else
//            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
//    }
//


    const val PER_CAMERA = Manifest.permission.CAMERA
    const val PER_WRITE_STORAGE = Manifest.permission.WRITE_EXTERNAL_STORAGE
    const val PER_READ_STORAGE = Manifest.permission.READ_EXTERNAL_STORAGE

    fun checkPermission(
        context: Context?,
        permission: String?
    ): Boolean {
        return ContextCompat.checkSelfPermission(
            context!!,
            permission!!
        ) == PackageManager.PERMISSION_GRANTED
    }

    //define functions for each permission
    fun isCameraGranted(context: Context?): Boolean {
        return checkPermission(
            context,
            PER_CAMERA
        )
    }

    fun isWriteStorageGranted(context: Context?): Boolean {
        return checkPermission(
            context,
            PER_WRITE_STORAGE
        )
    }

    fun isReadStorageGranted(context: Context?): Boolean {
        return checkPermission(
            context,
            PER_READ_STORAGE
        )
    }

    fun requestPermissions(
        activity: Activity?,
        permissionId: Int,
        vararg permissions: String?
    ) {
        ActivityCompat.requestPermissions(activity!!, permissions, permissionId)
    }


    fun createImageFile(mContext: Context): File {
        val imageFileName = "Whealth Connect" + "_" + System.currentTimeMillis()
        val storageDir = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                "Pictures"
            )
        } else {
            File(Environment.getExternalStorageDirectory(), "Whealth Connect")
        }

        if (!storageDir.exists()) {
            storageDir.mkdir()
        }
        var image: File
        try {
            image = File.createTempFile(imageFileName, ".jpg", storageDir)
        } catch (e: IOException) {
            e.printStackTrace()
            val newImageFileName = "Whealth Connect" + "-" + System.currentTimeMillis() + ".jpg"
            image = File(storageDir, newImageFileName)
            try {
                image.createNewFile()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        return image
    }


    fun compressImage(filePath: String?): String? {
        var scaledBitmap: Bitmap? = null
        val options = BitmapFactory.Options()

// by setting this field as true, the actual bitmap pixels are not loaded in the memory. Just the bounds are loaded. If
// you try the use the bitmap here, you will get null.
        options.inJustDecodeBounds = true
        var bmp = BitmapFactory.decodeFile(filePath, options)
        var actualHeight = options.outHeight
        var actualWidth = options.outWidth

// max Height and width values of the compressed image is taken as 816x612
        val maxHeight = 1024.0f
        val maxWidth = 1024.0f
        var imgRatio = (actualWidth / actualHeight).toFloat()
        val maxRatio = maxWidth / maxHeight

// width and height values are set maintaining the aspect ratio of the image
        if (actualHeight > maxHeight || actualWidth > maxWidth) {
            if (imgRatio < maxRatio) {
                imgRatio = maxHeight / actualHeight
                actualWidth = (imgRatio * actualWidth).toInt()
                actualHeight = maxHeight.toInt()
            } else if (imgRatio > maxRatio) {
                imgRatio = maxWidth / actualWidth
                actualHeight = (imgRatio * actualHeight).toInt()
                actualWidth = maxWidth.toInt()
            } else {
                actualHeight = maxHeight.toInt()
                actualWidth = maxWidth.toInt()
            }
        }

// setting inSampleSize value allows to load a scaled down version of the original image
        options.inSampleSize = calculateInSampleSize(options, actualWidth, actualHeight)

// inJustDecodeBounds set to false to load the actual bitmap
        options.inJustDecodeBounds = false

// this options allow android to claim the bitmap memory if it runs low on memory
        options.inPurgeable = true
        options.inInputShareable = true
        options.inTempStorage = ByteArray(16 * 1024)
        try {
// load the bitmap from its path
            bmp = BitmapFactory.decodeFile(filePath, options)
        } catch (exception: OutOfMemoryError) {
            exception.printStackTrace()
        }
        try {
            scaledBitmap = Bitmap.createBitmap(actualWidth, actualHeight, Bitmap.Config.ARGB_8888)
        } catch (exception: OutOfMemoryError) {
            exception.printStackTrace()
        }
        val ratioX = actualWidth / options.outWidth.toFloat()
        val ratioY = actualHeight / options.outHeight.toFloat()
        val middleX = actualWidth / 2.0f
        val middleY = actualHeight / 2.0f
        val scaleMatrix = Matrix()
        scaleMatrix.setScale(ratioX, ratioY, middleX, middleY)
        val canvas = Canvas(scaledBitmap!!)
        canvas.setMatrix(scaleMatrix)
        canvas.drawBitmap(
            bmp,
            middleX - bmp.width / 2,
            middleY - bmp.height / 2,
            Paint(Paint.FILTER_BITMAP_FLAG)
        )

// check the rotation of the image and display it properly
        val exif: ExifInterface
        try {
            exif = ExifInterface(filePath!!)
            val orientation = exif.getAttributeInt(
                ExifInterface.TAG_ORIENTATION, 0
            )
            Log.d("EXIF", "Exif: $orientation")
            val matrix = Matrix()
            if (orientation == 6) {
                matrix.postRotate(90f)
                Log.d("EXIF", "Exif: $orientation")
            } else if (orientation == 3) {
                matrix.postRotate(180f)
                Log.d("EXIF", "Exif: $orientation")
            } else if (orientation == 8) {
                matrix.postRotate(270f)
                Log.d("EXIF", "Exif: $orientation")
            }
            scaledBitmap = Bitmap.createBitmap(
                scaledBitmap, 0, 0,
                scaledBitmap.width, scaledBitmap.height, matrix,
                true
            )
        } catch (e: IOException) {
            e.printStackTrace()
        }
        val out: FileOutputStream
        val filename = getFilename()
        try {
            out = FileOutputStream(filename)

// write the compressed bitmap at the destination specified by filename.
            scaledBitmap!!.compress(Bitmap.CompressFormat.JPEG, 70, out)
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        }
        return filename
    }

    fun calculateInSampleSize(options: BitmapFactory.Options, reqWidth: Int, reqHeight: Int): Int {
        val height = options.outHeight
        val width = options.outWidth
        var inSampleSize = 1

        if (height > reqHeight || width > reqWidth) {
            val heightRatio = Math.round(height.toFloat() / reqHeight.toFloat())
            val widthRatio = Math.round(width.toFloat() / reqWidth.toFloat())
            if (heightRatio < widthRatio) {
                inSampleSize = heightRatio
            } else {
                inSampleSize = widthRatio
            }

        }
        var totalPixels = width * height
        var totalReqPixelsCap = reqWidth * reqHeight * 2
        while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap) {
            inSampleSize++
        }

        return inSampleSize
    }

    fun getFilename(): String? {
        val file = Environment.getExternalStoragePublicDirectory(
            Environment.DIRECTORY_PICTURES
        )
// val file = File(Environment.getExternalStorageDirectory().path, ".spongy/Images")
        if (!file.exists()) {
            file.mkdirs()
        }
        return file.absolutePath + "/" + System.currentTimeMillis() + ".jpg"
    }

    fun getRealPathFromUri(context: Context, uri: Uri?): File? {
        try {
            val inputStream = context.contentResolver.openInputStream(uri!!)
            val fileName: String = getFileName(context, uri)!!
            val splitName: Array<String> = splitFileName(fileName)!!
            var tempFile = File.createTempFile(splitName[0], splitName[1])
            tempFile = rename(tempFile, fileName)
            tempFile.deleteOnExit()
            val out = FileOutputStream(tempFile)
            if (inputStream != null) {
                copy(inputStream, out)
                inputStream.close()
            }
            out.close()
            return tempFile
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
        return null
    }

    @SuppressLint("Range")
    private fun getFileName(context: Context, uri: Uri): String? {
        var result: String? = null
        if (uri.scheme != null && uri.scheme == "content") {
            val cursor = context.contentResolver.query(uri, null, null, null, null)
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME))
                }
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
            } finally {
                cursor?.close()
            }
        }
        if (result == null) {
            result = uri.path
            if (result != null) {
                val cut = result.lastIndexOf(File.separator)
                if (cut != -1) {
                    result = result.substring(cut + 1)
                }
            }
        }
        return result
    }

    private fun rename(file: File, newName: String): File? {
        val newFile = File(file.parent, newName)
        if (newFile != file) {
            if (newFile.exists() && newFile.delete()) {
                Log.d("FileUtil", "Delete old $newName file")
            }
            if (file.renameTo(newFile)) {
                Log.d("FileUtil", "Rename file to $newName")
            }
        }
        return newFile
    }

    private fun splitFileName(fileName: String): Array<String>? {
        var name = fileName
        var extension = ""
        val i = fileName.lastIndexOf(".")
        if (i != -1) {
            name = fileName.substring(0, i)
            extension = fileName.substring(i)
        }
        return arrayOf(name, extension)
    }

    val EOF = -1
    private fun copy(input: InputStream, output: OutputStream) {
        var n: Int
        val buffer = ByteArray(DEFAULT_BUFFER_SIZE)
        while (EOF != input.read(buffer).also { n = it }) {
            output.write(buffer, 0, n)
        }
    }


    fun changeImageOrientation(photoPath: String?, bitmap: Bitmap): Bitmap? {
        var ei: ExifInterface? = null
        try {
            ei = ExifInterface(photoPath!!)
        } catch (e: IOException) {
            e.printStackTrace()
        }
        var orientation = 0
        if (ei != null) {
            orientation = ei.getAttributeInt(
                ExifInterface.TAG_ORIENTATION,
                ExifInterface.ORIENTATION_UNDEFINED
            )
        }
        val rotatedBitmap: Bitmap
        rotatedBitmap = when (orientation) {
            ExifInterface.ORIENTATION_ROTATE_90 -> rotateImage(
                bitmap,
                90f
            )
            ExifInterface.ORIENTATION_ROTATE_180 -> rotateImage(
                bitmap,
                180f
            )
            ExifInterface.ORIENTATION_ROTATE_270 -> rotateImage(
                bitmap,
                270f
            )
            ExifInterface.ORIENTATION_NORMAL -> bitmap
            else -> bitmap
        }
        return rotatedBitmap
    }

    fun rotateImage(source: Bitmap, angle: Float): Bitmap {
        val matrix = Matrix()
        matrix.postRotate(angle)
        return Bitmap.createBitmap(
            source, 0, 0, source.width, source.height,
            matrix, true
        )
    }

    fun getResizedBitmap(
        bitmapImage: Bitmap?,
        bitmapWidth: Int,
        bitmapHeight: Int
    ): Bitmap? {
        return Bitmap.createScaledBitmap(bitmapImage!!, bitmapWidth, bitmapHeight, true)
    }

    fun phoneIsOnline(context: Context): Boolean {
        val cm =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val netInfo = cm.activeNetworkInfo
        return netInfo != null && netInfo.isConnectedOrConnecting
    }

    //this method return the phone is Connected with internet or Not
    fun phoneIsOnline(): String? {
        val cm =
            mContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val netInfo = cm.activeNetworkInfo
        return if (netInfo != null && netInfo.isConnectedOrConnecting) {
            "Something went wrong. Please try again later."
        } else {
            "Internet connection not available!"
        }
    }

    fun logPrint(msg: String) {
        Log.e("FreeSpot", msg)
    }


    fun createFileFromBitMap(bitmap: Bitmap): File? {
        val file: File
        val imageFileName =
            "Whealth Connect" + "-" + System.currentTimeMillis() + ".jpg"
        val myDirectory = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                "Pictures"
            )
        } else {
            File(Environment.getExternalStorageDirectory(), "Whealth Connect")
        }
        if (!myDirectory.exists()) {
            myDirectory.mkdir()
        }
        file = File(myDirectory, imageFileName)
        try {
            file.createNewFile()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        //Convert bitmap to byte array
        val bos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos)
        val bitmapData = bos.toByteArray()

        //write the bytes in file
        val fos: FileOutputStream
        try {
            fos = FileOutputStream(file)
            fos.write(bitmapData)
            fos.flush()
            fos.close()
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
        return file
    }

    fun getDateForDisplay(getDateStamp: String): String {
        var date: Date? = null
        try {
            date = SimpleDateFormat("yyyy-MM-dd").parse(getDateStamp)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return SimpleDateFormat("EEEE, MM-dd-yyyy", Locale.ENGLISH).format(date)
    }

    fun ConvertDateFormat(dateType: String): String {

        val inputFormat: java.text.DateFormat = SimpleDateFormat("dd/MM/yyyy")//22/6/2022
        val outputFormat: java.text.DateFormat = SimpleDateFormat("MM-dd-yyyy")
        val inputDateStr = dateType
        val date: Date = inputFormat.parse(inputDateStr)
        val outputDateStr: String = outputFormat.format(date)

        return outputDateStr

    }

    fun ConvertDateFormatFullDay(dateType: String): String {

        val inputFormat: java.text.DateFormat = SimpleDateFormat("dd/MM/yyyy")//22/6/2022
        val outputFormat: java.text.DateFormat = SimpleDateFormat("MM-dd-yyyy")
        val inputDateStr = dateType
        val date: Date = inputFormat.parse(inputDateStr)
        val outputDateStr: String = outputFormat.format(date)

        return outputDateStr

    }

    fun ConvertDateFormatSecond(dateType: String): String {

        val inputFormat: java.text.DateFormat = SimpleDateFormat("yyyy-MM-dd")//2022-06-26
        val outputFormat: java.text.DateFormat = SimpleDateFormat("MM-dd-yyyy")
        val inputDateStr = dateType
        val date: Date = inputFormat.parse(inputDateStr)
        val outputDateStr: String = outputFormat.format(date)

        return outputDateStr

    }

    fun ConvertDateFormatApiFormat(dateType: String): String {

        val inputFormat: java.text.DateFormat = SimpleDateFormat("dd/MM/yyyy")//22/6/2022
        val outputFormat: java.text.DateFormat = SimpleDateFormat("yyyy-MM-dd")
        val inputDateStr = dateType
        val date: Date = inputFormat.parse(inputDateStr)
        val outputDateStr: String = outputFormat.format(date)

        return outputDateStr

    }

    @Throws(ParseException::class)
    fun getLocalTime(getDate: String?): String? {
//        2021-06-07T11:59:29.626Z
        val df = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH)
        df.timeZone = TimeZone.getTimeZone("UTC")
        val date = df.parse(getDate!!)
        val df1 = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH)
//        val df1 = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
        df.timeZone = TimeZone.getDefault()
        return df1.format(date!!)
    }

    fun getDate(getDateStamp: String, type: Int): String {
        val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH)
        val date = format.parse(getDateStamp)
//"yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
        val day = DateFormat.format("dd", date) as String
        val monthNumber = DateFormat.format("MMM", date) as String
        val year = DateFormat.format("yyyy", date) as String
        val dateFormate = DateFormat.format("MM/dd/yyyy hh:mm aa", date) as String
        val dateFormateMonth = DateFormat.format("MM-dd-yyyy", date) as String
        val year_month_date = DateFormat.format("yyyy-mm-dd", date) as String
        val timeGet = DateFormat.format("hh:mm aa", date) as String


        return if (type == 0) {
            day
        } else if (type == 1) {
            dateFormate
        } else if (type == 2) {
            timeGet
        } else if (type == 3) {
            dateFormateMonth
        } else if (type == 4) {
            year_month_date
        } else {
            year
        }
    }

    fun getCurrentDateCkeck_for_timeSlot(): String {
        return SimpleDateFormat("MM-dd-yyyy", Locale.getDefault()).format(Date())
    }

    fun compareTimeAfter(sTime: String, currentTime: String): Boolean {
        var boolean = false
        val dateFormat = SimpleDateFormat("hh:mm a")
        val EndTime = dateFormat.parse(sTime)
        val CurrentTime = dateFormat.parse(currentTime)
        if (EndTime.after(CurrentTime)) {
            println("timeeee end ")
            boolean = true
        }
        return boolean
    }

    fun timeChange24to12(time: String?): String? {
        var newTime = ""
        val sdf = SimpleDateFormat("HH:mm")
        val sdfs = SimpleDateFormat("hh:mm a")
        val dt: Date
        try {
            dt = sdf.parse(time)

            println("Time Display: " + sdfs.format(dt)) // <-- I got result here
            newTime = sdfs.format(dt)
        } catch (e: ParseException) {
            e.printStackTrace()
            Log.e("timeconverf", e.message.toString())
        }
        return newTime
    }

    fun getCurrentTime(getDateStamp: String): String {
        val format = SimpleDateFormat("EEE MMM dd HH:mm:ss zzzz yyyy", Locale.ENGLISH)
        val date = format.parse(getDateStamp)

        val day = DateFormat.format("dd", date) as String
        val monthNumber = DateFormat.format("EEE", date) as String
        val year = DateFormat.format("yyyy", date) as String
        val dateFormate = DateFormat.format("yyyy-MM-dd", date) as String
        val timeGet = DateFormat.format("hh:mm aa", date) as String

        val sdf = SimpleDateFormat("yyyy-MM-dd")
        val c = Calendar.getInstance()
        try {
            c.time = sdf.parse(dateFormate)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        c.add(
            Calendar.DAY_OF_MONTH,
            90
        ) // number of days to add, can also use Calendar.DAY_OF_MONTH in place of Calendar.DATE

        val sdf1 = SimpleDateFormat("MM-dd-yyyy")
        val output = sdf.format(c.time)


        return timeGet
    }

    @SuppressLint("CheckResult")
    fun LoadImage(view: ImageView, imageUrl: String?) {
        if (imageUrl != null) {
            val options: RequestOptions = RequestOptions()
                //  .placeholder(CommonMethod.imageProgressBar(mContext))
                .priority(Priority.HIGH)
                .dontAnimate()
                .dontTransform()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
//            val requestOptions = RequestOptions()
//                  requestOptions.placeholder(R.drawable.user)
//              requestOptions.error(R.drawable.user)
//            requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL)
//                .priority(Priority.HIGH)
            Glide.with(view.context)
                .load(imageUrl)
                .apply(options)
                .into(view)
        }
    }


//    fun imageProgressBar(context: Context): CircularProgressDrawable {
//        val circularProgressDrawable =
//            CircularProgressDrawable(context)
//        circularProgressDrawable.strokeWidth = 5f
//        circularProgressDrawable.centerRadius = 30f
//        circularProgressDrawable.start()
//        return circularProgressDrawable
//    }

    fun covertTimeToTextNewPost(dataDate: String?): String? {
        var convTime: String? = null
        val prefix = ""
        val suffix = ""
        //        String suffix = "";
        try {
            val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
//            val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            val pasTime: Date = dateFormat.parse(dataDate)
            val nowTime = Date()
            val dateDiff: Long = nowTime.time - pasTime.time
            val second: Long = TimeUnit.MILLISECONDS.toSeconds(dateDiff)
            val minute: Long = TimeUnit.MILLISECONDS.toMinutes(dateDiff)
            val hour: Long = TimeUnit.MILLISECONDS.toHours(dateDiff)
            val day: Long = TimeUnit.MILLISECONDS.toDays(dateDiff)
            val d = SimpleDateFormat("MMMM dd", Locale.getDefault())
            val week_day = SimpleDateFormat("EEEE", Locale.getDefault())
            val date_month: String = d.format(pasTime)
            val day_name: String = week_day.format(pasTime)
            if (second < 60) {
                // convTime = "Few seconds ago"
                convTime = "Few sec ago"
            } else if (minute < 60) {
                var minutesText = ""
                minutesText = if (minute == 1L) {
                    // " Minute "
                    " minute ago"
                } else {
                    " minutes ago"
                    // " Minutes "
                }
                convTime = minute.toString() + minutesText + suffix
            } else if (hour < 24) {
                var hourText = ""
                hourText = if (hour == 1L) {
                    //  " Hour "
                    " hour ago"
                } else {
                    // " Hours "
                    " hours ago"
                }
                //                convTime = " Today ";
                convTime = hour.toString() + hourText + suffix
            } else if (day >= 7) {
                if (day > 30) {
                    var monthText = ""
                    monthText = if (day / 30 == 1L) {
                        " month ago"

                    } else {
                        " months ago"

                    }
                    convTime = (day / 30).toString() + monthText + suffix
                    //                    convTime = date_month;
                } else if (day > 360) {
                    var yearText = ""
                    yearText = if (day / 360 == 1L) {

                        " year ago"
                    } else {
                        " years ago"
                    }
                    convTime = (day / 360).toString() + yearText + suffix
                } else {
                    var weekText = ""
                    weekText = if (day / 7 == 1L) {
                        " week ago"
                    } else {
                        " weeks ago"
                    }
                    convTime = (day / 7).toString() + weekText + suffix
                    //                    convTime = date_month;
                }
            } else if (day < 7) {
                var dayText = ""
                dayText = if (day == 1L) {
                    " day ago"
                } else {
                    " days ago"
                }
                convTime = day.toString() + dayText + suffix
                //                convTime = day_name;
            }
        } catch (e: ParseException) {
            e.printStackTrace()
            Log.e("ConvTimeE", e.toString())
        }
        return convTime
    }

    fun getYouTubeId(youTubeUrl: String): String? {
        val pattern = "(?<=youtu.be/|watch\\?v=|/videos/|embed\\/)[^#\\&\\?]*"
        val compiledPattern = Pattern.compile(pattern)
        val matcher = compiledPattern.matcher(youTubeUrl)
        return if (matcher.find()) {
            matcher.group()
        } else {
            "error"
        }
    }

    fun isValidYouTubeUrl(url: String?): Boolean {
        if (url == null) {
            return false
        }
        if (URLUtil.isValidUrl(url)) {
            // Check host of url if youtube exists
            val uri = Uri.parse(url)
            if ("www.youtube.com" == uri.host) {
                return true
            }
            // Other way You can check into url also like
            //if (url.startsWith("https://www.youtube.com/")) {
            //return true;
            //}
        }
        // In other any case
        return false
    }


    fun getDateAfter365DayFromCurrentDate(getDate: String): String {
        // val dt = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date()) // Start date

        val sdf = SimpleDateFormat("yyyy-MM-dd")
        val c = Calendar.getInstance()
        try {
            c.time = sdf.parse(getDate)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        c.add(
            Calendar.DATE,
            365
        ) // number of days to add, can also use Calendar.DAY_OF_MONTH in place of Calendar.DATE

        val sdf1 = SimpleDateFormat("yyyy-MM-dd")
        val output = sdf1.format(c.time)
        return output
    }


    fun getDateAfter30DayFromCurrentDate(getDate: String): String {
        // val dt = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date()) // Start date

        val sdf = SimpleDateFormat("yyyy-MM-dd")
        val c = Calendar.getInstance()
        try {
            c.time = sdf.parse(getDate)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        c.add(
            Calendar.DATE,
            30  //30 days for free trial  -1
        ) // number of days to add, can also use Calendar.DAY_OF_MONTH in place of Calendar.DATE

        val sdf1 = SimpleDateFormat("yyyy-MM-dd")
        val output = sdf1.format(c.time)
        return output
    }


}

// this method is used to validation in password field
fun isValidPassword(password: String): Boolean {
//        return Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!_])(?=\\S+$).{10,20}$")
//        return Pattern.compile("^(?=.*[0-9])(?=.*[a-zA-Z]).{8,2000}$")
    return Pattern.compile("^(?=.*[0-9])(?=.*[A-Za-z]).{8,20}\$")
        .matcher(password).matches()
}