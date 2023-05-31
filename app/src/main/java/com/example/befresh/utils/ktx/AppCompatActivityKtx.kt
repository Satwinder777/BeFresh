package com.gocarhub.utils.ktx

import android.app.Activity
import android.content.Context
import android.content.IntentSender
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.StateListDrawable
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import android.graphics.Rect
import android.os.Build
import android.provider.Settings
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.view.WindowManager
import androidx.core.graphics.drawable.DrawableCompat
import com.example.befresh.R
import com.example.befresh.utils.ConnectionLiveData
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.LocationSettingsResponse


fun Activity.takeScreenShot(): Bitmap? {
    val view: View = window.decorView
    view.isDrawingCacheEnabled = true
    view.buildDrawingCache()
    val b1: Bitmap = view.drawingCache
    val frame = Rect()
    window.decorView.getWindowVisibleDisplayFrame(frame)
    val statusBarHeight: Int = frame.top
    val width = windowManager.defaultDisplay.width
    val height = windowManager.defaultDisplay.height
    val b = Bitmap.createBitmap(b1, 0, statusBarHeight, width, height - statusBarHeight)
    view.destroyDrawingCache()
    return b
}

fun Activity.enableLocationSettings() {
    val locationRequest: LocationRequest = LocationRequest.create()
        .setInterval(100)
        .setFastestInterval(10)
        .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
    val builder = LocationSettingsRequest.Builder()
        .addLocationRequest(locationRequest)
    LocationServices
        .getSettingsClient(this)
        .checkLocationSettings(builder.build())
        .addOnSuccessListener(this) { response: LocationSettingsResponse? -> }
        .addOnFailureListener(this) { ex ->
            if (ex is ResolvableApiException) {
                // Location settings are NOT satisfied,  but this can be fixed  by showing the user a dialog.
                try {
                    // Show the dialog by calling startResolutionForResult(),  and check the result in onActivityResult().
                    val resolvable = ex as ResolvableApiException
                    resolvable.startResolutionForResult(
                        this,
                        100
                    )
                } catch (sendEx: IntentSender.SendIntentException) {
                    // Ignore the error.
                }
            }
        }
}

private lateinit var connectionLiveData: ConnectionLiveData

fun Activity.getConnectionLiveData(): ConnectionLiveData {
    connectionLiveData = ConnectionLiveData(this)
    return connectionLiveData
}


 fun Activity.isLocationEnabled(context: Context): Boolean {
    var locationMode = 0
    val locationProviders: String
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
        locationMode = try {
            Settings.Secure.getInt(context.contentResolver, Settings.Secure.LOCATION_MODE)
        } catch (e: Settings.SettingNotFoundException) {
            e.printStackTrace()
            return false
        }
        locationMode != Settings.Secure.LOCATION_MODE_OFF
    } else {
        locationProviders = Settings.Secure.getString(
            context.contentResolver,
            Settings.Secure.LOCATION_PROVIDERS_ALLOWED
        )
        !TextUtils.isEmpty(locationProviders)
    }
}

fun Activity.showProgress(requireActivity: Activity) {
   loaderManager(true)
}
fun Activity.dismissProgress() {
   loaderManager(false)
}


//fun Activity.showNetworkErrorDialog(message:String) {
//  Toast(this).showCustomToast(message,this)
//}

var biometricCancelFlag=false

// function for showing translucent screen
fun AppCompatActivity.translucentScreen() {
    val w = this.window
    w.setFlags(
        WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
        WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
    )
}

