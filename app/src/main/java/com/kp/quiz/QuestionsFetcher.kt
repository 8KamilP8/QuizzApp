package com.kp.quiz

import android.content.Context
import android.text.Html
import android.util.Log
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.kp.quiz.fetchedData.QuestionData
import com.kp.quiz.fetchedData.QuestionList

class QuestionsFetcher(private val context: Context, private val categoryId: Int) {

    private val getTokenUrl = "https://opentdb.com/api_token.php?command=request"
    var onFetchSuccess: (()->Unit)? = null
    var onFetchError: (()->Unit)? = null
    lateinit var questions: List<QuestionData>
    fun fetch(){
        val queue = Volley.newRequestQueue(context)
        var builder = QuestionsRequestBuilder()

        val req = JsonObjectRequest(
            Request.Method.GET,
            builder.setAmount(10).setCategory(categoryId).getProduct(),
            null,
            { response ->
                questions = Gson().fromJson(response.toString(), QuestionList::class.java).results
                onFetchSuccess?.invoke()
            },
            { error ->
                onFetchError?.invoke()
                Log.d("rates:fetchError", error.toString())
            }
        )
        queue.add(req)
    }


}

