package com.yonder.addtolist.scenes.lists

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController
import com.yonder.addtolist.R
import com.yonder.addtolist.common.utils.OnLifecycleEvent
import com.yonder.addtolist.domain.uimodel.UserListProductUiModel
import com.yonder.addtolist.scenes.activity.Screen
import com.yonder.addtolist.scenes.lists.row.ListRow
import com.yonder.addtolist.uicomponent.*
import timber.log.Timber

@Composable
fun ListScreen(navController: NavController) {
    val viewModel = hiltViewModel<ListsViewModel>()
    val uiState by viewModel.uiState.collectAsState()
    var showMenu by remember { mutableStateOf(false) }
    var listName by remember { mutableStateOf("") }
    var listUUID by remember { mutableStateOf("") }

    OnLifecycleEvent { _, _event ->
        when (_event) {
            Lifecycle.Event.ON_START -> {
                viewModel.getShoppingItems()
            }
            else -> Unit
        }
    }

    if (showMenu) {
        Dialog(
            onDismissRequest = {
                showMenu = false
            }, content = {
                Surface(
                    shape = MaterialTheme.shapes.medium,
                    color = MaterialTheme.colorScheme.surface,
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = listName,
                            style = MaterialTheme.typography.bodyLarge,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        OutlinedButton(onClick = {
                            showMenu = false
                            viewModel.deleteList(listUUID)
                        }) {
                            TextIcon(
                                Icons.Filled.Delete,
                                stringResource(id = R.string.delete_list),
                                colorResource(id = R.color.colorRed)
                            )
                        }
                    }
                }
            })
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
                        ListRow(list = list, onClick = {
                            navController.navigate(
                                Screen.ListDetail.route.replace(
                                    "{uuid}",
                                    list.uuid
                                )
                            )
                        }, onLongClick = {
                            listUUID = list.uuid
                            listName = list.name
                            showMenu = true
                        }
                        )
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