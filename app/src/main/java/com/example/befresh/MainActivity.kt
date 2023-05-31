package com.example.befresh

import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.befresh.databinding.ActivityMainBinding
import com.example.befresh.baseView.BaseActivity
import com.example.befresh.baseView.ToolbarConfig
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(),
    NavController.OnDestinationChangedListener {

    lateinit var navController: NavController


    override fun getLayoutResID(): Int {
        return R.layout.activity_main
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.navControllerFragmentHome) as NavHostFragment
        navController = navHostFragment.navController

        navController.addOnDestinationChangedListener(this)
        binding?.bottomNavigationBar?.setupWithNavController(navController)
    }


    override fun onDestinationChanged(
        controller: NavController,
        destination: NavDestination,
        arguments: Bundle?
    ) {
        // destination time change
    }

    fun configToolbar(configToolbar: ToolbarConfig?) {
        if (configToolbar != null) {
            if (configToolbar.image == 0) {
                // binding?.backStackImage?.isVisible = false
            } else {
                //  binding?.backStackImage?.isVisible = true
                //   binding?.backStackImage?.setImageResource(configToolbar.image)
            }
            if (configToolbar.menuIcon == 0) {
                // binding?.messageIcon?.isVisible = false
            } else {
                //  binding?.messageIcon?.isVisible = true
                // binding!!.messageIcon.setImageResource(configToolbar.menuIcon)
            }
            // binding!!.tvHeading.text = configToolbar.tittle
        }
    }

    fun isBottomBar(showBottomBar: Boolean): Boolean {
        if (showBottomBar) {
            binding?.bottomNavigationBar?.visibility = View.VISIBLE
        } else {
            binding?.bottomNavigationBar?.visibility = View.GONE
        }
        return false
    }
}
