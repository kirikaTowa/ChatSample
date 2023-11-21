package com.example.chatexample.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.chatexample.databinding.ItemMessageBinding
import com.example.chatexample.network.bean.MessageEntity


class MessageAdapter : ListAdapter<MessageEntity, MessageAdapter.MyViewHolder>(DIFFCALLBACK) {

    class MyViewHolder(private val itemBinding: ItemMessageBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bindView(position: Int, musicItem: MessageEntity) {
            if (musicItem.Type==2){
                itemBinding.apply {
                    clReponse.visibility= View.VISIBLE
                    tvRequest.visibility=View.GONE
                    itemBinding.tvReponse.text=musicItem.Message
                }
            }else{
                itemBinding.apply {
                    clReponse.visibility= View.GONE
                    tvRequest.visibility=View.VISIBLE

                    val sb=StringBuilder(musicItem.Message)
                    sb.append(" ")
                    sb.append(musicItem.time)


                    itemBinding.tvRequest.text=sb.toString()
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return super.getItemCount()
    }



    object DIFFCALLBACK : DiffUtil.ItemCallback<MessageEntity>() {
        override fun areItemsTheSame(oldItem: MessageEntity, newItem: MessageEntity): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: MessageEntity, newItem: MessageEntity): Boolean {
            return oldItem.id == newItem.id
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemBinding =
            ItemMessageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val musicItem: MessageEntity = getItem(position)
        holder.bindView(position, musicItem)
        holder.itemView.setOnClickListener {
            listener?.let {
                it(position)
            }
        }
    }

    //kotlin方法
    //直接定义函数类型的变量 传入参数为所需的数据类型，无返回值
    var listener: ((pst: Int) -> Unit)? = null

    //写个方法设置函数
    fun setMiaListListener(listener: (pst: Int) -> Unit) {
        this.listener = listener
    }
}