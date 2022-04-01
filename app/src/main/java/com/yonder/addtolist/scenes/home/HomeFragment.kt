package com.yonder.addtolist.scenes.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.colorResource
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.yonder.addtolist.R
import com.yonder.addtolist.domain.uimodel.UserListUiModel
import com.yonder.addtolist.scenes.home.row.CreateListFab
import com.yonder.addtolist.scenes.home.row.ListRow
import com.yonder.addtolist.uicomponent.LoadingView
import com.yonder.addtolist.uicomponent.NoListView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    val viewModel: ListViewModel by viewModels()

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
                Box(contentAlignment = Alignment.BottomEnd) {
                    LazyColumn(modifier = Modifier.fillMaxHeight()) {
                        items(uiState.userLists, itemContent = { list ->
                            ListRow(list = list) {
                                navigateUserListDetail(list)
                            }
                            Divider(modifier = Modifier.background(colorResource(id = R.color.white)))
                        })
                    }
                    CreateListFab {
                        navigateToCreateListScreen()
                    }
                }
            }
        }
    }

    private fun navigateUserListDetail(userList: UserListUiModel) {
      /*  findNavController().navigate(
            HomeFragmentDirections.actionShoppingListToListDetail(
                userList = userList,
                title = userList.name
            )
        )*/
    }

    private fun navigateToCreateListScreen() {
     //   findNavController().navigate(R.id.action_shopping_list_to_create_list)
    }

}
