package com.goforer.fitpettest.presentation.ui

import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.goforer.base.extension.setSystemBarTextDark
import com.goforer.fitpettest.R
import com.goforer.fitpettest.databinding.ActivityMainBinding
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import java.io.IOException
import javax.inject.Inject

open class MainActivity : AppCompatActivity(), HasAndroidInjector {
    private lateinit var binding: ActivityMainBinding

    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    @Inject
    internal lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>

    override fun androidInjector() = dispatchingAndroidInjector

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(this, R.color.colorPrimaryDark)
        window.setSystemBarTextDark()
        supportActionBar?.setDisplayShowTitleEnabled(false)
        init()
    }

    override fun onSupportNavigateUp(): Boolean {
        return if (::navController.isInitialized && ::appBarConfiguration.isInitialized)
            navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
        else
            super.onSupportNavigateUp()
    }

    private fun init() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        if (::binding.isInitialized) {
            with(binding) {
                setContentView(root)
                setSupportActionBar(toolbar)
                supportActionBar?.setDisplayShowTitleEnabled(false)
                val navHostFragment = supportFragmentManager
                    .findFragmentById(R.id.nav_host_container) as NavHostFragment
                navController = navHostFragment.navController
                appBarConfiguration = AppBarConfiguration(navController.graph)
                setupActionBarWithNavController(navController, appBarConfiguration)
            }
        }
    }
}