package com.yonder.addtolist.scenes.lists

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
import androidx.compose.ui.res.colorResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController
import com.yonder.addtolist.R
import com.yonder.addtolist.common.utils.OnLifecycleEvent
import com.yonder.addtolist.scenes.activity.Screen
import com.yonder.addtolist.uicomponent.CreateListFab
import com.yonder.addtolist.scenes.lists.row.ListRow
import com.yonder.addtolist.uicomponent.LoadingView
import com.yonder.addtolist.uicomponent.NoListView
import com.yonder.addtolist.uicomponent.ThinDivider

@Composable
fun ListScreen(navController: NavController) {
    val viewModel = hiltViewModel<ListsViewModel>()
    val uiState by viewModel.uiState.collectAsState()

    OnLifecycleEvent { _, _event ->
        when (_event) {
            Lifecycle.Event.ON_START -> {
                viewModel.getShoppingItems()
            }
            else -> Unit
        }
    }

    if (uiState.isLoading) {
        LoadingView()
    } else {
        if (uiState.shouldShowAddListView) {
            NoListView(onClickCreateNewList = {
                navController.navigate(Screen.CreateNewList.route)
            })
        } else {
            Box(
                contentAlignment = Alignment.BottomEnd,
                modifier = Modifier.background(
                    color = colorResource(
                        id = R.color.white
                    )
                )
            ) {
                LazyColumn(modifier = Modifier.fillMaxHeight()) {
                    items(uiState.userLists, itemContent = { list ->
                        ListRow(list = list) {
                            navController.navigate(
                                Screen.ListDetail.route.replace(
                                    "{uuid}",
                                    list.uuid
                                )
                            )
                        }
                        ThinDivider()
                    })
                }
                CreateListFab {
                    navController.navigate(Screen.CreateNewList.route)
                }
            }
        }
    }
}