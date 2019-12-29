package com.hexl.basemvvm.ui.view

import android.content.Context
import com.hexl.basemvvm.R
import com.hexl.basemvvm.net.file.upload.UploadFileDataEntity
import com.hexl.basemvvm.ui.base.BaseRecyclerViewAdapter
import com.hexl.basemvvm.utils.LogUtil

/**
 * @desc 多文件上传
 * @author Hexl
 * @date 2019/11/29
 */
class UploadFileAdapter(
    context: Context,
    datas: List<UploadFileDataEntity>,
    layoutItemId: Int
) : BaseRecyclerViewAdapter<UploadFileDataEntity>(context, datas, layoutItemId) {

    override fun convertData(holder: BaseViewHolder, data: UploadFileDataEntity) {
        LogUtil.e("UploadAdapter", "${data.currentProgress}")
        holder.setProgress(R.id.progress_horizontal, data.currentProgress)
            .setText(R.id.tv_fileName, data.file.name)
    }
}