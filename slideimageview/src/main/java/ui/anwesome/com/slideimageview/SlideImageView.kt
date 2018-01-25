package ui.anwesome.com.slideimageview

/**
 * Created by anweshmishra on 26/01/18.
 */
import android.view.*
import android.content.*
import android.graphics.*
class SlideImageView(ctx:Context,var bitmap:Bitmap):View(ctx) {
    val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    override fun onDraw(canvas:Canvas) {

    }
    override fun onTouchEvent(event:MotionEvent):Boolean {
        when(event.action) {
            MotionEvent.ACTION_DOWN -> {

            }
        }
        return true
    }
}