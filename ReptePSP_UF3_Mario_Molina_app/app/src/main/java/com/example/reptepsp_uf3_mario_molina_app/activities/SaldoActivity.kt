package com.example.reptepsp_uf3_mario_molina_app.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.reptepsp_uf3_mario_molina_app.R
import com.example.reptepsp_uf3_mario_molina_app.datamodels.Keys
import com.example.reptepsp_uf3_mario_molina_app.datamodels.Usuari
import com.google.android.material.textfield.TextInputEditText

class SaldoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_saldo)

        val usuariActual = intent.getSerializableExtra(Keys.constKeys.SALDO) as Usuari

        val textViewSaldo = findViewById<TextView>(R.id.textViewSaldo)
        val editTextSaldo = findViewById<TextInputEditText>(R.id.edtTextNewSaldo)
        val buttonAddSaldo = findViewById<Button>(R.id.buttonAddSaldo)
        val buttonSubtractSaldo = findViewById<Button>(R.id.buttonSubtractSaldo)
        val buttonExitSaldo = findViewById<Button>(R.id.buttonExitSaldo)

        textViewSaldo.text =  String.format("%.2f", usuariActual.saldo)

        buttonSubtractSaldo.setOnClickListener()
        {
            if(!editTextSaldo.text?.isEmpty()!!)
            {
                if(usuariActual.saldo - editTextSaldo.text.toString().toDouble()>0)
                {
                    usuariActual.saldo = usuariActual.saldo - editTextSaldo.text.toString().toDouble()
                    textViewSaldo.text =  String.format("%.2f", usuariActual.saldo)
                    Toast.makeText(this, "Saldo retirat correctament", Toast.LENGTH_LONG).show()
                }
                else
                {
                    Toast.makeText(this, "No pots retirar més saldo del que tens en el compte", Toast.LENGTH_LONG).show()
                }
            }
            else
            {
                Toast.makeText(this, "Introdueix un valor de saldo abans de fer cap operació", Toast.LENGTH_LONG).show()
            }
        }
        buttonAddSaldo.setOnClickListener()
        {
            if(!editTextSaldo.text?.isEmpty()!!)
            {
                if(editTextSaldo.text.toString().toDouble()<=20)
                {
                    usuariActual.saldo = usuariActual.saldo + editTextSaldo.text.toString().toDouble()
                    textViewSaldo.text =  String.format("%.2f", usuariActual.saldo)
                    Toast.makeText(this, "Saldo afegit correctament", Toast.LENGTH_LONG).show()
                }
                else
                {
                    Toast.makeText(this, "No pots afegir més de 20 de saldo", Toast.LENGTH_LONG).show()
                }
            }
            else
            {
                Toast.makeText(this, "Introdueix un valor de saldo abans de fer cap operació", Toast.LENGTH_LONG).show()
            }
        }

        buttonExitSaldo.setOnClickListener()
        {
            val intent = Intent(this,MainMenu::class.java)
            intent.putExtra(Keys.constKeys.TO_MAIN, usuariActual)
            intent.putExtra(Keys.constKeys.TO_MAIN_EXTRA, "mod")
            setResult(RESULT_OK,intent)
            finish()
        }
    }
    override fun onBackPressed() {
        //super.onBackPressed()
    }
}