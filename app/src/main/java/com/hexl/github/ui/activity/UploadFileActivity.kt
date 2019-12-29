package com.hexl.github.ui.activity

import androidx.lifecycle.Observer
import com.hexl.github.viewmodel.UploadFileViewModel
import com.hexl.basemvvm.ui.base.BaseActivity
import com.hexl.github.R
import kotlinx.android.synthetic.main.activity_fileupload.*

class UploadFileActivity : BaseActivity<UploadFileViewModel>() {

    override fun createLayout() = R.layout.activity_fileupload

    override fun init() {
        val fileUploadViewModel = createVM(UploadFileViewModel::class.java)
        btn_upload.setOnClickListener {
            fileUploadViewModel.request().observe(this, Observer {
                tv_name.text = it.content[0].fileName
                tv_name2.text = it.content[1].fileName
            })
        }
    }

    override fun onRequest() {
    }
}