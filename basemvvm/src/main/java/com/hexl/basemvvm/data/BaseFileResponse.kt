package com.hexl.basemvvm.data

open class BaseFileResponse(val code: String,
                            val message: String,
                            var content: List<Content>) {
    fun isSucceeded() = code == "0000"
}

open class Content(
    open var fileName: String,// 文件名称
    open var fileType: String,// 文件类型
    open var fileId: String,// 文件id
    open var fileDuration: String,// 文件时长
    open var coverId: String,// 缩略图id
    open var fileSize: Int// 文件大小
) {
    var asrText: String = ""
}