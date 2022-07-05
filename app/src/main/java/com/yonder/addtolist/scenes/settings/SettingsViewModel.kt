package com.yonder.addtolist.scenes.settings

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import com.yonder.addtolist.R
import com.yonder.addtolist.common.enums.ProviderType
import com.yonder.addtolist.data.local.UserPreferenceDataStore
import com.yonder.addtolist.domain.usecase.UserInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val userInfoUseCase: UserInfoUseCase,
    private val userPreferenceDataStore: UserPreferenceDataStore
) : ViewModel() {


    private val _state: MutableStateFlow<SettingsUIState> =
        MutableStateFlow(SettingsUIState.Initial(provideList(userPreferenceDataStore.getProviderType())))
    val state: StateFlow<SettingsUIState> get() = _state

    private fun provideList(providerType: ProviderType): List<ImageDetail> {
        Timber.d("provideList => $providerType")
        val arrayList: ArrayList<ImageDetail> = arrayListOf()

        arrayList.addAll(
            listOf(
                ImageDetail(
                    titleResId = R.string.about,
                    leftImageResId = R.drawable.ic_baseline_info_24
                ),
                ImageDetail(
                    titleResId = R.string.language,
                    leftImageResId = R.drawable.ic_baseline_language_24
                ),
                ImageDetail(
                    titleResId = R.string.rate_us,
                    leftImageResId = R.drawable.ic_baseline_rate_review_24
                ),
               /* ImageDetail(
                    titleResId = R.string.share_app,
                    leftImageResId = R.drawable.ic_baseline_share_24
                ),*/
              /*  ImageDetail(
                    titleResId = R.string.be_premium,
                    leftTitleResId = R.string.premium_emo
                ),*/
                // Enable after first version
                /*
                ImageDetail(
                  titleResId = R.string.help_us_to_translate_app,
                  leftTitleResId = R.string.world_emo
                ),*/
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

    fun logout() {
        userInfoUseCase.removeToken()
    }
}

data class ImageDetail(
    @StringRes var titleResId: Int,
    @DrawableRes var leftImageResId: Int? = null,
    @DrawableRes var leftPngResId: Int? = null,
    @StringRes var leftTitleResId: Int? = null,
    @StringRes var rightTitleResId: Int? = null,
    var rightTitle: String? = null,
    var leftImageUrl: String? = null
)

sealed class SettingsUIState {
    object Loading : SettingsUIState()
    data class Initial(val imageDetailList: List<ImageDetail>) : SettingsUIState()
}
