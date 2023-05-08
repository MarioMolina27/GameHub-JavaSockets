package com.example.reptepsp_uf3_mario_molina_app

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.reptepsp_uf3_mario_molina_app.MySocket.PORT
import org.w3c.dom.Text
import java.net.Socket
import java.util.concurrent.Executors

class MainActivity : AppCompatActivity() {
    private lateinit var socket: MySocket
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val edtTxtUsuari = findViewById<EditText>(R.id.edtTxtUsuari)
        val edtTxtContrasenya = findViewById<EditText>(R.id.edtTxtContrasenya)
        val txtNewUser = findViewById<TextView>(R.id.btnNewUsuari)
        val btnIniciSessio = findViewById<Button>(R.id.btnIniciSessio)
        val ip = "192.168.1.44"
        val executor = Executors.newSingleThreadExecutor()
        executor.execute{
            socket = MySocket(Socket(ip, PORT))
        }

        txtNewUser.setOnClickListener()
        {
            val executor = Executors.newSingleThreadExecutor()
            executor.execute{
                socket.sendInt(1)
                val intent = Intent(this,RegisterUsuari::class.java)
                socket.close()
                startActivity(intent)
            }
        }
        btnIniciSessio.setOnClickListener()
        {
            val executor = Executors.newSingleThreadExecutor()
            executor.execute{
                socket.sendInt(2)
                socket.sendString(edtTxtUsuari.text.toString())
                socket.sendString(edtTxtContrasenya.text.toString())
                val usuariActual = socket.rebreUsuari()
                if(!usuariActual.getNomUsuari().equals("null"))
                {
                    val intent = Intent(this,MainMenu::class.java)
                    startActivity(intent)
                }
                else
                {
                    socket.close()
                    socket = MySocket(Socket(ip, PORT))
                }
            }
        }

    }
}