package com.yonder.addtolist.scenes.listdetail.productlist

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import com.yonder.addtolist.databinding.LayoutYoProductListBinding
import com.yonder.addtolist.scenes.home.domain.model.UserListProductUiModel
import com.yonder.addtolist.scenes.listdetail.productlist.adapter.ListProductAdapter
import com.yonder.uicomponent.extensions.addVerticalDivider

/**
 * @author yusuf.onder
 * Created on 24.07.2021
 */

class YoProductListView @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr), UserListProductOperationListener {

  private val binding: LayoutYoProductListBinding by lazy {
    LayoutYoProductListBinding.inflate(LayoutInflater.from(context), this, true)
  }

  private var adapter: ListProductAdapter? = null
  private var lastModifiedIndex: Int? = null
  private lateinit var listener: UserListProductOperationListener

  init {
    initRecyclerView()
  }

  fun setVisible(isVisible: Boolean) {
    this.isVisible = isVisible
  }


  private fun initRecyclerView() {
    binding.rvProducts.addVerticalDivider()
  }

  fun bind(
    products: List<UserListProductUiModel>,
    listener: UserListProductOperationListener
  ) {
    this.listener = listener
    val sortedProducts = products.sortedBy { it.isDone }
    if (adapter == null) {
      binding.rvProducts.adapter = ListProductAdapter().apply {
        submitList(sortedProducts)
        userListProductOperationListener = this@YoProductListView
      }.also { adapter = it }
    } else {
      adapter?.apply {
        submitList(sortedProducts)
        lastModifiedIndex?.let {
          notifyItemChanged(it)
        }
      }
    }
  }

  override fun edit(item: UserListProductUiModel) {
    listener.edit(item)
  }

  override fun toggleDone(item: UserListProductUiModel, productPosition: Int) {
    lastModifiedIndex = productPosition
    listener.toggleDone(item, productPosition)
  }

}

