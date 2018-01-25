package ui.anwesome.com.slideimageview

/**
 * Created by anweshmishra on 26/01/18.
 */
import android.view.*
import android.content.*
import android.graphics.*
import java.util.concurrent.ConcurrentLinkedQueue

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
    data class SlideImageContainer(var bitmap:Bitmap,var w:Float,var h:Float,var n:Int = 10) {
        val slides:ConcurrentLinkedQueue<SlideImage> = ConcurrentLinkedQueue<SlideImage>()
        init {
            bitmap = Bitmap.createScaledBitmap(bitmap,w.toInt(),h.toInt(),true)
            for(i in 0..n-1) {
                slides.add(SlideImage(i))
            }
        }
        fun draw(canvas:Canvas,paint:Paint) {
            slides.forEach {
                it.draw(canvas,paint,bitmap,w,h,n,1f)
            }
        }
        fun update(stopcb:(Float)->Unit) {

        }
        fun startUpdating(startcb:()->Unit) {

        }
    }
    data class SlideImageState(var scale:Float = 0f,var dir:Float = 0f,var prevScale:Float = 0f) {
        fun update(stopcb:(Float)->Unit) {
            scale += 0.1f*dir
            if(Math.abs(scale - prevScale) > 1) {
                scale = prevScale + dir
                dir = 0f
                prevScale = scale
                stopcb(scale)
            }
        }
        fun startUpdating(startcb:()->Unit) {
            if(dir == 0f) {
                dir = 1-2*scale
                startcb()
            }
        }
    }
}