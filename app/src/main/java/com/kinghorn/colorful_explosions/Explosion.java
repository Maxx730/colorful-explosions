package com.kinghorn.colorful_explosions;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

import java.util.ArrayList;
import java.util.Random;

public class Explosion {
    private int x, y, rows = 8, cols = 10, screenWidth, screenHeight;
    private Paint debugPaint;
    private Bitmap source;
    private BitmapFactory.Options options = new BitmapFactory.Options();
    private ArrayList<Bitmap> frames = new ArrayList<>();

    public Explosion(Context con, int screenWidth, int screenHeight, int resource) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;

        this.randomPoint();
        debugPaint = new Paint();
        debugPaint.setStyle(Paint.Style.FILL);
        debugPaint.setColor(Color.RED);

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
        int row = frame / cols;
        int col = frame % cols;

        this.print("ROW: " + String.valueOf(row) + " COL: " + String.valueOf(col));

        Bitmap b = Bitmap.createBitmap(this.source, row * (this.source.getHeight() / rows), col * (this.source.getWidth() / cols), this.source.getWidth() / cols, this.source.getHeight() / rows);
        can.drawBitmap(b, this.x, this.y, null);
    }

    private void print(String message) {
        Log.d("COLORFUL EXPLOSIONS :: ", message);
    }

    //Deprecated to save potential memory.
    private void getFrames(Bitmap source) {
        this.print("SOURCE SIZE ::: " + String.valueOf(source.getHeight()) + " X " + String.valueOf(source.getWidth()));
        this.print("ROWS + COLS ::: " + String.valueOf(rows) + " X " + String.valueOf(cols));
        this.print("ROW HEIGHT + COL HEIGHT ::: " + String.valueOf(source.getHeight() / rows) + " X " + String.valueOf(source.getWidth() / cols));

        for (int c = 0; c < cols; c++) {
            int col_start = c * (source.getWidth() / cols);
            int col_width = source.getWidth() / cols;
            for (int r = 0;r < rows;r++) {
                int row_start = r * (source.getHeight() / rows);
                int row_height = source.getHeight() / rows;
                Bitmap b = Bitmap.createBitmap(source, col_start, row_start, col_width, row_height);
                frames.add(b);
            }
        }

        this.print("FRAME AMOUNT ::: " + String.valueOf(frames.size()));
    }

    private void randomPoint() {
        this.x = this.getRandomPoint(screenWidth / 2);
        this.y = this.getRandomPoint(screenHeight);
    }
}
