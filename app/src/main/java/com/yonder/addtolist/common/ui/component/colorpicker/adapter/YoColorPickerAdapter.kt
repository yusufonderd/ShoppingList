package com.yonder.addtolist.common.ui.component.colorpicker.adapter

/**
 * @author yusuf.onder
 * Created on 19.07.2021
 */
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yonder.addtolist.R
import com.yonder.addtolist.common.ui.base.BaseListAdapter

class YoColorPickerAdapter(
  internal var selectedItemPosition: Int,
  private val onClickPicker: ((value: Int) -> Unit)
) : BaseListAdapter<Int>(
  itemsSame = { old, new -> old == new },
  contentsSame = { old, new -> old == new }
) {

  override fun onCreateViewHolder(
    parent: ViewGroup,
    inflater: LayoutInflater,
    viewType: Int
  ): RecyclerView.ViewHolder {
    val view =
      LayoutInflater.from(parent.context).inflate(R.layout.item_yo_color_picker, parent, false)
    return YoColorPickerViewHolder(view)
  }

  override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
    when (holder) {
      is YoColorPickerViewHolder -> {
        holder.bind(getItem(position), isSelected(position), position) { value, pos ->
          selectedItemPosition = pos
          notifyDataSetChanged()
          onClickPicker.invoke(value)
        }
      }
    }
  }

  private fun isSelected(position: Int): Boolean {
    return position == selectedItemPosition
  }

  internal fun getSelectedItemPosition(position: Int): Int {
    return getItem(position)
  }

}
