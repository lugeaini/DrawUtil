package com.chenxulu.drawutil;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

/**
 * Created by xulu on 2017/2/6.
 */

public class SelectColorView extends LinearLayout implements View.OnClickListener {
    private static final int[] colors = {Color.RED, Color.BLUE, Color.YELLOW, Color.GREEN, Color.GRAY, Color.WHITE};
    private SelectColorListener mListener;

    public SelectColorView(Context context) {
        super(context);
        init();
    }

    public SelectColorView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        setOrientation(LinearLayout.HORIZONTAL);
        setPadding(20, 0, 20, 0);
        for (int i = 0; i < colors.length; i++) {
            FrameLayout layout = new FrameLayout(getContext());
            LayoutParams layoutParams = new LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT);
            layoutParams.weight = 1;
            layout.setTag(i + "");
            addView(layout, layoutParams);
            setOnClickListener(this);

            View view = new View(getContext());
            FrameLayout.LayoutParams viewParams = new FrameLayout.LayoutParams(40, 40);
            viewParams.gravity = Gravity.CENTER;
            view.setBackgroundColor(colors[i]);
            layout.addView(view, viewParams);
        }
    }

    @Override
    public void onClick(View v) {
        int i = Integer.valueOf((String) v.getTag());
        mListener.onSelect(colors[i]);
    }

    public void setmListener(SelectColorListener mListener) {
        this.mListener = mListener;
    }

    public interface SelectColorListener {
        void onSelect(int color);
    }

}
