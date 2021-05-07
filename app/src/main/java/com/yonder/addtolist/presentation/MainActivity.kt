package com.yonder.addtolist.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.navigation.NavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.navigation.ui.setupActionBarWithNavController
import com.yonder.addtolist.R
import com.yonder.addtolist.extensions.setupWithNavController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

  private var currentNavController: LiveData<NavController>? = null
  private val navGraphIds = listOf(R.navigation.list, R.navigation.settings)

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

    val controller = bottomNavigationView.setupWithNavController(
      navGraphIds = navGraphIds,
      fragmentManager = supportFragmentManager,
      containerId = R.id.nav_host_container,
      intent = intent
    )

    controller.observe(this, { navController ->
      setupActionBarWithNavController(navController)
      navController.addOnDestinationChangedListener { _, destination, _ ->
        val showButton = showUpButton(destination.id)
        setUpButtonVisibility(showButton)
      }
    })

    currentNavController = controller
  }

  private fun showUpButton(id: Int): Boolean {
    return id != R.id.splashScreen || id != R.id.settingsScreen
  }

  private fun setUpButtonVisibility(isVisible: Boolean) {
    supportActionBar?.setDisplayShowHomeEnabled(isVisible)
    supportActionBar?.setDisplayHomeAsUpEnabled(isVisible)
  }

  override fun onSupportNavigateUp(): Boolean {
    return currentNavController?.value?.navigateUp() ?: false
  }
}