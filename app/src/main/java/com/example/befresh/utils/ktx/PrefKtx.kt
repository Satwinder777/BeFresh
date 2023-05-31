package com.gocarhub.utils.ktx

import android.content.Context
import android.content.SharedPreferences
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.example.befresh.application.MyApplicationClass
import com.example.befresh.utils.PrefConstants


private const val MASTER_KEY_ALIAS = "secured_chrono"
private const val MASTER_KEY_ALIAS2 = "secured_chrono_2"
private const val KEY_SIZE = 256
private const val KEY_SIZE2 = 256

private val keyGenParameterSpec =
    KeyGenParameterSpec.Builder(
        MASTER_KEY_ALIAS,
        KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
    ).setBlockModes(KeyProperties.BLOCK_MODE_GCM)
        .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
        .setKeySize(KEY_SIZE)
        .build()

private val keyGenParameterSpec2 =
    KeyGenParameterSpec.Builder(
        MASTER_KEY_ALIAS2,
        KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
    ).setBlockModes(KeyProperties.BLOCK_MODE_GCM)
        .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
        .setKeySize(KEY_SIZE2)
        .build()

private val masterKeyAlias = MasterKey.Builder(MyApplicationClass.getInstance(), MASTER_KEY_ALIAS)
    .setKeyGenParameterSpec(keyGenParameterSpec)
    .build()

private val masterKeyAlias2 = MasterKey.Builder(MyApplicationClass.getInstance(), MASTER_KEY_ALIAS2)
    .setKeyGenParameterSpec(keyGenParameterSpec2)
    .build()


//// function for putting string List in preference
//fun Context.saveUserLoggedInStatus() {
//    getSharedPreference2.put(ChronoConstants.UserLoggedInStatus.LOGGED_IN_STATUS.value, true)
//}

// function for putting string List in preference
//fun Context.getUserLoggedInStatus():Boolean {
//    return getSharedPreference2.get(ChronoConstants.UserLoggedInStatus.LOGGED_IN_STATUS.value, false)
//}


/*
*
* method to return shared preferences instance
* */

val Context.getSharedPreference: SharedPreferences
    get() = EncryptedSharedPreferences.create(
        this,
        PrefConstants.PREFERENCE_NAME.value,
        masterKeyAlias,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )


val Context.getSharedPreference2: SharedPreferences
    get() = EncryptedSharedPreferences.create(
        this,
        PrefConstants.PREFERENCE_NAME2.value,
        masterKeyAlias2,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

inline fun <reified T> SharedPreferences.get(
    key: String,
    defaultValue: T
): T {
    when (T::class) {
        Boolean::class -> return this.getBoolean(key, defaultValue as Boolean) as T
        String::class -> return this.getString(key, defaultValue as String) as T
    }
    return defaultValue
}

inline fun <reified T> SharedPreferences.put(
    key: String, value: T
) {
    val editor = this.edit()

    when (T::class) {
        Boolean::class -> editor.putBoolean(key, value as Boolean)
        String::class -> editor.putString(key, value as String)
    }
    editor.apply()
}

//fun AppCompatActivity.logout() {
//    //stopService(Intent(this, LocationService::class.java))
//
//    getSharedPreference.edit().clear().apply()
//    startActivity(
//        Intent(
//            this,
//            LoginActivity::class.java
//        ).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
//    )
//    finish()
//}
//fun Context.logout() {
//    getSharedPreference.edit().clear().apply()
//    startActivity(
//        Intent(
//            this,
//            LoginActivity::class.java
//        ).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
//    )
//}

fun SharedPreferences.remove(
    key: String
) {
    this.edit().remove(key).apply()
}