package app.tets;

/**
 * Created by Jacek on 2018-09-03.
 */


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import app.tets.gameObjects.Block;
import app.tets.gameObjects.Controls;
import app.tets.gameObjects.Ground;
import app.tets.gameObjects.Hud;
import app.tets.gameObjects.LayoutManager;
import app.tets.gameObjects.LogicTracker;
import app.tets.gameObjects.Pause;
import app.tets.gameObjects.ThatOneFuckingBitmapThahIHadToMakeClassFor;

public class gameView extends SurfaceView implements SurfaceHolder.Callback, Runnable {
    SurfaceHolder ourHolder;
    boolean isRunning = true;

    public Ground ground_obj;
    public Block block_obj;
    public Hud hud_obj;
    public Pause pause_obj;
    public Controls controls_obj;
    public GameThread thread_obj = null;
    public SurfaceHolder holder_obj;
    public LogicTracker logic_obj;
    public Thread tthread;

    public Context context;

    public ThatOneFuckingBitmapThahIHadToMakeClassFor bitmaps;

    public void setBitmaps(ThatOneFuckingBitmapThahIHadToMakeClassFor bt){
        this.bitmaps = bt;
    }

    public gameView(Context context){
        super(context);
        this.context = context;

        this.setFocusable(true);
        this.getHolder().addCallback(this);
        this.holder_obj = getHolder();

        //this.ground_obj = new Ground(this.getWidth(), this.getHeight(), 0, 0);
        //this.block_obj = new Block(ground_obj);

        //this.layout_obj = new LayoutManager();

        gameOverPaint = new Paint();
        gameOverPaint.setTextSize(100);

        /*ourHolder = getHolder();
        ourThread = new Thread(this);
        ourThread.start();*/
    }

    public int deltaThresh = 2;

    public int keyDelayVal = 1;
    public int[] keyDelay = {0, 0, 0, 0};

    public boolean pretapped = false;
    public boolean tapped = false;

    public boolean groundInited = false;

    public float[] groundDims = {0f, 0.1f, 0.75f, 0.9f};
    public float[] hudDims = {0.8f, 0f, 1f - groundDims[2] - 0.05f, 1f};
    public float[] scoreDims = {0f, 0f, groundDims[2], groundDims[1]};

    public LayoutManager layout_obj;

    public boolean pause;
    public boolean block_gameOver = false;
    public Paint gameOverPaint;

