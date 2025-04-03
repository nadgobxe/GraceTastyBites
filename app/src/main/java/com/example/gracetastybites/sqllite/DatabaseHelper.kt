package com.example.gracetastybites.sqllite

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.gracetastybites.mockData.UserAuth

class DatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "graceTastyBitesDB"
        private const val DATABASE_VERSION = 1
        const val TABLE_STAFF = "staff"
        const val COL_ID = "id"
        const val COL_EMAIL = "email"
        const val COL_PASSWORD = "password"
        const val COL_FIRST_NAME = "first_name"
        const val COL_LAST_NAME = "last_name"
        const val COL_ROLE = "role"
        const val COL_POSITION = "position"
        const val COL_PROFILE_PIC = "profile_pic"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTableQuery =
            "CREATE TABLE $TABLE_STAFF (" +
                    "$COL_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "$COL_FIRST_NAME TEXT, $COL_LAST_NAME TEXT, $COL_EMAIL TEXT, " +
                    "$COL_PASSWORD TEXT, $COL_ROLE TEXT, $COL_POSITION TEXT, $COL_PROFILE_PIC TEXT)"
        db?.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_STAFF")
        onCreate(db)
    }

    fun insertUser(user: UserAuth): Long {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COL_EMAIL, user.email)
            put(COL_PASSWORD, user.password)
            put(COL_FIRST_NAME, user.firstname)
            put(COL_LAST_NAME, user.lastname)
            put(COL_ROLE, user.role)
            put(COL_POSITION, user.position)
            put(COL_PROFILE_PIC, user.profilePic)
        }
        return db.insert(TABLE_STAFF, null, values)
    }

    fun getAllUsers(): List<UserAuth> {
        val usersList = mutableListOf<UserAuth>()
        val db = this.readableDatabase
        val cursor: Cursor = db.rawQuery("SELECT * FROM $TABLE_STAFF", null)

        if (cursor.moveToFirst()) {
            do {
                val user = UserAuth(
                    email = cursor.getString(cursor.getColumnIndexOrThrow(COL_EMAIL)),
                    password = cursor.getString(cursor.getColumnIndexOrThrow(COL_PASSWORD)),
                    firstname = cursor.getString(cursor.getColumnIndexOrThrow(COL_FIRST_NAME)),
                    lastname = cursor.getString(cursor.getColumnIndexOrThrow(COL_LAST_NAME)),
                    position = cursor.getString(cursor.getColumnIndexOrThrow(COL_POSITION)),
                    role = cursor.getString(cursor.getColumnIndexOrThrow(COL_ROLE)),
                    profilePic = cursor.getString(cursor.getColumnIndexOrThrow(COL_PROFILE_PIC))
                )
                usersList.add(user)
            } while (cursor.moveToNext())
        }

        cursor.close()
        return usersList
    }
    fun deleteUserById(id: Int): Int {
        val db = this.writableDatabase
        return db.delete(TABLE_STAFF, "$COL_ID=?", arrayOf(id.toString()))
    }
}
