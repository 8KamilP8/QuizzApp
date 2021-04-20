package com.kp.quiz

import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.kp.quiz.fetchedData.TokenData

class SessionTokenGenerator(private val context: Context) {

    private lateinit var _token: String
    val token : String get() = _token
    var onFetchSuccess: (()->Unit)? = null
    var onFetchError: (()->Unit)? = null


    fun fetchToken(){
        val getTokenUrl: String = "https://opentdb.com/api_token.php?command=request"

        val queue = Volley.newRequestQueue(context)
        val req = JsonObjectRequest(
            Request.Method.GET, getTokenUrl, null,
            { response ->
                val data = Gson().fromJson(response.toString(), TokenData::class.java)
                _token = data.token
                onFetchSuccess?.invoke()
            },
            { error ->
                Log.e("fetchError", error.toString())
                onFetchError?.invoke()
            }
        )

        queue.add(req)
    }

}