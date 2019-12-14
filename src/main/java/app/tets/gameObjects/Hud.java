package app.tets.gameObjects;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import java.util.Vector;

import app.tets.R;

/**
 * Created by Jacek on 2018-09-08.
 */

public class Hud {
    public Shape shapes[];

    public int left;
    public int top;
    public int width;
    public int height;

    public int queueSize = 5;
    public Vector<int[]> blockQueue;
    public int numBlocks = 7;

    public int blockQueueTop = 100;
    public int blockSize = (this.height - this.blockQueueTop) / (this.queueSize * 4);
    public int blockQueueLeft = this.left + (this.width - (6 * this.blockSize)) / 2;

    public int[] scoreDims;

    public int score;
    public Paint scorePaint;
    public Paint labelPaint;

    public ThatOneFuckingBitmapThahIHadToMakeClassFor bitmaps;

    public void setBitmaps(ThatOneFuckingBitmapThahIHadToMakeClassFor b){
        this.bitmaps = b;


    }

    public Hud(int left,  int top, int width, int height, int[] scoredim, ThatOneFuckingBitmapThahIHadToMakeClassFor b){
        this.left = left;
        this.top = top;
        this.width = width;
        this.height = height;

        this.scoreDims = scoredim;
        System.out.println("scoredim: " + scoredim[0] + ", " + scoredim[1] + ", " + scoredim[2] + ", " + scoredim[3]);


        blockQueue = new Vector<>();

        score = 0;
        scorePaint = new Paint();
        scorePaint.setTextSize(50);
        scorePaint.setColor(Color.LTGRAY);

        this.bitmaps=  b;

        this.pause_obj = new pauseButton(scoredim, bitmaps.bitmaps[0]);

    }

    public Resources resources;
    public Context context;

    public void setContext(Context context){
        this.context = context;
    }

    public void setResources(Resources res){
        this.resources = res;
    }

    Paint[] paints;

    public int[] queuePoses;
    public int[] queueDims;

    public Bitmap[] blocksBitmaps;

    public int[] hold_dim;
    public int hold;

    public float[] percents;
    public int margin;
    public int textSize;
    public int[] textsPoses;

    public pauseButton pause_obj;

    public LogicTracker logic_obj;

    public void setLogicObj(LogicTracker logobj){
        this.logic_obj = logobj;
    }

    class pauseButton{
        public int[] pos;
        public int[] dim;
        public int[] scoredims;
        public Paint this_paint;

        public int button_width;
        public Bitmap imageBitmap;

        public Rect rect_obj;

        public pauseButton(int[] dims, Bitmap pauseImage){
            this.scoredims = dims;
            this_paint = new Paint();
            this_paint.setColor(Color.RED);
            this.button_width = dims[3];

            rect_obj = new Rect();
            rect_obj.set(this.scoredims[0] + this.scoredims[2] - this.button_width, scoredims[1], this.scoredims[0] + this.scoredims[2], scoredims[1] + this.button_width);


            //this.imageBitmap = pauseImage;
            this.imageBitmap = Bitmap.createScaledBitmap(pauseImage, this.button_width, scoredims[3], false);
        }

        public void draw(Canvas canvas){
            //canvas.drawRect(scoredims[2] - this.button_width, scoredims[1], scoredims[2], scoredims[1] + scoredims[3], this.this_paint);
            canvas.drawBitmap(this.imageBitmap, this.scoredims[0] + this.scoredims[2] - this.button_width, scoredims[1], this.this_paint);
        }

        public boolean if_tapped(int x, int y){
            return this.rect_obj.contains(x, y);
        }
    }

    // Score and level position
    public int[] scores_pos;
    public int score_text_size;

