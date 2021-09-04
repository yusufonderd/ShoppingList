package com.yonder.addtolist.scenes.languageselection.data.mapper

import com.yonder.addtolist.core.extensions.orZero
import com.yonder.addtolist.core.mapper.Mapper
import com.yonder.addtolist.scenes.languageselection.data.model.LanguageResponse
import com.yonder.addtolist.scenes.languageselection.data.model.LanguageUiModel
import javax.inject.Inject

/**
 * @author yusuf.onder
 * Created on 4.09.2021
 */
class LanguageResponseMapper @Inject constructor() : Mapper<LanguageResponse, LanguageUiModel> {
  override fun map(input: LanguageResponse): LanguageUiModel {
    return LanguageUiModel(
      id = input.id.orZero(),
      tag = input.tag.orEmpty(),
      name = input.name.orEmpty(),
      image = input.image.orEmpty()
    )
  }
}