package com.example.juhwan.pokeflight;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Juhwan on 2017-05-16.
 */

public class GameView extends SurfaceView implements SurfaceHolder.Callback {

    public static int RUN = 1;
    public static int PAUSE = 2;
    public int mMode = RUN;
    private static GameView gameView;
    protected boolean hitFlag = false;

    Bitmap imgMiniShip;

    private static final int LIST = 1;

    private static boolean last = true;

    public static Map background;
    public static Character SpaceShip;
    public static CharaBeam charaBeam1;
    private ChacterChange chacterChange;
    private static List<Enemy> enemys = new ArrayList();
    private List<BossBeam> bossBeams = new ArrayList<BossBeam>();
    private static List<Boss> bosses = new ArrayList();
    private static CharaBeam[] charaBeam;

    private List<Explotion> explotions = new ArrayList<Explotion>();

    public static MainActivity main;

    private SurfaceHolder mHolder;
    public static MyThread mThread;
    public static Context mContext;



    public static int Width, Height, cx, cy;     // 화면의 전체 폭과 중심점
    private int x1, y1, x2, y2;                     // Viewport 좌표
    private int sx1, sy1, sx2, sy2;               // Viewport가 1회에 이동할 거리
    private Bitmap imgBack1, imgBack2;       // 배경 이미지
    private long counter = 0;                       // 루프의 전체 반복 횟수
    private boolean canRun = true;              // 스레드 실행용 플래그
    boolean isWait = false;   // Thread 제어용
    protected boolean pauseFlag = false;
    boolean control = false;
    static boolean m_run = false;
    static int shipCnt = 5;
    // 추가된 부분  ---------------------------------

    private int touchX, touchX2;
    private int touchY, touchY2;
    int charaCenterX;
    int charaCenterY;
    //    private int sw, sh;                          // 우주선의 폭과 높이
    private int ww, wh;                          // 화살표의 폭과 높이
    private static int RAD = 80;              // 중심에서 화살표까지의 거리(반지름
    private int Dir = 2;                          // 시작시  스크롤하는 방향
    private int score = 0;
    private int count;
    //private int overTime;
    int displayX, displayY;
    boolean touchFlag = false;
    int beamCount = 0;
    private final int N = 12;
    int scale;
    boolean onelife = true;
    private int bossHP = 30;

    public static int Vec[][] = {{1, 0}, {1, -1}, {0, -1}, {-1, -1}, {-1, 0}, {-1, 1}, {0, 1}, {1, 1}};
    private boolean first = false;

    Resources res = this.getContext().getResources();

    Bitmap explotionImage = BitmapFactory.decodeResource(res, R.drawable.gotcha);
    Bitmap explotionImage1 = BitmapFactory.decodeResource(res, R.drawable.demage);
    Bitmap enemyImage = BitmapFactory.decodeResource(res, R.drawable.psyduck);
    Bitmap bossImage = BitmapFactory.decodeResource(res,R.drawable.instinct);
    Bitmap[] enemyImageArray;
    Bitmap[] bossImageArray;
    Matrix enemyMatrix;
    Matrix bossMatrix;

    public GameView(Context context ) {
        super(context);

        this.mContext = context;
        this.gameView = gameView;


        SurfaceHolder mHolder = getHolder();
        mHolder.addCallback(this);

        InitGame(context);          // 비트맵 읽기

        MakeStage();
        mThread = new MyThread(mHolder, this);
        setFocusable(true);
    }


