package com.example.xiaoming.testgame;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Created by xiaoming on 2016/10/28.
 */
public class Boss {
    public int hp =50;
    private Bitmap bmpBoss;
    public  int x,y;
    public int frameW,frameH;
    private int frameIndex;
    private int speed=5;
    private  boolean isCrazy;
    private  int crazyTime =200;
    private  int count=0;
    public Boss(Bitmap bmpBoss){
        this.bmpBoss=bmpBoss;
        frameW=bmpBoss.getWidth()/4;
        frameH=bmpBoss.getHeight();
        x=Gameview.screenW/2-frameW/2;
        y=0;
    }

    public void  draw(Canvas canvas, Paint paint){
        canvas.save();
        canvas.clipRect(x,y,x+frameW,y+frameH);
        canvas.drawBitmap(bmpBoss,x-frameIndex*frameW,y,paint);
        canvas.restore();
    }
public  void  logic(){
    frameIndex++;
    if (frameIndex>=4){
        frameIndex=0;
    }
    if (isCrazy==false){
        x+=speed;
        if (x+frameW>=Gameview.screenW){
            speed=-speed;
        }else  if (x<=0) {
            speed = -speed;
        }
        count++;
        if (count%crazyTime==0){
            isCrazy=true;
            speed=24;
        }
    }else {
        speed-=1;
        if (speed==0){
            Gameview.vcBulletBoss.add(new Bullet(Gameview.bmpplButtet,x+30,y,Bullet.BULLET_BOSS,Bullet.DIR_UP));
            Gameview.vcBulletBoss.add(new Bullet(Gameview.bmpplButtet,x+30,y,Bullet.BULLET_BOSS,Bullet.DIR_DOWN));
            Gameview.vcBulletBoss.add(new Bullet(Gameview.bmpplButtet,x+30,y,Bullet.BULLET_BOSS,Bullet.DIR_UP_LEFT));
            Gameview.vcBulletBoss.add(new Bullet(Gameview.bmpplButtet,x+30,y,Bullet.BULLET_BOSS,Bullet.DIR_UP_RIGHT));
            Gameview.vcBulletBoss.add(new Bullet(Gameview.bmpplButtet,x+30,y,Bullet.BULLET_BOSS,Bullet.DIR_LEFT));
            Gameview.vcBulletBoss.add(new Bullet(Gameview.bmpplButtet,x+30,y,Bullet.BULLET_BOSS,Bullet.DIR_RIGHT));
            Gameview.vcBulletBoss.add(new Bullet(Gameview.bmpplButtet,x+30,y,Bullet.BULLET_BOSS,Bullet.DIR_DOWN_LEFT));
            Gameview.vcBulletBoss.add(new Bullet(Gameview.bmpplButtet,x+30,y,Bullet.BULLET_BOSS,Bullet.DIR_DOWN_RIGHT));
        }
        y+=speed;
        if (y<=0){
            isCrazy=false;
            speed=5;
        }
    }
}

    public  boolean isCollsionWith(Bullet bullet){
        int x2 = bullet.bulletX;
        int w2=bullet.bmpBullet.getWidth();
        int y2=bullet.bulletY;
        int h2=bullet.bmpBullet.getHeight();
        if(x>=x2  && x>=x2+w2){
            return false;
        }else if (x<=x2 && x+frameW<=x2){
            return false;
        }else if (y>=y2 && y>=y2+h2){
            return  false;
        }else if (y<=y2 && y+frameH<=y2){
            return false;
        }
        return true;
    }

    public  void  setHp(int hp){
        this.hp =hp;
    }
}
