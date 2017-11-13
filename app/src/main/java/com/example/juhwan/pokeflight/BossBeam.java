package com.example.juhwan.pokeflight;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;

/**
 * Created by Juhwan on 2017-05-24.
 */

public class BossBeam {


    private Point p;
    private Paint paint;
    private int beamCenterX;
    private int beamCenterY;
    private double angle;
    private int scale, radius;
    private int moveSpeed;
    public boolean deathFlag = false;
    private Bitmap imgBosBeam;


    BossBeam(Context context, int w, int h, int charaX, int charaY, int displayX, int scale, int moveSpeed){
        p = new Point();
        p.x = w;
        p.y = h;

        paint = new Paint();
        paint.setFilterBitmap(true);
        paint.setColor(Color.rgb(196,15,24));

        angle = Math.atan2(charaY - h, charaX - w);
        this.scale = scale;
        radius = displayX / 80;
        this.moveSpeed = moveSpeed;

        imgBosBeam = BitmapFactory.decodeResource(context.getResources(),R.drawable.lightning);


    }

    public void move(){
        p.x += Math.cos(angle) * moveSpeed * scale;
        p.y += Math.sin(angle) * moveSpeed * scale;
        beamCenterX = p.x + radius / 2;
        beamCenterY = p.y + radius / 2;
    }

    public void drawMove(Canvas c){
        c.drawBitmap(imgBosBeam,p.x,p.y,paint);
    }

    public int getX(){
        return p.x;
    }

    public int getY(){
        return p.y;
    }

    public int getCenterX(){
        return beamCenterX;
    }

    public int getCenterY(){
        return beamCenterY;
    }

}