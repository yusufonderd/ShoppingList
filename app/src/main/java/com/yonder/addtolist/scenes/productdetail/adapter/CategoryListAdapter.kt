package com.yonder.addtolist.scenes.productdetail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.yonder.addtolist.R
import com.yonder.addtolist.scenes.productdetail.model.CategoryUIModel

/**
 * @author yusuf.onder
 * Created on 29.08.2021
 */
class CategoryListAdapter : ListAdapter<CategoryUIModel, CategoryViewHolder>(categoryDiffCallback), Filterable {

  companion object {
    val categoryDiffCallback = object : DiffUtil.ItemCallback<CategoryUIModel>() {
      override fun areItemsTheSame(oldItem: CategoryUIModel, newItem: CategoryUIModel): Boolean {
        return oldItem.image == newItem.image
      }

      override fun areContentsTheSame(oldItem: CategoryUIModel, newItem: CategoryUIModel): Boolean {
        return oldItem == newItem
      }
    }
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
    val inflater = LayoutInflater.from(parent.context)
    val view = inflater.inflate(R.layout.item_material_spinner, parent, false)
    return CategoryViewHolder(view)
  }

  override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
    holder.bind(getItem(position))
  }

  override fun getFilter(): Filter {
    return filter
  }

  private val filter = object: Filter() {
    override fun performFiltering(constraint: CharSequence?): FilterResults {
      val filterResults = FilterResults()
      filterResults.values = currentList
      filterResults.count = currentList.size
      return filterResults
    }
    override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
      notifyDataSetChanged()
    }
  }


}


