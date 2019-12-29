package com.hexl.basemvvm.ui

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent

/**
 * @desc
 * <li>
 *   管理ViewModel生命周期
 *   网络请求异常处理
 *   通过反射创建Model层
 * </li>
 * @author Hexl
 * @date 2019/11/18
 */
interface IVM : LifecycleObserver {

    // 异常回调处理
    fun handleError(e: Throwable)

    // 利用androidx提供过反射方式创建Model
    fun <T> createModel(modelClass: Class<T>): T

    //----------------------------- 生命周期与Activity or Fragment 相同 -----------------------------//

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate()

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy()

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onResume()

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onPause()

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onStart()

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onStop()

    //----------------------------- 生命周期与Activity or Fragment 相同 -----------------------------//
}