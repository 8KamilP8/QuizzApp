package com.kp.quiz

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kp.quiz.fetchedData.TriviaCategories

class CategoriesListAdapter(private val dataSet: TriviaCategories, private val context: Context, private val mode: Int) : RecyclerView.Adapter<CategoriesListAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val categoryNameText: TextView

        init {
            categoryNameText = view.findViewById(R.id.category_name)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.category_list_item, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dataSet.trivia_categories.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var category = dataSet.trivia_categories[position]
        holder.categoryNameText.text = category.name

        holder.categoryNameText.setOnClickListener{
            if(mode == 1){
                val intent = Intent(context, SinglePlay::class.java).apply {
                    putExtra(context.getString(R.string.select_category),category.id)
                }

                context.startActivity(intent)
            }
        }
    }
}
