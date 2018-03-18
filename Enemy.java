package com.example.xiaoming.testgame;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Created by xiaoming on 2016/10/26.
 */
public class Enemy {
    public  int type;
    public static final int TYPE_FLY=1;
    public static final int TYPE_DUCKL=2;
    public static final int TYPE_DUCKR=3;
        public Bitmap bmpEnemy;
    public int x,y;
    public int frameW,frameH;
    private  int frameIndex;
    private  int speed;
    public boolean isDead;
    public Enemy(Bitmap bmpEnemy,int enemyType,int x,int y){
        this.bmpEnemy=bmpEnemy;
        frameW=bmpEnemy.getWidth()/8;
        frameH=bmpEnemy.getHeight();
        this.type=enemyType;
        this.x=x;
        this.y=y;
        switch (type){
            case TYPE_FLY:
                speed=40;
                break;
            case TYPE_DUCKL:
                speed=3;
                break;
            case TYPE_DUCKR:
                speed=3;
                break;
        }
    }

    public void  draw(Canvas canvas,Paint paint){
       // canvas.drawRGB(0,0,0);
        canvas.save();
        canvas.clipRect(x,y,x+frameW,y+frameH);
        canvas.drawBitmap(bmpEnemy,x-frameIndex*frameW,y,paint);
        canvas.restore();
    }
    public boolean isCollsionWidh(Bullet bullet){

            int x2 = bullet.bulletX;
            int y2 = bullet.bulletY;
            int w2 = bullet.bmpBullet.getWidth();
            int h2 = bullet.bmpBullet.getHeight();
            if (x > x2 && x >= x2 + w2) {
                return false;
            } else if (x < x2 && x + frameW <= x2) {
                return false;
            } else if (y >= y2 && y >= y2 + h2) {
                return false;
            } else if (y <= y2 && y + frameH <= y2) {
                return false;
            }
        isDead=true;
            return true;
    }
    public void logic(){
        frameIndex++;
        if (frameIndex>=8){
            frameIndex=0;
        }
        switch (type){
            case TYPE_FLY:
                if (isDead==false){
                    speed-=1;
                    y+=speed;
                    if(y<=-100){
                        isDead=true;
                    }
                }break;
            case TYPE_DUCKL:
                if(isDead==false){
                    x+=speed/1.5;
                    y+=speed;
                    if (x>=Gameview.screenW||y>=Gameview.screenH){
                        isDead=true;
                    }
                }break;
            case TYPE_DUCKR:
                if (isDead==false){
                    x-=speed/1.5;
                    y+=speed;
                    if (x<=0||y>=Gameview.screenH){
                        isDead=true;
                    }
                }break;
        }
    }

}
