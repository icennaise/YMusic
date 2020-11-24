package com.example.ymusic

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class MusicAdapter(activity:Activity,val resourceID: Int ,data:List<Music>):ArrayAdapter<Music>(activity,resourceID,data) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view=LayoutInflater.from(context).inflate(resourceID,parent,false)
        val textView1:TextView=view.findViewById(R.id.musicName)
        val textView2:TextView=view.findViewById(R.id.musicName)
        val music=getItem(position)
        if (music!=null){
            textView1.text=music.name
            textView2.text=music.path
        }
        return super.getView(position, convertView, parent)
    }
}