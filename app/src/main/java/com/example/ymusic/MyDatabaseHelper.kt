package com.example.ymusic

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast

class MyDatabaseHelper(val context: Context, name:String, version:Int): SQLiteOpenHelper(context,name,null,version) {
    private val createMusicList="create table MusicList ("+
            "id integer primary key autoincrement,"+
            "name text,"+
            "pathList text)"

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(createMusicList)
        Toast.makeText(context,"Create succeeded",Toast.LENGTH_SHORT).show()
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("drop table if exists MusicList")
        onCreate(db)
    }
}