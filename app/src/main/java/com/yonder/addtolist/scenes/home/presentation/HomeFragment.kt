package com.yonder.addtolist.scenes.home.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.colorResource
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.yonder.addtolist.R
import com.yonder.addtolist.scenes.home.domain.model.UserListUiModel
import com.yonder.addtolist.theme.padding_8
import com.yonder.uicomponent.compose.LoadingView
import com.yonder.uicomponent.compose.NoListView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    val viewModel: ShoppingListItemsViewModel by viewModels()

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
        val uiState by viewModel.uiState.collectAsState()
        if (uiState.isLoading) {
            LoadingView()
        } else {
            if (uiState.shouldShowAddListView) {
                NoListView(onClickCreateNewList = {
                    findNavController().navigate(R.id.action_shopping_list_to_create_list)
                })
            } else {
                LazyColumn(modifier = Modifier.fillMaxHeight()) {
                    items(uiState.userLists, itemContent = { list ->
                        ListRow(list = list)
                        Divider()
                    })
                }
            }
        }
    }

    @Composable
    fun ListRow(list: UserListUiModel) {
        TextButton(
            onClick = {
                navigateUserListDetail(list)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = list.name,
                style = MaterialTheme.typography.h5,
                modifier = Modifier.padding(padding_8),
                color = colorResource(id = R.color.primaryTextColor)
            )
        }
    }

    private fun navigateUserListDetail(userList: UserListUiModel) {
        findNavController().navigate(
            HomeFragmentDirections.actionShoppingListToListDetail(
                userList = userList,
                title = userList.name
            )
        )
    }

}
