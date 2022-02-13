package com.yonder.addtolist.scenes.activity

import android.content.Context
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.LiveData
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.yonder.addtolist.R
import com.yonder.addtolist.common.ui.extensions.setupWithNavController
import com.yonder.addtolist.data.local.UserPreferenceDataStoreImpl
import com.yonder.addtolist.databinding.ActivityMainBinding
import com.yonder.addtolist.common.utils.ContextWrapper
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

  private var currentNavController: LiveData<NavController>? = null
  private val navGraphIds = listOf(R.navigation.list, R.navigation.settings)

  private lateinit var binding: ActivityMainBinding
  private lateinit var appBarConfiguration: AppBarConfiguration

  val viewModel: MainViewModel by viewModels()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityMainBinding.inflate(layoutInflater)
    setContentView(binding.root)
    if (savedInstanceState == null) {
      setupToolbar()
      setupBottomNavigationBar()
    }
  }

  override fun onRestoreInstanceState(savedInstanceState: Bundle) {
    super.onRestoreInstanceState(savedInstanceState)
    setupToolbar()
    setupBottomNavigationBar()
  }

    private fun setupBottomNavigationBar() {
        val controller = binding.bottomNav.setupWithNavController(
            navGraphIds = navGraphIds,
            fragmentManager = supportFragmentManager,
            containerId = R.id.nav_host_container,
            intent = intent
        )
        appBarConfiguration =
            AppBarConfiguration(setOf(R.id.shoppingListItemsScreen, R.id.settingsScreen))
        controller.observe(this) { navController ->
            setupActionBarWithNavController(navController, appBarConfiguration)
            navController.addOnDestinationChangedListener { _, destination, _ ->
                handleNavDestination(destination)
            }
        }
        currentNavController = controller
    }

    private fun handleNavDestination(destination: NavDestination) {
        when (destination.id) {
            R.id.shoppingListItemsScreen, R.id.settingsScreen -> {
                binding.toolbar.isVisible = true
                binding.bottomNav.isVisible = true
                setUpButtonVisibility(false)
            }
            R.id.loginScreen, R.id.splashScreen -> {
                binding.toolbar.isVisible = false
                binding.bottomNav.isVisible = false
            }
        }
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
    }

    private fun setUpButtonVisibility(isVisible: Boolean) {
        supportActionBar?.setDisplayShowHomeEnabled(isVisible)
        supportActionBar?.setDisplayHomeAsUpEnabled(isVisible)
    }

    override fun onSupportNavigateUp(): Boolean {
        return currentNavController?.value?.navigateUp() ?: false
    }

    override fun attachBaseContext(newBase: Context?) {
        val safeContext = newBase ?: return
        val sharedPreferences = safeContext.getSharedPreferences(
            UserPreferenceDataStoreImpl.KEY_APP_PREFERENCES,
            Context.MODE_PRIVATE
        )
        val userPreferenceDataStore = UserPreferenceDataStoreImpl(sharedPreferences)
        val context: Context = ContextWrapper.wrap(newBase, userPreferenceDataStore.getLocale())
        super.attachBaseContext(context)
    }

}
