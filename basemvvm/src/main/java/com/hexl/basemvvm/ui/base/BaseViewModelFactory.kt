package com.hexl.basemvvm.ui.base

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.kodein.di.Kodein


/**
 * @desc 自定义ViewModelFactory
 * @author Hexl
 * @date 2019/11/15
 */
class AndroidViewModelFactory(
    val context: Context,
    val kodein: Kodein
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass
            .getConstructor(Context::class.java, Kodein::class.java)
            .newInstance(context, kodein)
    }
}