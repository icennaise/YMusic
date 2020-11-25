package com.example.ymusic

import android.os.Environment
import android.util.Log
import android.widget.Toast
import com.hjq.permissions.OnPermission
import com.hjq.permissions.Permission
import com.hjq.permissions.XXPermissions
import java.io.File


object MusicList {
    var theMusicList= getMusicList()

    public fun sdCardIsAvailable():Boolean {
        //首先判断外部存储是否可用
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            var sd: File = File(Environment.getExternalStorageDirectory().getPath());
            Log.v("msg", "sd = " + sd);//sd = /storage/emulated/0
            return sd.canRead()
        } else {
            return false;
        }
    }

    fun getMusicList():MutableList<Music>{
        //获取音乐列表
        if(!sdCardIsAvailable()){
            MainActivity().getExternalStoragePermission()
        }

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
        //Toast.makeText(this,"getMusicList",Toast.LENGTH_SHORT).show()
        return fileList
    }
}