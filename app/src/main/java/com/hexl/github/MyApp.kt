package com.hexl.github

import com.hexl.basemvvm.ui.base.BaseApplication
import com.hexl.basemvvm.utils.LogUtil

/**
 * @desc
 * @author Hexl
 * @date 2019/11/6
 */
class MyApp : BaseApplication() {
    override fun onCreate() {
        super.onCreate()
        LogUtil.e(MyApp::class.java.simpleName)
    }
}
