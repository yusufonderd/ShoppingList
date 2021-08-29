package com.yonder.uicomponent.colorpicker

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.GridLayoutManager
import com.yonder.uicomponent.R
import com.yonder.uicomponent.colorpicker.adapter.YoColorPickerAdapter
import com.yonder.uicomponent.constants.FIRST_INDEX
import com.yonder.uicomponent.constants.GRID_SPAN_COUNT_6
import com.yonder.uicomponent.databinding.LayoutYoColorPickerBinding

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
    spanCount: Int = GRID_SPAN_COUNT_6,
    initialPosition: Int = FIRST_INDEX,
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
