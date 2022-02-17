package com.yonder.addtolist.common.enums

import androidx.annotation.ColorRes
import com.yonder.addtolist.R

/**
 * @author yusuf.onder
 * Created on 16.02.2022
 */

enum class AppColor(var colorName: String, @ColorRes var colorResId: Int) {
    Green("Green", R.color.listColor1),
    Orange("Orange", R.color.listColor2),
    Blue("Blue", R.color.listColor3),
    Pink("Pink", R.color.listColor4),
    Purple("Purple", R.color.listColor5),
    Red("Red", R.color.listColor6),
    Premium1("Premium1", R.color.premiumColor1),
    Premium2("Premium2", R.color.premiumColor2),
    Premium3("Premium3", R.color.premiumColor3),
    Premium4("Premium4", R.color.premiumColor4),
    Premium5("Premium5", R.color.premiumColor5),
    Premium6("Premium6", R.color.premiumColor6),
    Premium7("Premium7", R.color.premiumColor7),
    Premium8("Premium8", R.color.premiumColor8),
    Premium9("Premium9", R.color.premiumColor9),
    Premium10("Premium10", R.color.premiumColor10),
    Premium11("Premium11", R.color.premiumColor11),
    Premium12("Premium12", R.color.premiumColor12);

    companion object {
        fun find(colorName: String): AppColor = values().find { it.colorName == colorName } ?: Blue
    }

}