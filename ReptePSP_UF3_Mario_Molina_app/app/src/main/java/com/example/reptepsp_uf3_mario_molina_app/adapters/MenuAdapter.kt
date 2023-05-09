package com.example.reptepsp_uf3_mario_molina_app.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.reptepsp_uf3_mario_molina_app.R

class MenuAdapter(context: Context, val layout: Int, val menu: ArrayList<String>):
    ArrayAdapter<String>(context, layout, menu)
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
        bindMenu(view,menu[position])
        return view
    }

    fun bindMenu(view: View, menu: String)
    {
        val menuOption = view.findViewById<TextView>(R.id.menuOption)
        menuOption.text= menu
    }
}