package com.example.xiaoming.testgame;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.ParcelUuid;

/**
 * Created by xiaoming on 2016/10/27.
 */
public class Bullet {
    public Bitmap bmpBullet;
    public  int bulletX,bulletY;
    public int  speed=0;
    public  int bullType;
    public static final  int BULLET_PLAYER =-1;
    public static final  int BULLET_DUCK =1;
    public static final  int BULLET_FLY =2;
    public static final  int BULLET_BOSS =3;
    public  boolean isDead;

    private int dir;
    public  static final int DIR_UP = -1;
    public  static final int DIR_DOWN = 2;
    public  static final int DIR_LEFT= 3;
    public  static final int DIR_RIGHT= 4;
    public  static final int DIR_UP_LEFT= 5;
    public  static final int DIR_UP_RIGHT= 6;
    public  static final int DIR_DOWN_LEFT = 7;
    public  static final int DIR_DOWN_RIGHT = 8;



    public Bullet(Bitmap bmpBullet,int bulletX,int bulletY,int bullType){
        this.bmpBullet=bmpBullet;
        this.bulletX =bulletX;
        this.bulletY=bulletY;
        this.bullType=bullType;
        switch (bullType){
            case BULLET_PLAYER:
                speed=50;
                break;
            case BULLET_DUCK:
                speed=7;
                break;
            case BULLET_FLY:
                speed=10;
                break;
            case BULLET_BOSS:
                speed=15;
                break;

        }
    }

    public Bullet(Bitmap bmpBullet,int bulletX,int bulletY,int bullType,int dir){
        this.bmpBullet=bmpBullet;
        this.bulletX=bulletX;
        this.bulletY=bulletY;
        this.bullType=bullType;
        speed=5;
        this.dir=dir;
    }

public void draw(Canvas canvas, Paint paint){
    canvas.drawBitmap(bmpBullet,bulletX,bulletY,paint);
}
    public void  logic(){
        switch (bullType){
            case BULLET_PLAYER:
                bulletY-=speed;
                if (bulletY<=-50){
                    isDead=true;
                }break;
            case BULLET_DUCK:
            case BULLET_FLY:
               bulletY+=speed;
                if (bulletY>=Gameview.screenH){
                    isDead=true;
                }break;
            case BULLET_BOSS:
                switch (dir){
                    case DIR_UP:
                        bulletY-=speed;
                        break;
                    case DIR_DOWN:
                        bulletY+=speed;
                        break;
                    case DIR_LEFT:
                        bulletX-=speed;
                        break;
                    case DIR_RIGHT:
                        bulletX+=speed;
                        break;
                    case DIR_UP_LEFT:
                        bulletY-=speed;
                        bulletX-=speed;
                        break;
                    case DIR_UP_RIGHT:
                        bulletX+=speed;
                        bulletY-=speed;
                        break;
                    case DIR_DOWN_LEFT:
                        bulletX-=speed;
                        bulletY+=speed;
                        break;
                    case DIR_DOWN_RIGHT:
                        bulletX+=speed;
                        bulletY+=speed;
                        break;
                }

                if (bulletY>Gameview.screenH||bulletY<=-40||bulletX>Gameview.screenW||bulletX<=-40){
                    isDead=true;
                }

                break;
        }
    }
}
