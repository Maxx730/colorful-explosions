package com.kinghorn.colorful_explosions;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;

import java.util.ArrayList;
import java.util.Random;

public class Explosion {
    private int x, y, cols, rows, screenWidth, screenHeight, frames;
    public int frame = 0;
    private Paint debugPaint;
    private Bitmap source;
    private BitmapFactory.Options options = new BitmapFactory.Options();

    public Explosion(Context con, int screenWidth, int screenHeight, int resource, int rows, int cols) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.rows = rows;
        this.cols = cols;
        this.frames = rows * cols;

        this.randomPoint();
        debugPaint = new Paint();
        debugPaint.setStyle(Paint.Style.FILL);
        debugPaint.setColor(Color.RED);
        debugPaint.setTextSize(48);

        this.options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        source = BitmapFactory.decodeResource(con.getResources(), resource, this.options);
    }

    private int getRandomPoint(int max) {
        return new Random().nextInt(max - 0);
    }

    public void drawCircle (Canvas can) {
        can.drawCircle(this.x, this.y, 20, debugPaint);
    }

    public void drawFrame(Canvas can, int frame) {
        if (can != null) {
            int col = frame % cols;
            int row = frame / cols;
            //this.print("START X ::: " + String.valueOf(row * (this.source.getWidth() / rows)));
            //this.print("START Y ::: " + String.valueOf(col * (this.source.getHeight() / cols)));
            int frameWidth = this.source.getWidth() / rows;
            int frameHeight = this.source.getHeight() / cols;
            int xStart = col * frameWidth;
            int yStart = row * frameHeight;

            can.drawBitmap(this.source, new Rect(xStart,yStart,xStart + frameWidth,yStart + frameHeight), new RectF(this.x, this.y,  this.x + frameWidth, this.y + frameHeight),null);
            //can.drawText("FRAME: " + String.valueOf(this.x), this.x + (frameWidth), this.y + (frameHeight), debugPaint);

            if (frame >= this.frames) {
                this.frame = 0;
                this.randomPoint();
            }
        } else {
            this.print("ERROR DRAWING TO CANVAS");
        }
    }

    private void print(String message) {
        Log.d("COLORFUL EXPLOSIONS :: ", message);
    }

    private void randomPoint() {
        this.x = this.getRandomPoint(screenWidth / 2);
        this.y = this.getRandomPoint(screenHeight);
    }
}
