package com.yonder.addtolist.uicomponent.colorpicker

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.GridLayoutManager
import com.yonder.addtolist.R
import com.yonder.addtolist.databinding.LayoutYoColorPickerBinding
import com.yonder.addtolist.uicomponent.colorpicker.adapter.YoColorPickerAdapter

/**
 * @author yusuf.onder
 * Created on 19.07.2021
 */

class YoColorPickerView @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

  private val binding: LayoutYoColorPickerBinding by lazy {
    LayoutYoColorPickerBinding.inflate(LayoutInflater.from(context), this, true)
  }

  fun initView(
    colorValues: IntArray = context.resources.getIntArray(R.array.rainbow),
    spanCount: Int = 6,
    initialPosition: Int = 0,
    onClickPicker: ((value: Int) -> Unit) = {},
    onClickCreateOwnColor: (() -> Unit) = {}
  ) {
    initOwnColor(onClickCreateOwnColor)
    initRecyclerView(colorValues, spanCount, initialPosition, onClickPicker)
  }

  private fun initOwnColor(onClickCreateOwnColor: (() -> Unit)) = with(binding.tvOwnColor) {
    setOnClickListener {
      onClickCreateOwnColor.invoke()
    }
  }

  private fun initRecyclerView(
    colorValues: IntArray,
    spanCount: Int,
    initialPosition: Int,
    onClickPicker: ((value: Int) -> Unit)
  ) = with(binding.recyclerView) {
    layoutManager = GridLayoutManager(context, spanCount)
    adapter = YoColorPickerAdapter(initialPosition, onClickPicker).apply {
      submitList(colorValues.toList())
    }
  }

  fun getSelectedColorResId(): Int {
    val adapter = (binding.recyclerView.adapter as YoColorPickerAdapter)
    return adapter.getSelectedItemPosition(adapter.selectedItemPosition)
  }

}
