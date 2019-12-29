package com.hexl.basemvvm.ui.base

import com.hexl.basemvvm.net.RetrofitManager
import org.kodein.di.Kodein
import org.kodein.di.generic.instance

/**
 * @desc
 * @author Hexl
 * @date 2019/11/15
 */
open class BaseModel(kodein: Kodein) {
    protected val retrofitManager:RetrofitManager by kodein.instance()
}