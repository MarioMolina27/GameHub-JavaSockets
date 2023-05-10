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

class SaldoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_saldo)

        val usuariActual = intent.getSerializableExtra(Keys.constKeys.SALDO) as Usuari

        val textViewSaldo = findViewById<TextView>(R.id.textViewSaldo)
        val editTextSaldo = findViewById<EditText>(R.id.editTextSaldo)
        val buttonAddSaldo = findViewById<Button>(R.id.buttonAddSaldo)
        val buttonSubtractSaldo = findViewById<Button>(R.id.buttonSubtractSaldo)
        val buttonExitSaldo = findViewById<Button>(R.id.buttonExitSaldo)

        textViewSaldo.text =  usuariActual.saldo.toString()

        buttonSubtractSaldo.setOnClickListener()
        {
            if(!editTextSaldo.text.equals(""))
            {
                if(usuariActual.saldo - editTextSaldo.text.toString().toDouble()>0)
                {
                    usuariActual.saldo = usuariActual.saldo - editTextSaldo.text.toString().toDouble()
                    textViewSaldo.text =  usuariActual.saldo.toString()
                    Toast.makeText(this, "Saldo retirat correctament", Toast.LENGTH_SHORT).show()
                }
                else
                {
                    Toast.makeText(this, "No pots retirar més saldo del que tens en el compte", Toast.LENGTH_SHORT).show()
                }
            }
        }
        buttonAddSaldo.setOnClickListener()
        {
            if(!editTextSaldo.text.equals(""))
            {
                if(editTextSaldo.text.toString().toDouble()<=20)
                {
                    usuariActual.saldo = usuariActual.saldo + editTextSaldo.text.toString().toDouble()
                    textViewSaldo.text =  usuariActual.saldo.toString()
                    Toast.makeText(this, "Saldo afegit correctament", Toast.LENGTH_SHORT).show()
                }
                else
                {
                    Toast.makeText(this, "No pots afegir més de 20 de saldo", Toast.LENGTH_SHORT).show()
                }
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