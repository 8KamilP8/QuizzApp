package com.kp.quiz

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity



class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

    fun openCategoriesMenuSinglePlayer(view: View){
        val intent = Intent(this, CategoryMenu::class.java).apply {
            putExtra(getString(R.string.game_mode_msg), 1)
        }
        startActivity(intent)
    }

}
