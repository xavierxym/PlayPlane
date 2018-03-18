package com.example.xiaoming.testgame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.provider.Settings;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;

import java.util.Random;
import java.util.Vector;

/**
 * Created by xiaoming on 2016/10/24.
 */
public class Gameview extends SurfaceView implements Callback, Runnable{
    public  static final String tag ="Gameview";
    public static int screenW;
    public static int screenH;
    private Boss boss;
    private player pl;
private  Vector<Bullet> vcBullet =new Vector<Bullet>();
    public static Vector<Bullet> vcBulletBoss;
    private  int countEnemyBullet;
    private  Vector<Bullet> vcBulletpl =new  Vector<Bullet>();
    private  int countplBullet;
    private Vector<Enemy> vcEnemy;
    private  int createEnemyTime =100;
    private int count=0;
    private int enemyArray[][]={
            {1,3},{1,1},{1,2,1,3},{1,1,1},{1,2},{2,3},{3,1,3},{2,2},{1,2},
            {2,2},{1,3,1,1},{2,1},{1,3},{2,1},{-1}
    };
    private  int enemyArrayIndex;
    private  boolean isBoss=false;
    private Random random;

    // GameThread gameThread;
    public static Bitmap bmp,bmp2,bmppl,bmpplhp,bmpEnemyFly,bmpEnemyDuck,bmpEnemyBullet,bmpplButtet,bmpBossBullet,bmpBoss;
    public int bmpX,bmpY,temp=0,temp2=0,Gamestate=0;
    public static int textX=40,textY=40;
    private Paint paint;
    private  Thread th;
    public boolean flag;
    public static boolean isUp;
    public static boolean isDown;
    public static boolean isRight;
    public static boolean isleft;
    private Canvas canvas;
    private int speed=3;
    private  Vector<Boom> vcBoom =new Vector<Boom>();
    private Button btn;
    //private   int screenW=0, screenH=0;
    private  SurfaceHolder surfaceHolder;
    Rect rKeyUp = new Rect(100,500,200,600);
    Rect rKeyDown = new Rect(100, 700, 200, 800);
    Rect rKeyLeft = new Rect(0, 600, 100, 700);
    Rect rKeyRight = new Rect(200, 600, 300, 700);
    Rect KeyRestart = new Rect(200, 200, 400, 300);
    public Gameview(Context context) {
        super(context);

        surfaceHolder = getHolder();

        surfaceHolder.addCallback(this);
        paint=new Paint();
        paint.setColor(Color.WHITE);
        setFocusable(true);
     //   gameThread =new GameThread(surfaceHolder);
    }
   // @Override
    public void surfaceChanged(SurfaceHolder arg0,int arg1,int arg2,int arg3){
        Log.v(tag,"surfaceChanged");
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        Log.v(tag,"surfaceDestoryed");
        flag=false;
      //  gameThread.run =false;
    }
    @Override
    public  void surfaceCreated(SurfaceHolder holder){
       // Log.v(tag,"surfaceCreated");
        screenW = this.getWidth();
        screenH = this.getHeight();
        flag=true;

        th = new Thread(this);
        th.start();
        bmp= BitmapFactory.decodeResource(this.getResources(),R.drawable.star3);
        bmp2= BitmapFactory.decodeResource(this.getResources(),R.drawable.wave);
        bmpX=-bmp2.getWidth()+this.getWidth();
        temp2=bmpX;
        bmpY=this.getHeight()-bmp2.getHeight();
        initGame();

        //  btn= (Button) findViewById(R.id.btn);
     /*   btn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
               // textX+=5;
            }
        });*/
       // gameThread.start();
    }

