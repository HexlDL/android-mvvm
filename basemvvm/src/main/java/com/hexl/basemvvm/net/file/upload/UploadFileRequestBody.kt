package com.hexl.basemvvm.net.file.upload

import android.content.Context
import androidx.fragment.app.FragmentActivity
import com.hexl.basemvvm.ui.view.UploadFileDialog
import com.hexl.basemvvm.utils.LogUtil
import kotlinx.coroutines.CoroutineScope
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okio.*
import java.io.File

/**
 * @desc 自定义文件上传进度监听
 * @author Hexl
 * @date 2019/11/28
 */
open class UploadFileRequestBody(
    private val file: File,
    private val listener: UploadFileProgressListener
) :
    RequestBody() {

    private var requestBody = create("application/octet-stream".toMediaTypeOrNull(), file)

    override fun contentLength() = file.length()

    override fun contentType() = requestBody.contentType()

    override fun writeTo(sink: BufferedSink) {
        val countingSink = CountingSink(sink, listener, contentLength(), file)
        val buffer = countingSink.buffer()
        // 写入自定义的RequestBody
        requestBody.writeTo(buffer)
        // 刷新
        buffer.flush()
    }

    class CountingSink(
        sink: BufferedSink,
        private val listener: UploadFileProgressListener,
        private val contentLength: Long,
        private val file: File
    ) : ForwardingSink(sink) {

        companion object {
            val TAG = CountingSink::class.java.simpleName
        }

        var bytesWritten: Long = 0
        // 去除重复进度
        var tempProgress: Int = 0

        override fun write(source: Buffer, byteCount: Long) {
            super.write(source, byteCount)
            // 当前进度
            bytesWritten += byteCount
            // 当前文件长度 / 文件长度 * 100 = 当前进度百分比
            val currentProgress = (bytesWritten * 100 / contentLength).toInt()
            if (tempProgress != currentProgress) {
                listener.uploadFileProgressListener(currentProgress, file)
                tempProgress = currentProgress
            }
        }

        override fun close() {
            super.close()
            LogUtil.e(TAG, "close file")
        }

        override fun flush() {
            super.flush()
            LogUtil.e(TAG, "flush file")
            listener.uploadFileCompleted(file)
        }

        override fun timeout(): Timeout {
            LogUtil.e(TAG, "timeout file")
            return super.timeout()
        }
    }

    /**
     * 文件上传监听器
     * @param  files 文件集合
     */
    abstract class UploadFileProgressListener(
        val context: Context,
        private val files: List<File>,
        coroutineScope: CoroutineScope
    ) {
        companion object {
            val TAG = UploadFileProgressListener::class.java.simpleName
        }

        private val dialog = UploadFileDialog(context,coroutineScope)

        init {
            dialog.convertFileToUploadFileEntity(files)
        }

        /**
         * 上传文件回调方法
         * @param currentProgress 当前进度
         * @param currentFile 当前文件
         */
        open fun uploadFileProgressListener(currentProgress: Int, currentFile: File) {
            LogUtil.e(
                TAG,
                "currentProgress = $currentProgress 文件名称：${currentFile.name} "
            )

            // 该方法在文件上传时每次必然回调,所以在内部做判断如果Dialog已经展示在Activity or Fragment 中就不在展示
            dialog.showLoading(
                manager = (context as FragmentActivity).supportFragmentManager,
                tag = "UploadFileRequestWrapper"
            )
            // 每次更新UI都延迟500毫秒
            // 因为创建Dialog需要时间也同时防止上传文件过小网速过快导致Dialog一闪而过
            Thread.sleep(500L)
            if (currentProgress <= 100) {
                dialog.notifyAdapter(currentProgress, currentFile)
            }
        }

        /**
         * 上传文件完成回调方法
         */
        open fun uploadFileCompleted(file: File) {
            dialog.hideLoading()
        }
    }
}