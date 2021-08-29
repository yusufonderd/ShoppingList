package com.yonder.uicomponent.colorpicker.adapter

/**
 * @author yusuf.onder
 * Created on 19.07.2021
 */

import android.content.res.ColorStateList
import android.graphics.Color
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.yonder.uicomponent.databinding.ItemYoColorPickerBinding

class YoColorPickerViewHolder(
  view: View
) : RecyclerView.ViewHolder(view) {
  private val binding = ItemYoColorPickerBinding.bind(view)

  fun bind(
    value: Int,
    isSelected: Boolean,
    position: Int,
    onClickPicker: ((value: Int, position: Int) -> Unit)
  ) = with(binding) {
    if (isSelected) {
      cardViewOuter.setStrokeColor(ColorStateList.valueOf(value))
    }else{
      cardViewOuter.strokeColor = Color.TRANSPARENT

    }
    flInsideCardView.setBackgroundColor(value)
    itemView.setOnClickListener {
      onClickPicker.invoke(value, position)
    }
  }
}
