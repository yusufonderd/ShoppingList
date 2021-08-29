package com.yonder.uicomponent.basic

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.annotation.StringRes
import androidx.constraintlayout.widget.ConstraintLayout
import com.yonder.uicomponent.databinding.LayoutYoTitleButtonBinding

class YoTitleButtonView @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

  private val binding: LayoutYoTitleButtonBinding by lazy {
    LayoutYoTitleButtonBinding.inflate(LayoutInflater.from(context), this, true)
  }

  fun initView(
    @StringRes titleResId: Int,
    @StringRes buttonTextResId: Int,
    onClickButton: (() -> Unit)
  ) = with(binding) {
    textView.setText(titleResId)
    button.setText(buttonTextResId)
    button.setOnClickListener {
      onClickButton.invoke()
    }
  }

}
