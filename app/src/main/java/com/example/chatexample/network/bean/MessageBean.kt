package com.example.chatexample.network.bean

data class MessageBean(
    val code: Int,
    val `data`: Data,
    val msg: String
)

data class Data(
    val answer: String
)