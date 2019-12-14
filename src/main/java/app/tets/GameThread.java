package app.tets;

import android.content.Context;
import android.graphics.Canvas;
import android.view.SurfaceHolder;

/**
 * Created by Jacek on 2018-09-13.
 */

public class GameThread extends Thread{

    private SurfaceHolder holder_obj;
    private gameView surface_obj;
    private boolean running;

    public GameThread(SurfaceHolder holder, gameView surface_obj){
        this.holder_obj = holder;
        this.surface_obj = surface_obj;
        this.running = true;
    }

    @Override
    public void run(){
        while(running){

            if(!holder_obj.getSurface().isValid())
                continue;

            Canvas canvas = null;
            try{
                canvas = this.holder_obj.lockCanvas();

                synchronized (canvas){
                    // Do drawing
                    this.surface_obj.draw(canvas);
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