fun Activity.fastBlur(radius: Int): Bitmap? {
    val bitmap = takeScreenShot()!!.copy(takeScreenShot()!!.config, true)
    if (radius < 1) {
        return null
    }
    val w = bitmap.width
    val h = bitmap.height
    val pix = IntArray(w * h)
    Log.e("pix", w.toString() + " " + h + " " + pix.size)
    bitmap.getPixels(pix, 0, w, 0, 0, w, h)
    val wm = w - 1
    val hm = h - 1
    val wh = w * h
    val div = radius + radius + 1
    val r = IntArray(wh)
    val g = IntArray(wh)
    val b = IntArray(wh)
    var rsum: Int
    var gsum: Int
    var bsum: Int
    var x: Int
    var y: Int
    var i: Int
    var p: Int
    var yp: Int
    var yi: Int
    var yw: Int
    val vmin = IntArray(Math.max(w, h))
    var divsum = div + 1 shr 1
    divsum *= divsum
    val dv = IntArray(256 * divsum)
    i = 0
    while (i < 256 * divsum) {
        dv[i] = i / divsum
        i++
    }
    yi = 0
    yw = yi
    val stack = Array(div) { IntArray(3) }
    var stackpointer: Int
    var stackstart: Int
    var sir: IntArray
    var rbs: Int
    val r1 = radius + 1
    var routsum: Int
    var goutsum: Int
    var boutsum: Int
    var rinsum: Int
    var ginsum: Int
    var binsum: Int
    y = 0
    while (y < h) {
        bsum = 0
        gsum = bsum
        rsum = gsum
        boutsum = rsum
        goutsum = boutsum
        routsum = goutsum
        binsum = routsum
        ginsum = binsum
        rinsum = ginsum
        i = -radius
        while (i <= radius) {
            p = pix[yi + Math.min(wm, Math.max(i, 0))]
            sir = stack[i + radius]
            sir[0] = p and 0xff0000 shr 16
            sir[1] = p and 0x00ff00 shr 8
            sir[2] = p and 0x0000ff
            rbs = r1 - Math.abs(i)
            rsum += sir[0] * rbs
            gsum += sir[1] * rbs
            bsum += sir[2] * rbs
            if (i > 0) {
                rinsum += sir[0]
                ginsum += sir[1]
                binsum += sir[2]
            } else {
                routsum += sir[0]
                goutsum += sir[1]
                boutsum += sir[2]
            }
            i++
        }
        stackpointer = radius
        x = 0
        while (x < w) {
            r[yi] = dv[rsum]
            g[yi] = dv[gsum]
            b[yi] = dv[bsum]
            rsum -= routsum
            gsum -= goutsum
            bsum -= boutsum
            stackstart = stackpointer - radius + div
            sir = stack[stackstart % div]
            routsum -= sir[0]
            goutsum -= sir[1]
            boutsum -= sir[2]
            if (y == 0) {
                vmin[x] = Math.min(x + radius + 1, wm)
            }
            p = pix[yw + vmin[x]]
            sir[0] = p and 0xff0000 shr 16
            sir[1] = p and 0x00ff00 shr 8
            sir[2] = p and 0x0000ff
            rinsum += sir[0]
            ginsum += sir[1]
            binsum += sir[2]
            rsum += rinsum
            gsum += ginsum
            bsum += binsum
            stackpointer = (stackpointer + 1) % div
            sir = stack[stackpointer % div]
            routsum += sir[0]
            goutsum += sir[1]
            boutsum += sir[2]
            rinsum -= sir[0]
            ginsum -= sir[1]
            binsum -= sir[2]
            yi++
            x++
        }
        yw += w
        y++
    }
    x = 0
    while (x < w) {
        bsum = 0
        gsum = bsum
        rsum = gsum
        boutsum = rsum
        goutsum = boutsum
        routsum = goutsum
        binsum = routsum
        ginsum = binsum
        rinsum = ginsum
        yp = -radius * w
        i = -radius
        while (i <= radius) {
            yi = Math.max(0, yp) + x
            sir = stack[i + radius]
            sir[0] = r[yi]
            sir[1] = g[yi]
            sir[2] = b[yi]
            rbs = r1 - Math.abs(i)
            rsum += r[yi] * rbs
            gsum += g[yi] * rbs
            bsum += b[yi] * rbs
            if (i > 0) {
                rinsum += sir[0]
                ginsum += sir[1]
                binsum += sir[2]
            } else {
                routsum += sir[0]
                goutsum += sir[1]
                boutsum += sir[2]
            }
            if (i < hm) {
                yp += w
            }
            i++
        }
        yi = x
        stackpointer = radius
        y = 0
        while (y < h) {

            // Preserve alpha channel: ( 0xff000000 & pix[yi] )
            pix[yi] = -0x1000000 and pix[yi] or (dv[rsum] shl 16) or (dv[gsum] shl 8) or dv[bsum]
            rsum -= routsum
            gsum -= goutsum
            bsum -= boutsum
            stackstart = stackpointer - radius + div
            sir = stack[stackstart % div]
            routsum -= sir[0]
            goutsum -= sir[1]
            boutsum -= sir[2]
            if (x == 0) {
                vmin[y] = Math.min(y + r1, hm) * w
            }
            p = x + vmin[y]
            sir[0] = r[p]
            sir[1] = g[p]
            sir[2] = b[p]
            rinsum += sir[0]
            ginsum += sir[1]
            binsum += sir[2]
            rsum += rinsum
            gsum += ginsum
            bsum += binsum
            stackpointer = (stackpointer + 1) % div
            sir = stack[stackpointer]
            routsum += sir[0]
            goutsum += sir[1]
            boutsum += sir[2]
            rinsum -= sir[0]
            ginsum -= sir[1]
            binsum -= sir[2]
            yi += w
            y++
        }
        x++
    }
    Log.e("pix", w.toString() + " " + h + " " + pix.size)
    bitmap.setPixels(pix, 0, w, 0, 0, w, h)
    return bitmap
}

