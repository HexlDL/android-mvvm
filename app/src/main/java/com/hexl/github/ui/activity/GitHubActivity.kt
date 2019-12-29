package com.hexl.github.ui.activity

import androidx.arch.core.util.Function
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.Transformations
import com.hexl.github.viewmodel.GitHubViewModel
import com.hexl.basemvvm.ui.base.BaseActivity
import com.hexl.github.R
import kotlinx.android.synthetic.main.activity_github.*

/**
 * @desc GitHub测试接口
 * @author Hexl
 * @date 2019/11/13
 */
class GitHubActivity : BaseActivity<GitHubViewModel>() {

    override fun createLayout() = R.layout.activity_github

    override fun init() {
        // 第一种方案 可以获取BaseResponse
        createVM(GitHubViewModel::class.java)
            .data
            .observe(this, Observer {
                textView2.text = it!![0].name
            })

        // 第二种方案 直接获取数据源
//        Transformations.map(createVM(GitHubViewModel::class.java).getList()) { input -> input.currentDatas }
//            .observe(this, Observer {
//                textView2.text = it!![0].name
//            })
    }

    override fun onRequest() {
        val mutableLiveData = MutableLiveData<String>()
        mutableLiveData.observe(this, Observer {
            println("----------hexl: $it")
        })
        val mapData = Transformations.map(mutableLiveData, object : Function<String, Any> {
            override fun apply(input: String?): Any {
                return input + "Transformations.map"
            }
        })
        mapData.observe(this, Observer {
            println("----------hexl: $it")
        })
        mutableLiveData.postValue("正常发射数据")
    }

}

fun test1() {
    //        retrofitManager
//            .createService(GitHubService::class.java)
//            .getChapters()
//            .enqueue(object : Callback<ChaptersResponse> {
//                override fun onFailure(call: Call<ChaptersResponse>, t: Throwable) {
//                    LogUtil.e("onFailure ${t.message}")
//                }
//
//                override fun onResponse(call: Call<ChaptersResponse>, response: Response<ChaptersResponse>) {
//                    if (response.isSuccessful) {
//                        LogUtil.e("onResponse ${response.body().toString()}")
//                    } else {
//                        LogUtil.e("onResponse 失败了")
//                    }
//                }
//            })
}

fun test2() {
    //        GlobalScope.async(Dispatchers.Main) {
//            LogUtil.e("start thread name = ${Thread.currentThread().name}")
//            val newCoroutinesChapters = retrofitManager
//                .createService(GitHubService::class.java)
//                .getNewCoroutinesChapters()
//            LogUtil.e("end thread name = ${Thread.currentThread().name}")
//            LogUtil.e("onResponse $newCoroutinesChapters")
//            textView2.text = newCoroutinesChapters.currentDatas[0].name
//        }
}

fun test3() {
//        ViewModelProvider(
//            this,
//            AndroidViewModelFactory(this, kodein)
//        ).get(GitHubViewModel::class.java)
//            .getList()
//            .observe(this, Observer<ChaptersResponse> {
//                textView2.text = it.currentDatas[0].name
//            })
}