    private void initGame(){

        bmpplhp=BitmapFactory.decodeResource(this.getResources(),R.drawable.heart);
        bmppl=BitmapFactory.decodeResource(this.getResources(),R.drawable.you2);
        bmpEnemyFly=BitmapFactory.decodeResource(this.getResources(),R.drawable.wenzi);
        bmpEnemyDuck=BitmapFactory.decodeResource(this.getResources(),R.drawable.cangying2);
        bmpEnemyBullet=BitmapFactory.decodeResource(this.getResources(),R.drawable.zhidan3);
        bmpBossBullet=BitmapFactory.decodeResource(this.getResources(),R.drawable.bosszhidan);
        bmpplButtet=BitmapFactory.decodeResource(this.getResources(),R.drawable.bullt2);
        bmpBoss=BitmapFactory.decodeResource(this.getResources(),R.drawable.boss);
        Gamestate=0;
        enemyArrayIndex=0;
        isBoss=false;
        pl=new player(bmppl,bmpplhp);
        boss=new Boss(bmpBoss);
        vcBulletBoss =new Vector<Bullet>();
        vcEnemy=new Vector<Enemy>();
        random=new Random();
    }
    @Override
    public void  run() {
        // int i =0;
        while (flag){
            Log.v(tag,"GameThread");
            long start = System.currentTimeMillis();
            myDraw();
            logic();

            long end=System.currentTimeMillis();
            try{
                if ((end-start)<50){
                    Thread.sleep(50-(end-start));
                }
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            // Canvas c =null;
        /*try{

            synchronized (surfaceHolder){
                c =surfaceHolder.lockCanvas();
                c.drawARGB(255,255,255,255);
                c.drawText("" + i++,100,100,new Paint());
                Thread.sleep(1000);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (c != null){
                surfaceHolder.unlockCanvasAndPost(c);
            }
        }*/
        }
    }



    //@Override
    public void myDraw() {
       // super.onDraw(canvas);
        try {
            canvas=surfaceHolder.lockCanvas();
           if (Gamestate==0){
            if (canvas !=null) {
                canvas.drawRGB(255, 255, 255);

                canvas.save();
                canvas.clipRect(textX-10, textY-40, textX-10 + bmp.getWidth() / 4, textY -40+ bmp.getHeight());
                canvas.drawBitmap(bmp, textX-10 - temp * bmp.getWidth() / 4, textY-40, paint);
                canvas.restore();
                //  canvas.drawText("Game",textX,textY,paint);
                canvas.save();
                canvas.drawBitmap(bmp2, bmpX, bmpY, paint);
                canvas.restore();
                canvas.save();
                pl.draw(canvas, paint);
                canvas.restore();
                canvas.save();
                if (isBoss == false) {
                    for (int i = 0; i < vcEnemy.size(); i++) {
                        vcEnemy.elementAt(i).draw(canvas, paint);
                    }
                    canvas.restore();
                    canvas.save();
                    for (int i = 0; i < vcBullet.size(); i++) {
                        vcBullet.elementAt(i).draw(canvas, paint);
                    }
                    canvas.restore();
                } else {

                }
                canvas.save();
                for (int i = 0; i < vcBulletpl.size(); i++) {
                    vcBulletpl.elementAt(i).draw(canvas, paint);
                }
                canvas.restore();

                if (isBoss == false) {

                    for (int i = 0; i < vcEnemy.size(); i++) {
                        Enemy en = vcEnemy.elementAt(i);
                        if (en.isDead) {
                            vcEnemy.removeElementAt(i);
                        } else {
                            en.logic();
                        }
                    }
                    count++;
                    if (count % createEnemyTime == 0) {
                        for (int i = 0; i < enemyArray[enemyArrayIndex].length; i++) {
                            if (enemyArray[enemyArrayIndex][i] == 1) {
                                int x = random.nextInt(screenW - 100) + 50;
                                vcEnemy.addElement(new Enemy(bmpEnemyFly, 1, x, -50));
                            } else if (enemyArray[enemyArrayIndex][i] == 2) {
                                int y = random.nextInt(20);
                                vcEnemy.addElement(new Enemy(bmpEnemyDuck, 2, -50, y));
                            } else if (enemyArray[enemyArrayIndex][i] == 3) {
                                int y = random.nextInt(20);
                                vcEnemy.addElement(new Enemy(bmpEnemyDuck, 3, screenW, y));
                            }
                        }
                        if (enemyArrayIndex == enemyArray.length - 1) {
                            isBoss = true;
                        } else {
                            enemyArrayIndex++;
                        }
                    }
                } else {


                }


                if (isBoss == false) {
                    countEnemyBullet++;
                    if (countEnemyBullet == 200000) {
                        countEnemyBullet = 0;
                    }
                    if (countEnemyBullet % 80 == 0) {
                        for (int i = 0; i < vcEnemy.size(); i++) {
                            Enemy en = vcEnemy.elementAt(i);
                            int bulletType = 0;
                            switch (en.type) {
                                case Enemy.TYPE_FLY:
                                    bulletType = Bullet.BULLET_FLY;
                                    break;
                                case Enemy.TYPE_DUCKL:
                                case Enemy.TYPE_DUCKR:
                                    bulletType = Bullet.BULLET_DUCK;
                                    break;
                            }
                            vcBullet.add(new Bullet(bmpEnemyBullet, en.x + 10, en.y + 20, bulletType));
                        }
                    }
                    for (int i = 0; i < vcBullet.size(); i++) {
                        Bullet b = vcBullet.elementAt(i);
                        if (b.isDead) {
                            vcBullet.removeElement(b);
                        } else {
                            b.logic();
                        }
                    }


                }
                countplBullet++;
                if (countplBullet % 10 == 0) {
                    vcBulletpl.add(new Bullet(bmpplButtet, pl.x + 15, pl.y - 20, Bullet.BULLET_PLAYER));
                }

                for (int i = 0; i < vcBulletpl.size(); i++) {
                    Bullet b = vcBulletpl.elementAt(i);
                    if (b.isDead) {
                        vcBulletpl.removeElement(b);
                    } else {
                        b.logic();
                    }
                }
                for (int i = 0; i < vcEnemy.size(); i++) {
                    if (pl.isCokksionWidth(vcEnemy.elementAt(i))) {
                        pl.setPlayerhp(pl.getPlayerhp() - 1);
                        if (pl.getPlayerhp() <= -1) {
                            Gamestate=20;
                         //   canvas.drawRGB(0, 0, 0);
                            //lost
                        }
                    }
                }

                for (int i = 0; i < vcBullet.size(); i++) {
                    if (pl.isCollsionWidh(vcBullet.elementAt(i))) {
                        pl.setPlayerhp(pl.getPlayerhp() - 1);
                        if (pl.getPlayerhp() <= -1) {
                            Gamestate=20;
                        //    canvas.drawRGB(0, 0, 0);
                            //lost
                        }
                    }
                }
                for (int i = 0; i < vcBulletpl.size(); i++) {
                    Bullet blPl = vcBulletpl.elementAt(i);
                    for (int j = 0; j < vcEnemy.size(); j++) {
                        if (vcEnemy.elementAt(j).isCollsionWidh(blPl)) {

                        }
                    }
                }
                for (int i = 0; i < vcBoom.size(); i++) {
                    vcBoom.elementAt(i).draw(canvas, paint);
                }


                for (int i = 0; i < vcBulletpl.size(); i++) {
                    Bullet blpl = vcBulletpl.elementAt(i);
                    for (int j = 0; j < vcEnemy.size(); j++) {
                        if (vcEnemy.elementAt(j).isCollsionWidh(blpl)) {
                            vcBoom.add(new Boom(bmppl, vcEnemy.elementAt(j).x, vcEnemy.elementAt(j).y, 4));
                        }
                    }
                }

                for (int i = 0; i < vcBoom.size(); i++) {
                    Boom boom = vcBoom.elementAt(i);
                    if (boom.palyEnd) {
                        vcBoom.removeElementAt(i);
                    } else {
                        vcBoom.elementAt(i).logic();
                    }
                }
                if (isBoss) {
                    boss.draw(canvas, paint);
                    for (int i = 0; i < vcBulletBoss.size(); i++) {
                        vcBulletBoss.elementAt(i).draw(canvas, paint);
                    }
                    boss.logic();


                    if (countplBullet % 10 == 0) {
                        vcBulletBoss.add(new Bullet(bmpBossBullet, boss.x + 35, boss.y + 40, Bullet.BULLET_FLY));
                    }
                    for (int i = 0; i < vcBulletBoss.size(); i++) {
                        Bullet b = vcBulletBoss.elementAt(i);
                        if (b.isDead) {
                            vcBulletBoss.removeElement(b);
                        } else {
                            b.logic();
                        }
                    }

                    for (int i = 0; i < vcBulletBoss.size(); i++) {
                        if (pl.isCollsionWidh(vcBulletBoss.elementAt(i))) {
                            pl.setPlayerhp(pl.getPlayerhp() - 1);
                            if (pl.getPlayerhp() <= -1) {
                                Gamestate=20;
                         //       canvas.drawRGB(0, 0, 0);
                                //lost
                            }
                        }
                    }
                    for (int i = 0; i < vcBulletpl.size(); i++) {
                        Bullet b = vcBulletpl.elementAt(i);
                        if (boss.isCollsionWith(b)) {
                            if (boss.hp <= 0) {
                                Gamestate=100;

                            } else {
                                b.isDead = true;
                                boss.setHp(boss.hp - 1);
                                vcBoom.add(new Boom(bmppl, boss.x + 25, boss.y + 30, 5));
                                vcBoom.add(new Boom(bmppl, boss.x + 25, boss.y + 40, 5));
                                vcBoom.add(new Boom(bmppl, boss.x + 25, boss.y + 50, 5));
                            }
                        }
                    }
                }
            }



            }else if (Gamestate==20)
           {
               canvas.drawRGB(0, 0, 0);
               paint.setTextSize(30);
               canvas.drawText("你输了！，此重新开始",200,200,paint);
           }else  if (Gamestate==100){
               canvas.drawRGB(255, 255, 255);
               paint.setTextSize(30);
               paint.setColor(Color.BLACK);
               canvas.drawText("你赢了,点此重新开始",200,200,paint);

           }





        }catch (Exception e){

        }finally {
            if (canvas !=null)
                surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        textX =(int) event.getX();
        textY=(int) event.getY();
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            int ax = (int) event.getX();
            int ay = (int) event.getY();
            if (KeyRestart.contains(ax,ay)){
                if (Gamestate!=0){

                    initGame();
                }
            }
            if (rKeyUp.contains(ax, ay)) {
                isUp=true;

            } else if (rKeyDown.contains(ax, ay)) {
                isDown=true;

            } else if (rKeyLeft.contains(ax, ay)) {
                isleft=true;

            } else if (rKeyRight.contains(ax, ay)) {
                isRight=true;

            }
            postInvalidate(); //不要忘记刷新屏幕
        }

        if (event.getAction()==MotionEvent.ACTION_UP) {

            isUp = false;
            isDown = false;
            isRight = false;
            isleft = false;
            postInvalidate();
        }

        //return super.onTouchEvent(event);
        return  true;
    }

    private void logic(){
        if(isUp){
            textY -= 5;
        }else if(isDown){
            textY += 5;
        }else if (isleft){
            textX -= 5;
        }else if (isRight){
            textX += 5;
        }

        temp++;
        if(temp>=4){
            temp=0;
        }
         bmpX+=5;
        if (bmpX>=5){
            bmpX=temp2;
        }


    }


/*
    private class GameThread extends Thread{
        SurfaceHolder surfaceHolder;
        boolean run =true;

        public GameThread(SurfaceHolder surfaceHolder) {
            this.surfaceHolder=surfaceHolder;

        }*/




}




