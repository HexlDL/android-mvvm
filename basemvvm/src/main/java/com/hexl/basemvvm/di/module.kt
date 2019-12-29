package com.hexl.basemvvm.di

import com.hexl.basemvvm.Config.BASE_URL
import com.hexl.basemvvm.net.LiveDataCallAdapterFactory
import com.hexl.basemvvm.net.RetrofitManager
import com.hexl.basemvvm.net.interceptor.HttpLoggingInterceptor
import com.hexl.basemvvm.net.interceptor.LoggingInterceptor
import okhttp3.OkHttpClient
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val MODULE_TAG_RETROFIT = "module_tag_retrofit"
val moduleProviderRetrofit = Kodein.Module(MODULE_TAG_RETROFIT) {
    // 创建Retrofit
    bind<Retrofit>() with singleton {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(instance())
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(LiveDataCallAdapterFactory.invoke())
            //    .addCallAdapterFactory(CoroutineCallAdapterFactory.invoke())
            .build()
    }

    // 创建OkHttpClient
    bind<OkHttpClient>() with singleton {
        OkHttpClient
            .Builder()
            .addInterceptor(instance<LoggingInterceptor>())
            .addNetworkInterceptor(instance<HttpLoggingInterceptor>())
            .build()
    }

    // 创建网络拦截器
    bind<HttpLoggingInterceptor>() with singleton {
        HttpLoggingInterceptor()
    }

    // 创建日志拦截器
    bind<LoggingInterceptor>() with singleton {
        LoggingInterceptor()
    }
}

const val MODULE_TAG_RETROFIT_MANAGER = "MODULE_TAG_RETROFIT_MANAGER"
val moduleProviderRetrofitManager = Kodein.Module(MODULE_TAG_RETROFIT_MANAGER) {
    bind<RetrofitManager>() with singleton {
        RetrofitManager(instance())
    }
}