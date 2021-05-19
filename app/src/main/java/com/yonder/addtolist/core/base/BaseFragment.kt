package com.yonder.addtolist.core.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

/**
 * Yusuf Onder on 08,May,2021
 */

abstract class BaseFragment<T : ViewBinding> : Fragment() {

  private var _binding: T? = null

  protected open val binding get() = _binding!!

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    _binding = this.initBinding(inflater, container)
    return binding.root
  }

  override fun onDestroyView() {
    super.onDestroyView()
    _binding = null
  }

  abstract fun initBinding(inflater: LayoutInflater, container: ViewGroup?): T

  fun showToastMessage(@StringRes messageResId: Int) {
    showToastMessage(getString(messageResId))
  }

  fun showToastMessage(message: String) {
    Toast.makeText(context, message, Toast.LENGTH_LONG).show()
  }
}