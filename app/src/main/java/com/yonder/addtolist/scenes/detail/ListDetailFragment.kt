package com.yonder.addtolist.scenes.detail

import android.view.LayoutInflater
import com.yonder.addtolist.common.ui.base.BaseFragment
import com.yonder.addtolist.databinding.FragmentListDetailBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * @author yusuf.onder
 * Created on 19.07.2021
 */

@AndroidEntryPoint
class ListDetailFragment : BaseFragment<FragmentListDetailBinding>() {
  override fun initBinding(inflater: LayoutInflater) =
    FragmentListDetailBinding.inflate(inflater)

}