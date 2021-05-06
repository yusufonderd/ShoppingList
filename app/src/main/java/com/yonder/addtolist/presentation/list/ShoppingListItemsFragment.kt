package com.yonder.addtolist.presentation.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.yonder.addtolist.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ShoppingListItemsFragment : Fragment() {


  val viewModel: ShoppingListItemsViewModel by viewModels()

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    return inflater.inflate(R.layout.shopping_list_items_fragment, container, false)
  }


}