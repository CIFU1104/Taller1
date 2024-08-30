package com.example.myapplication

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.databinding.ExplorarDestinosBinding
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.google.gson.Gson
import model.Destino
import org.json.JSONObject
import java.io.IOException
import java.io.InputStream

class ExplorarDestinos : ComponentActivity() {
    private lateinit var binding: ExplorarDestinosBinding
    private var arreglo:ArrayList<Destino> = ArrayList<Destino>()
    private lateinit var destinoAdapter: DestinoAdapter
    private var filtro:String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ExplorarDestinosBinding.inflate(layoutInflater)
        setContentView(binding.root)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        filtro = intent.getStringExtra("Filtro")!!
        stringToObject()
        initRecyclerView()
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
            if(destino.categoria.equals(filtro) || filtro.equals("Todos")){
                arreglo.add(destino)
            }
        }
    }
    private fun initRecyclerView(){
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



@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyApplicationTheme {
        Greeting("Android")
    }
}