// function for setting selected drawable of the tabs
fun AppCompatActivity.setDrawableSelector(normal: Int, selected: Int): Drawable = setSelector(
    this,
    normal,
    selected
)

fun setSelector(context: Context, normal: Int, selected: Int): Drawable {
    val state_normal = ContextCompat.getDrawable(context, normal)
    val state_pressed = ContextCompat.getDrawable(context, selected)
    val state_normal_bitmap =
        getBitMapDrawable(context, normal)//(state_normal as BitmapDrawable).bitmap
    val disabledBitmap = Bitmap.createBitmap(
        state_normal?.intrinsicWidth!!,
        state_normal.intrinsicHeight, Bitmap.Config.ARGB_8888
    )
    val canvas = Canvas(disabledBitmap)
    val paint = Paint()

    canvas.drawBitmap(state_normal_bitmap, 0f, 0f, paint)
    val s = ""
    val state_normal_drawable = BitmapDrawable(context.resources, disabledBitmap)
    DrawableCompat.setTint(
        DrawableCompat.wrap(state_normal_drawable),
        context.resources.getColor(R.color.purple_200)
    );
    val drawable = StateListDrawable()
    drawable.addState(
        intArrayOf(android.R.attr.state_selected),
        state_pressed
    )
    drawable.addState(
        intArrayOf(android.R.attr.state_enabled),
        state_normal_drawable
    )
    return drawable
}

