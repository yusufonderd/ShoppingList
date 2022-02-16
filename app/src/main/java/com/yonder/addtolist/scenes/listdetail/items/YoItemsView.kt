package com.yonder.addtolist.scenes.listdetail.items

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isGone
import androidx.core.view.isVisible
import com.yonder.addtolist.databinding.LayoutYoFilteredItemsBinding
import com.yonder.addtolist.domain.uimodel.UserListProductUiModel
import com.yonder.addtolist.domain.uimodel.ProductEntityUiModel
import com.yonder.addtolist.scenes.listdetail.items.model.ItemUiModel
import com.yonder.addtolist.domain.mapper.ItemUiModelMapper

/**
 * @author yusuf.onder
 * Created on 23.07.2021
 */
class YoItemsView @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

  private val binding: LayoutYoFilteredItemsBinding by lazy {
    LayoutYoFilteredItemsBinding.inflate(LayoutInflater.from(context), this, true)
  }

  var adapter: ItemListAdapter? = null

  init {
    initRecyclerView()
  }

  private fun initRecyclerView() = with(binding.rvItems) {
    itemAnimator = null
    isNestedScrollingEnabled = false
  }

  fun setVisibility(isVisible: Boolean) {
    this.isVisible = isVisible
  }

  fun bind(
    products: List<UserListProductUiModel>,
    list: List<ProductEntityUiModel>,
    query: String,
    itemCallbacks: ItemCallbacks
  ) {

    val itemsList = ItemUiModelMapper.mapToUiModel(
      addedProducts = products,
      filteredProducts = list
    )

    val isVisibleQuery = query.isBlank().not()
    val isQueryExist: Boolean = itemsList.any { it.name == query }
    val shouldVisibleQueryItem = isVisibleQuery && isQueryExist.not()
    binding.yoProductQueryItem.isVisible = shouldVisibleQueryItem
    if (shouldVisibleQueryItem) {
      val entity = products.find { it.name == query }
      val queryItemModel = ItemUiModel(query, entity)
      binding.yoProductQueryItem.bind(
        listener = itemCallbacks,
        query = query,
        value = queryItemModel,
        boldEnabled = false
      )
    }

    binding.tvHeader.isGone = isVisibleQuery
    setAdapter(itemsList, query, itemCallbacks)
  }

  private fun setAdapter(
    itemsList: List<ItemUiModel>,
    query: String,
    itemCallbacks: ItemCallbacks
  ) {
    if (adapter == null) {
      adapter = ItemListAdapter(itemCallbacks = itemCallbacks).apply {
        this.itemCallbacks = itemCallbacks
        this.query = query
        submitList(itemsList)
      }
      binding.rvItems.adapter = adapter
    } else {
      adapter?.query = query
      adapter?.submitList(itemsList)
      adapter?.notifyDataSetChanged()
    }
  }

}
