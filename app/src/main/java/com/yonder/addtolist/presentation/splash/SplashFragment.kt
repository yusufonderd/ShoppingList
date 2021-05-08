package com.yonder.addtolist.presentation.splash

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.yonder.addtolist.R
import com.yonder.addtolist.presentation.list.ShoppingListNavigator
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SplashFragment : Fragment() {


  val viewModel: SplashViewModel by viewModels()


  @Inject
  lateinit var splashNavigator: SplashNavigator


  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    return inflater.inflate(R.layout.splash_fragment, container, false)
  }


}