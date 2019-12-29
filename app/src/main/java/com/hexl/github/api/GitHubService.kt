package com.hexl.github.api

import com.hexl.github.response.ChaptersResponse
import com.hexl.basemvvm.data.BaseFileResponse
import com.hexl.basemvvm.data.BaseResponse
import kotlinx.coroutines.Deferred
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*


interface GitHubService {
    @GET("wxarticle/chapters/json")
    fun getChapters(): Call<ChaptersResponse>

    @GET("wxarticle/chapters/json")
    fun getCoroutinesChapters(): Deferred<ChaptersResponse>

    @GET("wxarticle/chapters/json")
    suspend fun getNewCoroutinesChapters(): BaseResponse<List<ChaptersResponse>>

    @Streaming
    @POST
    suspend fun fileUpload(@Url url: String, @Body multipart: MultipartBody): BaseFileResponse

    @Streaming
    @POST
    suspend fun uploadMultibalFile(@Url url: String, @PartMap map: HashMap<String, MultipartBody>): BaseFileResponse
}