package org.thunderdog.challegram;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import org.thunderdog.challegram.tool.Screen;
import org.thunderdog.challegram.theme.Theme;

public class FloatingGlassUi {
    private static final Paint glassFillPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private static final Paint glassStrokePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private static final RectF tempRect = new RectF();

    // 是否开启全局悬浮毛玻璃 UI（可在自定义设置中随时开关）
    public static boolean enableFloatingGlassUi = true;

    static {
        glassStrokePaint.setStyle(Paint.Style.STROKE);
        glassStrokePaint.setStrokeWidth(Screen.dp(1f));
    }

    /**
     * 绘制截图同款的悬浮毛玻璃胶囊/圆形背景
     */
    public static void drawGlassCapsule(Canvas c, float left, float top, float right, float bottom) {
        boolean isDark = Theme.isDark();
        // 日间模式使用 82% 透明度的奶油白，夜间模式使用 75% 透明度的深空灰
        glassFillPaint.setColor(isDark ? 0xBF1E1E24 : 0xD1FFFFFF);
        // 边缘 1px 的玻璃高光描边，增强精致感
        glassStrokePaint.setColor(isDark ? 0x33FFFFFF : 0x66FFFFFF);

        tempRect.set(left, top, right, bottom);
        float radius = (bottom - top) / 2f; // 半径设为高度的一半，形成完美胶囊/正圆

        c.drawRoundRect(tempRect, radius, radius, glassFillPaint);
        c.drawRoundRect(tempRect, radius, radius, glassStrokePaint);
    }

    /**
     * 绘制截图同款三段式悬浮顶栏（左圆返回键 + 中间标题胶囊 + 右圆菜单键）
     */
    public static void drawFloatingHeader(Canvas c, int viewWidth, int statusBarHeight, int headerHeight) {
        float top = statusBarHeight + Screen.dp(6f);
        float bottom = statusBarHeight + headerHeight - Screen.dp(6f);
        float pillHeight = bottom - top;

        // 1. 左侧圆形返回键背景
        float leftCircleLeft = Screen.dp(10f);
        float leftCircleRight = leftCircleLeft + pillHeight;
        drawGlassCapsule(c, leftCircleLeft, top, leftCircleRight, bottom);

        // 2. 右侧圆形菜单键背景
        float rightCircleRight = viewWidth - Screen.dp(10f);
        float rightCircleLeft = rightCircleRight - pillHeight;
        drawGlassCapsule(c, rightCircleLeft, top, rightCircleRight, bottom);

        // 3. 中间群组信息大胶囊背景
        float centerPillLeft = leftCircleRight + Screen.dp(8f);
        float centerPillRight = rightCircleLeft - Screen.dp(8f);
        drawGlassCapsule(c, centerPillLeft, top, centerPillRight, bottom);
    }
}
