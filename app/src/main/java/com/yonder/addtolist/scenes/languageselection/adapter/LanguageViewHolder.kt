package com.yonder.addtolist.scenes.languageselection.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.yonder.addtolist.databinding.ItemLanguageBinding
import com.yonder.addtolist.scenes.languageselection.data.model.LanguageUiModel

/**
 * @author yusuf.onder
 * Created on 4.09.2021
 */

class LanguageViewHolder(
  view: View
) : RecyclerView.ViewHolder(view) {
  private val binding = ItemLanguageBinding.bind(view)

  fun bind(language: LanguageUiModel) {
    binding.tvLanguage.text = language.name
  }
}
