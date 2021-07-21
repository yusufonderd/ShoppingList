package com.yonder.addtolist.common.ui.component

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isInvisible
import com.yonder.addtolist.R
import com.yonder.addtolist.databinding.LayoutYoImageTextBinding


/**
 * @author yusuf.onder
 * Created on 20.07.2021
 */
private const val DEFAULT_RES_ID = -1
class YoImageTextView @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

  private val binding: LayoutYoImageTextBinding by lazy {
    LayoutYoImageTextBinding.inflate(LayoutInflater.from(context), this, true)
  }

  init {
    val ta = context.obtainStyledAttributes(attrs, R.styleable.YoImageTextView, 0, 0)
    try {
      val imageResId = ta.getResourceId(R.styleable.YoImageTextView_yo_imageResId, DEFAULT_RES_ID)
      val titleText = ta.getString(R.styleable.YoImageTextView_yo_text).orEmpty()

      val leftText = ta.getString(R.styleable.YoImageTextView_yo_left_text).orEmpty()

      val endText = ta.getString(R.styleable.YoImageTextView_yo_end_text).orEmpty()
      if (imageResId != DEFAULT_RES_ID){
        binding.imageView.setImageResource(imageResId)
      }
      binding.leftText.text = leftText
      binding.imageView.isInvisible = imageResId == DEFAULT_RES_ID
      binding.tvTitle.text = titleText
      binding.tvEnd.text = endText
    } finally {
      ta.recycle()
    }
  }


}