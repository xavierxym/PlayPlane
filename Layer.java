package com.example.xiaoming.testgame;

import android.graphics.Canvas;

/**
 * Created by xiaoming on 2016/10/24.
 */
public abstract class Layer {

    int x=0;
    int y=0;
    int width=0;
    int height=0;
    boolean visible =true;

    Layer(int width,int height){
        setWidthImpl(width);
        setHeightImpl(height);
    }

    public  void setPosition(int x,int y){
        this.x=x;
        this.y=y;
    }
    public  void move(int dx, int dy){
        x+=dx;
        y+=dy;
    }
    public  final  int getX(){
        return x;
    }
    public  final  int getY(){
        return y;
    }

    public  final  int getWidth(){
        return width;
    }
    public  final  int getHeight(){
        return height;
    }

    public void  setVisible(boolean visible){
        this.visible=visible;
    }


public  final boolean isVisible(){
    return  visible;
}

    public  abstract  void paint(Canvas c);

    void setWidthImpl(int width){

        if (width<0){
            throw  new IllegalArgumentException();
        }
        this.width=width;
    }
    void setHeightImpl(int height){

        if (height<0){
            throw  new IllegalArgumentException();
        }
        this.height=height;
    }
}
