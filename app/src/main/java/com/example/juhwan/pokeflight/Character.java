package com.example.juhwan.pokeflight;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

/**
 * Created by Juhwan on 2017-05-16.
 */

public class Character {

    public int InitX, InitY;  // 초기위치

    private int moveX, moveY;    // 우주선 위치
    private int width, height;  // 폭과 높이
    public int dir;        // 이동 방향 (1:왼쪽, 2:오른쪽, 3, 위쪽, 0:정지)
    public boolean isDead;                 // 사망

    private Paint paint;
    private Point p;

    public int hitDistance1, hitDistance2;

    public Bitmap imgShip;                 // 우주선 이미지

    private int sx[] = {0, -8, 8, 0}; // 이동 방향에 따른 속도
    private int sy[] = {0, 0, 0, -8};

    private Context mContext;

    public Character(int x, int y, Context mContext) {

        this.InitX = x;
        this.InitY = y;
        this.mContext = mContext;

        paint  = new Paint();
        p = new Point();
        ResetShip();
    }

    public void ResetShip() {
        setCharImg(R.drawable.pikachu0); // 이미지 셋팅// 우주선의 시작 위치
        this.width = imgShip.getWidth() / 2;            // 우주선의 폭과 높이
        this.height = imgShip.getHeight() / 2;
        this.moveX = InitX - width;
        this.moveY = InitY - height - 250;
        isDead = false;
        dir = 0;

        p.x = InitX - imgShip.getWidth() / 2;
        p.y = InitY - imgShip.getHeight() / 2;
        fixMove(p);
        
    }

    public void setCharImg(int Res){
        imgShip =  BitmapFactory.decodeResource(mContext.getResources(), Res);
    }

    public void move(int x, int y) {
        p.x = x - imgShip.getWidth() / 2;
        p.y = y - imgShip.getHeight() / 2;
        fixMove(p);
    }
    private void fixMove(Point p) {
        if(p.x < 0){
            p.x = 0;
        }
        if(p.x + imgShip.getWidth() > GameView.Width){
            p.x = GameView.Width - imgShip.getWidth();
        }
        if(p.y < 0){
            p.y = 0;
        }
        if(p.y + imgShip.getHeight() > GameView.Height * 4/4){
            p.y = GameView.Height * 4/4 - imgShip.getHeight();
        }
    }

    public int getCenterX(){
        return p.x + (imgShip.getWidth()) / 2;
    }

    public int getCenterY(){
        return p.y + (imgShip.getHeight()) / 2;
    }

    public void drawMove(Canvas c){
        c.drawBitmap(imgShip, p.x, p.y, paint);
    }
}
