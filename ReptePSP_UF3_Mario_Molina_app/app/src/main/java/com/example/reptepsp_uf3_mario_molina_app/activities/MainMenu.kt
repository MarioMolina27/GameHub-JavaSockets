package com.example.reptepsp_uf3_mario_molina_app.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import com.example.reptepsp_uf3_mario_molina_app.MenuAdapter
import com.example.reptepsp_uf3_mario_molina_app.R

class MainMenu : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_menu)

        val lstMainMenu = findViewById<ListView>(R.id.lstMainMenu)
        val opcions = arrayListOf(
            "1- Jugar",
            "2- Gestionar Jocs",
            "3- Gestionar Saldo",
            "4- Gestionar dades usuari",
            "0- Sortida al menú d'entrada"
        )
        val adapter = MenuAdapter(this, R.layout.menu_item,opcions)
        lstMainMenu.adapter = adapter


    }
}