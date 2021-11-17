package com.yonder.addtolist.scenes.login.domain.model

import com.yonder.addtolist.core.network.responses.BaseUiModel
import com.yonder.addtolist.core.network.responses.BaseUiResult

/**
 * Yusuf Onder on 09,May,2021
 */
/*
* var first_name : String?
var last_name : String?
var device_uuid : String?
var api_token : String?
var provider_type : String?
var photo_url : String?
var created_at : String
var premium : Int?
var email : String?*/

data class UserUiModel(
  override val result: BaseUiResult,
  val token: String?,
  val email: String,
  val profileImage: String,
  val firstName: String,
  val lastName: String,
  val isPremium: Boolean,
  val fullName: String,
  val createdAt : String
) : BaseUiModel(result)