    public void RestartGame() {

        if(mThread.isAlive()){
            mThread.interrupt();
        }

        mThread = null;
        Log.e("TAG",(mHolder == null)+"/"+(gameView == null));
        mThread = new MyThread(mHolder, this);

        mThread.start();

        synchronized (this) {
            this.notify();                   // 스레드에 통지
        }
    }
    public void InitGame(Context context) {
        // 화면 해상도 구하기
        DisplayMetrics dm = mContext.getResources().getDisplayMetrics();

        Width = dm.widthPixels;
        Height = dm.heightPixels;

        displayX = Width;
        displayY = Height;

        scale = Width / 480;


        cx = Width / 2;                   // 화면의 중심점
        cy = Height / 2;

        Resources res = mContext.getResources();          // 리소스 읽기
        // 배경화면 읽고 배경화면의 크기를 화면의 크기로 조정

        x1 = cx;          // Viewport의 시작 위치는 이미지의 한가운데
        y1 = cy;
        //-----------추가된 내용 ----------------------
        sx1 = 1;         // 원경을 1회에 이동시킬 거리
        sy1 = 1;
        sx2 = 2;         // 근경을 1회에 이동시킬 거리
        sy2 = 2;

        SpaceShip = new Character(Width / 2, Height, mContext);


        background = new Map(context,displayX,displayY);

        imgMiniShip = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.heart);

        enemyImage = Bitmap.createScaledBitmap(enemyImage, displayX / 11, displayX / 11, true);
        bossImage = Bitmap.createScaledBitmap(bossImage, displayX/5, displayY/5, true);

        enemyImageArray = new Bitmap[4];
        enemyMatrix = new Matrix();

        bossImageArray = new Bitmap[4];
        bossMatrix = new Matrix();


        for (int i = 0; i < 4; i++) {
            enemyImageArray[i] = Bitmap.createBitmap(enemyImage, 0, 0, enemyImage.getWidth(), enemyImage.getHeight(), enemyMatrix, true);
        }

