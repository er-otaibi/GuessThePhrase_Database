package com.example.guessthephrase

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context) : SQLiteOpenHelper(context, "phrases.db", null, 1) {

    private var sqLiteDatabase: SQLiteDatabase = writableDatabase
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("create table phrase ( Phrase text )")

    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {}

    fun savePhrase(s1: String): Long {
        val cv = ContentValues()
        cv.put("Phrase", s1)
        return sqLiteDatabase.insert("phrase", null, cv)
    }



    @SuppressLint("Range")
    fun readData() {
        var selectQuery = "SELECT  * FROM phrase"
        var cursor: Cursor? = null
        try {

            cursor = sqLiteDatabase.rawQuery(selectQuery, null)
        } catch (e: SQLiteException) {
            sqLiteDatabase.execSQL(selectQuery)
        }
        var phrase: String
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    phrase = cursor.getString(cursor.getColumnIndex("Phrase"))
                    MyList.mylist.add(phrase)
                } while (cursor.moveToNext())
            }
        }
    }
}