package com.hexl.basemvvm.data

/**
 * @desc
 * @author Hexl
 * @date 2019/11/15
 */
open class BaseResponse<R> {
    var errorCode: Int = 0
    var errorMsg: String = ""
    var data: R? = null

    // 成功
    fun isErrorCode() = errorCode == 0
}