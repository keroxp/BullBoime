package me.keroxp.bullboime.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import me.keroxp.bullboime.graphics.asRect
import me.keroxp.bullboime.graphics.extend
import me.keroxp.bullboime.graphics.pad
import me.keroxp.bullboime.graphics.reset
import tv.loilo.LoiLog
import java.util.*

class CanvasView(context: Context?, attrs: AttributeSet?)
        : View(context, attrs)
        , View.OnTouchListener {

    private val debugPaint = Paint()
    private val paint = Paint()
    private val path = Path()
    private var canvas: Canvas? = null
    private var bitmap: Bitmap? = null
    private var startX: Float = 0f
    private var startY: Float = 0f
    private val points = ArrayList<PointF>()

    private val tmpRect = Rect()
    private var dirty = false
    private val dirtyRect = RectF()

    init {
        debugPaint.color = Color.RED
        debugPaint.style = Paint.Style.STROKE

        paint.color = Color.BLACK
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 2f

        setOnTouchListener(this)
    }

    private fun extendDirtyRect(x: Float, y: Float) {
        if (dirty) {
            dirtyRect.extend(x,y)
        } else {
            dirtyRect.reset(x,y)
            dirty = true
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        bitmap = Bitmap.createBitmap(w,h,Bitmap.Config.ARGB_8888)
        canvas = Canvas(bitmap)
    }

    override fun onDraw(drawCanvas: Canvas?) {
        if (isInEditMode) return
        if (drawCanvas == null) return
        if (canvas == null || bitmap == null) return
        val rect = dirtyRect.asRect(tmpRect)
        drawCanvas.save()
        drawCanvas.drawBitmap(bitmap,0f,0f,null)
        drawCanvas.restore()

        drawCanvas.save()
        drawCanvas.clipRect(rect)
        drawCanvas.drawRect(rect,debugPaint)
        drawCanvas.restore()
    }

    private fun draw() {
        if (!dirty || dirtyRect.width() == 0f || dirtyRect.height() == 0f) return

        canvas?.save()
        canvas?.drawPath(path,paint)
        canvas?.restore()
        invalidate()

        dirty = false
    }

    public fun clear() {
        canvas?.drawColor(0, PorterDuff.Mode.CLEAR);
        dirtyRect.set(0f,0f,measuredWidth.toFloat(),measuredHeight.toFloat())
        invalidate()
    }

    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        if (event == null) return false
        extendDirtyRect(event.x,event.y)
        when(event.action) {
            MotionEvent.ACTION_DOWN -> {
                startX = event.x
                startY = event.y
                path.moveTo(startX,startY)
            }
            MotionEvent.ACTION_MOVE -> {
                val lastIndex = event.historySize-1
                val prevX = if (lastIndex < 0) startX else event.getHistoricalX(lastIndex)
                val prevY = if (lastIndex < 0) startY else event.getHistoricalY(lastIndex)
//                path.moveTo(prevX,prevY)
                path.quadTo(event.x,event.y,(prevX+event.x)/2,(prevY+event.y)/2)
//                path.lineTo(event.x,event.y)
                extendDirtyRect(prevX,prevY)
                draw()
            }
            MotionEvent.ACTION_UP -> {
                draw()
                path.reset()
                dirty = false
            }
        }
        return true
    }
}