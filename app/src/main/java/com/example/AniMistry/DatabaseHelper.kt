package com.example.AniMistry

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "Education.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_NAME = "Users"
        private const val COLUMN_ID = "id"
        private const val COLUMN_NAME = "name"
        private const val COLUMN_SCORE = "score"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTable = "CREATE TABLE $TABLE_NAME (" +
                "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "$COLUMN_NAME TEXT," +
                "$COLUMN_SCORE INTEGER)"
        db?.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun insertUser(name: String, score: Int): Long {
        val db = this.writableDatabase
        val contentValues = ContentValues().apply {
            put(COLUMN_NAME, name)
            put(COLUMN_SCORE, score)
        }
        return db.insert(TABLE_NAME, null, contentValues)
    }

    fun getCurrentUser(name: String): Int {
        val db = this.readableDatabase
        val cursor = db.query(TABLE_NAME, arrayOf(COLUMN_NAME), null, null, null, null, null)
        var scores = 0

        if (cursor.moveToFirst()) {
            do {
                val score = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SCORE))
                scores = score.toInt()
            } while (cursor.moveToNext())
        }
        cursor.close()
        return scores
    }

    fun getAnimalByName(name: String): Int? {
        val db = this.readableDatabase
        val cursor = db.query(
            TABLE_NAME,
            arrayOf(COLUMN_SCORE, COLUMN_NAME),
            "$COLUMN_NAME = ?",
            arrayOf(name),
            null,
            null,
            null
        )

        var retrievedScore: Int? = 0
        if (cursor.moveToFirst()) {
            retrievedScore = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_SCORE))
        }
        cursor.close()
        return retrievedScore
    }

    fun updateScore(newName: String, newScore: Int): Int {
        val db = this.writableDatabase
        val contentValues = ContentValues().apply {
            put(COLUMN_SCORE, newScore)
        }

        // Update the row
        return db.update(
            TABLE_NAME,
            contentValues,
            "$COLUMN_NAME = ?",
            arrayOf(newName)
        )
    }

}
