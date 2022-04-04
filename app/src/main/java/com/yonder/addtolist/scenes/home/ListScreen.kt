package com.yonder.addtolist.scenes.home

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
import androidx.navigation.NavController
import com.yonder.addtolist.R
import com.yonder.addtolist.scenes.activity.Screen
import com.yonder.addtolist.scenes.home.row.CreateListFab
import com.yonder.addtolist.scenes.home.row.ListRow
import com.yonder.addtolist.scenes.listdetail.ListDetail
import com.yonder.addtolist.uicomponent.LoadingView
import com.yonder.addtolist.uicomponent.NoListView

@Composable
fun ListScreen(navController: NavController) {
    val viewModel = hiltViewModel<ListViewModel>()
    val uiState by viewModel.uiState.collectAsState()
    if (uiState.isLoading) {
        LoadingView()
    } else {
        if (uiState.shouldShowAddListView) {
            NoListView(onClickCreateNewList = {
                navController.navigate(Screen.CreateNewList.route)
            })
        } else {
            Box(contentAlignment = Alignment.BottomEnd) {
                LazyColumn(modifier = Modifier.fillMaxHeight()) {
                    items(uiState.userLists, itemContent = { list ->
                        ListRow(list = list) {
                            navController.currentBackStackEntry?.arguments?.putParcelable(
                                ListDetail.LIST_UI_MODEL,
                                list
                            )
                            navController.navigate(Screen.ListDetail.route)
                        }
                        Divider(modifier = Modifier.background(colorResource(id = R.color.white)))
                    })
                }
                CreateListFab {
                    navController.navigate(Screen.CreateNewList.route)
                }
            }
        }
    }
}