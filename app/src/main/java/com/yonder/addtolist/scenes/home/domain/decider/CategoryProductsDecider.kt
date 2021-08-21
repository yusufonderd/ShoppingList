package com.yonder.addtolist.scenes.home.domain.decider

import com.yonder.addtolist.core.base.BaseDecider
import com.yonder.addtolist.core.extensions.orZero
import com.yonder.addtolist.scenes.home.data.remote.response.CategoryProductResponse
import javax.inject.Inject

/**
 * Yusuf Onder on 12,May,2021
 */


class CategoryProductsDecider @Inject constructor() : BaseDecider<ArrayList<CategoryProductResponse>>(){
  fun isPopular(popular : Int?) = popular.orZero() == 1
}