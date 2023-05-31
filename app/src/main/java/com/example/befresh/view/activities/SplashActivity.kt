package com.example.befresh.view.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.example.befresh.MainActivity
import com.example.befresh.R
import com.example.befresh.databinding.ActivitySplashActvityBinding
import com.example.befresh.utils.PrefConstants
import com.example.befresh.utils.SharedPref
import com.example.befresh.baseView.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity : BaseActivity<ActivitySplashActvityBinding>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Handler(Looper.getMainLooper()).postDelayed({
            if (SharedPref.instance.getString(PrefConstants.ACCESS_TOKEN.value)!!.isEmpty()) {
                startActivity(
                    Intent(this, MainActivity::class.java)
                )
                finishAffinity()

            } else {
                startActivity(
                    Intent(this, MainActivity::class.java)
                )
                finishAffinity()
                //  finishAffinity()
            }
        }, 3000)
    }

    override fun getLayoutResID(): Int {
        return R.layout.activity_splash_actvity
    }


}