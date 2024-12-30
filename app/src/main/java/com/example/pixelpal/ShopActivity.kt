package com.example.pixelpal

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pixelpal.adapter.ShopAdapter
import com.example.pixelpal.db.StoreDatabaseHelper
import com.example.pixelpal.model.ShopItem

class ShopActivity : AppCompatActivity() {

    private lateinit var databaseHelper: StoreDatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop)

        databaseHelper = StoreDatabaseHelper(this)
        if (databaseHelper.getAllItems().isEmpty()) {
            databaseHelper.insertItem("Ítem 1", 10.0)
            databaseHelper.insertItem("Ítem 2", 20.0)
            databaseHelper.insertItem("Ítem 3", 30.0)
        }

        val storeItems = databaseHelper.getAllItems()

        val recyclerView: RecyclerView = findViewById(R.id.item_list)
        val adapter = ShopAdapter(storeItems) { item ->
            buyItem(item)
        }
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        val closeButton: Button = findViewById(R.id.btn_close)
        closeButton.setOnClickListener {
            val intent = Intent(this, MenuActivity::class.java)
            startActivity(intent)
        }
    }

    private fun buyItem(item: ShopItem) {

    }
}