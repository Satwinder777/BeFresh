package com.gocarhub.utils.ktx

import android.content.Context
import android.text.InputType
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.graphics.Typeface
import java.util.regex.Pattern

fun EditText.validateEmail(): Boolean {
    /*return (!TextUtils.isEmpty(
        this.text.toString().trim()
    ) && Patterns.EMAIL_ADDRESS.matcher(this.text.toString().trim()).matches())*/

    return Pattern.compile(
        "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$"
    ).matcher(this.text.toString().trim()).matches()
}

fun EditText.openKeypad() {

    if(this.requestFocus()) {
        val imm = this.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
        this.setSelection(this.length())
        imm!!.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
    }

}



fun EditText.togglePasswordEye(status: Boolean) {

    if (status) {
        this.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD;
    } else {
        this.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
    }
    this.setSelection(this.length())
    val face = Typeface.createFromAsset(
        this.context.assets,
        "font/montserrat_bold.otf"
    )
    this.typeface = face
}

fun EditText.togglePasswordEyeFromSignup(status: Boolean) {

    if (status) {
        this.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD;
    } else {
        this.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
    }
    this.setSelection(this.length())
}

fun EditText.textAndToString() = this.text.toString().trim()

fun EditText.isValidPassword(): Boolean {

    val regex = ("^(?=.*[0-9])"
            + "(?=.*[a-z])(?=.*[A-Z])"
            + "(?=.*[^a-zA-Z0-9])"
            + "(?=\\S+$).{8,20}$")

    val passwordREGEX = Pattern.compile(regex)

    return passwordREGEX.matcher(this.text).matches()

}

