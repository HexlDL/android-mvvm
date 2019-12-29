package com.hexl.basemvvm.net.file.upload

import android.content.Context
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import okhttp3.MultipartBody
import java.io.File

/**
 * @desc 上传文件工具类
 * @author Hexl
 * @date 2019/11/28
 */
class UploadFileHelper(val context: Context, val files: List<File>,val coroutineScope:CoroutineScope) {

    /**
     * 将File 转换 Multipart 类型
     * @param listener 默认实现[com.hexl.basemvvm.net.file.upload.UploadFileRequestBody.UploadFileProgressListener]
     * 如开发者需要监听状态也可自行实现
     */
    fun convertFileToMultipart(
        listener: UploadFileRequestBody.UploadFileProgressListener = object :
            UploadFileRequestBody.UploadFileProgressListener(context, files,coroutineScope) {
        }
    ): MultipartBody {
        val builder = MultipartBody.Builder()

        files.forEach {
            val requestBody = UploadFileRequestBody(it, listener)
            builder
                // 与服务端约定参数
                .addFormDataPart("file", it.name, requestBody)
                // 与服务端约定参数
                .addFormDataPart("multifile", "true")
        }

        return builder
            .setType(MultipartBody.FORM)
            .build()
    }
}