package com.example.ymusic

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import android.widget.TextView
import java.util.jar.Attributes

class CustomTextView(context: Context,attrs:AttributeSet): androidx.appcompat.widget.AppCompatTextView(context,attrs){
    override fun setTypeface(tf: Typeface?) {
        val tf=Typeface.createFromAsset(context.assets,"A-OTF-RyuminPro-Light.otf")
        super.setTypeface(tf)
    }
}