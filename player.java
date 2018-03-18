package com.example.xiaoming.testgame;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
/*import android.support.v4.widget.SearchViewCompatIcs;*/

/**
 * Created by xiaoming on 2016/10/26.
 */
public class player {
    private int playerhp=3;
    private Bitmap bmpplhp;
    public int x,y;
    private  Bitmap bmppl;
    private int speed=5;
   private int nocoll=0;
    private int nocolltime=60;
    private boolean iscoll=false;
    //private boolean isUp,isDown,isLeft,isRight;
    public player(Bitmap bmppl,Bitmap bmpplhp){
        this.bmppl=bmppl;
        this.bmpplhp=bmpplhp;
        x= Gameview.screenW/2-bmppl.getWidth()/2;
        y=Gameview.screenH/2-bmppl.getHeight()/2;
    }

    public void draw(Canvas canvas, Paint paint){
        logic();
        if (iscoll){
            if (nocoll%2==0){
                canvas.drawBitmap(bmppl,x,y,paint);
            }
            }else {
            canvas.drawBitmap(bmppl,x,y,paint);
        }
      //  canvas.drawBitmap(bmppl,x,y,paint);
        for (int i=0;i<playerhp;i++){
            canvas.drawBitmap(bmpplhp,i*bmpplhp.getWidth(),Gameview.screenH-bmpplhp.getHeight(),paint);
        }
    }
    public void logic(){

        x=Gameview.textX-20;
        y=Gameview.textY-120;
        if(Gameview.isleft){
            x-=speed;
        }
        if(Gameview.isRight){
            x+=speed;
        }
        if (Gameview.isUp){
            y-=speed;
        }
        if (Gameview.isDown){
            y+=speed;
        }

        if (x+bmppl.getWidth()>=Gameview.screenW){
            x=Gameview.screenW-bmppl.getWidth();
        }else if (x<=0){
            x=0;
        }

        if (y+bmppl.getHeight()>=Gameview.screenH){
            y=Gameview.screenH-bmppl.getHeight();
        }else if (y<=0){
            y=0;
        }

        if(iscoll){
            nocoll++;
            if (nocoll>=nocolltime){
                iscoll=false;
                nocoll=0;
            }
        }
    }

public boolean isCokksionWidth(Enemy en){
    if (iscoll==false) {
        int x2 = en.x;
        int y2 = en.y;
        int w2 = en.frameW;
        int h2 = en.frameH;
        if (x > x2 && x >= x2 + w2) {
            return false;
        } else if (x < x2 && x + bmppl.getWidth() <= x2) {
            return false;
        } else if (y >= y2 && y >= y2 + h2) {
            return false;
        } else if (y <= y2 && y + bmppl.getHeight() <= y2) {
            return false;
        }
        iscoll=true;
        return true;
    }else{
        return false;
    }
}

    public boolean isCollsionWidh(Bullet bullet){
        if (iscoll==false) {
            int x2 = bullet.bulletX;
            int y2 = bullet.bulletY;
            int w2 = bullet.bmpBullet.getWidth();
            int h2 = bullet.bmpBullet.getHeight();
            if (x > x2 && x >= x2 + w2) {
                return false;
            } else if (x < x2 && x + bmppl.getWidth() <= x2) {
                return false;
            } else if (y >= y2 && y >= y2 + h2) {
                return false;
            } else if (y <= y2 && y + bmppl.getHeight() <= y2) {
                return false;
            }
            iscoll=true;
            return true;
        }else{
            return false;
        }
    }

    public void  setPlayerhp(int hp){
        this.playerhp=hp;
    }
    public  int getPlayerhp(){
        return playerhp;
    }
}
