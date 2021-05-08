package com.yonder.addtolist.base

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment

/**
 * Yusuf Onder on 08,May,2021
 */

abstract class BaseFragment : Fragment() {



  abstract fun setObserver()

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    setObserver()
  }
}