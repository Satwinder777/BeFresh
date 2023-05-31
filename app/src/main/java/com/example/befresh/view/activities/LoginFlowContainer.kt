package com.example.befresh.view.activities

import android.content.Intent
import android.content.Intent.ACTION_VIEW
import android.net.Uri
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.befresh.R
import com.example.befresh.application.MyApplicationClass
import com.example.befresh.baseView.BaseActivity
import com.example.befresh.databinding.ActivityLoginFlowContainerBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class LoginFlowContainer : BaseActivity<ActivityLoginFlowContainerBinding>() {
    lateinit var navController: NavController

    override fun getLayoutResID(): Int {
        return R.layout.activity_login_flow_container
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.navControllerFragmentAuth) as NavHostFragment
        navController = navHostFragment.navController

        MyApplicationClass.getInstance()

    }
}