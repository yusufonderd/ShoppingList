package com.yonder.addtolist.common.ui.component.list.result

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yonder.addtolist.R
import com.yonder.addtolist.common.ui.base.BaseListAdapter
import com.yonder.addtolist.common.ui.component.list.result.model.ItemUiModel
import com.yonder.addtolist.local.entity.ProductEntitySummary
import com.yonder.addtolist.local.entity.UserListProductEntity

/**
 * @author yusuf.onder
 * Created on 19.07.2021
 */

class ProductListsAdapter :
  BaseListAdapter<ItemUiModel>(
    itemsSame = { old, new -> old.name == new.name || old.entity?.quantity == new.entity?.quantity },
    contentsSame = { old, new -> old == new || old.entity == new.entity }
  ) {

  lateinit var iProductOperation: IProductOperation

  override fun onCreateViewHolder(
    parent: ViewGroup,
    inflater: LayoutInflater,
    viewType: Int
  ): RecyclerView.ViewHolder {
    val view = LayoutInflater.from(parent.context).inflate(R.layout.item_product, parent, false)
    return ProductListViewHolder(view, iProductOperation)
  }

  override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
    when (holder) {
      is ProductListViewHolder -> {
        val product = getItem(position)
        holder.bind(product)
      }
    }
  }

}