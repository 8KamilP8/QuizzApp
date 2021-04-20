package com.kp.quiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class CategoryMenu : AppCompatActivity() {

    private lateinit var categoriesFetcher: CategoriesFetcher
    private lateinit var recyclerView: RecyclerView
    private lateinit var listAdapter: CategoriesListAdapter
    private lateinit var progressBar: ProgressBar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category_menu)


        val mode = intent.getIntExtra(getString(R.string.game_mode_msg),1)

        recyclerView = findViewById(R.id.categories_menu)
        progressBar = findViewById(R.id.progressBar)


        categoriesFetcher = CategoriesFetcher(this)

        recyclerView.layoutManager = LinearLayoutManager(this)

        categoriesFetcher.onFetchSuccess = {
            listAdapter = CategoriesListAdapter(categoriesFetcher.fetchedData, this, mode)
            recyclerView.adapter = listAdapter

            showData()
        }

        categoriesFetcher.fetch()

        showLoading()
    }

    private fun showLoading(){
        recyclerView.visibility = View.GONE
        progressBar.visibility = View.VISIBLE
    }
    private fun showData(){
        recyclerView.visibility = View.VISIBLE
        progressBar.visibility = View.GONE
    }
}

