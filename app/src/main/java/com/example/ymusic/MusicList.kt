package com.example.ymusic

import android.content.ContentValues
import android.media.MediaPlayer
import android.util.Log
import org.json.JSONArray
import java.lang.Exception
import java.text.ParsePosition


object MusicList {
    var theMusicList: MutableList<Music> = mutableListOf()
    var nowPlaying= Music("")
    val mediaPlayer= MediaPlayer()
    init {
        mediaPlayer.setOnCompletionListener {
            playNextMusic()
        }
    }
    fun getMusicList():MutableList<Music>{
        //获取音乐列表

        val fileList: MutableList<Music> = mutableListOf()
        //val fileNames: MutableList<String> = mutableListOf()
        val fileTree: FileTreeWalk = Music("/sdcard").walk()
        fileTree.maxDepth(5) //需遍历的目录层次为1，即无须检查子目录
            .filter { it.isFile } //只挑选文件，不处理文件夹
            //.filter { it.extension == "flac"  } //选择扩展名为txt的文本文件
            .filter { it.extension in listOf("flac", "mp3") }//选择扩展名为txt或者mp3的文件
            .forEach { //fileNames.add(it.name)
                fileList.add(Music(it))}//循环 处理符合条件的文件
        //fileNames.forEach(::println)

        for (music in fileList){
            Log.d("msg", "find music "+music.path)
        }

        Log.d("msg", "getMusicList")
        return fileList
    }

    fun getTheMusicListFromDb():MutableList<Music>{
        val fileList: MutableList<Music> = mutableListOf()
        return fileList
    }

    fun enCodeMusicListToString (musicList:MutableList<Music>):String{
        val temp= JSONArray()
        for (music in musicList){
            temp.put(music.path)
        }
        val pathList=temp.toString()
        return pathList
    }

    fun deCodeMusicList(string:String):MutableList<Music>{
        val temp= JSONArray(string)
        val musicList: MutableList<Music> = mutableListOf()
        for (i in 0 until temp.length()){
            musicList.add(Music(temp[i].toString()))
        }
        return musicList
    }

    fun updateLocalMusicList(dbHelper: MyDatabaseHelper){
        theMusicList=getMusicList()
        val db=dbHelper.writableDatabase

        db.delete("MusicList","name=?", arrayOf("本地音乐"))
        Log.d("msg","delete成功")
        val pathList= enCodeMusicListToString(MusicList.theMusicList)
        val value=ContentValues().apply {
            put("name","本地音乐")
            put("pathList",pathList)
        }
        db.insert("MusicList",null,value)
        Log.d("msg","insert成功")
    }

    fun loadLocalMusicList(dbHelper: MyDatabaseHelper){
        val db=dbHelper.writableDatabase
        val cursor=db.query("MusicList", arrayOf("name","pathList"),"name=?", arrayOf("本地音乐"),null,null,null)
        cursor.moveToFirst()
        try {

            val pathList=cursor.getString(cursor.getColumnIndex("pathList"))
            cursor.close()
            theMusicList=deCodeMusicList(pathList)
        }catch (e:Exception){

        }
    }

    fun playLastMusic(){
        if (nowPlaying.path==""){
            nowPlaying=theMusicList[0]
        }
        var position=theMusicList.indexOf(nowPlaying)
        if (position==0){
            position= theMusicList.size-1
        }else{
            position-=1
        }
        nowPlaying= theMusicList[position]
        switchMusic()
    }
    fun playNextMusic(){
        if (nowPlaying.path==""){
            nowPlaying=theMusicList[0]
        }
        var position=theMusicList.indexOf(nowPlaying)
        if (position==theMusicList.size-1){
            position= 0
        }else{
            position+=1
        }
        nowPlaying= theMusicList[position]
        switchMusic()
    }

    fun playOrPause(){
        if (mediaPlayer.isPlaying){
            mediaPlayer.pause()
        }
        else if (nowPlaying.path==""){
            nowPlaying=theMusicList[0]
            initMediaPlayer(nowPlaying.path)
            mediaPlayer.start()
        } else{
            mediaPlayer.start()
        }
    }
    fun playPosition(musicList: MutableList<Music>, position: Int){
        theMusicList=musicList
        nowPlaying= theMusicList[position]
        switchMusic()
    }
    fun initMediaPlayer(path: String){
        mediaPlayer.setDataSource(path)
        mediaPlayer.prepare()
    }
    fun switchMusic(){
        mediaPlayer.reset()
        mediaPlayer.setDataSource(nowPlaying.path)
        mediaPlayer.prepare()
        mediaPlayer.start()
    }
}

