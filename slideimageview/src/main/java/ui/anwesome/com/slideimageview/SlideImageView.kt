package ui.anwesome.com.slideimageview

/**
 * Created by anweshmishra on 26/01/18.
 */
import android.app.Activity
import android.view.*
import android.content.*
import android.graphics.*
import java.util.concurrent.ConcurrentLinkedQueue

class SlideImageView(ctx:Context,var bitmap:Bitmap):View(ctx) {
    val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    val renderer = SlideImageRenderer(this)
    var slideImageListener:SlideImageListener?=null
    fun addSlideImageListener(onSlideListener:()->Unit,offSlideListener:()->Unit) {
        slideImageListener = SlideImageListener(onSlideListener,offSlideListener)
    }
    override fun onDraw(canvas:Canvas) {
        renderer?.render(canvas,paint)
    }
    override fun onTouchEvent(event:MotionEvent):Boolean {
        when(event.action) {
            MotionEvent.ACTION_DOWN -> {
                renderer.handleTap()
            }
        }
        return true
    }
    data class SlideImage(var i:Int) {
        fun draw(canvas:Canvas,paint:Paint,bitmap:Bitmap,w:Float,h:Float,n:Int,scale:Float) {
            var sy = i*(h/n)
            var k = i%2
            var ki = (i+1)%2
            canvas.save()
            val path = Path()
            path.addRect(RectF(k*w*(1-scale),sy,w*(k+ki*scale),sy+h/n),Path.Direction.CW)
            canvas.clipPath(path)
            canvas.drawBitmap(bitmap,Rect(0,0,bitmap.width,bitmap.height),RectF(0f,0f,w,h),paint)
            canvas.restore()
        }
    }
    data class SlideImageContainer(var bitmap:Bitmap,var w:Float,var h:Float,var n:Int = 10) {
        val state = SlideImageState()
        val slides:ConcurrentLinkedQueue<SlideImage> = ConcurrentLinkedQueue<SlideImage>()
        init {
            bitmap = Bitmap.createScaledBitmap(bitmap,w.toInt(),h.toInt(),true)
            for(i in 0..n-1) {
                slides.add(SlideImage(i))
            }
        }
        fun draw(canvas:Canvas,paint:Paint) {
            slides.forEach {
                it.draw(canvas,paint,bitmap,w,h,n,state.scale)
            }
        }
        fun update(stopcb:(Float)->Unit) {
            state.update(stopcb)
        }
        fun startUpdating(startcb:()->Unit) {
            state.startUpdating(startcb)
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
    data class SlideImageAnimator(var view:SlideImageView,var animated:Boolean = false) {
        fun animate(updatecb:()->Unit) {
            if(animated) {
                updatecb()
                try {
                    Thread.sleep(70)
                    view.invalidate()
                }
                catch(ex:Exception) {

                }
            }
        }
        fun start() {
            if(!animated) {
                animated = true
                view.postInvalidate()
            }
        }
        fun stop() {
            if(animated) {
                animated = false
            }
        }
    }
    data class SlideImageRenderer(var view:SlideImageView,var time:Int = 0) {
        val animator = SlideImageAnimator(view)
        var container:SlideImageContainer?=null
        fun render(canvas:Canvas,paint:Paint) {
            if(time == 0) {
                val w = canvas.width.toFloat()
                val h = canvas.height.toFloat()
                container = SlideImageContainer(view.bitmap,w,h)
            }
            canvas.drawColor(Color.parseColor("#212121"))
            container?.draw(canvas,paint)
            time++
            animator.animate {
                container?.update {
                    animator.stop()
                    when(it) {
                        0f -> view.slideImageListener?.onSlideListener?.invoke()
                        1f -> view.slideImageListener?.offSlideListener?.invoke()
                    }
                }
            }
        }
        fun handleTap() {
            container?.startUpdating {
                animator.start()
            }
        }
    }
    companion object {
        fun create(activity:Activity,bitmap:Bitmap):SlideImageView {
            val view = SlideImageView(activity,bitmap)
            activity.setContentView(view)
            return view
        }
    }
    data class SlideImageListener(var onSlideListener:()->Unit,var offSlideListener:()->Unit)
}