package com.example.reptepsp_uf3_mario_molina_app.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.reptepsp_uf3_mario_molina_app.R
import com.example.reptepsp_uf3_mario_molina_app.datamodels.GamesBuyed

class JocsCompratsAdapter(context: Context, val layout: Int, val games: MutableList<GamesBuyed>):
    ArrayAdapter<GamesBuyed>(context, layout, games)
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
        bindGames(view,games[position])
        return view
    }

    fun bindGames(view: View, game: GamesBuyed)
    {
        val txtNomJocComprat = view.findViewById<TextView>(R.id.txtNomJocComprat)
        val txtTarifaPlana = view.findViewById<TextView>(R.id.txtTarifaPlana)
        val txtNumPartides = view.findViewById<TextView>(R.id.txtNumPartides)

        txtNomJocComprat.text = game.nomJoc
        if(game.tarifaPlana==0)
        {
            txtTarifaPlana.visibility = View.GONE
            txtNumPartides.text = "Partides disponibles: "+game.partidesComprades
            txtNumPartides.visibility = View.VISIBLE
        }
        if(game.tarifaPlana==1)
        {
            txtTarifaPlana.visibility = View.VISIBLE
            txtTarifaPlana.text = "Tarifa plana: SI"
            txtNumPartides.visibility = View.GONE
        }
    }
}