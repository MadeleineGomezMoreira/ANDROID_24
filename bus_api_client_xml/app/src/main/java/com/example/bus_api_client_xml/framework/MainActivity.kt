package com.example.bus_api_client_xml.framework

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.onNavDestinationSelected
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.bus_api_client_xml.R
import com.example.bus_api_client_xml.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_appbar, menu)
        return true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            setSupportActionBar(topAppBar)
            val navHostFragment =
                supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
            navController = navHostFragment.findNavController()

            //drawer configuration
            appBarConfiguration = AppBarConfiguration(
                setOf(
                    R.id.driverDetailFragment, R.id.driversListFragment
                ), drawerLayout
            )

            setupActionBarWithNavController(navController, appBarConfiguration)
            navView.setupWithNavController(navController)

            //when you click on the appbar where the drawer is it opens
            topAppBar.setNavigationOnClickListener {
                navController.navigateUp()
                drawerLayout.open()
            }

            //what happens when you click on the bottom navigation items
            bottomNavigation.setOnItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.driversListFragment -> {
                        navController.navigate(R.id.driversListFragment)
                        true
                    }

                    R.id.driverDetailFragment -> {
                        navController.navigate(R.id.driverDetailFragment)
                        true
                    }

                    else -> false
                }
            }

            //each time the destination changes, here is where it is decided what is shown or not
            navController.addOnDestinationChangedListener { _, destination, _ ->
                when (destination.id) {
                    R.id.driversListFragment -> {
                        topAppBar.visibility = View.VISIBLE
                        binding.bottomNavigation.visibility = View.VISIBLE
                        navView.isVisible = true
                        topAppBar.menu.findItem(R.id.driverDetailFragment).isVisible = false
                        topAppBar.menu.findItem(R.id.driversListFragment).isVisible = false
                    }

                    R.id.driverDetailFragment -> {
                        topAppBar.visibility = View.VISIBLE
                        binding.bottomNavigation.visibility = View.VISIBLE
                        navView.isVisible = true
                        topAppBar.menu.findItem(R.id.driverDetailFragment).isVisible = false
                        topAppBar.menu.findItem(R.id.driversListFragment).isVisible = true
                    }

                    R.id.loginFragment -> {
                        binding.bottomNavigation.visibility = View.GONE
                        topAppBar.visibility = View.INVISIBLE
                        navView.isVisible = false
                        topAppBar.menu.findItem(R.id.driverDetailFragment).isVisible = false
                        topAppBar.menu.findItem(R.id.driversListFragment).isVisible = false
                    }

                    else -> {
                        topAppBar.isVisible = true
                        navView.isVisible = true
                    }
                }
            }
        }
    }

    //appbar menu configuration
    override fun onOptionsItemSelected(menuItem: MenuItem): Boolean {
        return when (menuItem.itemId) {
            R.id.driversListFragment -> {
                //TODO: see if the args are correctly handled
                //we use action to pass arguments to the destination fragment
//                val action = ThirdFragmentDirections.actionThirdFragmentToSecondFragment()
//                navController.navigate(action)
                menuItem.onNavDestinationSelected(navController)
                true
            }

            else -> menuItem.onNavDestinationSelected(navController)
        }
    }

}
