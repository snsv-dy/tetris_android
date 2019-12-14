package app.tets.gameObjects;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

/**
 * Created by Jacek on 2018-09-11.
 */

public class Pause {
    public String[] options = new String[]{
            "Unpause",
            "Settings",
            "Exit"
    };
    public Paint buttonPaint;

    public int textSize;
    public Paint textPaint;

    //Pause text margin, pause text size, buttons spacing, buttons margin
    public float[] screenPercents = new float[]{
            0.05f, 0.15f, 0.05f, 0.1f
    };

    public int[] screenDims;
    public int[] screenPoses;

    public int width, height;
    public int left, top;

    public int[] buttonDims;
    public int buttonLeft;
    public Rect[] buttonsRects;

    public int pauseTextLeft;
    public int pauseTextWidth;
    public int pauseTextSize;
    public Paint pauseTextPaint;
    public int[] pauseTextDims;

    public boolean active;

    public Pause(int left, int top, int width, int height){
        this.active = false;

        buttonPaint = new Paint();
        buttonPaint.setColor(Color.BLUE);

        this.textSize = 80;
        textPaint = new Paint();
        textPaint.setTextSize(this.textSize);
        textPaint.setColor(Color.WHITE);
        textPaint.setTextAlign(Paint.Align.LEFT);

        this.width = width;
        this.height = height;
        this.top = top;
        this.left = left;
        // 4 = button size
        screenDims = new int[]{
                (int)(screenPercents[0] * this.height),
                (int)(screenPercents[1] * this.height),
                (int)(screenPercents[2] * this.height),
                (int)(screenPercents[3] * this.height),
                0
        };

        screenDims[4] = (this.height - (screenDims[0] * 2 + screenDims[1] + options.length * screenDims[2] + 2 * screenDims[3])) / options.length;
        //pause text pos,
        //first button start
        screenPoses = new int[]{
                screenDims[0],
                2 * screenDims[0] + screenDims[1] + screenDims[2]
        };

        this.buttonDims = new int[]{
                (int)(this.width * 0.7f),
                screenDims[4]
        };
        this.buttonLeft = (int)((this.width - this.buttonDims[0]) / 2);

        this.pauseTextWidth = (int)(this.width * 0.85f);
        this.pauseTextSize = 100;
        this.pauseTextLeft = (int)(this.width - pauseTextWidth) / 2;
        this.pauseTextPaint = new Paint();
        this.pauseTextPaint.setTextSize(100);
        this.pauseTextPaint.setColor(Color.WHITE);

        System.out.println("PAUSE: !!!! " + this.left + ", " + this.top + ", " + this.width + ", " + this.height);

        Rect ptr = new Rect();
        pauseTextPaint.getTextBounds("Paused", 0, "Paused".length(), ptr);
        pauseTextDims = new int[]{
                (pauseTextWidth - ptr.width()) / 2 - ptr.left,
                (screenDims[1] - ptr.height()) / 2 - ptr.top
        };

        // Initing buttons rects for pointer checking
        buttonsRects = new Rect[this.options.length];
        for(int i = 0; i < this.options.length; i++){
            int y = i * (screenDims[2] + screenDims[4]);
            buttonsRects[i] = new Rect();
            buttonsRects[i].set(
                    this.left + this.buttonLeft,
                    this.top + screenPoses[1] + y,
                    this.left + this.buttonLeft + this.buttonDims[0],
                    this.top + screenPoses[1] + y + buttonDims[1]);
           /* buttonsRects[i].set(
                    this.left,
                    this.top,
                    this.left + 120,
                    this.top + 120
            )*/;

            /*
            buttonsRects[i].set(
                    0 + this.buttonLeft,
                    this.top + screenPoses[1] + y,
                    this.buttonLeft + 0 + this.buttonDims[0],
                    this.top + screenPoses[1] + y + buttonDims[1]);*/
        }
    }

    public void draw(Canvas canvas){
        //Drawing Pause
        canvas.drawRect(this.left + pauseTextLeft, this.top + screenDims[0], this.left + pauseTextLeft + pauseTextWidth, this.top + screenDims[1] + screenDims[0], buttonPaint);
        canvas.drawText("Paused", this.left + pauseTextLeft + pauseTextDims[0], this.top + screenDims[0] + pauseTextDims[1], pauseTextPaint);

        //Drawing options
        for(int i = 0; i < options.length; i++){
            int y = i * (screenDims[2] + screenDims[4]);
            /*canvas.drawRect(
                    this.left + this.buttonLeft,
                    this.top + screenPoses[1] + y,
                    this.buttonLeft + this.left + this.buttonDims[0],
                    this.top + screenPoses[1] + y + buttonDims[1],
                    buttonPaint);*/
            canvas.drawRect(buttonsRects[i], buttonPaint);

            Rect tr = new Rect();
            textPaint.getTextBounds(options[i], 0, options[i].length(), tr);
            int textx = (int)((buttonDims[0]  - tr.width()) / 2f  - tr.left);
            int texty = (int)(buttonDims[1] / 2f + tr.height() / 2f - tr.bottom);
            canvas.drawText(options[i], this.left + this.buttonLeft + textx, this.top + y + screenPoses[1] + texty, textPaint);
        }
    }

    public void performAction(int butid){
        switch (butid){
            case 0:{
                this.active = false;
            }break;
            default:{

            }break;
        }
    }

    public void touchAt(int x, int y){

        //Check if button was touched
        int butid = -1;
        for(int i = 0; i < buttonsRects.length; i++){
            if(buttonsRects[i].contains(x, y)){
                butid = i;
                break;
            }
        }

        System.out.println(buttonsRects[0].toString() + ", " + x + ", " + y);

        if(butid > -1){
            this.performAction(butid);
        }
    }
}
