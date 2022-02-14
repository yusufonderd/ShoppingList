package com.yonder.addtolist.uicomponent.horizontalpicker

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.yonder.addtolist.databinding.LayoutYoHorizontalPickerBinding
import com.yonder.addtolist.uicomponent.horizontalpicker.adapter.YoHorizontalAdapter

class YoHorizontalPickerView @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

  private val binding: LayoutYoHorizontalPickerBinding by lazy {
    LayoutYoHorizontalPickerBinding.inflate(LayoutInflater.from(context), this, true)
  }

  fun initView(values: Array<String>, onClickPicker: ((value: String) -> Unit)) = with(binding) {
    recyclerView.adapter = YoHorizontalAdapter(onClickPicker).apply {
      submitList(values.toList())
    }
  }

}

