package com.example.reptepsp_uf3_mario_molina_app.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.reptepsp_uf3_mario_molina_app.R
import com.example.reptepsp_uf3_mario_molina_app.datamodels.Joc

class JocAdapter(context: Context, val layout: Int, val jocs: MutableList<Joc>):
    ArrayAdapter<Joc>(context, layout, jocs)
{
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View

        if (convertView !=null){
            view = convertView
        }
        else{
            view =
                LayoutInflater.from(getContext()).inflate(layout, parent, false)
        }
        bindMenu(view,jocs[position])
        return view
    }

    fun bindMenu(view: View, game: Joc)
    {
        val txtNomJocComprat = view.findViewById<TextView>(R.id.txtNomJocComprat)
        val txtTarifaPlana = view.findViewById<TextView>(R.id.txtTarifaPlana)
        val txtNumPartides = view.findViewById<TextView>(R.id.txtNumPartides)

        txtNomJocComprat.text = game.nomJoc
        txtTarifaPlana.text = "Tarifa Plana: "+game.preuJoc
        txtNumPartides.text = "Preu Partida: "+game.preuPartida

    }
}