        for (int i = 0; i < 4; i++) {
            bossImageArray[i] = Bitmap.createBitmap(bossImage, 0, 0, bossImage.getWidth(), bossImage.getHeight(), bossMatrix, true);
        }

    }
    //
    public void MakeStage() {
        Resources res = mContext.getResources();          // 리소스 읽기
        // 배경화면 읽고 배경화면의 크기를 화면의 크기로 조정
        imgBack1 = BitmapFactory.decodeResource(res, R.drawable.world);
        imgBack1 = Bitmap.createScaledBitmap(imgBack1, Width, Height, true);
        // 전경
        imgBack2 = BitmapFactory.decodeResource(res, R.drawable.world1);
        imgBack2 = Bitmap.createScaledBitmap(imgBack2, Width, Height, true);


    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Log.d("SurfaceStart~!~~!~","tetestete");
        startSet();
    }

    private void startSet(){
        if(first) {
            mThread = new MyThread(mHolder, this);
            setFocusable(true);
        }
        mThread.setRunning(true);

        mThread.start();
        charaBeam = new CharaBeam[N];

        for (int i = 0; i < N; i++) {
            charaBeam[i] = new CharaBeam(getContext(), displayX, scale);
        }
        first = true;
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        // TODO Auto-generated method stub

    }
    //-------------------------------------
    //  스레드 완전 정지
    //-------------------------------------
    public static void StopGame() {
//        System.exit(0);
        mThread.StopThread();
    }
    //-------------------------------------
    //  스레드 일시 정지
    //-------------------------------------
    public static void PauseGame() {
        mThread.PauseNResume(true);
    }

    //-------------------------------------
    //  스레드 재기동
    //-------------------------------------
    public static void ResumeGame() {
        mThread.PauseNResume(false);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        while (retry) {
            try {
                mThread.join();
                retry = false;
            } catch (InterruptedException e) {
                //
            }
        }

    }

    public class MyThread extends Thread {
//        private boolean m_run = false;

        Paint paint = new Paint();

        public MyThread(SurfaceHolder Holder, GameView gameView) {
            mHolder = Holder;
            gameView = gameView;
        }

        public void setRunning(boolean run) {
            m_run = run;
        }

        public void run() {
            Rect src = new Rect();                     // Viewport의 좌표
            Rect dst = new Rect();                     // View(화면)의 좌표
            dst.set(0, 0, Width, Height);              // View는 화면 전체 크기
            Rect src1 = new Rect();

            Paint mPaint = new Paint();
            mPaint.setFilterBitmap(true);

            count = 50 * scale;
            //overTime = 0;
            int enemyMoveSpeed, enemyIncidence;
            int BoseBeamMoveSpeed, BoseBeamIncidence;


            final int explotionSpeed = 10;


            final int enemyR = displayX / 11;
            final int bossR = displayX / 20;
            final int charaR = displayX / 20;

            boolean overFlag = false;
            Canvas canvas;
            while (m_run) {
                canvas = null;
                try {
                    canvas = mHolder.lockCanvas();
                    synchronized (mHolder) { //실제 그래픽 처리 부분

                        src.set(x1, y1, x1 + cx, y1 + cy);            // 이동한 Viewport 좌표
                        src1.set(x2, y2, x2 + cx, y2 + cy);


                        canvas.drawBitmap(imgBack1, src, dst, null);
                        canvas.drawBitmap(imgBack2, src1, dst, null);

                        ScrollImage();
                        SpaceShip.drawMove(canvas);

                        paint.setColor(Color.WHITE);
                        paint.setAntiAlias(true);
                        paint.setTextSize(50);

                        DrawScore(canvas);

                        if (!pauseFlag) {
                            count++;
                            if (!hitFlag) {
                                enemyMoveSpeed = ((score / 1000) + 2);
                                enemyIncidence = (100 - (score / 30) * 9);
                                if (enemyIncidence < 10) {
                                    enemyIncidence = 10;
                                }
                                addEnemy(count, enemyMoveSpeed, enemyIncidence);
                                BoseBeamMoveSpeed = ( (score / 15) + 4) * scale;
                                BoseBeamIncidence = ( 70 - (score / 15) * 7 ) * scale;
                                if(BoseBeamIncidence < 10){
                                    BoseBeamIncidence = 10;
                                }

                                addBoseBeam(count, BoseBeamIncidence, BoseBeamMoveSpeed);

                                if(score >= 20){
                                    Bitmap enemyImage = BitmapFactory.decodeResource(res, R.drawable.enemy02);
                                    enemyImage = Bitmap.createScaledBitmap(enemyImage, displayX / 11, displayX / 11, true);
                                    for (int i = 0; i < 4; i++) {
                                        enemyImageArray[i] = Bitmap.createBitmap(enemyImage, 0, 0, enemyImage.getWidth(), enemyImage.getHeight(), enemyMatrix, true);
                                    }
                                }
                                if(score>=30){

                                    Bitmap enemyImage = BitmapFactory.decodeResource(res, R.drawable.enemy05);
                                    enemyImage = Bitmap.createScaledBitmap(enemyImage, displayX / 11, displayX / 11, true);
                                    for (int i = 0; i < 4; i++) {
                                        enemyImageArray[i] = Bitmap.createBitmap(enemyImage, 0, 0, enemyImage.getWidth(), enemyImage.getHeight(), enemyMatrix, true);
                                    }
                                }

                                if(score >= 50){
                                    addBoss(count,enemyMoveSpeed,enemyIncidence);
                                }
                            }

                            addBeam(count);

                            moveEnemy(charaR, count);

                            moveBoss(charaR,count);

                            moveBoseBeam(charaR,count);

                            moveCharaBeam(enemyR, count);

                            moveCharaBeam2(enemyR, count);

                            removeEnemy();

                            removeBossBeam();

                            removeBoss();

                            doExplotion(explotionSpeed, count);


                            for (int i = 0; i < enemys.size(); i++) {
                                enemys.get(i).drawMove(canvas);
                            }
                            for (int i = 0; i < bosses.size(); i++) {
                                bosses.get(i).drawMove(canvas);
                            }
                            for(int i = 0; i < bossBeams.size(); i++){
                                bossBeams.get(i).drawMove(canvas);
                            }
                            for (int i = 0; i < explotions.size(); i++) {
                                explotions.get(i).drawMove(canvas);
                            }
                        }
                        if (!hitFlag) {

                            for (int i = 0; i < N; i++) {

                                charaBeam[i].drawMove(canvas, SpaceShip.getCenterX()-30, SpaceShip.getCenterY() - (displayX / 11) / 2 + charaBeam[i].getHeight()-70);
                            }
                        }
                    }
                } finally {
                    if (canvas != null) mHolder.unlockCanvasAndPost(canvas);
                    if(shipCnt == 0){

                        overFlag = true;
                    }
                    if(overFlag){
                        setRunning(false);
                        Log.e("Gameover","gameover 1111");
                        StopGame();
                        GameOver();
                    }
                }
                synchronized (this){
                    if(isWait)
                        try{
                            this.wait();
                        } catch (Exception e){
                            e.printStackTrace();
                        }
                }//sync
            } // while
        } // run

        //스레드 완전정지.
        public void StopThread() {
            //canRun = false;
            m_run = false;

            if(mThread.isAlive()){
                mThread.interrupt();
                Log.d("Thread Interrupt","Thread Interrupt");
            }
            synchronized (this) {
                this.notify();                   // 스레드에 통지
            }
        }


        //스레드 일시정지 / 재기동
        public void PauseNResume(boolean value) {
            isWait = value;
            synchronized (this) {
                this.notify();               // 스레드에 통지
            }
        }


        //////////////////////////////////////////
        //enemy
        private void addBeam(int count) {

            if (touchFlag && count % 15 == 0) {
                charaBeam[beamCount].CharaBeamFlag = true;
                beamCount = (beamCount + 1) % N;
            }
        }

        private void addEnemy(int count, int moveSpeed, int incidence) {
            if (count % (incidence / scale) == 0) {
                Random rnd = new Random();
                int ran = rnd.nextInt(Width - (Width / 11));

                enemys.add(new Enemy(enemyImageArray, ran, 0, displayX, displayY, scale, moveSpeed));
            }
        }

        private void removeEnemy() {
            for (int i = 0; i < enemys.size(); i++) {
                if (enemys.get(i).deathFlag == true) {
                    enemys.remove(i);
                }
            }
        }

        private void addBoss(int count, int moveSpeed, int incidence) {

            if (count % (incidence / scale) == 0) {
                Random rnd = new Random();
                int ran = rnd.nextInt(Width - (Width / 11));

                if(onelife) {
                    bosses.add(new Boss(bossImageArray, 600, 0, displayX+200, displayY, scale, 0));
                }
                Log.d("plzdead",bossImage + "");
                onelife = false;

            }
        }

        private void removeBoss() {
            for (int i = 0; i < bosses.size(); i++) {
                if (bosses.get(i).deathFlag == true) {
                    bosses.remove(i);
                    bosses.clear();
                }
            }
        }
        private void addBoseBeam(int count, int incidence, int moveSpeed){
            if(count % incidence == 0 && !bosses.isEmpty()){
                for(int i = 0; i < bosses.size(); i++){
                    bossBeams.add(new BossBeam(mContext,bosses.get(i).getCenterX()
                            , bosses.get(i).getCenterY(), SpaceShip.getCenterX(),SpaceShip.getCenterY(), displayX, scale, moveSpeed));
                }
            }
        }




        private void moveBoseBeam(int charaR, int count){

            for(int i = 0; i < bossBeams.size(); i++){

                if(!hitFlag){
                    bossBeams.get(i).move();
                }

                SpaceShip.hitDistance2 = (int) Math.sqrt(Math.pow(SpaceShip.getCenterX() - bossBeams.get(i).getCenterX(), 2)
                        + Math.pow(SpaceShip.getCenterY() - bossBeams.get(i).getCenterY(), 2));

                if(bossBeams.get(i).getY() > displayY * 3/4 || bossBeams.get(i).getY() < 0
                        || bossBeams.get(i).getX() < 0 || bossBeams.get(i).getX() > displayX){
                    bossBeams.get(i).deathFlag = true;
                }

                if(SpaceShip.hitDistance2 < charaR){

                    //hitFlag = true;
                    shipCnt--;
                    explotions.add(new Explotion(explotionImage1, SpaceShip.getCenterX()
                            , SpaceShip.getCenterY(), count, displayX));
                    bossBeams.get(i).deathFlag = true;

                    if(shipCnt == 0){
                        hitFlag = true;
                    }

                }
            }
        }

        private void removeBossBeam(){
            for(int i = 0; i < bossBeams.size(); i++){
                if(bossBeams.get(i).deathFlag){
                    bossBeams.remove(i);
                }
            }
        }

        private void moveEnemy(int charaR, int count) {
            Canvas canvas = null;
            for (int i = 0; i < enemys.size(); i++) {
                if (!hitFlag) {
                    enemys.get(i).move();
                }
                SpaceShip.hitDistance1 = (int) Math.sqrt(Math.pow(SpaceShip.getCenterX() - enemys.get(i).getCenterX(), 2)
                        + Math.pow(SpaceShip.getCenterY() - enemys.get(i).getCenterY(), 2));
                //피카츄 꼬부기 충돌
                if (SpaceShip.hitDistance1 < charaR) {

                    explotions.add(new Explotion(explotionImage1, SpaceShip.getCenterX()
                            , SpaceShip.getCenterY(), count, displayX));

                    enemys.get(i).deathFlag = true;

                    //목숨깍이기.
                    shipCnt--;


                }

                if (enemys.get(i).getY() > Height * 3 / 4 || enemys.get(i).getCenterX() > Width || enemys.get(i).getCenterX() < 0) {
                    enemys.get(i).deathFlag = true;
                }

            }

        }

        private void moveBoss(int charaR, int count) {
            Canvas canvas = null;
            for (int i = 0; i < bosses.size(); i++) {
                if (!hitFlag) {
                    bosses.get(i).move();
                }
                SpaceShip.hitDistance1 = (int) Math.sqrt(Math.pow(SpaceShip.getCenterX() - bosses.get(i).getCenterX(), 2)
                        + Math.pow(SpaceShip.getCenterY() - bosses.get(i).getCenterY(), 2));
                //피카츄 꼬부기 충돌
                if (SpaceShip.hitDistance1 < charaR) {

                    explotions.add(new Explotion(explotionImage1, SpaceShip.getCenterX()
                            , SpaceShip.getCenterY(), count, displayX));

                    bosses.get(i).deathFlag = true;

                    //목숨깍이기.
                    shipCnt--;

                }
            }

        }

        public void moveCharaBeam(int enemyR, int count) {

            for (int i = 0; i < N; i++) {

                if (charaBeam[i].CharaBeamFlag) {
                    charaBeam[i].isDead = false;

                    if (!hitFlag) {
                        charaBeam[i].move();
                    }

                    for (int j = 0; j < enemys.size(); j++) {

                        enemys.get(j).hitDistance = Math.sqrt(Math.pow((enemys.get(j).getCenterX() - charaBeam[i].getCenterX()), 2)
                                + Math.pow((enemys.get(j).getCenterY() - charaBeam[i].getCenterY()), 2));

                        //죽음
                        if (enemys.get(j).hitDistance < enemyR) {

                            score++;

                            explotions.add(new Explotion(explotionImage, enemys.get(j).getCenterX()
                                    , enemys.get(j).getCenterY(), count, displayX));

                            charaBeam[i].CharaBeamFlag = false;
                            charaBeam[i].isDead = true;
                            enemys.get(j).deathFlag = true;
                        }
                    }
                }
            }
        }

        public void moveCharaBeam2(int enemyR, int count) {
            for (int i = 0; i < N; i++) {

                if (charaBeam[i].CharaBeamFlag) {
                    charaBeam[i].isDead = false;

                    if (!hitFlag) {
                        charaBeam[i].move();
                    }

                    for (int j = 0; j < bosses.size(); j++) {

                        bosses.get(j).hitDistance = Math.sqrt(Math.pow((bosses.get(j).getCenterX() - charaBeam[i].getCenterX()), 2)
                                + Math.pow((bosses.get(j).getCenterY() - charaBeam[i].getCenterY()), 2));

                        //보스 감전사
                        if (bosses.get(j).hitDistance < enemyR) {

                            bossHP--;
                            explotions.add(new Explotion(explotionImage, bosses.get(j).getCenterX()
                                    , bosses.get(j).getCenterY(), count, displayX));

                            charaBeam[i].CharaBeamFlag = false;
                            charaBeam[i].isDead = true;

                            if(bossHP <= 1) {

                                score++;

                                bosses.get(j).deathFlag = true;
                            }
                        }
                    }
                }
            }
        }

        public void doExplotion(int explotionSpeed, int count) {

            for (int i = 0; i < explotions.size(); i++) {

                for (int j = 1; j < 10; j++) {
                    if (explotions.get(i).count + explotionSpeed * j < count) {
                        explotions.get(i).exSwitch = j;
                    }
                }
                if (explotions.get(i).count + explotionSpeed * 2 < count) {
                    explotions.remove(i);
                }
            }
        }

        public void ScrollImage() {
            //if (mMode == PAUSE) return;
            counter++;
            x2 += sx2 * Vec[Dir][0];
            y2 += sy2 * Vec[Dir][1];
            if (x2 < 0) x2 = cx;                        // Viewpoint가 이미지를 벗어났나 조사
            else if (x2 > cx) x2 = 0;
            else if (y2 < 0) y2 = cy;
            else if (y2 > cy) y2 = 0;
            if (counter % 2 == 0) {
                x1 += sx1 * Vec[Dir][0];           // Viewport 이동
                y1 += sy1 * Vec[Dir][1];
                if (x1 < 0) x1 = cx;                  // Viewpoint가 이미지를 벗어났나 조사
                else if (x1 > cx) x1 = 0;
                else if (y1 < 0) y1 = cy;
                else if (y1 > cy) y1 = 0;


            }
        } // ScrollBackground

        public void DrawScore(Canvas canvas) {
            int x, x1, x2, y = 30;

            // 남은 우주선 수
            for (int i = 0; i < shipCnt; i++)
                canvas.drawBitmap(imgMiniShip, i * 20 + 10, y - 15, null);

            // Score
            paint.setColor(Color.WHITE);
            canvas.drawText("Score " + score, 220, y+20, paint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        int action = event.getAction();

        if (!hitFlag) {

            if (action == MotionEvent.ACTION_DOWN) {

                touchX = (int) event.getX();
                touchY = (int) event.getY();

                charaCenterX = SpaceShip.getCenterX();
                charaCenterY = SpaceShip.getCenterY();
                if (!SpaceShip.isDead) {
                    touchFlag = true;
                }
            }
        }
        if (action == MotionEvent.ACTION_MOVE) {
            touchX2 = (int) event.getX();
            touchY2 = (int) event.getY();
            if (!SpaceShip.isDead) {
                SpaceShip.move(charaCenterX - (touchX - touchX2), charaCenterY - (touchY - touchY2));
            }
        }
        if (action == MotionEvent.ACTION_UP) {
            touchFlag = false;
        }
        return true;
    }

    private void GameOver(){

        Intent gameover = new Intent(getContext(),GameOver.class);
        gameover.putExtra("score",score);
        gameover.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        mContext.startActivity(gameover);
        System.exit(0);


    }
}