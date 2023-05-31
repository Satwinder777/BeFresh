package com.gocarhub.utils.ktx

import android.animation.Animator
import android.content.Context
import android.os.Build
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.example.befresh.R
import com.example.befresh.application.MyApplicationClass
import com.google.android.material.snackbar.Snackbar
import java.time.DayOfWeek
import java.time.temporal.WeekFields
import java.util.*


/*
  *
  * method to show snack bar
  * */

fun View.showSnackBar(
    message: String?
) {
    message?.let {

        Snackbar.make(
            this,
            it,
            Snackbar.LENGTH_LONG
        ).show()
    } ?: run {
        Snackbar.make(
            this,
            MyApplicationClass.getInstance().getString(R.string.error_something_went_wrong),
            Snackbar.LENGTH_LONG
        ).show()
    }
}

fun View.setViewEnableState(enable: Boolean) {
    if (!enable) {
        this.isEnabled = false
        this.alpha = 0.3F
    } else {
        this.isEnabled = true
        this.alpha = 1F
    }
}

/*
*
* method to hide keyboard
*
* */

fun View.openKeypad() {
    val imm = this.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
    this.requestFocus()
    this.postDelayed({
        run {
            imm!!.hideSoftInputFromWindow(this.windowToken, 0)
        }
    }, 200)
}

fun View.closeKeypad() {
    val imm = this.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
    imm!!.hideSoftInputFromWindow(this.windowToken, 0)
}

/**
 * Function for handling visibility of the view
 */

fun View.visibilityVisible() {
    visibility = View.VISIBLE
}

fun View.visibilityInVisible() {
    visibility = View.INVISIBLE
}

fun View.visibilityGone() {
    visibility = View.GONE
}


/**
 * Function for handling visibility of the view with aplha changes
 */


fun View.visibilityVisibleWithAplhaAnimation() {
    alpha = 0f
    visibility = View.VISIBLE
    animate()
        .alpha(1f)
        .setDuration(300)
        .setListener(null)
}

fun View.visibilityInVisibleWithAplhaAnimation() {
    alpha = 1f
    animate()
        .alpha(0f)
        .setDuration(300)
        .setListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(p0: Animator?) {

            }

            override fun onAnimationEnd(p0: Animator?) {
                visibility = View.INVISIBLE
            }

            override fun onAnimationCancel(p0: Animator?) {

            }

            override fun onAnimationRepeat(p0: Animator?) {

            }


        })
}

fun View.visibilityGoneWithAplhaAnimation() {
    alpha = 1f
    animate()
        .alpha(0f)
        .setDuration(300)
        .setListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(p0: Animator?) {

            }

            override fun onAnimationEnd(p0: Animator?) {
                visibility = View.GONE
            }

            override fun onAnimationCancel(p0: Animator?) {

            }

            override fun onAnimationRepeat(p0: Animator?) {

            }


        })
}


@RequiresApi(Build.VERSION_CODES.O)
fun daysOfWeekFromLocale(): Array<DayOfWeek> {
    val firstDayOfWeek = WeekFields.of(Locale.getDefault()).firstDayOfWeek
    var daysOfWeek = DayOfWeek.values()
    // Order `daysOfWeek` array so that firstDayOfWeek is at index 0.
    // Only necessary if firstDayOfWeek != DayOfWeek.MONDAY which has ordinal 0.
    if (firstDayOfWeek != DayOfWeek.MONDAY) {
        val rhs = daysOfWeek.sliceArray(firstDayOfWeek.ordinal..daysOfWeek.indices.last)
        val lhs = daysOfWeek.sliceArray(0 until firstDayOfWeek.ordinal)
        daysOfWeek = rhs + lhs
    }
    return daysOfWeek
}

fun View.setBackgroundResourceImage(drawable: Int) {
    this.background = ContextCompat.getDrawable(context, drawable)
}


//
//// function for setting layout margin programmatically
//fun View.setMargin(top: Int, bottom: Int, left: Int, right: Int, layout: String) {
//    val d = this.context.resources.displayMetrics.density
//    val marginLeft = (left * d).toInt()
//    val marginRight = (right * d).toInt()
//    val marginTop = (top * d).toInt()
//    val marginBottom = (bottom * d).toInt()
//    when (layout) {
//        ChronoConstants.Params.LINEAR.value -> {
//            val params = this.layoutParams as LinearLayout.LayoutParams
//            params.setMargins(
//                marginLeft,
//                marginTop,
//                marginRight,
//                marginBottom
//            ) //substitute parameters for left, top, right, bottom
//            this.layoutParams = params
//        }
//        ChronoConstants.Params.RECYCLER.value -> {
//            val params = this.layoutParams as RecyclerView.LayoutParams
//            params.setMargins(
//                marginLeft,
//                marginTop,
//                marginRight,
//                marginBottom
//            ) //substitute parameters for left, top, right, bottom
//            this.layoutParams = params
//        }
//        ChronoConstants.Params.RELATIVE.value -> {
//            val params = this.layoutParams as RelativeLayout.LayoutParams
//            params.setMargins(
//                marginLeft,
//                marginTop,
//                marginRight,
//                marginBottom
//            ) //substitute parameters for left, top, right, bottom
//            this.layoutParams = params
//        }
//        ChronoConstants.Params.FRAME.value -> {
//            val params = this.layoutParams as FrameLayout.LayoutParams
//            params.setMargins(
//                marginLeft,
//                marginTop,
//                marginRight,
//                marginBottom
//            ) //substitute parameters for left, top, right, bottom
//            this.layoutParams = params
//        }
//    }
//}
//


fun View.hideKeyboard() {
    try {
        val inputManager = this.context
            .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        inputManager.hideSoftInputFromWindow(this.windowToken, 0)
    } catch (e: Exception) {
        e.printStackTrace()
    }
}



