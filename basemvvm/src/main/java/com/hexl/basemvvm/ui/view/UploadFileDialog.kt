package com.hexl.basemvvm.ui.view

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.util.DisplayMetrics
import android.view.*
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.hexl.basemvvm.R
import com.hexl.basemvvm.net.file.upload.UploadFileDataEntity
import com.hexl.basemvvm.utils.LogUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.cancelChildren
import java.io.File


/**
 * @desc 文件上传进度view
 * @author Hexl
 * @date 2019/11/28
 */
class UploadFileDialog(private val ctx: Context, val coroutineScope: CoroutineScope) :
    DialogFragment() {
    companion object {
        val TAG = UploadFileDialog::class.java.simpleName
    }

    private lateinit var window: Window
    /**
     * 当前上传文件弹窗列表
     */
    private lateinit var uploadFileRecyclerView: RecyclerView
    /**
     * 当前上传文件弹窗适配器
     */
    private lateinit var uploadFileAdapter: UploadFileAdapter
    /**
     * 使用[Handler]从OkHttp线程切换到UI线程更新UI数据
     */
    private val handler = Handler(Handler.Callback {
        // 更新当前进度
        updateProgress(it.data)
        true
    })
    private var uploadDatas = arrayListOf<UploadFileDataEntity>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        LogUtil.e(TAG, "onCreateView")
        window = dialog!!.window!!
        window.requestFeature(Window.FEATURE_NO_TITLE)
        // 获取 view
        val view = inflater.inflate(R.layout.dialog_fileupload, container, false)
        // 初始化配置
        configDialog()
        // RecyclerView
        uploadFileRecyclerView = view.findViewById(R.id.recyclerView)
        val btnCancel = view.findViewById<AppCompatButton>(R.id.btn_cancel)
        val btnConfirm = view.findViewById<AppCompatButton>(R.id.btn_confirm)
        // 创建适配器
        createAdapter(uploadFileRecyclerView)

        btnCancel.setOnClickListener {
            /**
             * 停止文件上传使用cancelChildren而不是cancel
             * 使用cancel 再次点击按钮进行文件上传时协程不会启动
             * cancel 和 cancelChildren 的区别待调研?
             */
//            coroutineScope.cancel("user cancel")
            coroutineScope.coroutineContext.cancelChildren()
            hideLoading()
        }

        return view
    }

    override fun onStart() {
        super.onStart()
        val dm = DisplayMetrics()
        activity!!.windowManager.defaultDisplay.getMetrics(dm)
        // 设置Dialog的宽、高(此方法需在onStart中调用)
        window.setLayout(((dm.widthPixels) * 0.7).toInt(), ((dm.heightPixels) * 0.7).toInt())
    }

    /**
     * 显示当前进度条Dialog
     */
    fun showLoading(manager: FragmentManager, tag: String?) {
        try {
            // Return true if the fragment is currently added to its activity.
            if (!isAdded) {
                this.show(manager, tag)
            }
        } catch (e: IllegalStateException) {
            println("IllegalStateException: ${e.message} 请关闭loading 或者 动态控制loading 显示 或 隐藏")
        }
    }

    /**
     * 隐藏当前进度条Dialog
     */
    fun hideLoading() {
        // Return true if the fragment is currently added to its activity.
        if (this.isAdded) {
            dismissAllowingStateLoss()
        }
    }

    /**
     * 提供给[com.hexl.basemvvm.net.file.upload.UploadFileRequestBody.UploadFileProgressListener]
     * 更新数据的方法,内部对数据封装为[Bundle] 然后使用Handler切换线程更新 UI 页面
     */
    fun notifyAdapter(currentProgress: Int, currentFile: File) {
        val message = Message.obtain()
        val bundle = Bundle()
        // 当前进度
        bundle.putInt("currentProgress", currentProgress)
        // 当前文件名称
        bundle.putString("currentFileName", currentFile.name)
        message.data = bundle
        handler.sendMessage(message)
    }

    /**
     * 数据转换
     * 将文件集合封装成[UploadFileDataEntity]
     */
    fun convertFileToUploadFileEntity(files: List<File>) {
        files.forEach {
            uploadDatas.add(UploadFileDataEntity(it, 0))
        }
    }

    /**
     * 创建适配器并绑定RecyclerView
     */
    private fun createAdapter(recyclerView: RecyclerView) {
        uploadFileAdapter =
            UploadFileAdapter(ctx, uploadDatas, R.layout.item_fileupload)
        uploadFileAdapter.bindToRecyclerView(recyclerView)
    }

    /**
     * 更新当前进度,使用[bundle] 存储当前进度和当前文件名称
     */
    private fun updateProgress(bundle: Bundle) {
        // 当前进度
        val currentProgress = bundle.getInt("currentProgress")
        // 当前文件名称
        val currentFileName = bundle.getString("currentFileName")
        // 获取adapter中获取数据源
        val currentDatas = uploadFileAdapter.currentDatas
        // 存储当前文件索引位置,用于更新item
        var tempIndex = 0
        for (index in currentDatas.indices) {
            // 找到当前文件位置
            if (currentDatas[index].file.name == currentFileName) {
                tempIndex = index
            }
        }
        // 更新当前文件进度
        currentDatas[tempIndex].currentProgress = currentProgress
        // 刷新当前文件进度 UI
        uploadFileAdapter.notifyPositionItemChanged(tempIndex)
    }

    /**
     * Dialog配置
     */
    private fun configDialog() {
        //透明状态栏
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        //清理背景变暗
        window.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
        //点击外部,不消失
        isCancelable = false
        //设置背景颜色,只有设置了这个属性,宽度才能全屏MATCH_PARENT
        window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val windowAttributes = window.attributes
        //gravity
        windowAttributes.gravity = Gravity.CENTER
        window.attributes = windowAttributes
    }
}