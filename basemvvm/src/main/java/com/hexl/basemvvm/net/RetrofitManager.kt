package com.hexl.basemvvm.net

import retrofit2.Retrofit

/**
 * @desc Retrofit管理类
 * @author Hexl
 * @date 2019/11/13
 */
class RetrofitManager(val retrofit: Retrofit) {
    fun <T> createService(clazz: Class<T>): T {
        return retrofit.create(clazz)
    }
}