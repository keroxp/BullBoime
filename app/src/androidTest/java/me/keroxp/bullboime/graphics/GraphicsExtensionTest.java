package me.keroxp.bullboime.graphics;

import android.graphics.RectF;
import android.test.AndroidTestCase;
import me.keroxp.bullboime.graphics.GraphicsExtensionKt;

public class GraphicsExtensionTest extends AndroidTestCase {
    public void testExtend() {
        RectF rect = new RectF();
        GraphicsExtensionKt.extend(rect, -1f, -1f);
        assertEquals(-1f, rect.left);
        assertEquals(-1f, rect.top);
    }
    public void testExtend2() {
        RectF rect = new RectF();
        GraphicsExtensionKt.extend(rect,10f,10f,20f,20f);
        assertEquals(0f,rect.left);
        assertEquals(0f,rect.top);
        assertEquals(30f,rect.right);
        assertEquals(30f,rect.bottom);
    }
    public void testPad() {
        RectF rect = new RectF();
        GraphicsExtensionKt.pad(rect,10f);
        assertEquals(-10f, rect.left);
        assertEquals(-10f, rect.top);
        assertEquals(10f, rect.right);
        assertEquals(10f,rect.bottom);
    }
}
