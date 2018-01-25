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
    data class SlideImage(var i:Int) {
        fun draw(canvas:Canvas,paint:Paint,bitmap:Bitmap,w:Float,h:Float,n:Int,scale:Float) {
            var sy = i*(h/n)
            var k = i%2
            var ki = (i+1)%2
            canvas.drawBitmap(bitmap,Rect(0,0,bitmap.width,bitmap.height),RectF(k*w*(1-scale),sy,w*(k+ki*scale),sy+h/n),paint)
        }
    }
}