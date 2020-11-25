package com.example.ymusic

import org.json.JSONArray

fun main(){
    val fileList: MutableList<Music> = mutableListOf()
    //val fileNames: MutableList<String> = mutableListOf()
    val fileTree: FileTreeWalk = Music("d:\\").walk()
    fileTree.maxDepth(5) //需遍历的目录层次为1，即无须检查子目录
        .filter { it.isFile } //只挑选文件，不处理文件夹
        //.filter { it.extension == "flac"  } //选择扩展名为txt的文本文件
        .filter { it.extension in listOf("flac", "mp3") }//选择扩展名为txt或者mp3的文件
        .forEach { //fileNames.add(it.name)
            fileList.add(Music(it))}//循环 处理符合条件的文件
    //fileNames.forEach(::println)

    println("pathList")

    val temp:MutableList<String> = mutableListOf()
    for (music in fileList){
        temp.add(music.path)
    }
    val pathList=temp.toString()
    val jsonObj: JSONArray = JSONArray(pathList)
    println(pathList)
    val result:MutableList<String> = mutableListOf(pathList)

}
