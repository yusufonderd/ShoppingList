package com.yonder.addtolist.scenes.productdetail.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.yonder.addtolist.databinding.ItemMaterialSpinnerBinding
import com.yonder.addtolist.scenes.productdetail.model.CategoryUIModel

/**
 * @author yusuf.onder
 * Created on 29.08.2021
 */

class CategoryViewHolder(
  view: View
) : RecyclerView.ViewHolder(view) {
  private val binding = ItemMaterialSpinnerBinding.bind(view)

  fun bind(categoryUIModel: CategoryUIModel) {
    binding.tvItem.text = categoryUIModel.wrappedName
  }
}
