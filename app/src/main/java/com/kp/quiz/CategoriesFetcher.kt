package com.kp.quiz

import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.kp.quiz.fetchedData.CategoryData
import com.kp.quiz.fetchedData.TriviaCategories

class CategoriesFetcher(private val context: Context) {
    private val getCategoriesUrl = "https://opentdb.com/api_category.php"
    var onFetchSuccess: (()->Unit)? = null
    var onFetchError: (()->Unit)? = null
    lateinit var fetchedData: TriviaCategories
    fun fetch(){
        val queue = Volley.newRequestQueue(context)
        val req = JsonObjectRequest(
            Request.Method.GET, getCategoriesUrl, null,
            { response ->
                val data = Gson().fromJson(response.toString(),TriviaCategories::class.java)
                fetchedData = data
                onFetchSuccess?.invoke()
            },
            { error ->
                Log.e("fetchError", error.toString())
                onFetchError?.invoke()
            }
        )

        //showFetchingProgress()
        queue.add(req)
    }
}
