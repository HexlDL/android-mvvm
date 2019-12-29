package com.hexl.basemvvm.ui.base

import android.app.Activity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.hexl.basemvvm.ui.IActivity
import org.kodein.di.Copy
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import org.kodein.di.android.retainedKodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance

/**
 * @desc
 * @author Hexl
 * @date 2019/11/13
 */
abstract class BaseActivity<VM : BaseViewModel> : AppCompatActivity(),
    IActivity, KodeinAware {
    // 对于Activity来说它返回的是Application层级的kodein容器
    private val parentKodein by closestKodein()
    override val kodein: Kodein by retainedKodein {
        // 通过extend（）函数，我们将Application层级的Kodien容器也放在了Activity的kodien容器中，
        // 这样activity就能从上层的Kodein容器取出对应依赖
        extend(parentKodein, copy = Copy.All)
        bind<Activity>() with instance(this@BaseActivity)
    }

    lateinit var viewModel: VM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 创建布局
        val layoutResID = createLayout()
        // 布局id不为0则认为设置了布局
        if (layoutResID != 0) {
            setContentView(layoutResID)
        } else {
            throw KotlinNullPointerException("layoutResID is null")
        }
        // 创建createVM
        init()
        // 由于createVM()是在init()代码块中创建的所以关联生命周期的方法一定要在init()后执行
        lifecycle.addObserver(viewModel)
        onRequest()
    }

    /**
     * 使用ViewModelProvider创建ViewModel,而不是直接new ViewModel
     * 是因为太过ViewModelProvider创建的对象不会因为屏幕旋转而导致对象重新创建
     */
    @Suppress("UNCHECKED_CAST")
    fun <T : ViewModel?> AppCompatActivity.createVM(viewModelClass: Class<T>): VM {
        viewModel =
            ViewModelProvider(this, AndroidViewModelFactory(this, kodein)).get(viewModelClass) as VM
        return viewModel
    }
}
