package com.yonder.addtolist.common.ui.component.productlist

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.yonder.addtolist.common.ui.component.productlist.adapter.ListProductAdapter
import com.yonder.addtolist.common.ui.extensions.addVerticalDivider
import com.yonder.addtolist.common.ui.extensions.removeAnimator
import com.yonder.addtolist.databinding.LayoutYoProductListBinding
import com.yonder.addtolist.local.entity.UserListWithProducts

/**
 * @author yusuf.onder
 * Created on 24.07.2021
 */

class YoProductListView @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

  private val binding: LayoutYoProductListBinding by lazy {
    LayoutYoProductListBinding.inflate(LayoutInflater.from(context), this, true)
  }

  var adapter: ListProductAdapter? = null

  init {
    initRecyclerView()
  }

  private fun initRecyclerView() = with(binding.rvProducts) {
    addVerticalDivider()
    removeAnimator()
  }

  fun bind(userListWithProducts: UserListWithProducts){
    if (adapter == null) {
      adapter = ListProductAdapter().apply {
        submitList(userListWithProducts.products)
      }
      binding.rvProducts.adapter = adapter
    } else {
      adapter?.apply {
        submitList(userListWithProducts.products)
      }
    }
  }
}

