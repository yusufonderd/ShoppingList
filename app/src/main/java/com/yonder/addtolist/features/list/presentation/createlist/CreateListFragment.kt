package com.yonder.addtolist.features.list.presentation.createlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import com.yonder.addtolist.R
import com.yonder.addtolist.common.ui.base.BaseFragment
import com.yonder.addtolist.databinding.FragmentCreateListBinding

class CreateListFragment : BaseFragment<FragmentCreateListBinding>() {

  override fun initBinding(inflater: LayoutInflater) = FragmentCreateListBinding.inflate(inflater)

  private val listNames get() = requireContext().resources.getStringArray(R.array.list_names)

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    initViews()
  }

  private fun initViews() {
    binding.horizontalPicker.initView(listNames) { listName ->
      binding.tvListName.setText(listName)
    }
  }

}
