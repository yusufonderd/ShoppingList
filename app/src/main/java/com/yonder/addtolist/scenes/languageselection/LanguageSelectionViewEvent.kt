package com.yonder.addtolist.scenes.languageselection

import com.yonder.addtolist.core.data.State
import com.yonder.addtolist.scenes.languageselection.data.model.LanguageUiModel

/**
 * @author yusuf.onder
 * Created on 4.09.2021
 */
sealed class LanguageSelectionViewEvent {
  data class SetLayoutState(val layoutState: State<*>) : LanguageSelectionViewEvent()
  data class Load(val languages : List<LanguageUiModel>):  LanguageSelectionViewEvent()
}
