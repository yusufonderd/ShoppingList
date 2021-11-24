package com.yonder.addtolist.scenes.settings.presentation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import com.yonder.addtolist.R
import com.yonder.addtolist.common.ProviderType
import com.yonder.addtolist.data.local.UserPreferenceDataStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
  var userPreferenceDataStore: UserPreferenceDataStore
) : ViewModel() {

  private val _state: MutableStateFlow<SettingsUIState> =
    MutableStateFlow(SettingsUIState.Initial(provideList(userPreferenceDataStore.getProviderType())))
  val state: StateFlow<SettingsUIState> get() = _state

  private fun provideList(providerType: ProviderType): List<ImageDetail> {
    val arrayList: ArrayList<ImageDetail> = arrayListOf()
    val imageDetail =
      if (providerType == ProviderType.UNKNOWN || providerType == ProviderType.GUEST) {
        ImageDetail(
          titleResId = R.string.account,
          leftImageResId = R.drawable.ic_baseline_person_outline_24,
          rightTitleResId = R.string.create_account
        )
      } else {
        ImageDetail(
          titleResId = R.string.account,
          leftImageResId = R.drawable.ic_baseline_person_outline_24
        )
      }
    arrayList.add(imageDetail)
    arrayList.addAll(
      listOf(
        ImageDetail(titleResId = R.string.about, leftImageResId = R.drawable.ic_baseline_info_24),
        ImageDetail(
          titleResId = R.string.language,
          leftImageResId = R.drawable.ic_baseline_language_24
        ),
        ImageDetail(
          titleResId = R.string.rate_us,
          leftImageResId = R.drawable.ic_baseline_rate_review_24
        ),
        ImageDetail(
          titleResId = R.string.share_app,
          leftImageResId = R.drawable.ic_baseline_share_24
        ),
        ImageDetail(
          titleResId = R.string.be_premium,
          leftTitleResId = R.string.premium_emo
        ),
        ImageDetail(
          titleResId = R.string.help_us_to_translate_app,
          leftTitleResId = R.string.world_emo
        ),
        ImageDetail(
          titleResId = R.string.instagram,
          leftPngResId = R.drawable.instagram_sketched
        ),
        ImageDetail(
          titleResId = R.string.twitter,
          leftPngResId = R.drawable.twitter
        )
      )
    )
    return arrayList

  }
}

data class ImageDetail(
  @StringRes var titleResId: Int,
  @DrawableRes var leftImageResId: Int? = null,
  @DrawableRes var leftPngResId: Int? = null,
  @StringRes var leftTitleResId: Int? = null,
  @StringRes var rightTitleResId: Int? = null,

  )

sealed class SettingsUIState {
  object Loading : SettingsUIState()

  data class Initial(val imageDetailList: List<ImageDetail>) : SettingsUIState()
}
