package com.yonder.addtolist.core.network.exceptions

/**
 * @author yusuf.onder
 * Created on 19.07.2021
 */
private const val SERVER_ERROR_MESSAGE = "Server response success is false"
class ServerResultException : Throwable(SERVER_ERROR_MESSAGE)


