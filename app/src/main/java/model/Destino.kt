package model

import java.io.Serializable

data class Destino(val id: Int, val nombre: String, val pais: String, var categoria: String, val plan: String, val precio:Int): Serializable

