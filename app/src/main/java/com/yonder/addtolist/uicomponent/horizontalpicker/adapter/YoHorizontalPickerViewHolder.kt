package com.yonder.addtolist.uicomponent.horizontalpicker.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.yonder.addtolist.databinding.ItemYoPickerBinding

class YoHorizontalPickerViewHolder(
  view: View,
  private val onClickPicker: ((value: String) -> Unit)
) : RecyclerView.ViewHolder(view) {
  private val binding = ItemYoPickerBinding.bind(view)

  fun bind(value: String) = with(binding) {
    tvPicker.text = value
    itemView.setOnClickListener {
      onClickPicker.invoke(value)
    }
  }
}
