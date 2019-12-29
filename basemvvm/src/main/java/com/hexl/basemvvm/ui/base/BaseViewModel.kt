package com.hexl.basemvvm.ui.base

import android.content.Context
import android.net.ParseException
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.JsonParseException
import com.hexl.basemvvm.ui.IVM
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import org.json.JSONException
import org.kodein.di.Kodein
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

/**
 * @desc BaseViewModel职责分配：
 * 1 对数据请求进行统一管理，让开发者更专注于业务开发
 * 2 对HTTP异常处理，防止由于解析错误、500、404等原因造成应用崩溃（未来可能做数据埋点）
 * 3 通过[Lifecycle]监听所有继承[BaseActivity] or [BaseFragment] 组件的生命周期
 * <code>
 * override fun onCreate() {
 *      super.onCreate()
 *           launchUI {
 *               currentDatas.postLoading()
 *               val response = createModel(GitHubModel::class.java).getNewList()
 *               currentDatas.postValue(response.currentDatas!!)
 *           }
 *      }
 * </code>
 * @author Hexl
 * @date 2019/11/15
 */
abstract class BaseViewModel(val context: Context, val kodein: Kodein) : ViewModel(), IVM {
    companion object {
        val TAG = BaseViewModel::class.java.simpleName
    }

    /**
     * viewModel 支持当UI页面销毁时取消viewModelScope创建的任务,避免内存泄漏
     */
    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }

    /**
     * @param block 执行挂起的代码块
     */
    fun launchUI(
        block: suspend () -> Unit,
        onCompleted: () -> Unit = {},
        onError: (e: Throwable) -> Unit = {}
    ) {
        viewModelScope.launch {
            try {
                block()
            } catch (e: Throwable) {
                onError(e)
                // Retrofit在支持协程的时候没有提供 onFailure() 这样的回调,如果想捕获异常信息需要在catch中执行
                handleError(e)
            } finally {
                onCompleted()
            }
        }
    }

    /**
     * 通过反射创建M层
     */
    override fun <T> createModel(modelClass: Class<T>) =
        modelClass.getConstructor(Kodein::class.java).newInstance(kodein)

    /**
     * 异常处理
     */
    override fun handleError(e: Throwable) {
        when (e) {
            is JSONException,
            is ParseException,
            is JsonParseException,
            is IllegalStateException -> {
                println("----------hexl: parse error,check currentDatas format")
            }
            is HttpException -> {
                println("----------hexl: http error")
            }
            is ConnectException,
            is UnknownHostException -> {
                println("----------hexl: connect error")
            }
            is InterruptedException,
            is SocketTimeoutException -> {
                println("----------hexl: connect time out")
            }
            else -> {
                println("----------hexl: error: ${e.message}")
            }
        }
    }

    //----------------------------- 生命周期与Activity or Fragment 相同 -----------------------------//
    override fun onCreate() {
    }

    override fun onDestroy() {
    }

    override fun onResume() {
    }

    override fun onPause() {
    }

    override fun onStart() {
    }

    override fun onStop() {
    }
    //----------------------------- 生命周期与Activity or Fragment 相同 -----------------------------//
}