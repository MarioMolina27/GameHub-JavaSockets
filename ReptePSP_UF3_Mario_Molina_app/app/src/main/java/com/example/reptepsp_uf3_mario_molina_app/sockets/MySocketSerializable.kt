package com.example.reptepsp_uf3_mario_molina_app.sockets

import java.io.Serializable

class MySocketSerializable(private val mySocket: MySocket) : Serializable {
    fun getSocket(): MySocket {
        return mySocket
    }
}