package com.example.taipeitour.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.taipeitour.model.Attraction
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.Headers
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject

class AttractionsListViewModel: ViewModel() {
    private val _attractionsList = MutableLiveData<List<Attraction>>()

    val attractionsList: LiveData<List<Attraction>> get() = _attractionsList
    fun fetchData(selectedLanguage: String) {
        val client = OkHttpClient.Builder().build()
        val request = Request.Builder()
            .url("https://www.travel.taipei/open-api/${selectedLanguage}/Attractions/All?page=1")
            .headers(Headers.headersOf("Accept", "application/json"))
            .build()

        CoroutineScope(Dispatchers.IO).launch {
            val response = client.newCall(request).execute()
            response.body?.run {
                val json = string()
                val transList = parseJSON(json)
                _attractionsList.postValue(transList)
            }
        }
    }

    private fun parseJSON(json: String): List<Attraction> {
        val trans = mutableListOf<Attraction>()
        val jsonObject = JSONObject(json)
        val dataArray = jsonObject.getJSONArray("data")

        for (i in 0 until dataArray.length()) {
            val obj = dataArray.getJSONObject(i)

            val name = obj.getString("name") ?: ""
            val introduction = obj.getString("introduction") ?: ""
            val openTime = obj.getString("open_time") ?: ""
            val address = obj.getString("address") ?: ""
            val tel = obj.getString("tel") ?: ""
            val url = obj.getString("url") ?: ""

            val imagesArray = obj.getJSONArray("images")
            val firstImageObject = imagesArray.optJSONObject(0)
            val image = firstImageObject?.optString("src")?.replace("\\/", "/") ?: ""

            val attraction = Attraction(name, introduction, image, openTime, address, tel, url)
            trans.add(attraction)
        }

        return trans
    }


}