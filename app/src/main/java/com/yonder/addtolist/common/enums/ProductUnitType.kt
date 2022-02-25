package com.yonder.addtolist.common.enums

/**
 * @author yusuf.onder
 * Created on 21.08.2021
 */

enum class ProductUnitType(val value: String, val index: Int) {
    Piece("piece", 0),
    Package("package", 1),
    Kg("kg", 2),
    Lt("lt", 3),
    NoChoice("", -1);

    companion object {
        fun find(index: Int): ProductUnitType = values().find { it.index == index } ?: NoChoice
        fun find(value: String): ProductUnitType = values().find { it.value == value } ?: NoChoice
    }

}
