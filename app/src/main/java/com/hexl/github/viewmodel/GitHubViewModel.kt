package com.hexl.github.viewmodel

import android.content.Context
import com.hexl.github.model.GitHubModel
import com.hexl.github.response.ChaptersResponse
import com.hexl.basemvvm.data.DataStateLiveData
import com.hexl.basemvvm.ui.base.BaseViewModel
import com.hexl.basemvvm.utils.LogUtil
import org.kodein.di.Kodein


class GitHubViewModel(context: Context, kodein: Kodein) : BaseViewModel(context, kodein) {

    val data = DataStateLiveData<List<ChaptersResponse>>()

    override fun onCreate() {
        super.onCreate()
        launchUI({
            val response = createModel(GitHubModel::class.java).getNewList()
            data.postValue(response.data!!)
        }, {
            LogUtil.e(TAG, "----------> completed")
        }, {
            LogUtil.e(TAG, "----------> $it.message")
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        LogUtil.e(TAG, "onDestroy")
    }

    override fun onResume() {
        super.onResume()
        LogUtil.e(TAG, "onResume")
    }

    override fun onPause() {
        super.onPause()
        LogUtil.e(TAG, "onPause")
    }

    override fun onStart() {
        super.onStart()
        LogUtil.e(TAG, "onStart")
    }

    override fun onStop() {
        super.onStop()
        LogUtil.e(TAG, "onStop")
    }
    //    private fun getList(): MutableLiveData<List<ChaptersResponse>> {
//        launchUI {
//            currentDatas.postLoading()
//
//            val response = createModel(GitHubModel::class.java).getNewList()
//
//            currentDatas.postValue(response.currentDatas!!)
//        }
//        /* LiveData transformedLiveData =Transformations.map(mutableLiveData, new Function<String, Object>() {
//             @Override
//             public Object apply(String name) {
//                 return name + "+Android进阶解密";
//             }
//         });
//         transformedLiveData.observe(this, new Observer() {
//             @Override
//             public void onChanged(@Nullable Object o) {
//                 Log.d(TAG, "onChanged2:"+o.toString());
//             }
//         });*/
////        Transformations.map(currentDatas) {
////
////        }
//        return currentDatas
////        GlobalScope.async(Dispatchers.Main) {
////            val currentDatas = retrofitManager
////                .createService(GitHubService::class.java)
////                .getNewCoroutinesChapters()
////                .currentDatas
////            currentDatas.forEach {
////                println("----------hexl: ${it.name}")
////            }
////        }
//    }

}