package com.example.myapplication

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import model.Destino

class DestinoViewHolder(view: View):RecyclerView.ViewHolder(view){

    val destino = view.findViewById<TextView>(R.id.destinoId)
    fun render(destinoModel: Destino){
        destino.text = destinoModel.pais
    }


}