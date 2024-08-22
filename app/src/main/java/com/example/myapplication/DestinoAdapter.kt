package com.example.myapplication

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import model.Destino

class DestinoAdapter(private val destinoList: List<Destino>) : RecyclerView.Adapter<DestinoViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DestinoViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return DestinoViewHolder(layoutInflater.inflate(R.layout.item_destino, parent, false))
    }

    override fun getItemCount(): Int {
        return destinoList.size
    }

    override fun onBindViewHolder(holder: DestinoViewHolder, position: Int) {
        val item = destinoList[position]
        holder.render(item)
    }

}