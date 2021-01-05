package com.kinghorn.colorful_explosions;

import android.app.WallpaperManager;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.os.Handler;
import android.service.wallpaper.WallpaperService;
import android.util.DisplayMetrics;
import android.view.SurfaceHolder;

import androidx.preference.PreferenceManager;

import java.util.ArrayList;

public class WallService extends WallpaperService {
    private int WallWidth, WallHeight;

    @Override
    public void onCreate() {
        super.onCreate();
        //Get Wallpaper Width and Height
        WallpaperManager manager = WallpaperManager.getInstance(getApplicationContext());
        WallWidth = manager.getDesiredMinimumWidth();
        WallHeight = manager.getDesiredMinimumHeight();
    }

    @Override
    public Engine onCreateEngine() {
        return new WallEngine();
    }

    class WallEngine extends Engine {
        private int framerate = 60, frame = 0;
        private final Handler handler = new Handler();
        private int PAN_SPEED, SHADE_AMOUNT;
        private ArrayList<Explosion> exps = new ArrayList<>();

        private final Runnable WallRunnable = new Runnable() {
            @Override
            public void run() {
                drawFrame();
            }
        };

        public WallEngine() {
            //Load preferences and elements here
            SharedPreferences prefs  = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            exps.add(new Explosion(getApplicationContext(), WallWidth, WallHeight, R.drawable.round_a));
            exps.add(new Explosion(getApplicationContext(), WallWidth, WallHeight, R.drawable.round_b));
            exps.add(new Explosion(getApplicationContext(), WallWidth, WallHeight, R.drawable.round_c));
            exps.add(new Explosion(getApplicationContext(), WallWidth, WallHeight, R.drawable.round_d));
        }

        @Override
        public void onCreate(SurfaceHolder surfaceHolder) {
            super.onCreate(surfaceHolder);
        }

        @Override
        public void onSurfaceCreated(SurfaceHolder holder) {
            super.onSurfaceCreated(holder);
        }

        @Override
        public void onSurfaceDestroyed(SurfaceHolder holder) {
            super.onSurfaceDestroyed(holder);
            handler.removeCallbacks(WallRunnable);
        }

        @Override
        public void onSurfaceChanged(SurfaceHolder holder,
                                     int format, int width, int height) {
            super.onSurfaceChanged(holder, format, width, height);

            drawFrame();
        }

        void drawFrame() {
            final SurfaceHolder holder = getSurfaceHolder();
            Matrix mat = new Matrix();
            Canvas canvas = null;
            Paint p = new Paint();
            Paint pixelPerfect = new Paint(Paint.FILTER_BITMAP_FLAG);
            p.setStyle(Paint.Style.FILL);
            pixelPerfect.setAntiAlias(false);
            pixelPerfect.setDither(false);
            pixelPerfect.setFilterBitmap(false);

            try {
                canvas = holder.lockCanvas();

                if(canvas != null) {
                    //Erase what was on the screen
                    canvas.drawRect(0, 0, WallWidth, WallHeight, p);

                    for(Explosion exp : exps) {
                        //exp.drawCircle(canvas);
                        exp.drawFrame(canvas, this.frame);
                    }
                }
            } finally {
                if (canvas != null) {
                    holder.unlockCanvasAndPost(canvas);
                }
            }

            handler.removeCallbacks(WallRunnable);
            handler.postDelayed(WallRunnable, 1000 / framerate);
        }

        private void drawShade(Canvas can) {
            if (can != null) {
                Paint shadeP = new Paint();
                shadeP.setColor(Color.BLACK);
                shadeP.setAlpha(SHADE_AMOUNT);
                can.drawRect(0,0, WallWidth, WallHeight, shadeP);
            }
        }
    }
}