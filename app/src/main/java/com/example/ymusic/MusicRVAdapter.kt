package com.example.ymusic

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView


class MusicRVAdapter(val musicList: MutableList<Music>) :
        RecyclerView.Adapter<MusicRVAdapter.ViewHolder>(){
    inner class ViewHolder(view: View):RecyclerView.ViewHolder(view){
        val name:TextView=view.findViewById(R.id.musicName)
        val path:TextView=view.findViewById(R.id.musicPath)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.music_item, parent, false)

        val viewHolder=ViewHolder(view)

        viewHolder.itemView.setOnClickListener {
            val position=viewHolder.adapterPosition
            Toast.makeText(parent.context, "you click view ${position}", Toast.LENGTH_SHORT).show()
            MusicList.playPosition(musicList, position)
            //val intent=Intent(this, MusicListActivity::class.java)
            //val intent=Intent(this, MusicRecyclerActivity::class.java)
            val intent=Intent(parent.context, MusicPlayerActivity::class.java)
            startActivity(parent.context,intent,null)
        }

        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val music=musicList[position]
        holder.name.text=music.name
        holder.path.text=music.path



    }

    override fun getItemCount()=musicList.size
}