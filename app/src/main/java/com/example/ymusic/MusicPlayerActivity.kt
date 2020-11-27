package com.example.ymusic

import android.graphics.BitmapFactory
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.ymusic.MusicList.isMusic
import com.example.ymusic.MusicList.mediaPlayer
import com.example.ymusic.MusicList.nowPlaying
import com.example.ymusic.MusicList.playLastMusic
import com.example.ymusic.MusicList.playMode
import com.example.ymusic.MusicList.playNextMusic
import com.example.ymusic.MusicList.playOrPause
import kotlinx.android.synthetic.main.activity_music_player.*
import java.lang.Exception


class MusicPlayerActivity : AppCompatActivity() {
    override fun onRestart() {
        super.onRestart()
        setInfo()
    }

    override fun onResume() {
        super.onResume()
        setInfo()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("msg", "Player On Create")
        setContentView(R.layout.activity_music_player)
        if(mediaPlayer.isPlaying){
            Log.d("msg", "is Playing")
        }
        else{
            Log.d("msg", "not Playing")
        }
        mediaPlayer.setOnCompletionListener {
            when(MusicList.playMode){
                0->playNextMusic()
                1-> MusicList.playThisMusic()
                2-> MusicList.playRandomMusic()
                else->null
            }
            setInfo()
        }
        if (isMusic()){
            setInfo()

        }

        playOrPause.setOnClickListener{
            playOrPause()
            setInfo()
        }


        lastMusic.setOnClickListener {
            playLastMusic()
            setInfo()
        }

        nextMusic.setOnClickListener {
            playNextMusic()
            setInfo()
        }
        playMode.setOnClickListener{
            if (MusicList.playMode==3)
                MusicList.playMode=0
            else {
                MusicList.playMode+=1
            }
            playMode.text=MusicList.playModeStringArray[MusicList.playMode]
        }
    }
    fun setInfo(){
        try {
            val tf = Typeface.createFromAsset(this.assets, "fonts/A-OTF-RyuminPro-Light.otf")
            val tf2 = Typeface.createFromAsset(this.assets, "fonts/RyuminPro-Regular.otf")
            val tf3 = Typeface.createFromAsset(this.assets, "fonts/RyuminPro-Bold.otf")
            val tf4 = Typeface.createFromAsset(this.assets, "fonts/FZNiNSJW.TTF")
            TitleView.text= nowPlaying.title
            AblumView.text= nowPlaying.album
            ArtistView.text= nowPlaying.artist
            //val tf=Typeface.createFromAsset(,"A-OTF-RyuminPro-Light.otf")
            TitleView.setTypeface(tf2)
            AblumView.setTypeface(tf)
            ArtistView.setTypeface(tf3)
            val bitmap = BitmapFactory.decodeByteArray(nowPlaying.artwork.binaryData, 0, nowPlaying.artwork.binaryData.size)
            ArtworkView.setImageBitmap(bitmap)
//            playOrPause.setTypeface(tf4)
//            lastMusic.setTypeface(tf4)
//            nextMusic.setTypeface(tf4)
    }catch (e:Exception){
            TitleView.text= nowPlaying.name
        }
    }
}