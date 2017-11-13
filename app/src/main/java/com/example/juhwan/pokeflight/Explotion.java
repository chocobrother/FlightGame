package com.example.juhwan.pokeflight;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import java.lang.reflect.GenericArrayType;

/**
 * Created by Juhwan on 2017-05-17.
 */

public class Explotion {
    private Bitmap explotionImage;
    private Paint paint;

    public int exSwitch = 0;

    private int displayX;
    public int count;


    Rect[] srcs = new Rect[10];
    Rect dst;


    Explotion(Bitmap explotionImage, int X, int Y, int count, int displayX){
        this.explotionImage = explotionImage;
        this.displayX = displayX;
        paint = new Paint();
        paint.setFilterBitmap(true);

        int w = explotionImage.getWidth();
        int h = explotionImage.getHeight();


        this.count = count;

        for(int i = 0; i < 10; i++){
            srcs[i] = new Rect(i * w, 0, w + i * w, h);
        }

        dst = new Rect(X - displayX/20, Y - displayX/20, X + displayX/20, Y + displayX/20);
    }

    public void drawMove(Canvas c){
        c.drawBitmap(explotionImage, srcs[exSwitch], dst, paint);
    }

}