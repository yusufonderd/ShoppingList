package com.yonder.addtolist.scenes.languageselection.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yonder.addtolist.R
import com.yonder.addtolist.common.ui.base.BaseListAdapter
import com.yonder.addtolist.scenes.languageselection.data.model.LanguageUiModel

/**
 * @author yusuf.onder
 * Created on 4.09.2021
 */
class LanguageAdapter : BaseListAdapter<LanguageUiModel>(
  itemsSame = { old, new -> old.id == new.id },
  contentsSame = { old, new -> old == new }
) {

  var onClickLanguage: ((LanguageUiModel) -> Unit)? = null

  override fun onCreateViewHolder(
    parent: ViewGroup,
    inflater: LayoutInflater,
    viewType: Int
  ): RecyclerView.ViewHolder {
    val view = LayoutInflater.from(parent.context).inflate(R.layout.item_language, parent, false)
    return LanguageViewHolder(view)
  }

  override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
    when (holder) {
      is LanguageViewHolder -> {
        holder.bind(getItem(position),onClickLanguage)
      }
    }
  }
}
