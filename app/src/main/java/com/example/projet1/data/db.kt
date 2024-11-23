package com.example.projet1.data

import Flower
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DB(context: Context) : SQLiteOpenHelper(context, "flowers.db", null, 1) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(
            """
            CREATE TABLE users (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                name TEXT,
                mdp TEXT,
                email TEXT,
                confmdp TEXT
            )
            """
        )
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS users")
        onCreate(db!!)
    }
    fun checkUserCredentials(username: String, password: String): Boolean {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM users WHERE username = ? AND password = ?", arrayOf(username, password))
        val exists = cursor.count > 0
        cursor.close()
        return exists
    }
    fun getUserByName(username: String): Map<String, String>? {
        val db = this.readableDatabase
        val query = "SELECT * FROM users WHERE name = ?"
        val cursor = db.rawQuery(query, arrayOf(username))

        return if (cursor.moveToFirst()) {
            val user = mapOf(
                "name" to cursor.getString(cursor.getColumnIndexOrThrow("name")),
                "mdp" to cursor.getString(cursor.getColumnIndexOrThrow("mdp"))
            )
            cursor.close()
            user
        } else {
            cursor.close()
            null
        }
    }
    fun insertFlower(name: String, imageUrl: Int, description: String) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put("name", name)
            put("image_url", imageUrl)
            put("description", description)
        }
        db.insert("flowers", null, values)
    }

    fun getAllFlowers(): List<Flower> {
        val db = readableDatabase
        val cursor = db.query("flowers", null, null, null, null, null, null)
        val flowers = mutableListOf<Flower>()
        with(cursor) {
            while (moveToNext()) {
                val name = getString(getColumnIndexOrThrow("name"))
                val imageUrl = getInt(getColumnIndexOrThrow("image_url"))
                val description = getString(getColumnIndexOrThrow("description"))
                flowers.add(Flower(name, imageUrl, description))
            }
        }
        cursor.close()
        return flowers
    }

    fun getPasswordByEmail(email: String): String? {
        val db = this.readableDatabase
        val query = "SELECT mdp FROM users WHERE email = ?"
        val cursor = db.rawQuery(query, arrayOf(email))

        return if (cursor.moveToFirst()) {
            val password = cursor.getString(cursor.getColumnIndexOrThrow("mdp"))
            cursor.close()
            password
        } else {
            cursor.close()
            null
        }
    }


    fun insertUser(name: String, mdp: String, email: String,confmdp:String): Long {
        val db = writableDatabase
        val values = ContentValues().apply {
            put("name", name)
            put("mdp", mdp)
            put("email", email)
        }
        val result = db.insert("users", null, values)
        db.close()
        return result
    }

    fun getAllUsers(): List<Map<String, String>> {
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT name, mdp, email, confmdp FROM users", null)
        val userList = mutableListOf<Map<String, String>>()

        while (cursor.moveToNext()) {
            userList.add(
                mapOf(
                    "name" to cursor.getString(0),
                    "mdp" to cursor.getString(1),
                    "email" to cursor.getString(2),
                    "confmdp" to cursor.getString(3)
                )
            )
        }

        db.close()
        return userList
    }
}
