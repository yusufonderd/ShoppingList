package com.yonder.addtolist.scenes.detail.adapter.productlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yonder.addtolist.R
import com.yonder.addtolist.common.ui.base.BaseListAdapter
import com.yonder.addtolist.local.entity.ProductEntitySummary
import com.yonder.addtolist.local.entity.UserListProductEntity

/**
 * @author yusuf.onder
 * Created on 19.07.2021
 */

class ProductListsAdapter(private val onClickProduct: ((value: ProductEntitySummary) -> Unit)) :
  BaseListAdapter<ProductEntitySummary>(
    itemsSame = { old, new -> old.name == new.name },
    contentsSame = { old, new -> old == new }
  ) {

  var userListProducts: List<UserListProductEntity> = listOf()

  override fun onCreateViewHolder(
    parent: ViewGroup,
    inflater: LayoutInflater,
    viewType: Int
  ): RecyclerView.ViewHolder {
    val view = LayoutInflater.from(parent.context).inflate(R.layout.item_product, parent, false)
    return ProductListViewHolder(view, onClickProduct)
  }

  override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
    when (holder) {
      is ProductListViewHolder -> {
        val product = getItem(position)
        holder.bind(product, getUserListProduct(product))
      }
    }
  }

  private fun getUserListProduct(value: ProductEntitySummary): UserListProductEntity? {
    return userListProducts.find { value.name == it.name }
  }

}