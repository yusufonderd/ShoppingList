package com.yonder.addtolist.common.ui.component.colorpicker

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.yonder.addtolist.databinding.LayoutYoColorPickerBinding

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

}
