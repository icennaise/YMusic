package com.example.ymusic

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MusicPlayerActivity : AppCompatActivity() {
    private val mediaPlayer=MediaPlayer()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_music_player)


        mediaPlayer.setDataSource("/sdcard/Download/01. みきとP Feat. 初音ミク - 39みゅーじっく! 2016.flac")
        mediaPlayer.prepare() ;
        mediaPlayer.start()
    }
}