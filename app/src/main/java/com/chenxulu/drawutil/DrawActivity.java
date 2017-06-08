package com.chenxulu.drawutil;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.util.Random;

public class DrawActivity extends AppCompatActivity {
    private MyDrawView myDrawView;
    private boolean canDraw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw);

        myDrawView = (MyDrawView) findViewById(R.id.image_view);
    }

    public void changeColor(View view) {
        int[] colors = {Color.BLACK, Color.BLUE, Color.CYAN, Color.GRAY, Color.RED, Color.GREEN};
        myDrawView.setPaintColor(colors[new Random().nextInt(colors.length - 1)]);
    }

    public void undo(View view) {
        myDrawView.undo();
    }

    public void addText(View view) {
        myDrawView.addText("aaaaaaaaaaaa\naaaaaaaa");
    }

    public void canDraw(View view) {
        myDrawView.setCanDraw(!canDraw);
        canDraw = !canDraw;
    }

}
