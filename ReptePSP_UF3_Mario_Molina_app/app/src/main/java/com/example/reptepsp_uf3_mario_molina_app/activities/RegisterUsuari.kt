package com.example.reptepsp_uf3_mario_molina_app.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.example.reptepsp_uf3_mario_molina_app.R
import com.example.reptepsp_uf3_mario_molina_app.datamodels.Keys
import com.example.reptepsp_uf3_mario_molina_app.datamodels.Usuari
import com.google.android.material.textfield.TextInputEditText
import java.security.Key
import java.util.concurrent.Executors

class RegisterUsuari : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_usuari)
        var usuari = Usuari()
        val edtTextNewUsuari = findViewById<TextInputEditText>(R.id.edtTextNewUsuari)
        val edtTextNewName = findViewById<TextInputEditText>(R.id.edtTextNewName)
        val edtTextNewCognoms = findViewById<TextInputEditText>(R.id.edtTextNewCognoms)
        val edtTextNewCompteCorrent = findViewById<TextInputEditText>(R.id.edtTextNewCompteCorrent)
        val edtTextNewEmail = findViewById<TextInputEditText>(R.id.edtTextNewEmail)
        val edtTextNewPassword = findViewById<TextInputEditText>(R.id.edtTextNewPassword)
        val edtTextNewPassword2 = findViewById<TextInputEditText>(R.id.edtTextNewPassword2)
        val btnCrearUsuari = findViewById<Button>(R.id.btnCrearUsuari)
        val action = intent.getStringExtra(Keys.constKeys.MOD_USER)?: "add"

        if(action.equals("mod"))
        {
            usuari = intent.getSerializableExtra(Keys.constKeys.TO_MOD_USER) as Usuari
            edtTextNewUsuari.setText(usuari.nomUsuari)
            edtTextNewName.setText(usuari.nom)
            edtTextNewCognoms.setText(usuari.cognoms)
            edtTextNewCompteCorrent.setText(usuari.compteCorrent)
            edtTextNewEmail.setText(usuari.email)
        }



        btnCrearUsuari.setOnClickListener()
        {
            if(edtTextNewPassword2.text.toString().equals(edtTextNewPassword.text.toString()))
            {
                usuari.nomUsuari = edtTextNewUsuari.text.toString()
                usuari.nom = edtTextNewName.text.toString()
                usuari.cognoms = edtTextNewCognoms.text.toString()
                usuari.compteCorrent = edtTextNewCompteCorrent.text.toString()
                usuari.email = edtTextNewEmail.text.toString()
                if(action.equals("add"))
                {
                    usuari.password = edtTextNewPassword.text.toString()
                    val executor = Executors.newSingleThreadExecutor()
                    executor.execute{
                        MainActivity.serverConnect.socket.sendString(edtTextNewPassword2.text.toString())
                        MainActivity.serverConnect.socket.enviarUsuari(usuari)
                        val msg = MainActivity.serverConnect.socket.recieveString()
                        runOnUiThread {
                            Toast.makeText(this@RegisterUsuari, msg, Toast.LENGTH_SHORT).show()
                        }
                        finish()
                    }
                }
                else
                {
                    if(edtTextNewPassword.text.toString().equals(""))
                    {
                        Toast.makeText(this, "Les contrasenyes no s'han cambiat", Toast.LENGTH_SHORT).show()
                    }
                    else
                    {
                        usuari.password = edtTextNewPassword.text.toString()
                        intent.putExtra(Keys.constKeys.MOD_PASSWORD, 1)
                    }
                    intent.putExtra(Keys.constKeys.TO_MAIN, usuari)
                    intent.putExtra(Keys.constKeys.TO_MAIN_EXTRA, "mod")
                    setResult(RESULT_OK,intent)
                    finish()
                }
            }
            else
            {
                Toast.makeText(this, "Les contrasenyes no coincideixen", Toast.LENGTH_SHORT).show()
            }
        }
    }
}

