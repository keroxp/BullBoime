package me.keroxp.bullboime.view

import android.content.Context
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.view.ViewGroup
import kotlinx.android.synthetic.main.layout_keyboard_view.view.*
import me.keroxp.bullboime.R
import tv.loilo.LoiLog

class KeyboardView(context: Context?, attrs: AttributeSet?) : ViewGroup(context, attrs) {

    override fun onFinishInflate() {
        LoiLog.d("called")
        submitButton.setOnClickListener {
            LoiLog.d("tapped")
        }
        clearButton.setOnClickListener {
            canvasView.clear()
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val screenWidth = MeasureSpec.getSize(widthMeasureSpec)
        val screenHeight = MeasureSpec.getSize(heightMeasureSpec)
        val width = screenWidth
        val height = resources.getDimension(R.dimen.keyboard_total_height).toInt()
        LoiLog.d("sw: $screenWidth, sh: $screenHeight, w: $width, h:$height, canvash: ${(height*.8).toInt()}, toolbarh: ${(height*.2).toInt()}")
        for (i in 0..childCount-1) {
            val child = getChildAt(i)
            val childHeight = when(child.id) {
                R.id.canvasView -> (height*.8).toInt()
                R.id.toolBarLayout -> (height*.2).toInt()
                else -> throw IllegalStateException("unhandled child view}")
            }
            child.measure(
                    MeasureSpec.makeMeasureSpec(width,MeasureSpec.EXACTLY),
                    MeasureSpec.makeMeasureSpec(childHeight,MeasureSpec.EXACTLY)
            )
        }
        setMeasuredDimension(
                MeasureSpec.makeMeasureSpec(width,MeasureSpec.EXACTLY),
                MeasureSpec.makeMeasureSpec(height,MeasureSpec.AT_MOST)
        )
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        LoiLog.d("l: $l, t: $t, r: $r, b:$b")
        var y = 0
        for (i in 0..childCount-1) {
            val child = getChildAt(i)
            when(child.id) {
                R.id.canvasView -> {
                    child.layout(0,0,child.measuredWidth,y+child.measuredHeight)
                }
                R.id.toolBarLayout -> {
                    child.layout(0,y,child.measuredWidth,y+child.measuredHeight)
                }
            }
            y += child.measuredHeight
        }
    }
}