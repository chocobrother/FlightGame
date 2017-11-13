package com.example.juhwan.pokeflight;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.Log;

/**
 * Created by Juhwan on 2017-05-17.
 */

public class CharaBeam {

    private int beamCenterX;
    private int beamCenterY;

    private Point p;
    private Paint paint;
    private Bitmap imgGun;
    public boolean CharaBeamFlag = false;
    public boolean isDead = true;

    private int charaBeamSpeed;
    private int radius;
    private int width;
    private int height;
    private Context mContext;
    CharaBeam(Context mContext, int displayX, int scale){
        p = new Point();
        paint = new Paint();
        paint.setFilterBitmap(true);
        radius = displayX / 80;
        charaBeamSpeed = 5 * scale;

        this.mContext = mContext;
        ResetBeam();
        this.width = imgGun.getWidth();
        this.height = imgGun.getHeight();
    }

    public void ResetBeam() {
        setBeamImg(R.drawable.pokeball);
    }

    public void setBeamImg(int Res){
        imgGun =  BitmapFactory.decodeResource(mContext.getResources(), Res);
    }

    public int getHeight(){
        return height;
    }
    public void init(int w, int h){
        p.x = w;
        p.y = h;
    }

public void move(){
    p.y -= charaBeamSpeed;
    beamCenterX = p.x + width / 2;
    beamCenterY = p.y + height / 2;

    if(p.y < 0){
        isDead = true;
        CharaBeamFlag = false;
    }
}
    public void drawMove(Canvas c, int w, int h){
        if(isDead){
            init(w, h);
        }
        Log.d("Pika",imgGun + "");
        c.drawBitmap(imgGun,p.x,p.y,paint);
    }
    public int getCenterX(){
        return beamCenterX;
    }

    public int getCenterY(){
        return beamCenterY;
    }

    public int getRadius(){
        return radius;
    }


}
