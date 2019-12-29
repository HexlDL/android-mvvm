package com.hexl.basemvvm.ui.base

import android.app.Application
import com.hexl.basemvvm.di.moduleProviderRetrofit
import com.hexl.basemvvm.di.moduleProviderRetrofitManager
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule

/**
 * @desc
 * @author Hexl
 * @date 2019/11/13
 */
open class BaseApplication : Application(), KodeinAware {
    override val kodein: Kodein = Kodein.lazy {
        // 导入androidx
        import(androidXModule(this@BaseApplication))
        // 导入retrofit
        import(module = moduleProviderRetrofit)
        // 导入retrofit管理类
        import(module = moduleProviderRetrofitManager)
    }

    companion object {
        var instance: Application? = null
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}