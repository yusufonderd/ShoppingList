package com.yonder.addtolist.common.enums

/**
 * @author yusuf.onder
 * Created on 17.02.2022
 */


enum class AppLanguage(var languageTag: String, var languageId: Int) {
    TR("tr", 1),
    EN("en", 2),
    DE("de", 3),
    FR("fr", 4),
    RU("ru", 5),
    ES("es", 6),
    AR("ar", 7),
    IN("hi", 8),
    RO("ro", 9);

    companion object {
        fun find(languageTag: String): AppLanguage =
            values().find { it.languageTag == languageTag } ?: EN
    }

}