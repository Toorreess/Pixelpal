package com.example.pixelpal

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pixelpal.adapter.ShopAdapter
import com.example.pixelpal.db.StoreDatabaseHelper
import com.example.pixelpal.manager.CoinManager
import com.example.pixelpal.model.ShopItem

class ShopActivity : AppCompatActivity() {

    private lateinit var databaseHelper: StoreDatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop)

        val coinManager = CoinManager(this)

        databaseHelper = StoreDatabaseHelper(this)
        if (databaseHelper.getAllItems().isEmpty()) {
            databaseHelper.insertItem("Ítem 1", 10.0, 2)
            databaseHelper.insertItem("Ítem 2", 20.0, 4)
            databaseHelper.insertItem("Ítem 3", 30.0, 6)
        }

        val storeItems = databaseHelper.getAllItems()

        val recyclerView: RecyclerView = findViewById(R.id.item_list)
        val adapter = ShopAdapter(storeItems) { item ->
            buyItem(item, coinManager)
        }
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        val closeButton: Button = findViewById(R.id.btn_close)
        closeButton.setOnClickListener {
            val intent = Intent(this, MenuActivity::class.java)
            startActivity(intent)
        }

        val balanceTextView: TextView = findViewById(R.id.tv_balance)
        val newTextView = coinManager.getCoins().toString()

        balanceTextView.text="${balanceTextView.text} $newTextView"
    }

    private fun buyItem(item: ShopItem, coinManager: CoinManager) {
        val canBuy = coinManager.spendCoins(item.price)
        if (canBuy) {
            val balanceTextView: TextView = findViewById(R.id.tv_balance)
            val newTextView = coinManager.getCoins().toString()

            balanceTextView.text = "${balanceTextView.text} $newTextView"

            val intent = Intent(this, MenuActivity::class.java)
            intent.putExtra("progress_value", 10)
            startActivity(intent)


        } else {
            AlertDialog.Builder(this)
                .setTitle("Alerta")
                .setMessage("No tienes suficientes monedas para comprar este ítem.")
                .setPositiveButton("Aceptar") { dialog, _ ->
                    dialog.dismiss()
                }
                .show()
        }
    }
}