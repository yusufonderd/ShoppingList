package com.yonder.addtolist.scenes.languageselection

import com.yonder.addtolist.domain.uimodel.LanguageUiModel

/**
 * @author yusuf.onder
 * Created on 4.09.2021
 */
sealed class LanguageSelectionViewEvent {
    object Initial : LanguageSelectionViewEvent()
    data class Load(val languages: List<LanguageUiModel>) : LanguageSelectionViewEvent()
    data class Error(val message: String) : LanguageSelectionViewEvent()
}
