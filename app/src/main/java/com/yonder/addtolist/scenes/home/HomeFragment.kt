package com.yonder.addtolist.scenes.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.yonder.addtolist.R
import com.yonder.addtolist.domain.uimodel.UserListUiModel
import com.yonder.addtolist.scenes.home.row.AddNewListRow
import com.yonder.addtolist.scenes.home.row.ListRow
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

    override fun onResume() {
        super.onResume()
       viewModel.getShoppingItems()
    }

    @Composable
    fun MainContent() {
        val uiState by viewModel.uiState.collectAsState()
        if (uiState.isLoading) {
            LoadingView()
        } else {
            if (uiState.shouldShowAddListView) {
                NoListView(onClickCreateNewList = {
                    navigateToCreateListScreen()
                })
            } else {
                LazyColumn(modifier = Modifier.fillMaxHeight()) {
                    item {
                        AddNewListRow(onClick = {
                            navigateToCreateListScreen()
                        })
                        Divider()
                    }
                    items(uiState.userLists, itemContent = { list ->
                        ListRow(list = list){
                            navigateUserListDetail(list)
                        }
                        Divider()
                    })
                }
            }
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

    private fun navigateToCreateListScreen() {
        findNavController().navigate(R.id.action_shopping_list_to_create_list)
    }

}
