package com.yonder.addtolist.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

/**
 * Yusuf Onder on 08,May,2021
 */

abstract class BaseFragment<T : ViewBinding> : Fragment() {

  private var _binding: T? = null

  protected open val binding get() = _binding!!

  abstract fun setObserver()

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    _binding = this.initBinding(inflater, container)
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    setObserver()
  }
  override fun onDestroyView() {
    super.onDestroyView()
    _binding = null
  }

  abstract fun initBinding(inflater: LayoutInflater, container: ViewGroup?): T

}