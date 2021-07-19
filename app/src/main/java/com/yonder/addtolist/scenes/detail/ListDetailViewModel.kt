package com.yonder.addtolist.scenes.detail

import androidx.lifecycle.ViewModel
import com.yonder.addtolist.scenes.detail.domain.CategoryListUseCase
import com.yonder.addtolist.scenes.list.domain.usecase.UserListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * @author yusuf.onder
 * Created on 19.07.2021
 */

@HiltViewModel
class ShoppingListItemsViewModel @Inject constructor(
  private val userListUseCase: CategoryListUseCase
) : ViewModel() {


}
