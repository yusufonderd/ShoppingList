package com.yonder.addtolist.common.ui.component.list.result

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.yonder.addtolist.common.ui.component.list.result.model.ItemUiModelMapper
import com.yonder.addtolist.common.ui.extensions.addVerticalDivider
import com.yonder.addtolist.common.ui.extensions.removeAnimator
import com.yonder.addtolist.databinding.LayoutYoFilteredItemsBinding
import com.yonder.addtolist.local.entity.ProductEntitySummary
import com.yonder.addtolist.local.entity.UserListWithProducts

/**
 * @author yusuf.onder
 * Created on 23.07.2021
 */
class YoFilteredItemsView @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

  private val binding: LayoutYoFilteredItemsBinding by lazy {
    LayoutYoFilteredItemsBinding.inflate(LayoutInflater.from(context), this, true)
  }

  var adapter: ProductListsAdapter? = null

  init {
    initRecyclerView()
  }

  private fun initRecyclerView() = with(binding.rvItems) {
    addVerticalDivider()
    removeAnimator()
  }

  fun bind(
    userListWithProducts: UserListWithProducts,
    list: List<ProductEntitySummary>,
    query: String,
    productOperation: IProductOperation

  ) {

    val itemsList = ItemUiModelMapper.mapToUiModel(
      addedProducts = userListWithProducts.products,
      filteredProducts = list
    )

    binding.tvHeader.isVisible = query.isEmpty()
    if (adapter == null) {
      adapter = ProductListsAdapter().apply {
        iProductOperation = productOperation
        submitList(itemsList)
      }
      binding.rvItems.adapter = adapter
    } else {
      adapter?.submitList(itemsList)
    }
  }

}