private fun getBitMapDrawable(context: Context, drawableId: Int): Bitmap {
    val drawable = ContextCompat.getDrawable(context, drawableId);
    val bitmap = Bitmap.createBitmap(
        drawable?.intrinsicWidth!!,
        drawable.intrinsicHeight, Bitmap.Config.ARGB_8888
    )
    val canvas = Canvas(bitmap)
    drawable.setBounds(0, 0, canvas.width, canvas.height)
    drawable.draw(canvas)
    return bitmap!!
}
//
//fun AppCompatActivity.showAlertDialog(
//    mTitle: String?,
//    mMessage: String?,
//    mPositiveText: String?,
//    mNegativeText: String?,
//    dialogClickListener: (position: Int, requestCode: Int) -> Unit?,
//    position: Int,
//    requestCode: Int
//) {
//
//    var title = mTitle
//    var message = mMessage
//    if (!this.isFinishing) {
//        val alertDialog = AlertDialog.Builder(this)
//        val viewBinding = LayoutDialogDefaultBinding.inflate(LayoutInflater.from(this))
//        alertDialog.setView(viewBinding.root)
//            .setCancelable(false)
//        val alert = alertDialog.create()
//        alert.requestWindowFeature(Window.FEATURE_NO_TITLE)
//        alert.show()
//
//
//        if (mMessage.isNullOrEmpty()) {
//            message = getString(R.string.error_something_went_wrong)
//        }
//
//
//        if (mTitle.isNullOrEmpty()) {
//            title = getString(R.string.text_alert)
//        }
//
//        viewBinding.apply {
//
//            tvLabel.text = title
//            tvMessage.text = message
//            if (!mPositiveText.isNullOrEmpty()) {
//                tvPositive.visibilityVisible()
//                tvPositive.text = mPositiveText
//            } else {
//                tvPositive.visibilityGone()
//            }
//
//            if (!mNegativeText.isNullOrEmpty()) {
//                tvNegative.visibilityVisible()
//                tvNegative.text = mNegativeText
//            } else {
//                tvNegative.visibilityGone()
//            }
//            tvPositive.setOnClickListener {
//                alert.cancel()
//                dialogClickListener(position, requestCode)
//            }
//            tvNegative.setOnClickListener {
//                alert.cancel()
//            }
//        }
//    }
//}

//
//fun AppCompatActivity.showCustomAlert(
//    mTitle: String,
//    mMessage: String,
//    mPositiveText: String,
//) {
//
//    if (!this.isFinishing) {
//        val alertDialog = AlertDialog.Builder(this)
//        val viewBinding = LayoutDialogDefaultBinding.inflate(LayoutInflater.from(this))
//        alertDialog.setView(viewBinding.root)
//            .setCancelable(false)
//        val alert = alertDialog.create()
//        alert.requestWindowFeature(Window.FEATURE_NO_TITLE)
//        alert.show()
//
//        viewBinding.apply {
//
//            tvLabel.text = mTitle
//            tvMessage.text = mMessage
//            tvPositive.text = mPositiveText
//            tvNegative.visibilityGone()
//            tvPositive.setOnClickListener {
//                alert.cancel()
//            }
//        }
//    }
//}

//
//fun AppCompatActivity.showLogoutAlertDialog(
//    dialogClickListener: () -> Unit?,
//) {
//    if (!this.isFinishing) {
//        val alertDialog = AlertDialog.Builder(this)
//        val viewBinding = LayoutLogoutDialogBinding.inflate(LayoutInflater.from(this))
//        alertDialog.setView(viewBinding.root)
//            .setCancelable(false)
//        val alert = alertDialog.create()
//        alert.requestWindowFeature(Window.FEATURE_NO_TITLE)
//        alert.window?.setBackgroundDrawable(
//            ContextCompat.getDrawable(
//                this,
//                R.drawable.rect_white_border
//            )
//        )
//        alert.show()
//
//        viewBinding.apply {
//            tvLogout.setOnClickListener {
//                alert.cancel()
//                dialogClickListener()
//            }
//            tvCancel.setOnClickListener {
//                alert.cancel()
//            }
//        }
//    }
//}
//
//fun AppCompatActivity.showNetworkErrorDialog(errorMessage: String) {
//    showAlertDialog(
//        null, errorMessage, getString(R.string.text_dismiss), null,
//        { _: Int, _: Int ->
//            //no op
//
//        }, 0, AppConstants.REQUEST_CODE_NETWORK_DIALOG.value as Int
//    )
//}
//
//fun AppCompatActivity.showPasswordErrorDialog(title: String, errorMessage: String) {
//    showAlertDialog(
//        title, errorMessage, getString(R.string.text_dismiss), null,
//        { _: Int, _: Int ->
//            //no op
//
//        }, 0, AppConstants.REQUEST_CODE_NETWORK_DIALOG.value as Int
//    )
//}
