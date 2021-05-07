package com.yonder.addtolist.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.navigation.ui.setupActionBarWithNavController
import com.yonder.addtolist.R
import com.yonder.addtolist.extensions.setupWithNavController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

  private var currentNavController: LiveData<NavController>? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    if (savedInstanceState == null) {
      setupBottomNavigationBar()
    }

  }

  override fun onRestoreInstanceState(savedInstanceState: Bundle) {
    super.onRestoreInstanceState(savedInstanceState)
    setupBottomNavigationBar()
  }


  private fun setupBottomNavigationBar() {
    val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_nav)

    val navGraphIds = listOf(R.navigation.list, R.navigation.settings)

    // Setup the bottom navigation view with a list of navigation graphs
    val controller = bottomNavigationView.setupWithNavController(
      navGraphIds = navGraphIds,
      fragmentManager = supportFragmentManager,
      containerId = R.id.nav_host_container,
      intent = intent
    )

    // Whenever the selected controller changes, setup the action bar.
    controller.observe(this, Observer { navController ->
      setupActionBarWithNavController(navController)
      navController.addOnDestinationChangedListener { _, destination, _ ->
        val showButton = showUpButton(destination.id)
        setUpButtonVisibility(showButton)
      }
    })

    currentNavController = controller
  }

  private fun showUpButton(id: Int): Boolean {
    return id != R.id.splashScreen
  }

  private fun setUpButtonVisibility(isVisible: Boolean) {
    supportActionBar?.setDisplayShowHomeEnabled(isVisible)
    supportActionBar?.setDisplayHomeAsUpEnabled(isVisible)
  }

  override fun onSupportNavigateUp(): Boolean {
    return currentNavController?.value?.navigateUp() ?: false
  }
}