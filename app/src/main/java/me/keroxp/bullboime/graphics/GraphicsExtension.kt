package me.keroxp.bullboime.graphics

import android.graphics.Rect
import android.graphics.RectF

public fun RectF.extend(x: Float, y: Float) {
    extend(x,y,0f,0f)
}
public fun RectF.extend(x: Float, y: Float, width: Float = 0f, height: Float = 0f) {
    if (x < left) {
        left = x
    } else if(right < x+width){
        right = x+width
    }
    if (y < top) {
        top = y
    } else if (bottom < y+height) {
        bottom = y+height
    }
}

public fun RectF.extend(rect: RectF) {
    extend(rect.left,rect.bottom,rect.width(),rect.height())
}

public fun RectF.reset(x: Float, y: Float) {
    set(x,y,x,y)
}

public fun RectF.asRect(rect: Rect = Rect()): Rect {
    rect.set(left.toInt(),top.toInt(),right.toInt(),bottom.toInt())
    return rect
}

public fun RectF.pad(v: Float): RectF {
    pad(v,v,v,v)
    return this
}

public fun RectF.pad(l: Float = 0f, t: Float = 0f, r: Float = 0f, b: Float = 0f): RectF {
    left -= l
    top -= t
    right += r
    bottom += b
    return this
}

public fun Rect.extend(x: Int, y: Int, width: Int = 0, height: Int = 0) {
    if (x < left) {
        left = x
    } else if(right < x+width){
        right = x
    }
    if (y < top) {
        top = y
    } else if (bottom < y+height) {
        bottom = y
    }
}

public fun Rect.extend(rect: Rect) {
    extend(rect.left,rect.bottom,rect.width(),rect.height())
}

public fun Rect.reset(x: Int, y: Int) {
    set(x,y,x,y)
}