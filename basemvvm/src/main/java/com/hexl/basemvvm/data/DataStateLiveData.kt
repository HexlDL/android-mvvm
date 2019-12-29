package com.hexl.basemvvm.data

import androidx.lifecycle.MutableLiveData
import com.hexl.basemvvm.utils.LogUtil

/**
 * @desc 自定义LiveData类,实现对数据加载的管理
 * @author Hexl
 * @date 2019/11/15
 */
class DataStateLiveData<Data> : MutableLiveData<Data>(), IDataState {
    companion object {
        val TAG = DataStateLiveData::class.java.simpleName
    }

    override fun onActive() {
        postLoading()
    }

    override fun onInactive() {
    }

    enum class State {
        // 空闲
        Idle,
        // 加载中
        Loading,
        // 加载成功
        Succeeded,
        // 加载失败
        Failed,
        // 加载完成
        Completed
    }

    val state = MutableLiveData<State>()

    init {
        clearState()
    }

    override fun clearState() {
        state.postValue(State.Idle)
        LogUtil.i(TAG, "状态闲置")
    }

    override fun postLoading() {
        state.postValue(State.Loading)
        LogUtil.i(TAG, "开始加载")
    }

    override fun postSuccess() {
        state.postValue(State.Succeeded)
        LogUtil.i(TAG, "业务加载成功")
        postCompleted()
    }

    override fun postFailed() {
        state.postValue(State.Failed)
        LogUtil.i(TAG, "业务加载失败")
        postCompleted()
    }

    override fun changeState(changeState: State) {
        state.postValue(changeState)
    }

    override fun postCompleted() {
        state.postValue(State.Completed)
        LogUtil.i(TAG, "加载完成")
    }

    // todo 当数据集为数组时 value的数据结构不在是 BaseResponse了 而是 List<> 不太理解是因为什么原因照成的
    override fun postValue(value: Data) {
        if (value is BaseResponse<*>) {
            if (value.isErrorCode())
                postSuccess()
            else
                postFailed()
        }
//        if (value is BaseFileResponse) {
//            if (value.isSucceeded())
//                postSuccess()
//            else
//                postFailed()
//        }
        super.postValue(value)
    }
}