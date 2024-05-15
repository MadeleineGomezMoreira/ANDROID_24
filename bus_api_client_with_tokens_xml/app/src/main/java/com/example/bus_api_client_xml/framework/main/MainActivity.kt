package com.example.bus_api_client_xml.framework.main

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.onNavDestinationSelected
import com.example.bus_api_client_xml.R
import com.example.bus_api_client_xml.databinding.ActivityMainBinding
import com.example.bus_api_client_xml.framework.driver.DriverDetailFragmentDirections
import com.example.bus_api_client_xml.utils.Constants
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        return true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {

            val navHostFragment =
                supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
            navController = navHostFragment.findNavController()

            //what happens when you click on the bottom navigation items
            bottomNavigation.setOnItemSelectedListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.loginFragment -> {
                        navController.navigate(R.id.action_logOff)
                        true
                    }
                    R.id.driverDetailFragment -> {
                        val action = DriverDetailFragmentDirections.actionToDriverDetail()
                        navController.navigate(action)
                        true
                    }
                    else -> {
                        menuItem.onNavDestinationSelected(navController)
                        true
                    }
                }
            }

            //each time the destination changes, here is where it is decided what is shown or not
            navController.addOnDestinationChangedListener { _, destination, _ ->
                when (destination.id) {
                    R.id.driversListFragment -> {
                        topAppBar.visibility = View.VISIBLE
                        topAppBar.title = Constants.DRIVERS_LIST_TITLE
                        bottomNavigation.visibility = View.VISIBLE
                    }

                    R.id.driverDetailFragment -> {
                        topAppBar.visibility = View.VISIBLE
                        topAppBar.title = Constants.DRIVER_DETAIL_TITLE
                        bottomNavigation.visibility = View.VISIBLE
                    }

                    R.id.loginFragment -> {
                        bottomNavigation.visibility = View.GONE
                        topAppBar.visibility = View.INVISIBLE
                    }

                    R.id.busLineDetailFragment -> {
                        topAppBar.isVisible = true
                        topAppBar.title = Constants.LINE_DETAIL_TITLE
                    }

                    else -> {
                        topAppBar.title = Constants.STOP_DETAIL_TITLE
                        topAppBar.isVisible = true
                    }
                }
            }
        }
    }

    override fun onOptionsItemSelected(menuItem: MenuItem): Boolean {
        menuItem.onNavDestinationSelected(navController)
        return true
    }

}
