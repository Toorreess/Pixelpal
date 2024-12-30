package com.example.pixelpal.manager

import android.content.Context

class CoinManager(context: Context) {
    private val sharedPreferences = context.getSharedPreferences("user_data", Context.MODE_PRIVATE)

    companion object {
        private const val KEY_COINS = "coins"
    }

    fun getCoins(): Int {
        return sharedPreferences.getInt(KEY_COINS, 0)
    }

    fun addCoins(amount: Int) {
        val currentCoins = getCoins()
        sharedPreferences.edit().putInt(KEY_COINS, currentCoins + amount).apply()
    }

    fun spendCoins(amount: Int): Boolean {
        val currentCoins = getCoins()
        return if (currentCoins >= amount) {
            sharedPreferences.edit().putInt(KEY_COINS, currentCoins - amount).apply()
            true
        } else {
            false
        }
    }
}
