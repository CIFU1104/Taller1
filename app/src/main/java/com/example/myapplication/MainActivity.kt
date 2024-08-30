package com.example.myapplication

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.get
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.ui.theme.ActivityFavoritos
import com.google.gson.Gson
import model.Destino
import org.json.JSONObject
import java.io.IOException
import java.io.InputStream

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var totalDestinos:ArrayList<Destino> = ArrayList<Destino>()
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val spinner: Spinner = findViewById(R.id.spinner)
// Create an ArrayAdapter using the string array and a default spinner layout.
        ArrayAdapter.createFromResource(
            this,
            R.array.spinner,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner.
            spinner.adapter = adapter
        }
        val explorar: Button = binding.explorarDestinosBoton
        explorar.setOnClickListener{
            val intent = Intent(this, ExplorarDestinos::class.java).apply {
                putExtra("Filtro", binding.spinner.selectedItem.toString())
            }

            startActivity(intent)
        }
        val favoritos: Button = binding.favoritosBoton
        favoritos.setOnClickListener{
            val intent = Intent(this, ActivityFavoritos::class.java)
            startActivity(intent)
        }

        val recomendacion: Button = binding.recomendacionesBoton
        recomendacion.setOnClickListener{
            var arreglo:ArrayList<Destino> = DestinoRepository.obtenerDestinos() as ArrayList<Destino>
            if(arreglo.isEmpty()){
                Toast.makeText(this, "No hay favoritos agregados", Toast.LENGTH_SHORT).show()
            }
            else{
                val conteoCategorias = mutableMapOf<String, Int>()
                for(destino in arreglo){
                    val categoria = destino.categoria
                    conteoCategorias[categoria] = conteoCategorias.getOrDefault(categoria, 0) + 1
                }
                stringToObject()
                val categoriaMasRepetida = conteoCategorias.maxByOrNull { it.value }?.key
                var destino:Destino = totalDestinos.random()
                if(destino.categoria != categoriaMasRepetida || arreglo.contains(destino)){
                    while(destino.categoria != categoriaMasRepetida){
                        destino = totalDestinos.random()
                    }
                }
                val intent = Intent(this, ActivityDetailed::class.java)
                intent.putExtra("destino", destino)
                startActivity(intent)
            }

        }

    }
    private fun loadJSONFromAsset(context: Context): String? {
        var json: String? = null
        try {
            val isStream: InputStream = context.assets.open("destinos.json")
            val size:Int = isStream.available()
            val buffer = ByteArray(size)
            isStream.read(buffer)
            isStream.close()
            json = String(buffer, Charsets.UTF_8)
        } catch (ex: IOException) {
            ex.printStackTrace()
            return null
        }
        return json
    }
    private fun stringToObject(){
        val json = JSONObject(loadJSONFromAsset(this))
        val destinosJson = json.getJSONArray("destinos")
        for (i in 0 until destinosJson.length()){
            val jsonObject = destinosJson.getJSONObject(i)
            val jsonStringFromObject = jsonObject.toString()
            val destino: Destino = Gson().fromJson(jsonStringFromObject, Destino::class.java)
            totalDestinos.add(destino)

        }
    }
}