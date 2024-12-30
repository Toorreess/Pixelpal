package com.example.pixelpal

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity

class MenuActivity : AppCompatActivity() {

    private lateinit var heartProgressBar: ProgressBar
    private lateinit var heartImageView: ImageView
    private val handler = Handler(Looper.getMainLooper())
    private var progressStatus = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        heartProgressBar = findViewById(R.id.heartProgressBar)
        heartImageView = findViewById(R.id.iv_heart)

        val shopButton: Button = findViewById(R.id.btn_store)
        shopButton.setOnClickListener {
            val intent = Intent(this, ShopActivity::class.java)
            startActivity(intent)
        }

        val petButton: Button = findViewById(R.id.btn_pet)
        petButton.setOnClickListener {
            showHeartImage()
            increaseProgressBar()
        }

        startProgressBar()
    }

    private fun startProgressBar() {
        Thread {
            while (progressStatus > 0) {
                progressStatus -= 1
                handler.post {
                    heartProgressBar.progress = progressStatus
                }
                Thread.sleep(100) // Ajusta el tiempo de espera según sea necesario
            }
        }.start()
    }

    private fun showHeartImage() {
        heartImageView.visibility = View.VISIBLE
        handler.postDelayed({
            heartImageView.visibility = View.GONE
        }, 1000) // La imagen desaparece después de 1 segundo
    }

    private fun increaseProgressBar() {
        progressStatus = (progressStatus + 10).coerceAtMost(100)
        heartProgressBar.progress = progressStatus
    }
}