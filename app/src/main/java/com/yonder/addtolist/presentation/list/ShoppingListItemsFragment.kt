package com.yonder.addtolist.presentation.list

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yonder.addtolist.R

class ShoppingListItemsFragment : Fragment() {

  private lateinit var viewModel: ShoppingListItemsViewModel

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    return inflater.inflate(R.layout.shopping_list_items_fragment, container, false)
  }

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)
    viewModel = ViewModelProvider(this).get(ShoppingListItemsViewModel::class.java)

  }

}