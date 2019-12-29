package com.hexl.basemvvm.net

import androidx.lifecycle.LiveData
import com.hexl.basemvvm.data.BaseResponse
import com.hexl.basemvvm.utils.LogUtil
import retrofit2.*
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type
import java.util.concurrent.atomic.AtomicBoolean

class LiveDataCallAdapterFactory private constructor(): CallAdapter.Factory() {
    companion object {
        @JvmStatic @JvmName("create")
        operator fun invoke() = LiveDataCallAdapterFactory()
    }

    override fun get(
        returnType: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *>? {
        if (getRawType(returnType) != LiveData::class.java) return null
        // 获取第一个泛型类型
        val type = getParameterUpperBound(0, returnType as ParameterizedType)
        val rawType = getRawType(type)
        if (rawType != BaseResponse::class.java) {
            throw IllegalArgumentException("type must be BaseResponse")
        }
        if (type !is ParameterizedType) {
            throw IllegalArgumentException("resource must be parameterized")
        }
        return LiveDataCallAdapter<Any>(type)
    }

    class LiveDataCallAdapter<T>(private val responseType: Type) : CallAdapter<T, LiveData<T>> {
        override fun adapt(call: Call<T>): LiveData<T> {
            return object : LiveData<T>() {
                private val started = AtomicBoolean(false)
                override fun onActive() {
                    super.onActive()
                    if (started.compareAndSet(false, true)) {
                        call.enqueue(object : Callback<T> {
                            override fun onFailure(call: Call<T>, t: Throwable) {
//                                postValue(call.execute() as T)
                                LogUtil.e("LiveDataCallAdapter", "onFailure = ${t.message}")
                            }

                            override fun onResponse(call: Call<T>, response: Response<T>) {
                                LogUtil.e("LiveDataCallAdapter", "onResponse = ${response.body()}")
                                postValue(response.body())
                            }
                        })
                    }
                }
            }
        }

        override fun responseType() = responseType

    }
}