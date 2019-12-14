package app.tets.startScreenObjects;

import android.content.Context;
import android.graphics.Canvas;
import android.view.SurfaceHolder;


/**
 * Created by Jacek on 2018-09-16.
 */

public class startScreenThread extends Thread{

    private SurfaceHolder holder_obj;
    private startScreenSurface surface_obj;
    private boolean running;

    public startScreenThread(SurfaceHolder holder, startScreenSurface surface_obj){
        this.holder_obj = holder;
        this.surface_obj = surface_obj;
        this.running = true;
    }

    @Override
    public void run(){
        while(this.running){

            if(!holder_obj.getSurface().isValid())
                continue;

            Canvas canvas = null;
            try{
                canvas = this.holder_obj.lockCanvas();

                synchronized (canvas){
                    // Do drawing
                    this.surface_obj.draw(canvas);
                    System.out.print("t");
                }
            }catch(Exception e){
                e.printStackTrace();
            }finally {
                if(canvas != null){
                    this.holder_obj.unlockCanvasAndPost(canvas);
                }
            }
        }
    }

    public void setRunning(boolean val){
        this.running = val;
    }
}
