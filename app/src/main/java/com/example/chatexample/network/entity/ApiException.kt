package com.example.chatexample.network.entity

class  ApiException  (val code:Int?, private val msg:String):Exception(msg) {



}