    public void pause(){
        //this.thread_obj.setRunning(false);
        this.isRunning = false;
        while(true) {
            try {
                //thread_obj.join();
                tthread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            break;
        }

        tthread = null;
        this.pause_obj.active = true;
        System.out.println("PAUSED @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
    }

    public void resume(){
        //this.thread_obj = new GameThread(getHolder(), this);
        this.tthread = new Thread(this);
        this.tthread.start();
        this.isRunning = true;
            //this.thread_obj.start();
        System.out.println("RESUMED @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
    }

    @Override
    public void draw(Canvas canvas){
        super.draw(canvas);

        //
        //  DRAWING THINGS
        //

        canvas.drawRGB(36, 36, 107);
        if(!block_gameOver && !this.pause_obj.active) {


            if(controls_obj.tryMoveLeft()){
                block_obj.tryMove(1);
            }else if(controls_obj.tryMoveRight()){
                block_obj.tryMove(0);
            }else if(controls_obj.tryFall()){
                block_obj.fallBlock();
            }else if(controls_obj.tryDrop()){
                block_obj.drop();
            }

            if(this.controls_obj.tryRotate()){
                block_obj.try_rotate((block_obj.rotation + 1) % 4);
            }
        }


        if(!stopped && !this.pause_obj.active) {
            //this.ground_obj.update();
            this.block_obj.update();
        }

        if(this.block_obj.gameOver){
            block_gameOver = true;
            this.stopped = true;
            this.logic_obj.onGameOver();
        }


        this.ground_obj.draw(canvas);
        this.block_obj.draw(canvas);
        this.hud_obj.draw(canvas);

        if(block_gameOver){
            int cx = (int)(groundDims[2] * this.getWidth()) / 2;
            int cy = (int)(groundDims[3] * this.getHeight()) / 2;
            canvas.drawText("Game", cx - 30, cy - 50, gameOverPaint);
            canvas.drawText("Over", cx - 15, cy + 50, gameOverPaint);
        }

        //
        //  Drawing Pause
        //
        if(this.pause_obj.active){
            this.pause_obj.draw(canvas);
        }

        //
        //  END OF DRAWING
        //
    }

    public boolean inited = true;

    @Override
    public void surfaceCreated(SurfaceHolder holder){
        //if(this.thread_obj == null)
        //     this.thread_obj = new GameThread(this.getHolder(), this);
        //if(!inited) {
          //  while (this.getWidth() == 0) {
            //}

            //
            //  INITING THINGS
            //

           /* controls_obj = new Controls();

            int glob_margin = (int) (this.getWidth() * 0.05f);

            this.layout_obj.setScreenMargins(glob_margin, glob_margin, glob_margin, glob_margin);
            this.layout_obj.setScreenDims(this.getWidth(), this.getHeight());


            int[] ground_dims = this.layout_obj.getGroundDims();
            System.out.println("GROUND DIMS: " + ground_dims[0] + ", " + ground_dims[1] + ", " + ground_dims[2] + ", " + ground_dims[3]);
            ground_obj = new Ground(
                    ground_dims[0],
                    ground_dims[1],
                    ground_dims[2],
                    ground_dims[3]
            );

            ground_obj.pubinit();
            pause_obj = new Pause(
                    this.ground_obj.frame_l,
                    this.ground_obj.frame_t,
                    this.ground_obj.width,
                    this.ground_obj.height
            );
            pause_obj.active = true;


            this.block_obj = new Block(ground_obj);
            this.groundInited = true;

            Paint blackPaint = new Paint();
            blackPaint.setColor(Color.BLACK);
            blackPaint.setStrokeWidth(2f);

            int[] score_dims_calc = new int[]{
                    (int) (scoreDims[0] * this.getWidth()),
                    (int) (scoreDims[1] * this.getHeight()),
                    (int) (scoreDims[2] * this.getWidth()),
                    (int) (scoreDims[3] * this.getHeight())
            };

        //this.hud_obj = new Hud(
          //      (int)(hudDims[0] * this.getWidth()),
            //    (int)(hudDims[1] * this.getHeight()),
              //  (int)(hudDims[2] * this.getWidth()),
              //  (int)(hudDims[3] * this.getHeight()),
              //  score_dims_calc,
               // bitmaps);
            int[] hud_dims = this.layout_obj.getHudDims();
            this.hud_obj = new Hud(
                    hud_dims[0],
                    hud_dims[1],
                    hud_dims[2],
                    hud_dims[3],
                    this.layout_obj.getScoreDims(),
                    bitmaps);

            this.hud_obj.init(this.block_obj.shapes);
            this.hud_obj.setBitmaps(this.bitmaps);
            block_obj.setHud_obj(this.hud_obj);
            ground_obj.setBlockObj(this.block_obj);

            System.out.println("view dims: " + this.getWidth() + ", " + this.getHeight());

            // boolean block_gameOver = false;
            gameOverPaint = new Paint();
            gameOverPaint.setTextSize(100);

            //
            //  END OF INITING THINGS
            //

            this.inited = true;*/

            //this.thread_obj.start();
        //this.tthread = new Thread(this);
        //tthread.start();
       // }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int w, int h){

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder){

    }

    @Override
    public void run(){
        while(isRunning) {
            if (!holder_obj.getSurface().isValid())
                continue;

            Canvas canvas = null;
            try {
                canvas = this.holder_obj.lockCanvas();

                synchronized (canvas) {
                    // Do drawing
                    this.draw(canvas);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (canvas != null) {
                    this.holder_obj.unlockCanvasAndPost(canvas);
                }
            }
        }
        /*Paint pt = new Paint();
        pt.setColor(Color.BLACK);
        pt.setTextSize(50);

        while(!this.groundInited && this.getWidth() == 0){

        }

        controls_obj = new Controls();

        int glob_margin = (int)(this.getWidth() * 0.05f);

        this.layout_obj.setScreenMargins(glob_margin, glob_margin, glob_margin, glob_margin);
        this.layout_obj.setScreenDims(this.getWidth(), this.getHeight());



        int[] ground_dims = this.layout_obj.getGroundDims();
        System.out.println("GROUND DIMS: " + ground_dims[0] + ", " + ground_dims[1] + ", " + ground_dims[2] + ", " + ground_dims[3]);
        ground_obj = new Ground(
                ground_dims[0],
                ground_dims[1],
                ground_dims[2],
                ground_dims[3]
                );

        ground_obj.pubinit();
        pause_obj = new Pause(
                this.ground_obj.frame_l,
                this.ground_obj.frame_t,
                this.ground_obj.width,
                this.ground_obj.height
        );
        pause_obj.active = true;


        this.block_obj = new Block(ground_obj);
        this.groundInited = true;

        Paint blackPaint = new Paint();
        blackPaint.setColor(Color.BLACK);
        blackPaint.setStrokeWidth(2f);

        int[] score_dims_calc = new int[]{
                (int)(scoreDims[0] * this.getWidth()),
                (int)(scoreDims[1] * this.getHeight()),
                (int)(scoreDims[2] * this.getWidth()),
                (int)(scoreDims[3] * this.getHeight())
        };

        this.hud_obj = new Hud(
                (int)(hudDims[0] * this.getWidth()),
                (int)(hudDims[1] * this.getHeight()),
                (int)(hudDims[2] * this.getWidth()),
                (int)(hudDims[3] * this.getHeight()),
                score_dims_calc,
                bitmaps);
        int[] hud_dims = this.layout_obj.getHudDims();
        this.hud_obj = new Hud(
                hud_dims[0],
                hud_dims[1],
                hud_dims[2],
                hud_dims[3],
                this.layout_obj.getScoreDims(),
                bitmaps);

        this.hud_obj.init(this.block_obj.shapes);
        this.hud_obj.setBitmaps(this.bitmaps);
        block_obj.setHud_obj(this.hud_obj);
        ground_obj.setBlockObj(this.block_obj);

        System.out.println("view dims: " + this.getWidth() + ", " + this.getHeight());

        boolean block_gameOver = false;
        Paint gameOverPaint = new Paint();
        gameOverPaint.setTextSize(100);


        while(isRunning && this.groundInited){
            if(!ourHolder.getSurface().isValid())
                continue;

            Canvas canvas = null;

            try{
                canvas = ourHolder.lockCanvas();

                canvas.drawRGB(36, 36, 107);
                if(!block_gameOver && !this.pause_obj.active) {


                    if(controls_obj.tryMoveLeft()){
                        block_obj.tryMove(1);
                    }else if(controls_obj.tryMoveRight()){
                        block_obj.tryMove(0);
                    }else if(controls_obj.tryFall()){
                        block_obj.fallBlock();
                    }else if(controls_obj.tryDrop()){
                        block_obj.drop();
                    }

                    if(this.controls_obj.tryRotate()){
                        block_obj.try_rotate((block_obj.rotation + 1) % 4);
                    }
                }


                if(!stopped && !this.pause_obj.active) {
                    //this.ground_obj.update();
                    this.block_obj.update();
                }

                if(this.block_obj.gameOver){
                    block_gameOver = true;
                    this.stopped = true;
                }


                this.ground_obj.draw(canvas);
                this.block_obj.draw(canvas);
                this.hud_obj.draw(canvas);

                if(block_gameOver){
                    int cx = (int)(groundDims[2] * this.getWidth()) / 2;
                    int cy = (int)(groundDims[3] * this.getHeight()) / 2;
                    canvas.drawText("Game", cx - 30, cy - 50, gameOverPaint);
                    canvas.drawText("Over", cx - 15, cy + 50, gameOverPaint);
                }

                //
                //  Drawing Pause
                //
                if(this.pause_obj.active){
                    this.pause_obj.draw(canvas);
                }


            }catch (Exception e){

            }finally {
                ourHolder.unlockCanvasAndPost(canvas);
            }


        }*/
    }

    public int[] moveDelay = {0, 0, 0, 0};
    public int moveDelayVal = 3;

    // l, r, u, d
    public int[] mousePos = {0, 0};
    public int[] lastPos = {0, 0};
    public int[] deltaDir = {0, 0};

    public void clickLeft(){

        block_obj.tryMove(1);
    }
    public void clickUp(){
        ground_obj.dump_data();
    }
    public void clickDown(){
        this.block_obj.drop();
    }
    public void clickRight(){
        block_obj.tryMove(0);
    }

    public boolean stopped = false;

    public void stop(){
        stopped = !stopped;
    }
    public void clickHold(){this.block_obj.holdBlock();}
    public void clickPause(){this.pause_obj.active = true;}


    @Override
    public boolean onTouchEvent(MotionEvent e){
        int action = e.getAction();

        int x = (int)e.getX();
        int y = (int)e.getY();

        if(!this.pause_obj.active) {
            controls_obj.checkMove(e);

            //
            // Hud checks
            //
            if(hud_obj.check_if_in_hold(x, y)){
                if(this.controls_obj.holdTapHandler()){
                    this.block_obj.check_if_hold_tapped(x, y);
                }

                //if(this.controls_obj.holdTapHandler()){
                //    this.block_obj.tryHold();
                //}
            }
            if(hud_obj.check_if_pause_tapped(x, y)){
                this.pause_obj.active = true;
            }
        }else{
            if(action == MotionEvent.ACTION_UP){
                this.pause_obj.touchAt(x, y);
            }
        }


        return true;
    }


}

