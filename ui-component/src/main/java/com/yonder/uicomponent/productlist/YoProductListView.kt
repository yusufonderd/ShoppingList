package com.yonder.uicomponent.productlist

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.recyclerview.widget.SimpleItemAnimator
import com.yonder.uicomponent.productlist.adapter.ListProductAdapter
import com.yonder.uicomponent.base.model.UserListProductUiModel
import com.yonder.uicomponent.databinding.LayoutYoProductListBinding
import com.yonder.uicomponent.extensions.addVerticalDivider

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

  fun setVisible(isVisible: Boolean) {
    this.isVisible = isVisible
  }

  private fun initRecyclerView() = with(binding.rvProducts) {
    addVerticalDivider()
  }

  fun bind(products: List<UserListProductUiModel>, productOperationListener: IProductOperation) {
    val sortedProducts = products.sortedBy { it.isDone }
    if (adapter == null) {
      //Disable blinking animation
      (binding.rvProducts.itemAnimator as? SimpleItemAnimator)
        ?.supportsChangeAnimations = false

      adapter = ListProductAdapter().apply {
        submitList(sortedProducts)
        this.iProductOperation = productOperationListener
      }
      binding.rvProducts.adapter = adapter
    } else {
      adapter?.apply {
        submitList(sortedProducts)
      }
    }


  }
}

