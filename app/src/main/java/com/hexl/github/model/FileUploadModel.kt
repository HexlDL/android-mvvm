package com.hexl.github.model

import com.hexl.github.api.GitHubService
import com.hexl.basemvvm.ui.base.BaseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MultipartBody
import org.kodein.di.Kodein

class FileUploadModel(kodein: Kodein) : BaseModel(kodein) {

    suspend fun fileUpload(url: String, multipartBody: MultipartBody) =
        withContext(Dispatchers.IO) {
            retrofitManager
                .createService(GitHubService::class.java)
                .fileUpload(url, multipartBody)
        }
}