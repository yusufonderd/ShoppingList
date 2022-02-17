package com.yonder.addtolist.scenes.languageselection

import com.yonder.addtolist.domain.uimodel.LanguageUiModel

/**
 * @author yusuf.onder
 * Created on 4.09.2021
 */
sealed class LanguageSelectionViewEvent {
    object Loading : LanguageSelectionViewEvent()
    object Error : LanguageSelectionViewEvent()
    data class Load(val languages: List<LanguageUiModel>) : LanguageSelectionViewEvent()
}
