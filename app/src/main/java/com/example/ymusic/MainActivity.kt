package com.example.ymusic

import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.ymusic.MusicList.getMusicList
import com.example.ymusic.MusicList.sdCardIsAvailable
import com.hjq.permissions.OnPermission
import com.hjq.permissions.Permission
import com.hjq.permissions.XXPermissions
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File


open class MainActivity : AppCompatActivity() {
    //public val amusicList=getMusicList()
    //public val bmusicList=amusicList
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //val intent=Intent(this, PermissionActivity::class.java)
        //startActivity(intent)

        //getExternalStoragePermission()
        //若无外部存储权限则申请
        if(!sdCardIsAvailable()){
            getExternalStoragePermission()
        }

        //val dbHelper=MyDatabaseHelper(this,"MusicList.db",1)
        //dbHelper.writableDatabase

        button.setOnClickListener{
            //查找sdcard目录下的音乐文件并返回fileList
            Toast.makeText(this,"YMusic",Toast.LENGTH_SHORT).show()
            MusicList.theMusicList=getMusicList()
        }

        button2.setOnClickListener{
            Toast.makeText(this,"进入列表",Toast.LENGTH_SHORT).show()
            //val intent=Intent(this, MusicListActivity::class.java)
            val intent=Intent(this, MusicRecyclerActivity::class.java)
            startActivity(intent)
        }
        //val file=File(Environment.getExternalStorageDirectory().getPath());
        //val fileList = file.listFiles()
        //val is1: Boolean = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)
        //val fileTree: FileTreeWalk = File("/sdcard").walk()
        //val fileTree: FileTreeWalk = file.walk()
        //println(sdCardIsAvailable())
    }


    public fun getExternalStoragePermission(){
        //申请外部存储权限的函数
        XXPermissions.with(this@MainActivity) // 不适配 Android 11 可以这样写
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
                        XXPermissions.startPermissionActivity(this@MainActivity, denied)
                    } else {
                        Log.d("msg","获取存储权限失败")
                    }
                }
            })
    }
}