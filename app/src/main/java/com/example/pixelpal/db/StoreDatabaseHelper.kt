package com.example.pixelpal.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.pixelpal.model.ShopItem

class StoreDatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        const val DATABASE_NAME = "store.db"
        const val DATABASE_VERSION = 2

        const val TABLE_ITEMS = "items"
        const val COLUMN_ID = "id"
        const val COLUMN_NAME = "name"
        const val COLUMN_PRICE = "price"
        const val COLUMN_RESTORE_POINTS = "restore_points"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTableQuery = """
            CREATE TABLE $TABLE_ITEMS (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_NAME TEXT NOT NULL,
                $COLUMN_PRICE REAL NOT NULL,
                $COLUMN_RESTORE_POINTS INTEGER NOT NULL
            )
        """.trimIndent()
        db.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        if (oldVersion < 2) {
            db.execSQL("ALTER TABLE items ADD COLUMN restore_points INTEGER DEFAULT 0")
        } else {
            db.execSQL("DROP TABLE IF EXISTS $TABLE_ITEMS")
            onCreate(db)
        }
    }

    fun insertItem(name: String, price: Double, restore: Int) {
        val db = writableDatabase
        val query = "INSERT INTO $TABLE_ITEMS ($COLUMN_NAME, $COLUMN_PRICE, $COLUMN_RESTORE_POINTS) VALUES (?, ?, ?)"
        db.execSQL(query, arrayOf(name, price, restore))
        db.close()
    }

    fun getAllItems(): List<ShopItem> {
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_ITEMS", null)
        val items = mutableListOf<ShopItem>()

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
                val name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME))
                val price = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_PRICE))
                val restorePoints = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_RESTORE_POINTS))
                items.add(ShopItem(
                    id, name, price, restorePoints))
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return items
    }
}
