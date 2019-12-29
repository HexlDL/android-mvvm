package com.hexl.basemvvm.ui.base

import android.content.Context
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * @desc 自定义RecyclerViewAdapter
 * @author Hexl
 * @date 2019/11/29
 */
abstract class BaseRecyclerViewAdapter<T>(
    val context: Context,
    val layoutItemId: Int
) :
    RecyclerView.Adapter<BaseRecyclerViewAdapter.BaseViewHolder>() {

    constructor(context: Context, datas: List<T>, layoutItemId: Int) : this(context, layoutItemId) {
        this.currentDatas = datas
    }

    // 当前数据源
    lateinit var currentDatas: List<T>
    // 当前 RecyclerView
    lateinit var currentRecyclerView: RecyclerView
    // 当前 item 位置
    var currentItemPosition: Int = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val view = LayoutInflater.from(context).inflate(layoutItemId, parent,false)
        return BaseViewHolder(view)
    }

    override fun getItemCount() = if (currentDatas.isNotEmpty()) currentDatas.size else 0

    override
    fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        currentItemPosition = position
        val data = currentDatas[position]
        convertData(holder, data)
    }

    abstract fun convertData(holder: BaseViewHolder, data: T)

    fun bindToRecyclerView(recyclerView: RecyclerView) {
        this.currentRecyclerView = recyclerView
        this.currentRecyclerView.layoutManager = LinearLayoutManager(context)
        this.currentRecyclerView.adapter = this
    }

    fun notifyData(currentDatas: List<T>) {
        this.currentDatas = currentDatas
        notifyDataSetChanged()
    }

    fun notifyPositionItemChanged(position: Int) {
        notifyItemChanged(position)
    }

    class BaseViewHolder(private val layoutItemView: View) : RecyclerView.ViewHolder(layoutItemView) {
        var views = SparseArray<View>()

        @Suppress("UNCHECKED_CAST")
        fun <T : View> getView(id: Int): T {
            var view = views.get(id)
            if (view == null) {
                view = layoutItemView.findViewById(id)
                views.put(id, view)
            }
            return view as T
        }

        fun setText(id: Int, value: String): BaseViewHolder {
            getView<TextView>(id).text = value
            return this
        }

        fun setProgress(id: Int, value: Int): BaseViewHolder {
            getView<ProgressBar>(id).progress = value
            return this
        }
    }
}