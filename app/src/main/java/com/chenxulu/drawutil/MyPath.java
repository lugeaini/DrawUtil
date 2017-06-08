package com.chenxulu.drawutil;

import android.graphics.Paint;
import android.graphics.PointF;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xulu on 2017/2/2.
 */

public class MyPath {
    private List<PointF> list;
    private Paint paint;

    public MyPath() {
    }

    public MyPath(List<PointF> list, Paint paint) {
        this.list = list;
        this.paint = paint;
    }

    public List<PointF> getList() {
        if (list == null)
            return new ArrayList<>();
        return list;
    }

    public void setList(List<PointF> list) {
        this.list = list;
    }

    public Paint getPaint() {
        return paint;
    }

    public void setPaint(Paint paint) {
        this.paint = paint;
    }
}
