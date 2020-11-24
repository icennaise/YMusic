package com.example.ymusic

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.hjq.permissions.OnPermission
import com.hjq.permissions.Permission
import com.hjq.permissions.XXPermissions
import kotlinx.android.synthetic.main.activity_music_list.*
import kotlinx.android.synthetic.main.activity_music_recycler.*
import java.io.File

class MusicRecyclerActivity : AppCompatActivity() {
    private val musicList=ArrayList<Music>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_music_recycler)

        //Toast.makeText(this,"Recycler", Toast.LENGTH_SHORT).show()

        getMusicList()

        Log.d("msg", musicList.size.toString())
        val layoutManager= LinearLayoutManager(this)
        recyclerView.layoutManager=layoutManager

        val adapter= MusicRVAdapter(musicList)
        recyclerView.adapter=adapter

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
        XXPermissions.with(this@MusicRecyclerActivity) // 不适配 Android 11 可以这样写
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
                        XXPermissions.startPermissionActivity(this@MusicRecyclerActivity, denied)
                    } else {
                        Log.d("msg","获取存储权限失败")
                    }
                }
            })
    }

    private fun getMusicList(){
        //获取音乐列表
        if(!sdCardIsAvailable()){
            getExternalStoragePermission()
        }


        val fileTree: FileTreeWalk = Music("/sdcard").walk()
        fileTree.maxDepth(5) //需遍历的目录层次为1，即无须检查子目录
            .filter { it.isFile } //只挑选文件，不处理文件夹
            //.filter { it.extension == "flac"  } //选择扩展名为txt的文本文件
            .filter { it.extension in listOf("flac", "mp3") }//选择扩展名为txt或者mp3的文件
            .forEach { //fileNames.add(it.name)
                musicList.add(Music(it))}//循环 处理符合条件的文件
        //fileNames.forEach(::println)

        for (music in musicList){
            Log.d("msg", "find music "+music.path)
        }

        Toast.makeText(this,"getMusicList", Toast.LENGTH_SHORT).show()
    }
}