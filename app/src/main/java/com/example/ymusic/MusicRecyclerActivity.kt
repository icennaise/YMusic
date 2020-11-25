package com.example.ymusic

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_music_recycler.*

class MusicRecyclerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_music_recycler)

        Log.d("msg", MusicList.theMusicList.size.toString())
        val layoutManager= LinearLayoutManager(this)
        recyclerView.layoutManager=layoutManager

        val adapter= MusicRVAdapter(MusicList.theMusicList)
        recyclerView.adapter=adapter

    }


}