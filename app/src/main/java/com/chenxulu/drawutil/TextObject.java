package com.chenxulu.drawutil;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;

/**
 * @author jarlen
 */
public class TextObject extends ImageObject {
    private static final int textSize = 90;

    private int color = Color.WHITE;
    private String text;

    Paint mPaint = new Paint();

    /**
     * 构造方法
     *
     * @param text     输入的文字
     * @param x        位置x坐标
     * @param y        位置y坐标
     * @param rotateBm 旋转按钮的图片
     * @param deleteBm 删除按钮的图片
     */
    public TextObject(String text, int x, int y, Bitmap rotateBm, Bitmap deleteBm) {
        super();
        this.text = text;
        this.mPoint.x = x;
        this.mPoint.y = y;
        this.rotateBm = rotateBm;
        this.deleteBm = deleteBm;
        commit();
    }

    /**
     * 绘画出字体
     */
    private void commit() {
        initPaint();

        //计算输入字体的长宽
        String lines[] = text.split("\n");

        int textWidth = 0;
        int textHeight = lines.length * textSize + 20;
        for (String str : lines) {
            int temp = (int) mPaint.measureText(str);
            if (temp > textWidth)
                textWidth = temp;
        }
        textWidth += 40;

        int padding = deleteBm.getWidth() / 2;
        srcBitmap = Bitmap.createBitmap(textWidth + padding * 2, textHeight + padding * 2, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(srcBitmap);

        drawBackground(canvas, new RectF(padding, padding, padding + textWidth, padding + textHeight));
        canvas.drawBitmap(deleteBm, 0, 0, mPaint);
        canvas.drawBitmap(rotateBm, textWidth, textHeight, mPaint);

        for (int i = 0; i < lines.length; i++) {
            float baseline = (i + 1) * textSize - mPaint.getFontMetrics().descent + padding + 10;
            canvas.drawText(lines[i], 20 + padding, baseline, mPaint);
        }

        setCenter();
    }

    private void initPaint() {
        mPaint.setAntiAlias(true);
        mPaint.setTextSize(textSize);
        mPaint.setColor(color);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setDither(true);
        mPaint.setFlags(Paint.SUBPIXEL_TEXT_FLAG);
    }

    /**
     * 背景
     *
     * @param canvas
     */
    private void drawBackground(Canvas canvas, RectF rectF) {
        Paint paint = new Paint();
        paint.setARGB(50, 0, 0, 0);

        canvas.drawRect(rectF, paint);

        paint.setARGB(255, 255, 255, 255);
        paint.setStrokeWidth(2);
        paint.setStyle(Paint.Style.STROKE);

        Path path = new Path();
        path.moveTo(rectF.left, rectF.top);
        path.lineTo(rectF.right, rectF.top);
        path.lineTo(rectF.right, rectF.bottom);
        path.lineTo(rectF.left, rectF.bottom);
        path.close();

        canvas.drawPath(path, paint);
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

}