    public void init(Shape[] shapes){
        queueSize = 5;
        paints = new Paint[7];
        for(int i = 0; i < 7; i++){
            paints[i] = new Paint();
            paints[i].setColor(Color.HSVToColor(new float[]{ ((float)i / 7f) * 360f, 1f, 1f }));
        }
        for(int i = 0; i < queueSize; i++){
            int blockNum = (int)(Math.random() * (float)numBlocks);
            blockQueue.add(new int[] {blockNum, blockNum});
        }

        this.shapes = shapes;

        System.out.println("HUD INITED");

        //blockQueueTop = 100;
        //blockSize = (this.height - this.blockQueueTop) / (this.queueSize * 6);
        //blockQueueLeft = this.left + (this.width - (6 * this.blockSize)) / 2;

        // single text size, margin between blocks
        this.percents = new float[]{
                0.05f, 0.015f
        };
        float blockPercent = (1f - (this.percents[1] * 8 + 2 * percents[0])) / (this.queueSize + 1);
        this.margin = (int)(this.height * percents[1]);
        this.textSize = (int)(this.height * percents[0]);

        this.labelPaint = new Paint();
        labelPaint.setTextSize(this.textSize);
        labelPaint.setColor(Color.LTGRAY);

        blockQueueLeft = this.left ;

        System.out.println("huds dums: " + this.width + ", " + this.height + ", " + this.left + ", " + this.top);

        queueDims = new int[]{
                this.width - 10, (int)(this.height * blockPercent)
        };
        hold_dim = new int[]{
                queueDims[0], queueDims[1]
        };

        blockQueueTop = 2 * this.textSize + 3 * this.margin + hold_dim[1] + this.top;

        //Hold, Next
        this.textsPoses = new int[]{ this.textSize / 2,  blockQueueTop - this.margin - this.textSize / 2};

       /* queueDims = new int[] {
                  this.width - 10, (this.height - blockQueueTop) / (this.queueSize + 1) - 50
        };*/

        //this.blockQueueTop += queueDims[1] + 100;
        //queueDims[1] = (this.height - blockQueueTop) / (this.queueSize) - 50;

        System.out.println("qdims = " + queueDims[0] + ", " + queueDims[1]);

        queuePoses = new int[queueSize];
        for(int i = 0; i < queueSize; i++){
            //50 - distance between blocks
            queuePoses[i] = blockQueueTop + (int)((queueDims[1] + this.margin) * i);
        }

        this.hold = -1;
        //this.hold_dim = new int[]{queueDims[0], queueDims[1]};

        int queueBlockSize = Math.min(queueDims[1] / 5, queueDims[0] / 5);
        blocksBitmaps = new Bitmap[shapes.length];
        for(int i = 0; i < shapes.length; i++){
            blocksBitmaps[i] = Bitmap.createBitmap(queueDims[0], queueDims[1], Bitmap.Config.ARGB_8888);
            Canvas tcan = new Canvas(blocksBitmaps[i]);
            tcan.drawRGB(19, 18, 89);
            int rot = 0;
            if(i == shapes.length - 1){
                rot = 3;
            }
            boolean[][] tdata = shapes[i].getRotation(rot);
            int tleft = (queueDims[0] - tdata[0].length * queueBlockSize) / 2;
            int ttop = (queueDims[1] - tdata.length * queueBlockSize) / 2;

            for(int j = 0; j < tdata.length; j++){
                for(int k = 0; k < tdata[0].length; k++){
                    if(tdata[j][k]){
                        tcan.drawRect(tleft + k * queueBlockSize, ttop + j * queueBlockSize, tleft + ( k + 1) * queueBlockSize, ttop + (j + 1) * queueBlockSize, paints[i]);
                    }
                }
            }
        }

        this.score_text_size = 50;

        this.scores_pos = new int[]{
                0,
                this.score_text_size
        };
    }

    public boolean check_if_pause_tapped(int x, int y){
        return this.pause_obj.if_tapped(x, y);
    }

    public boolean check_if_in_hold(int x, int y){
        Rect hr = new Rect();
        int hold_top = this.textSize + this.margin;
        hr.set(blockQueueLeft, hold_top, blockQueueLeft + hold_dim[0], hold_dim[1] + hold_top);
        return hr.contains(x, y);

    }


