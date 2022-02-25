package com.yonder.addtolist.scenes.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.yonder.addtolist.R
import com.yonder.addtolist.uicomponent.LoadingView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashFragment : Fragment() {

    val viewModel: SplashViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                MainContent()
            }
        }
    }

    @Composable
    fun MainContent() {
        val event by viewModel.effect.collectAsState(initial = SplashViewModel.UiEvent.Loading)
        when (event) {
            is SplashViewModel.UiEvent.Login -> {
                findNavController().navigate(R.id.action_splash_to_login)
            }
            is SplashViewModel.UiEvent.ShoppingListItems -> {
                findNavController().navigate(R.id.action_splash_to_shopping_list_items)
            }
            is SplashViewModel.UiEvent.Loading -> {
                LoadingView()
            }
        }

    }

}
