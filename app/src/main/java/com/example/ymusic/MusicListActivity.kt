package com.example.ymusic

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import com.hjq.permissions.OnPermission
import com.hjq.permissions.Permission
import com.hjq.permissions.XXPermissions
import kotlinx.android.synthetic.main.activity_music_list.*
import java.io.File

class MusicListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_music_list)

        val musicList=getMusicList()

        val adapter=ArrayAdapter<Music>(this,android.R.layout.simple_list_item_1,musicList)
        musicListView.adapter=adapter
        musicListView.setOnItemClickListener { parent, view, position, id ->
            val music = musicList[position]
            Toast.makeText(this,music.name,Toast.LENGTH_SHORT).show()
        }
    }

    public fun sdCardIsAvailable():Boolean {
        //首先判断外部存储是否可用
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            var sd: File = File(Environment.getExternalStorageDirectory().getPath());
            Log.e("msg", "sd = " + sd);//sd = /storage/emulated/0
            return sd.canRead()
        } else {
            return false;
        }
    }

    public fun getExternalStoragePermission(){
        //申请外部存储权限的函数
        XXPermissions.with(this@MusicListActivity) // 不适配 Android 11 可以这样写
            .permission(Permission.MANAGE_EXTERNAL_STORAGE)
            .request(object : OnPermission {
                override fun hasPermission(granted: List<String>, all: Boolean) {
                    if (all) {
                        Log.d("msg","获取存储权限成功")
                    }
                }

                override fun noPermission(denied: List<String>, never: Boolean) {
                    if (never) {
                        Log.d("msg","被永久拒绝授权，请手动授予存储权限")
                        // 如果是被永久拒绝就跳转到应用权限系统设置页面
                        XXPermissions.startPermissionActivity(this@MusicListActivity, denied)
                    } else {
                        Log.d("msg","获取存储权限失败")
                    }
                }
            })
    }

    fun getMusicList():MutableList<Music>{
        //获取音乐列表
        if(!sdCardIsAvailable()){
            getExternalStoragePermission()
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

        Toast.makeText(this,"getMusicList", Toast.LENGTH_SHORT).show()
        return fileList
    }
}