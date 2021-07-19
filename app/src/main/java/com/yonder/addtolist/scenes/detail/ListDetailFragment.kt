package com.yonder.addtolist.scenes.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.navigation.fragment.navArgs
import com.yonder.addtolist.common.ui.base.BaseFragment
import com.yonder.addtolist.common.ui.extensions.openWithKeyboard
import com.yonder.addtolist.databinding.FragmentListDetailBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * @author yusuf.onder
 * Created on 19.07.2021
 */

@AndroidEntryPoint
class ListDetailFragment : BaseFragment<FragmentListDetailBinding>() {

  private val args: ListDetailFragmentArgs by navArgs()

  override fun initBinding(inflater: LayoutInflater) =
    FragmentListDetailBinding.inflate(inflater)

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    initViews()
  }

  private fun initViews() = with(binding) {
    etSearch.openWithKeyboard(requireContext())
  }

}