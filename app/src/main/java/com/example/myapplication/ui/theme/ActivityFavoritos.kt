package com.example.myapplication.ui.theme

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.ActivityDetailed
import com.example.myapplication.DestinoAdapter
import com.example.myapplication.DestinoRepository
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityFavoritosBinding
import com.example.myapplication.databinding.ExplorarDestinosBinding
import model.Destino

class ActivityFavoritos : AppCompatActivity() {
    private lateinit var binding: ActivityFavoritosBinding
    private var arreglo:ArrayList<Destino> = ArrayList<Destino>()
    private lateinit var destinoAdapter: DestinoAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityFavoritosBinding.inflate(layoutInflater)
        setContentView(binding.root)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        initRecyclerView()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
    private fun initRecyclerView(){
        arreglo = DestinoRepository.obtenerDestinos() as ArrayList<Destino>
        val recyclerView = binding.recyclerDestinos
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)
        destinoAdapter = DestinoAdapter(arreglo)
        recyclerView.adapter = destinoAdapter
        destinoAdapter.onItemClick = {
            val intent = Intent(this, ActivityDetailed::class.java)
            intent.putExtra("destino", it)
            startActivity(intent)
        }
    }
}