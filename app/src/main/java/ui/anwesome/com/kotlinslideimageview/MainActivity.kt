package ui.anwesome.com.kotlinslideimageview

import android.graphics.BitmapFactory
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import ui.anwesome.com.slideimageview.SlideImageView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view = SlideImageView.create(this,BitmapFactory.decodeResource(resources,R.drawable.nature_more))
        view.addSlideImageListener({
            Toast.makeText(this,"it's on",Toast.LENGTH_SHORT).show()
        },{
            Toast.makeText(this,"it's off",Toast.LENGTH_SHORT).show()
        })
    }
}
