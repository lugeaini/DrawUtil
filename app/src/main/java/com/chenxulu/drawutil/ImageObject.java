package com.chenxulu.drawutil;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;

import java.util.ArrayList;
import java.util.List;

public class ImageObject {
    private final int resizeBoxSize = 50;

    protected PointF mPoint = new PointF();
    protected float mRotation;

    private float centerDegrees;
    private float centerDistance;

    protected float mScale = 1.0f;

    protected boolean isTextObject;

    protected Bitmap srcBitmap;
    protected Bitmap rotateBm;
    protected Bitmap deleteBm;

    Paint paint = new Paint();
    private Canvas canvas;

    public ImageObject() {
    }

    /**
     * 构造方法
     *
     * @param srcBm    源图片
     * @param x        图片初始化x坐标
     * @param y        图片初始化y坐标
     * @param rotateBm 旋转图片
     * @param deleteBm 删除图片
     */
    public ImageObject(Bitmap srcBm, int x, int y, Bitmap rotateBm, Bitmap deleteBm) {
        this.srcBitmap = Bitmap.createBitmap(srcBm.getWidth(), srcBm.getHeight(), Config.ARGB_8888);

        this.canvas = new Canvas(srcBitmap);
        this.canvas.drawBitmap(srcBm, 0, 0, paint);

        this.rotateBm = rotateBm;
        this.deleteBm = deleteBm;

        mPoint.x = x;
        mPoint.y = y;

        paint.setColor(Color.WHITE);
        paint.setAntiAlias(true);// 去掉边缘锯齿
        paint.setStrokeWidth(2);// 设置线宽
    }

    public void draw(Canvas canvas, float scale, RectF rectF) {
        int sc = canvas.save();
        try {
            canvas.translate(mPoint.x, mPoint.y);
            canvas.rotate(mRotation);
            canvas.scale(mScale * scale, mScale * scale);
            canvas.drawBitmap(srcBitmap, -getWidth() / 2, -getHeight() / 2, paint);
        } catch (Exception e) {
            e.printStackTrace();
        }
        canvas.restoreToCount(sc);
    }

    /**
     * 计算中心点的坐标
     */
    protected void setCenter() {
        float delX = getWidth() * mScale / 2;
        float delY = getHeight() * mScale / 2;
        centerDistance = (float) Math.sqrt((delX * delX + delY * delY));
        centerDegrees = (float) Math.toDegrees(Math.atan(delY / delX));
    }

    /**
     * 获取矩形图片左上角的点
     *
     * @return
     */
    protected PointF getPointLeftTop() {
        PointF pointF = getPointByRotation(centerDegrees - 180);
        return pointF;
    }

    /**
     * 获取矩形图片右上角的点
     *
     * @return
     */
    protected PointF getPointRightTop() {
        PointF pointF = getPointByRotation(-centerDegrees);
        return pointF;
    }

    /**
     * 获取矩形图片右下角的点
     *
     * @return
     */
    protected PointF getPointRightBottom() {
        PointF pointF = getPointByRotation(centerDegrees);
        return pointF;
    }

    /**
     * 获取矩形图片左下角的点
     *
     * @return
     */
    protected PointF getPointLeftBottom() {
        PointF pointF = getPointByRotation(-centerDegrees + 180);
        return pointF;
    }

    /**
     * 根据旋转角度获取定点坐标
     *
     * @param rotation
     * @return
     */
    private PointF getPointByRotation(float rotation) {
        PointF pointF = new PointF();
        double rot = (mRotation + rotation) * Math.PI / 180;
        pointF.x = mPoint.x + (float) (centerDistance * Math.cos(rot));
        pointF.y = mPoint.y + (float) (centerDistance * Math.sin(rot));
        return pointF;
    }

    /**
     * 根据旋转角度获取在画布中的定点坐标
     *
     * @param rotation
     * @return
     */
    public PointF getPointByRotationInCanvas(float rotation) {
        PointF pointF = new PointF();
        double rot = (mRotation + rotation) * Math.PI / 180;
        pointF.x = (float) (centerDistance * Math.cos(rot));
        pointF.y = (float) (centerDistance * Math.sin(rot));
        return pointF;
    }

    public void setScale(float Scale) {
        if (getWidth() * Scale >= resizeBoxSize / 2 && getHeight() * Scale >= resizeBoxSize / 2) {
            this.mScale = Scale;
            setCenter();
        }
    }

    /**
     * 获取显示图片的宽
     *
     * @return
     */
    public int getWidth() {
        return srcBitmap.getWidth();
    }

    /**
     * 获取显示图片的高
     *
     * @return
     */
    public int getHeight() {
        return srcBitmap.getHeight();
    }

    /**
     * 判断点是否在多边形内
     *
     * @param pointX
     * @param pointY
     * @return
     */
    public boolean contains(float pointX, float pointY) {
        List<PointF> listPoints = new ArrayList<>();
        listPoints.add(getPointLeftTop());
        listPoints.add(getPointRightTop());
        listPoints.add(getPointRightBottom());
        listPoints.add(getPointLeftBottom());
        return GraphicsUtil.contains(listPoints, new PointF(pointX, pointY));
    }

    /**
     * 判断点击是否在边角按钮上
     *
     * @param x    触点的横坐标
     * @param y    触点得纵坐标
     * @param type 四角的位置
     * @return
     */
    public boolean pointOnCorner(float x, float y, int type) {
        PointF point = null;
        if (GraphicsUtil.LEFT_TOP == type) {
            point = getPointLeftTop();
        } else if (GraphicsUtil.LEFT_BOTTOM == type) {
            point = getPointLeftBottom();
        } else if (GraphicsUtil.RIGHT_TOP == type) {
            point = getPointRightTop();
        } else if (GraphicsUtil.RIGHT_BOTTOM == type) {
            point = getPointRightBottom();
        }

        float delX = x - (point.x + rotateBm.getWidth() / 2);
        float delY = y - (point.y + rotateBm.getHeight() / 2);
        float diff = (float) Math.sqrt((delX * delX + delY * delY));
        if (Math.abs(diff) <= resizeBoxSize) {
            return true;
        }
        return false;
    }

    public PointF getPoint() {
        return mPoint;
    }

    public void setPoint(PointF point) {
        mPoint.set(point.x, point.y);
    }

    public float getRotation() {
        return mRotation;
    }

    public void setRotation(float Rotation) {
        this.mRotation = Rotation;
    }

    public float getScale() {
        return mScale;
    }

    public boolean isTextObject() {
        return isTextObject;
    }

}
