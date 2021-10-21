package com.example.flickrbrowserapp2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.net.URL

class MainActivity : AppCompatActivity() {
    lateinit var button: Button
    lateinit var etSearch: EditText
    lateinit var rv:RecyclerView
    lateinit var imageView2: ImageView
    lateinit var photoList:ArrayList<ArrayList<String>>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        button=findViewById(R.id.button)
        etSearch=findViewById(R.id.etSearch)
        imageView2=findViewById(R.id.imageView2)
        rv=findViewById(R.id.rv)
        photoList= arrayListOf()

        button.setOnClickListener {
            val word=etSearch.text.toString()
            fetchData(word)
        }
    }

    private fun fetchData(word:String){
        val key="76b2587abc33c3289092e0e822964af6"
        CoroutineScope(Dispatchers.IO).launch {
            val api=URL("https://api.flickr.com/services/rest/?method=flickr.photos.search&per_page=10&api_key=$key&tags=$word&format=json&nojsoncallback=1")
                .readText(Charsets.UTF_8)
                getData(api)
        }
    }

    private suspend fun getData(api:String){
        withContext(Dispatchers.Main){
            val images=JSONObject(api).getJSONObject("photos").getJSONArray("photo")
            for (i in 0 until  images.length()){
                val id=images.getJSONObject(i).getString("id")
                val title=images.getJSONObject(i).getString("title")
                val secret=images.getJSONObject(i).getString("secret")
                val server=images.getJSONObject(i).getString("server")
                photoList+= arrayListOf(arrayListOf(id,title,secret,server))
            }
            rv.adapter=myAdapter(photoList,this@MainActivity)
            rv.layoutManager=LinearLayoutManager(this@MainActivity)
        }
    }
}