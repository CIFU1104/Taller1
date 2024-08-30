package com.example.myapplication

import model.Destino

object DestinoRepository {
    val destinosList: MutableList<Destino> = mutableListOf()

    fun agregarDestino(destino: Destino) {
        destinosList.add(destino)
    }

    fun eliminarDestino(destino: Destino){
        destinosList.remove(destino)
    }

    fun obtenerDestinos(): List<Destino> {
        return destinosList
    }
}