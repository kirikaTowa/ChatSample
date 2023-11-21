package com.example.chatexample

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chatexample.adapter.MessageAdapter
import com.example.chatexample.databinding.ActivityMainBinding
import com.example.chatexample.network.bean.MessageBean
import com.example.chatexample.network.bean.MessageEntity
import com.kakusummer.sample.network.entity.RetrofitObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    val listMessage = ArrayList<MessageEntity>()

    private val messageAdapter by lazy { MessageAdapter() }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)


        initDate()
        initListener()
    }

    private fun initDate(){
        binding.recycle.apply {
            adapter = messageAdapter

            layoutManager = LinearLayoutManager(this@MainActivity)
            (layoutManager as LinearLayoutManager).orientation = LinearLayoutManager.VERTICAL
        }
    }

    private fun initListener(){
        binding.apply {
            ivSend.setOnClickListener {
                val message=etMessage.text.toString()
                sendMessage(message)
                etMessage.setText("")

                val dateformat = SimpleDateFormat("HH:mm")
                val dateStr: String = dateformat.format(System.currentTimeMillis())

                listMessage.add(MessageEntity(System.currentTimeMillis(),1,message,dateStr))
                messageAdapter.submitList(listMessage)

                recycle.scrollToPosition(messageAdapter.itemCount -1)
            }
        }



    }

    private fun sendMessage(message:String){
        RetrofitObject.callRetro.getMessage(message)?.enqueue(object : Callback<MessageBean?> {
            override fun onResponse(call: Call<MessageBean?>, response: Response<MessageBean?>) {
                if (response.isSuccessful && response.body() != null) {
                    Log.d("yeTest", "onResponse: "+response.body())
                    listMessage.add(MessageEntity(System.currentTimeMillis(),2, response.body()!!.data.answer,""))
                    runOnUiThread {
                        binding.recycle.scrollToPosition(messageAdapter.itemCount -1)
                        messageAdapter.submitList(listMessage)
                    }

                }
               //closeLoading()
            }

            override fun onFailure(call: Call<MessageBean?>, t: Throwable) {
                Log.d("yeTest", "onFailure: ")
                //closeLoading()
            }
        });

    }
}