package com.example.gracetastybites.sqllite

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "friendlyDB"
        private const val DATABASE_VERSION = 1
        const val TABLE_USERS = "UserMD"
        const val COL_ID = "id"
        const val COL_NAME = "name"
    }

    // this creates the table if it doesn't exist
    override fun onCreate(db: SQLiteDatabase?) {
        val createTableQuery =
            "CREATE TABLE $TABLE_USERS ($COL_ID INTEGER PRIMARY KEY AUTOINCREMENT, $COL_NAME TEXT)";

        // Execute the SQL query to create the table if it doesn't exist
        db?.execSQL(createTableQuery)
    }

    // this updates the table if the version changes
    override fun onUpgrade(
        db: SQLiteDatabase?,
        oldVersion: Int,
        newVersion: Int
    ) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_USERS") //? is to say if DB is not null do this (instead of having to an if statement)
    }

    // this inserts a user into the table, makes it into a reusable function
    fun insertUser(name: String): Long {
        val db = this.writableDatabase // makes the database writable
        val values = ContentValues() // allows for key value pairs
        values.put(COL_NAME, name) // so the column name is the key and the name is the value
        val result = db.insert(
            TABLE_USERS,
            null,
            values
        ) // inserts the values into the table null represents the id and is auto incremented, and values is the values like the name
        //db.close()
        return result
    }

    fun getAllUsers(): List<String> {
        val usersList = mutableListOf<String>() // creates a list of strings
        val db = this.readableDatabase // makes the database readable
        val cursor: Cursor = db.rawQuery(
            "SELECT * FROM $TABLE_USERS",
            null
        ) // creates a cursor that points to the first row of the table

        if (cursor.moveToFirst()) { // moves the cursor to the first row)
            do {

                val name = cursor.getString(cursor.getColumnIndexOrThrow(COL_NAME)) // gets the name from the cursor or throws an exception if it doesn't exist
                usersList.add(name) // adds the name to the list

            } while (cursor.moveToNext())
        }
        cursor.close()
        //db.close()
        return usersList
    }

    fun deleteUser(id: Int): Int {
        val db = this.writableDatabase // makes the database writable
        val result = db.delete(TABLE_USERS, "$COL_ID=?", arrayOf(id.toString())) // deletes the user with the given id and converts it to a string so it can be used in the query as SQL uses a string
        //db.close()
        return result
    }


}