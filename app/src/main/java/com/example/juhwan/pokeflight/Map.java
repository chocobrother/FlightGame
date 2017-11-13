package com.example.juhwan.pokeflight;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

/**
 * Created by Juhwan on 2017-05-23.
 */

public class Map {
    private Bitmap map1,map2;
    private int width,height;
    Paint paint = new Paint();
    private int x1, y1, x2, y2;                     // Viewport 좌표
    private int cx,cy;
    private int sx1, sy1, sx2, sy2;               // Viewport가 1회에 이동할 거리
    public static int Vec[][] = {{1, 0}, {1, -1}, {0, -1}, {-1, -1}, {-1, 0}, {-1, 1}, {0, 1}, {1, 1}};
    Rect src = new Rect();                     // Viewport의 좌표
    Rect dst = new Rect();                     // View(화면)의 좌표
    Rect src1 = new Rect();

    public Map(Context context,int Width, int Height){
        this.width = Width;
        this.height = Height;


        cx = width / 2;                   // 화면의 중심점
        cy = height / 2;

        dst.set(0, 0, width, height);              // View는 화면 전체 크기
        src.set(x1, y1, x1 + cx, y1 + cy);            // 이동한 Viewport 좌표
        src1.set(x2, y2, x2 + cx, y2 + cy);

        x1 = cx;          // Viewport의 시작 위치는 이미지의 한가운데
        y1 = cy;
        //-----------추가된 내용 ----------------------
        sx1 = 1;         // 원경을 1회에 이동시킬 거리
        sy1 = 1;
        sx2 = 2;         // 근경을 1회에 이동시킬 거리
        sy2 = 2;

        map1 = BitmapFactory.decodeResource(context.getResources(), R.drawable.world);
        map1 = Bitmap.createScaledBitmap(map1, width, height, true);
        // 전경
        map2 = BitmapFactory.decodeResource(context.getResources(), R.drawable.world1);
        map2 = Bitmap.createScaledBitmap(map2, width, height, true);

    }

    public void drawMap(Canvas c){
        c.drawBitmap(map1,src,dst,paint);
        c.drawBitmap(map2,src1,dst,paint);
    }

}
