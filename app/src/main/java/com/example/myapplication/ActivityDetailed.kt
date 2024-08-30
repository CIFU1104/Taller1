package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myapplication.databinding.ActivityDetailedBinding
import model.Destino

class ActivityDetailed : AppCompatActivity() {
    private lateinit var binding: ActivityDetailedBinding
    private lateinit var destino:Destino
    private var arreglo = DestinoRepository.obtenerDestinos() as ArrayList<Destino>
    override fun onCreate(savedInstanceState: Bundle?) {

        binding = ActivityDetailedBinding.inflate(layoutInflater)
        setContentView(binding.root)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        destino = intent.getSerializableExtra("destino") as Destino
        if(arreglo.contains(destino)){
            binding.favoritosBoton.text = "Quitar de favoritos"
        }
        if(destino != null){
            binding.paisTextView.text = destino.pais
            binding.tituloTextView.text = destino.nombre
            binding.planTextView.text = destino.plan
            binding.precioTextView.text = "USD "+destino.precio.toString()
            binding.categoriaTextView.text = destino.categoria
        }
        val favoritos: Button = binding.favoritosBoton
        favoritos.setOnClickListener{

            if(arreglo.contains(destino)){
                DestinoRepository.eliminarDestino(destino)
                Toast.makeText(this, "Eliminado de favoritos", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
            else{
                Toast.makeText(this, "Añadido a favoritos", Toast.LENGTH_SHORT).show()
                DestinoRepository.agregarDestino(destino)
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }

        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}