package com.hexl.github.model

import com.hexl.basemvvm.data.BaseResponse
import com.hexl.github.api.GitHubService
import com.hexl.basemvvm.ui.base.BaseModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.kodein.di.Kodein
import kotlin.coroutines.CoroutineContext

suspend fun <R> launchSyncTask(
    block: suspend CoroutineScope.() -> BaseResponse<R>,
    context: CoroutineContext = Dispatchers.IO
): BaseResponse<R> {
    return withContext(context, block)
}

class GitHubModel(kodein: Kodein) : BaseModel(kodein) {

    suspend fun getNewList() = launchSyncTask({
        retrofitManager
            .createService(GitHubService::class.java)
            .getNewCoroutinesChapters()
    })
}