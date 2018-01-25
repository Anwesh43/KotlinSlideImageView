package ui.anwesome.com.kotlinslideimageview

import android.graphics.BitmapFactory
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import ui.anwesome.com.slideimageview.SlideImageView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        SlideImageView.create(this,BitmapFactory.decodeResource(resources,R.drawable.nature_more))
    }
}