    public int setGetHold(int block_id){
        int ret = this.hold;
        this.hold = block_id;
        return ret;
    }

    public int peekHold(){
        return this.hold;
    }

    public int[] getNextBlock(){
        int[] ret = blockQueue.lastElement();
        blockQueue.remove(blockQueue.lastElement());
        int blockNum = (int)(Math.random() * (float)numBlocks);
        blockQueue.add(0, new int[]{blockNum, blockNum});
        return ret;
    }

    public void addScore(int numLines){
        this.score += Math.pow(3, numLines) * numBlocks;
    }

    public void draw(Canvas canvas){

        //blockSize = this.height / this.queueSize / 4;

        Paint pt = new Paint();
        pt.setColor(Color.LTGRAY);
        pt.setStrokeWidth(3);
        pt.setStyle(Paint.Style.STROKE);


        int hold_top = this.textSize + this.margin + this.top;

        canvas.drawText("Hold", blockQueueLeft, this.textsPoses[0] + this.top, scorePaint);

        if(this.hold > -1){
            canvas.drawBitmap(blocksBitmaps[this.hold], blockQueueLeft, hold_top, null);
        }
        canvas.drawRect(blockQueueLeft, hold_top, blockQueueLeft + hold_dim[0], hold_dim[1] + hold_top, pt);

        for(int i = blockQueue.size() - 1; i >= 0 ; i--){
            /*boolean[][] tshape = shapes[blockQueue.get(i)[0]].getRotation(0);

            for(int j = 0; j < tshape.length; j++){
                for(int k = 0; k < tshape[0].length; k++){
                    if(tshape[j][k]) {
                        int x = blockQueueLeft + blockSize * k;
                        int y = blockQueueTop + (queueSize - i - 1) * 6 * blockSize + blockSize * j;
                        canvas.drawRect(x, y, x + blockSize, y + blockSize, this.paints[blockQueue.get(i)[1]]);
                    }
                }
            }

            float thisy = i * 6 * blockSize - blockSize;*/

            /*canvas.drawLines(new float[]{
                    blockQueueLeft - blockSize, blockQueueTop + thisy,
                    bql, blockQueueTop + thisy,
                    bql, blockQueueTop + thisy,
                    bql, blockQueueTop + thisy + 5 * blockSize,
                    bql, blockQueueTop + thisy + 5 * blockSize,
                    blockQueueLeft - blockSize, blockQueueTop + thisy + 5 * blockSize,
                    blockQueueLeft - blockSize, blockQueueTop + thisy + 5 * blockSize,
                    blockQueueLeft - blockSize, blockQueueTop + thisy

            }, pt);*/
            canvas.drawBitmap(this.blocksBitmaps[blockQueue.get(queuePoses.length - 1 - i)[0]], blockQueueLeft, queuePoses[i], null);

            canvas.drawRect(blockQueueLeft, queuePoses[i],
                            blockQueueLeft + queueDims[0], queuePoses[i] + queueDims[1], pt);

            canvas.drawText("Next", blockQueueLeft, this.textsPoses[1], scorePaint);
            //canvas.drawText("blocks", blockQueueLeft, blockQueueTop - 10, scorePaint);


            //canvas.drawText(i + "", blockQueueLeft + blockSize * 1, blockQueueTop + i * 4 * blockSize, pt);
        }

        // Drawing score
        canvas.drawText("Score: " + formatScore(this.logic_obj.score), this.scoreDims[0], this.scores_pos[0] + this.scoreDims[1] + this.scorePaint.getTextSize() , this.scorePaint);
        // Drawing level
        canvas.drawText("Level: " + this.logic_obj.level, this.scoreDims[0], this.scores_pos[1] + this.scoreDims[1] + this.scorePaint.getTextSize() , this.scorePaint);

        // Drawing pause

        this.pause_obj.draw(canvas);

    }

    public String formatScore(int score){
        String ret = score + "";

        int numzeros = 10;

        for(int i = ret.length(); i < numzeros; i++){
            ret = "0" + ret;
        }

        return ret;
    }


}
