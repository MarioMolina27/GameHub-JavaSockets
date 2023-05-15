package com.example.reptepsp_uf3_mario_molina_app.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.reptepsp_uf3_mario_molina_app.sockets.MySocket
import com.example.reptepsp_uf3_mario_molina_app.R
import com.example.reptepsp_uf3_mario_molina_app.datamodels.Keys
import com.example.reptepsp_uf3_mario_molina_app.sockets.MySocket.PORT
import com.google.android.material.textfield.TextInputEditText
import java.net.Socket
import java.net.UnknownHostException
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
        var conexion = false
        showInputDialog(this, "IP", "Introdueix la ip del server") { text ->
            val ip = "192.168.1.132"
            val executor = Executors.newSingleThreadExecutor()
            executor.execute {
                try
                {
                    serverConnect.socket = MySocket(Socket(text, PORT))
                    conexion = true
                }
                catch (e: UnknownHostException) {
                    runOnUiThread {
                        AlertDialog.Builder(this)
                            .setTitle("Error")
                            .setMessage("No s'ha trobat el host: $text")
                            .setPositiveButton("OK", null)
                            .show()
                    }
                }
                catch(e: java.net.ConnectException)
                {
                    runOnUiThread {
                        AlertDialog.Builder(this)
                            .setTitle("Error")
                            .setMessage("No s'ha pogut connectar al host: $text")
                            .setPositiveButton("OK", null)
                            .show()
                    }
                }
                catch (e: java.net.NoRouteToHostException)
                {
                    runOnUiThread {
                        AlertDialog.Builder(this)
                            .setTitle("Error")
                            .setMessage("No s'ha pogut connectar al host: $text")
                            .setPositiveButton("OK", null)
                            .show()
                    }
                }
            }
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
            if(conexion)
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
            else
            {
                Toast.makeText(this, "La connexió amb el host no s'ha efectuat correctament", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun showInputDialog(context: Context, title: String, message: String, onTextEntered: (String) -> Unit) {
        val input = EditText(context)
        val lp = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT
        )
        input.layoutParams = lp

        val alertDialog = AlertDialog.Builder(context)
            .setTitle(title)
            .setMessage(message)
            .setView(input)
            .setCancelable(false)
            .setPositiveButton("Aceptar") { _, _ ->
                onTextEntered(input.text.toString())
            }
            .setNegativeButton("Cancelar") { dialog, _ -> dialog.cancel() }
            .create()
        alertDialog.show()
    }

    override fun onBackPressed() {
        //super.onBackPressed()
    }
}