package com.hexl.basemvvm.net.interceptor

import com.hexl.basemvvm.utils.LogUtil
import okhttp3.Interceptor
import okhttp3.Response

/**
 * @desc 网络拦截器
 * @author Hexl
 * @date 2019/11/13
 */
class HttpLoggingInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        LogUtil.e(request.url.toString())
        return chain.proceed(request)
    }
}