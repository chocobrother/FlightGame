package com.example.juhwan.pokeflight;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

/**
 * Created by Juhwan on 2017-05-24.
 */

public class Boss {
    private Bitmap[] bossImage;
    private Paint paint;
    private Point p;
    private int bossCenterX;
    private int bossCenterY;
    private int displayX, displayY;



    private int scale;
    private int moveSpeed;

    public double hitDistance;

    public boolean deathFlag = false;

    private int count;

    Boss(Bitmap[] enemyImage, int w, int h, int displayX, int displayY, int scale, int moveSpeed){
        this.bossImage = enemyImage;
        p = new Point();
        paint = new Paint();
        paint.setFilterBitmap(true);
        p.x = w;
        p.y = h;
        this.scale = scale;
        this.displayX = displayX;
        this.displayY = displayY;
        int i;
        this.moveSpeed = moveSpeed;
//에러
        bossCenterX = p.x + (enemyImage[0].getWidth()) / 2;
        bossCenterY = p.y + (enemyImage[0].getHeight()) / 2;

        count = 0;
    }

    public void move(){
        p.y += moveSpeed * scale;
        bossCenterX = p.x + (bossImage[0].getWidth()) / 2;
        bossCenterY = p.y + (bossImage[0].getHeight()) / 2;
    }
    public void drawMove(Canvas c){

     //   count++;

        c.drawBitmap(bossImage[0], p.x, p.y, paint);

        if(count < 5){
            c.drawBitmap(bossImage[0], p.x, p.y, paint);
        }else if(5 <= count && count < 10){
            c.drawBitmap(bossImage[1], p.x, p.y, paint);
        }else if(10 <= count && count < 15){
            c.drawBitmap(bossImage[2], p.x, p.y, paint);
        }else{
            c.drawBitmap(bossImage[3], p.x, p.y, paint);
        }

        if(count >= 20){
            count = 0;
        }
    }

    public int getX(){
        return p.x;
    }

    public int getY(){
        return p.y;
    }

    public int getCenterX(){
        return bossCenterX;
    }

    public int getCenterY(){
        return bossCenterY;
    }

}





