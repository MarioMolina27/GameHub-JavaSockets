package com.example.reptepsp_uf3_mario_molina_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import java.net.Socket
import java.util.concurrent.Executors

class RegisterUsuari : AppCompatActivity() {
    private lateinit var socket: MySocket
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_usuari)

        val executor = Executors.newSingleThreadExecutor()
        executor.execute{
            val ip = "192.168.1.44"
            socket = MySocket(Socket(ip, MySocket.PORT))
        }

        val usuari = Usuari()
        val edtTextNewUsuari = findViewById<TextInputEditText>(R.id.edtTextNewUsuari)
        val edtTextNewName = findViewById<TextInputEditText>(R.id.edtTextNewName)
        val edtTextNewCognoms = findViewById<TextInputEditText>(R.id.edtTextNewCognoms)
        val edtTextNewCompteCorrent = findViewById<TextInputEditText>(R.id.edtTextNewCompteCorrent)
        val edtTextNewEmail = findViewById<TextInputEditText>(R.id.edtTextNewEmail)
        val edtTextNewPassword = findViewById<TextInputEditText>(R.id.edtTextNewPassword)
        val edtTextNewPassword2 = findViewById<TextInputEditText>(R.id.edtTextNewPassword2)
        val btnCrearUsuari = findViewById<Button>(R.id.btnCrearUsuari)

        btnCrearUsuari.setOnClickListener()
        {
            usuari.nomUsuari = edtTextNewUsuari.text.toString()
            usuari.nom = edtTextNewName.text.toString()
            usuari.cognoms = edtTextNewCognoms.text.toString()
            usuari.compteCorrent = edtTextNewCompteCorrent.text.toString()
            usuari.email = edtTextNewEmail.text.toString()
            usuari.password = edtTextNewPassword.text.toString()
            val executor = Executors.newSingleThreadExecutor()
            executor.execute{
                socket.sendString(edtTextNewPassword2.text.toString())
                socket.enviarUsuari(usuari)
                socket.close()
                val intent = Intent(this,MainActivity::class.java)
                startActivity(intent)
                }
            }
        }

    }