package com.example.ymusic

import android.graphics.BitmapFactory
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.ymusic.MusicList.mediaPlayer
import com.example.ymusic.MusicList.nowPlaying
import com.example.ymusic.MusicList.playLastMusic
import com.example.ymusic.MusicList.playNextMusic
import com.example.ymusic.MusicList.playOrPause
import kotlinx.android.synthetic.main.activity_music_player.*
import java.lang.Exception


class MusicPlayerActivity : AppCompatActivity() {
    //val mediaPlayer=MediaPlayer()
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

        if (MusicList.nowPlaying.path!=""){
            setInfo()
//            if (mediaPlayer.isPlaying){
//                mediaPlayer.reset()
//                initMediaPlayer(MusicList.nowPlaying.path)
//                mediaPlayer.start()
//            }
//            else{
//                initMediaPlayer(MusicList.nowPlaying.path)
//                mediaPlayer.start()
//            }
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
    }
    fun setInfo(){
        try {
            TitleView.text= nowPlaying.title
            AblumView.text= nowPlaying.album
            ArtistView.text= nowPlaying.artist
            //val tf=Typeface.createFromAsset(,"A-OTF-RyuminPro-Light.otf")
            val tf = Typeface.createFromAsset(this.assets, "fonts/A-OTF-RyuminPro-Light.otf")
            TitleView.setTypeface(tf)
            AblumView.setTypeface(tf)
            ArtistView.setTypeface(tf)
            val bitmap = BitmapFactory.decodeByteArray(nowPlaying.artwork.binaryData, 0, nowPlaying.artwork.binaryData.size)
            ArtworkView.setImageBitmap(bitmap)
    }catch (e:Exception){
            TitleView.text= nowPlaying.name
        }

    }
}