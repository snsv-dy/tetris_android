package app.tets.gameObjects;

import android.view.MotionEvent;

/**
 * Created by Jacek on 2018-09-14.
 */

public class Controls {
    public float moveThresh;
    public float dropThresh;
    public boolean dropped = false;
    public float fallThresh;
    public float fastMove;
    public boolean[] ready;

    public float[] lastPos;

    public Controls(){
        this.moveThresh = this.fallThresh = 40;
        this.dropThresh = 120;
        this.fastMove = 70;
        this.ready = new boolean[]{false, false, false, false, false};
        this.lastPos = new float[]{-1, -1};
        this.skipframes = new int[]{0, 0};
        this.skipval = 2;
        this.tap_time = -1;
        this.tap_thr = 100;
        this.tapped = false;
    }

    public boolean tapped;

    public boolean tap_down, tap_up;

    public int[] skipframes;
    public int skipval;


    public long tap_time;
    public long tap_thr;

    public boolean moving = false;


    public boolean hold_tapped = false;
    public boolean holdTapHandler(){
        if(!hold_tapped){
            this.hold_tapped = true;
            return true;
        }
        return false;
    }

    public void checkMove(MotionEvent e){
        float x = e.getX();
        float y = e.getY();


        //
        // Tapping part
        //
        int action = e.getAction();
        if(action == MotionEvent.ACTION_DOWN){
            this.lastPos[0] = x;
            this.lastPos[1] = y;
            this.tap_time = System.nanoTime() / 1000000;
            this.moving = false;

            this.tap_down = true;
            this.hold_tapped = false;
        }
        if(action == MotionEvent.ACTION_UP){
            long curTime = System.nanoTime() / 1000000;
            if(!hold_tapped && !moving && !this.tapped && (curTime - this.tap_time) < this.tap_thr){
                this.ready[2] = true;
                this.tap_time = -1;
                this.tapped = true;

                this.tap_down = false;
                this.tap_up = true;
            }

            this.dropped = false;
        }

        //
        // Moving part
        //
        if(!tapped) {
            if (lastPos[0] > -1) {
                float dx = this.lastPos[0] - x;
                if (dx > this.moveThresh) { // Left
                    this.moving = true;
                    this.ready[0] = true;
                    lastPos[0] = -1;

                } else if (dx < -this.moveThresh) { // Right
                    this.moving = true;
                    this.ready[1] = true;
                    lastPos[0] = -1;
                }
            } else {
                this.lastPos[0] = x;
                this.lastPos[1] = y;
            }

            if(lastPos[1] > -1){
                float dy = this.lastPos[1] - y;
                if(dy > this.dropThresh){ // up
                    if(!dropped) {
                        this.moving = true;
                        this.ready[4] = true;
                        this.lastPos[1] = -1;
                        this.dropped = true;
                    }
                }else if(dy < -this.fallThresh) { // down
                    this.moving = true;
                    this.ready[3] = true;
                    lastPos[1] = -1;
                }
            }else{
                lastPos[1] = y;
            }
        }else{
            tapped = false;
        }




    }

    public boolean tryDrop(){
        if(this.ready[4]){
            this.ready[4] = false;
            return true;
        }
        return false;
    }

    public boolean tryFall(){
        if(this.ready[3]){
            this.ready[3] = false;
            return true;
        }
        return false;
    }

    public boolean tryRotate(){
        if(this.ready[2]){
            this.ready[2] = false;
            return true;
        }
        return false;
    }

    public boolean tryMoveRight(){

        if(this.ready[1]){
            this.ready[1] = false;
            return true;
        }
        return false;
    }

    public boolean tryMoveLeft(){
        if(this.ready[0]){
            this.ready[0] = false;
            return true;
        }
        return false;
    }

}
