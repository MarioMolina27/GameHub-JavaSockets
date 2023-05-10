package com.example.reptepsp_uf3_mario_molina_app.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.AdapterView
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.example.reptepsp_uf3_mario_molina_app.adapters.MenuAdapter
import com.example.reptepsp_uf3_mario_molina_app.R
import com.example.reptepsp_uf3_mario_molina_app.datamodels.Joc
import com.example.reptepsp_uf3_mario_molina_app.datamodels.Keys
import com.example.reptepsp_uf3_mario_molina_app.datamodels.Usuari
import java.util.concurrent.Executors

class MainMenu : AppCompatActivity() {

    private lateinit var usuariActual : Usuari
    private val getResult =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult())
        {
            if (it.resultCode == RESULT_OK)
            {
                val modPass = it.data?.getIntExtra(Keys.constKeys.MOD_PASSWORD,0)
                usuariActual = it.data?.getSerializableExtra(Keys.constKeys.TO_MAIN) as Usuari
                val mod = it.data?.getStringExtra(Keys.constKeys.TO_MAIN_EXTRA)
                val textViewSaldoMainManu = findViewById<TextView>(R.id.txtViewSaldoMainManu)
                textViewSaldoMainManu.text = String.format("%.2f", usuariActual.saldo)
                if(mod.equals("mod"))
                {
                    val executor = Executors.newSingleThreadExecutor()
                    executor.execute {
                        MainActivity.serverConnect.socket.enviarUsuari(usuariActual)
                        if (modPass==0)
                        {
                            MainActivity.serverConnect.socket.sendInt(0)
                        }
                        else
                        {
                            MainActivity.serverConnect.socket.sendInt(1)
                        }

                    }
                }
            }
            else if (it.resultCode == RESULT_CANCELED)
            {
                Toast.makeText(this, "Opció de menú no completada", Toast.LENGTH_SHORT).show()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_menu)
        usuariActual = intent.getSerializableExtra(Keys.constKeys.LOGIN) as Usuari
        val textViewSaldoMainManu = findViewById<TextView>(R.id.txtViewSaldoMainManu)
        val lstMainMenu = findViewById<ListView>(R.id.lstMainMenu)
        val opcions = arrayListOf(
            "Jugar",
            "Gestionar Jocs",
            "Gestionar Saldo",
            "Gestionar dades usuari",
            "Sortida al menú d'entrada"
        )
        val adapter = MenuAdapter(this, R.layout.menu_item,opcions)
        lstMainMenu.adapter = adapter

        textViewSaldoMainManu.text = String.format("%.2f", usuariActual.saldo)

        lstMainMenu.onItemClickListener = AdapterView.OnItemClickListener()
        {
                _,_,i,_ ->
            when (i) {
                0 -> {
                    val executor = Executors.newSingleThreadExecutor()
                    executor.execute {
                        MainActivity.serverConnect.socket.sendInt(1)
                        val intent = Intent(this,JugantActivity::class.java)
                        intent.putExtra(Keys.constKeys.TO_JUGAR,usuariActual)
                        startActivity(intent)
                    }
                }
                1 -> {
                    val executor = Executors.newSingleThreadExecutor()
                    executor.execute {
                        MainActivity.serverConnect.socket.sendInt(2)
                        val intent = Intent(this,ComprarJocActivity::class.java)
                        intent.putExtra(Keys.constKeys.TO_COMPRAR,usuariActual)
                        getResult.launch(intent)
                    }
                }
                2 -> {
                    val executor = Executors.newSingleThreadExecutor()
                    executor.execute {
                        MainActivity.serverConnect.socket.sendInt(3)
                        val intent = Intent(this,SaldoActivity::class.java)
                        intent.putExtra(Keys.constKeys.SALDO,usuariActual)
                        getResult.launch(intent)
                    }
                }
                3-> {
                    val executor = Executors.newSingleThreadExecutor()
                    executor.execute {
                        MainActivity.serverConnect.socket.sendInt(4)
                        val intent = Intent(this,RegisterUsuari::class.java)
                        intent.putExtra(Keys.constKeys.MOD_USER,"mod")
                        intent.putExtra(Keys.constKeys.TO_MOD_USER,usuariActual)
                        getResult.launch(intent)
                    }
                }
                4 -> {
                    val executor = Executors.newSingleThreadExecutor()
                    executor.execute {
                        MainActivity.serverConnect.socket.sendInt(0)
                        finish()
                    }
                }
            }
        }
    }
    override fun onBackPressed() {
        //super.onBackPressed()
    }
}