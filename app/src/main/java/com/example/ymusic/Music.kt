package com.example.ymusic

import java.io.File

//class Music(loc:File):File(loc.path) {
class Music:File{
    constructor(path:String):super(path)
    constructor(file:File):super(file.path)
}