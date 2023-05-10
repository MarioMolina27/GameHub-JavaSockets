package com.example.reptepsp_uf3_mario_molina_app.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.reptepsp_uf3_mario_molina_app.sockets.MySocket
import com.example.reptepsp_uf3_mario_molina_app.R
import com.example.reptepsp_uf3_mario_molina_app.datamodels.Keys
import com.example.reptepsp_uf3_mario_molina_app.sockets.MySocket.PORT
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import java.net.Socket
import java.util.concurrent.Executors

class MainActivity : AppCompatActivity() {
    object serverConnect
    {
        lateinit var socket: MySocket
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val edtTxtUsuari = findViewById<TextInputEditText>(R.id.edtTxtUsuari)
        val edtTxtContrasenya = findViewById<TextInputEditText>(R.id.edtTxtContrasenya)
        val txtNewUser = findViewById<TextView>(R.id.btnNewUsuari)
        val btnIniciSessio = findViewById<Button>(R.id.btnIniciSessio)


        val ip = "192.168.50.63"
        val executor = Executors.newSingleThreadExecutor()
        executor.execute {
            serverConnect.socket = MySocket(
                Socket(ip, PORT)
            )
        }

        txtNewUser.setOnClickListener()
        {
            val executor = Executors.newSingleThreadExecutor()
            executor.execute {
                serverConnect.socket.sendInt(1)
                val intent = Intent(this, RegisterUsuari::class.java)
                startActivity(intent)
            }
        }
        btnIniciSessio.setOnClickListener()
        {
            val executor = Executors.newSingleThreadExecutor()
            executor.execute {
                serverConnect.socket.sendInt(2)
                serverConnect.socket.sendString(edtTxtUsuari.text.toString())
                serverConnect.socket.sendString(edtTxtContrasenya.text.toString())
                val usuariActual = serverConnect.socket.rebreUsuari()
                if (!usuariActual.getNomUsuari().equals("null")) {
                    val intent = Intent(this, MainMenu::class.java)
                    intent.putExtra(Keys.constKeys.LOGIN,usuariActual)
                    startActivity(intent)
                }
                else
                {
                    runOnUiThread{
                        Toast.makeText(this@MainActivity, "Aquest usuari no existeix o la contrasenya es incorrecte", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }
    override fun onBackPressed() {
        //super.onBackPressed()
    }
}