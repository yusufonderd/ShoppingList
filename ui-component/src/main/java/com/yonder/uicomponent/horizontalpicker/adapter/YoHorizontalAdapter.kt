package com.yonder.uicomponent.horizontalpicker.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yonder.uicomponent.R
import com.yonder.uicomponent.base.BaseListAdapter

class YoHorizontalAdapter (private val onClickPicker: ((value: String) -> Unit)) : BaseListAdapter<String>(
  itemsSame = { old, new -> old === new },
  contentsSame = { old, new -> old == new }
) {
  override fun onCreateViewHolder(
    parent: ViewGroup,
    inflater: LayoutInflater,
    viewType: Int
  ): RecyclerView.ViewHolder {
    val view = LayoutInflater.from(parent.context).inflate(R.layout.item_yo_picker, parent, false)
    return YoHorizontalPickerViewHolder(view,onClickPicker)
  }

  override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
    when(holder){
      is YoHorizontalPickerViewHolder ->{
        holder.bind(getItem(position))
      }
    }
  }

}
