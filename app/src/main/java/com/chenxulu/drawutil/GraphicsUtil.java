package com.chenxulu.drawutil;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.PorterDuff;
import android.graphics.RectF;
import android.util.Log;
import android.view.View;

import java.util.List;

/**
 * Created by xulu on 2017/2/4.
 */

public class GraphicsUtil {
    public static final int LEFT_TOP = 1;
    public static final int RIGHT_TOP = 2;
    public static final int RIGHT_BOTTOM = 3;
    public static final int LEFT_BOTTOM = 4;

    /**
     * 判断多边形是否包含点
     *
     * @return true
     */
    public static boolean contains(List<PointF> pointFList, PointF pointF) {
        int mPolySize = pointFList.size();

        float[] mPolyX = new float[mPolySize];
        float[] mPolyY = new float[mPolySize];

        for (int i = 0; i < mPolySize; i++) {
            mPolyX[i] = pointFList.get(i).x;
            mPolyY[i] = pointFList.get(i).y;
        }

        Log.d("lasso", "lasso size:" + mPolySize);

        boolean result = false;
        float x = pointF.x;
        float y = pointF.y;
        for (int i = 0, j = mPolySize - 1; i < mPolySize; j = i++) {
            if ((mPolyY[i] < y && mPolyY[j] >= y) || (mPolyY[j] < y && mPolyY[i] >= y)) {
                if (mPolyX[i] + (y - mPolyY[i]) / (mPolyY[j] - mPolyY[i]) * (mPolyX[j] - mPolyX[i]) < x) {
                    result = !result;
                }
            }
        }
        return result;
    }

    /**
     * 绘制一组路径
     *
     * @param view
     * @param canvas
     * @param list
     * @param rect
     */
    public static void drawPathList(View view, Canvas canvas, List<MyPath> list, RectF rect) {
        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);

        float scale = (rect.right - rect.left) / view.getWidth();
        for (int i = 0; i < list.size(); i++) {
            MyPath myPath = list.get(i);
            List<PointF> tList = myPath.getList();
            Path path = new Path();
            for (int j = 0; j < tList.size(); j++) {
                PointF pointF = tList.get(j);
                float x = pointF.x * scale + rect.left;
                float y = pointF.y * scale + rect.top;
                if (j == 0)
                    path.moveTo(x, y);
                else
                    path.lineTo(x, y);
            }
            canvas.drawPath(path, myPath.getPaint());
        }
        view.invalidate();
    }
}
