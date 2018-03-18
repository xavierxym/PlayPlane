package com.example.xiaoming.testgame;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Created by xiaoming on 2016/10/27.
 */
public class Boom {
    private Bitmap bmpBoom;
    private  int boomX,boomY;
    private  int currentFrameIndex=0;
    private  int totleFrame;
    private  int frameW,frameH;
    public  boolean palyEnd=false;
    public  Boom(Bitmap bmpBoom ,int x,int y,int totaleFrame){
        this.bmpBoom =bmpBoom;
        this.boomX=x;
        this.boomY=y;
        this.totleFrame=totaleFrame;
        frameW=bmpBoom.getWidth()/totaleFrame;
        frameH=bmpBoom.getHeight();
    }

    public  void  draw(Canvas canvas, Paint paint){
        canvas.save();
        canvas.clipRect(boomX,boomY,boomX+frameW,boomY+frameH);
        canvas.drawBitmap(bmpBoom,boomX-currentFrameIndex*frameW,boomY,paint);
        canvas.restore();
    }

    public  void logic(){
        if (currentFrameIndex<totleFrame){
            currentFrameIndex++;
        }else {
            palyEnd=true;
        }
    }
}
