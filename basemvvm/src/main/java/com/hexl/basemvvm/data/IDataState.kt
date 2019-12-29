package com.hexl.basemvvm.data

/**
 * @desc 数据状态
 * @author Hexl
 * @date 2019/11/18
 */
interface IDataState {
    /**
     * 清空当前网络状态
     */
    fun clearState()

    /**
     * 加载中
     */
    fun postLoading()

    /**
     * 加载成功
     */
    fun postSuccess()

    /**
     * 加载失败
     */
    fun postFailed()

    /**
     * 加载完成
     */
    fun postCompleted()

    /**
     * 改变当前加载状态
     */
    fun changeState(changeState: DataStateLiveData.State)
}