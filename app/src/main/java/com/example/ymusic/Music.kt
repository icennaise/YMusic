package com.example.ymusic

import org.jaudiotagger.audio.flac.FlacFileReader
import org.jaudiotagger.tag.FieldKey
import org.jaudiotagger.tag.datatype.Artwork
import java.io.File

//class Music(loc:File):File(loc.path) {
class Music:File{
    constructor(path:String):super(path)
    constructor(file:File):super(file.path)
    var title=""
    var artist=""
    var album=""
    var artwork= Artwork()
    init {
        if(this.isFile()){
            val musicTag= FlacFileReader().read(this).tag
            title=musicTag.getFirst(FieldKey.TITLE)
            artist=musicTag.getFirst(FieldKey.ARTIST)
            album=musicTag.getFirst(FieldKey.ALBUM)
            artwork= musicTag.firstArtwork
        }

    }
}