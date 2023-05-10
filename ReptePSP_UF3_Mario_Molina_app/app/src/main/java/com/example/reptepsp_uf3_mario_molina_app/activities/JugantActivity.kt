package com.example.reptepsp_uf3_mario_molina_app.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.AdapterView
import android.widget.ListView
import android.widget.Toast
import com.example.reptepsp_uf3_mario_molina_app.R
import com.example.reptepsp_uf3_mario_molina_app.adapters.JocsCompratsAdapter
import com.example.reptepsp_uf3_mario_molina_app.datamodels.GamesBuyed
import com.example.reptepsp_uf3_mario_molina_app.datamodels.Keys
import com.example.reptepsp_uf3_mario_molina_app.datamodels.Usuari
import java.util.concurrent.Executors

class JugantActivity : AppCompatActivity() {
    private lateinit var usuariActual : Usuari
    private lateinit var jocsComprats: MutableList<GamesBuyed>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_jugant)

        usuariActual = intent.getSerializableExtra(Keys.constKeys.TO_JUGAR) as Usuari
        val lstJocsJugar = findViewById<ListView>(R.id.lstJocsJugar)

        val executor = Executors.newSingleThreadExecutor()
        executor.execute {
            val llistaAmbElements = MainActivity.serverConnect.socket.recieveInt()
            if(llistaAmbElements==1)
            {
                jocsComprats = mutableListOf()
                val arraysize = MainActivity.serverConnect.socket.recieveInt()
                for (i in 0 until arraysize)
                {
                    jocsComprats.add(MainActivity.serverConnect.socket.rebreJocComptat())
                }
                runOnUiThread {
                    val adapter = JocsCompratsAdapter(this, R.layout.joc_comprat_item, jocsComprats)
                    lstJocsJugar.adapter = adapter

                    lstJocsJugar.onItemClickListener = AdapterView.OnItemClickListener()
                    {
                            _, _, i, _ ->
                        val executor = Executors.newSingleThreadExecutor()
                        executor.execute {
                            MainActivity.serverConnect.socket.sendInt(i)
                            val msg = MainActivity.serverConnect.socket.recieveString()
                            runOnUiThread {
                                Toast.makeText(this@JugantActivity, msg, Toast.LENGTH_LONG).show()
                            }
                            finish()
                        }
                    }
                }
            }
        }
    }
}