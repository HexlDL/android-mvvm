package com.hexl.github.viewmodel

import android.content.Context
import android.os.Environment
import androidx.lifecycle.viewModelScope
import com.hexl.github.model.FileUploadModel
import com.hexl.basemvvm.data.BaseFileResponse
import com.hexl.basemvvm.data.DataStateLiveData
import com.hexl.basemvvm.net.file.upload.UploadFileHelper
import com.hexl.basemvvm.ui.base.BaseViewModel
import org.kodein.di.Kodein
import java.io.File

class UploadFileViewModel(context: Context, kodein: Kodein) : BaseViewModel(context, kodein) {

    var data = DataStateLiveData<BaseFileResponse>()

    fun request(): DataStateLiveData<BaseFileResponse> {
        val url = "http://172.17.0.31:9011/web-bizupload/upload/file"
        val path1 =
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                .absolutePath + "/Calendar.tgz"
        val path2 =
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                .absolutePath + "/Contacts.tgz"
        val file1 = File(path1)
        val file2 = File(path2)
        val files = mutableListOf<File>()
        files.add(file1)
        files.add(file2)

        launchUI({
            data.postLoading()

            val uploadModel = createModel(FileUploadModel::class.java)

            val baseFileResponse = uploadModel.fileUpload(
                url,
                UploadFileHelper(context, files, viewModelScope).convertFileToMultipart()
            )

            data.postValue(baseFileResponse)

        })
        return data
    }

    override fun onCreate() {
        super.onCreate()
//        request()
    }
}
