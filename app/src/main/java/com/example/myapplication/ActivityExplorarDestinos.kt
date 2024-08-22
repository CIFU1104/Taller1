package com.example.myapplication

import android.content.Context
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
import model.Destino
import org.json.JSONObject
import java.io.IOException
import java.io.InputStream

class ExplorarDestinos : ComponentActivity() {
    private lateinit var bindig: ExplorarDestinosBinding
    private val arreglo:ArrayList<Destino> = ArrayList<Destino>()
    override fun onCreate(savedInstanceState: Bundle?) {
        bindig = ExplorarDestinosBinding.inflate(layoutInflater)
        setContentView(bindig.root)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val filtro = intent.getStringExtra("Filtro")
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
            //arreglo.add(()jsonObject.getString("destinos"))
        }

    }
    private fun initRecyclerView(){
        val recyclerView = bindig.recyclerDestinos
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = DestinoAdapter(DestinoProvider.mutableEmptyList)

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