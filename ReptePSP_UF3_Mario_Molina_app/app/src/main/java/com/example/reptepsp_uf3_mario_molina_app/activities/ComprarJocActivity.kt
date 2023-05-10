package com.example.reptepsp_uf3_mario_molina_app.activities

import android.content.DialogInterface
import android.os.Bundle
import android.widget.AdapterView
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.reptepsp_uf3_mario_molina_app.R
import com.example.reptepsp_uf3_mario_molina_app.adapters.JocAdapter
import com.example.reptepsp_uf3_mario_molina_app.datamodels.Joc
import com.example.reptepsp_uf3_mario_molina_app.datamodels.Keys
import com.example.reptepsp_uf3_mario_molina_app.datamodels.Usuari
import java.util.concurrent.Executors


class ComprarJocActivity : AppCompatActivity() {
    private lateinit var jocsDisponibles: MutableList<Joc>
    private lateinit var usuariActual : Usuari
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comprar_joc)

        usuariActual = intent.getSerializableExtra(Keys.constKeys.TO_COMPRAR) as Usuari
        jocsDisponibles = mutableListOf()
        val executor = Executors.newSingleThreadExecutor()
        executor.execute {
            val isEmpty = MainActivity.serverConnect.socket.recieveInt()
            if(isEmpty>0)
            {
                val listSize = MainActivity.serverConnect.socket.recieveInt()
                for (i in 0 until listSize)
                {
                    jocsDisponibles.add(MainActivity.serverConnect.socket.rebreJoc())
                }
                val lstJocsComprar = findViewById<ListView>(R.id.lstJocsComprar)
                runOnUiThread {
                    val adapter = JocAdapter(this,R.layout.joc_comprat_item,jocsDisponibles)
                    lstJocsComprar.adapter = adapter

                    lstJocsComprar.onItemClickListener = AdapterView.OnItemClickListener()
                    {
                            _, _, i, _ ->
                        val executor = Executors.newSingleThreadExecutor()
                        executor.execute {
                            MainActivity.serverConnect.socket.sendInt(i)
                        }
                        showDialog()
                    }
                }
            }
            else
            {
                runOnUiThread{
                    Toast.makeText(this, "No tens cap joc disponible per comptar", Toast.LENGTH_LONG).show()
                }
                finish()
            }
        }
    }

    fun showDialog()
    {
        val options = arrayOf("Tarifa plana", "Partida")

        val builder = AlertDialog.Builder(this)
        builder.setTitle("Selecciona una opción")
            .setCancelable(false)
            .setItems(options) { dialog, which ->
                when (which) {
                    0 -> {
                        val executor = Executors.newSingleThreadExecutor()
                        executor.execute {
                            MainActivity.serverConnect.socket.sendString("tp")
                            val msg = MainActivity.serverConnect.socket.recieveString()
                            runOnUiThread {
                                Toast.makeText(this@ComprarJocActivity, msg, Toast.LENGTH_SHORT).show()
                            }
                            finish()
                        }
                    }
                    1 -> {
                        val executor = Executors.newSingleThreadExecutor()
                        executor.execute {
                            MainActivity.serverConnect.socket.sendString("p")
                            val msg = MainActivity.serverConnect.socket.recieveString()
                            runOnUiThread {
                                Toast.makeText(this@ComprarJocActivity, msg, Toast.LENGTH_SHORT).show()
                            }
                            finish()
                        }
                    }
                }
            }

        val dialog = builder.create()
        dialog.show()
    }
    override fun onBackPressed() {
        //super.onBackPressed()
    }
}