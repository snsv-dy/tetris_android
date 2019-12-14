package app.tets.startScreenObjects;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import app.tets.gameObjects.Shape;

/**
 * Created by Jacek on 2018-09-16.
 */


public class startScreenSurface extends SurfaceView implements SurfaceHolder.Callback {
    private startScreenThread thread_obj;

    public startScreenSurface(Context context){
        super(context);

        this.setFocusable(true);

        this.getHolder().addCallback(this);
        //tp = new Paint();
       // tp.setColor(Color.BLUE);
    }

    //private Paint tp;

    public void pause(){
        this.thread_obj.setRunning(false);
        try{
            this.thread_obj.join();
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    public void resume(){
        this.thread_obj = new startScreenThread(getHolder(), this);
        this.thread_obj.start();
    }

    @Override
    public void draw(Canvas canvas){
        super.draw(canvas);

        if(initt) {
            canvas.drawRGB(36, 36, 107);

            //canvas.drawRect(100, 100, 300, 300, tp);
            this.fallingBlocks_obj.draw(canvas);
            //this.menuOptions_obj.draw(canvas);
        }
    }

    public Shape[] shapes;
    public Bitmap[][][] shape_bitmaps;
    Paint[] paints;
    public BackgroundFallingBlocks fallingBlocks_obj;
    public MenuOptions menuOptions_obj;

    public boolean initt = false;

    public void inited(){
        this.initt = true;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder){
        //this.thread_obj = new startScreenThread(this.getHolder(), this);
        //
        // INITING
        /*
        this.initShapes();
        this.initBitmaps();
        this.fallingBlocks_obj = new BackgroundFallingBlocks(new int[]{getWidth() / 15, getWidth() / 16 / 1, getWidth() / 17 / 1}, getWidth(), getHeight(), shape_bitmaps);
        this.menuOptions_obj = new MenuOptions(this.getWidth(), this.getHeight());*/

        //this.thread_obj.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int w, int h){

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder){

    }

    public void initBitmaps(){
        paints = new Paint[7];
        for(int i = 0; i < 7; i++){
            paints[i] = new Paint();
            paints[i].setColor(Color.HSVToColor(new float[]{ ((float)i / 7f) * 360f, 1f, 1f }));
        }

        this.shape_bitmaps = new Bitmap[3][shapes.length][4];

        /*Bitmap[][] base_bitmaps = new Bitmap[shapes.length][4];

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
        }*/

        int block_size = getWidth() / 15;
        int block_size1 = block_size / 2;
        int block_size2 = block_size1 / 2;
        for(int k = 0; k < shapes.length; k++){
            for(int l = 0; l < 4; l++){
                boolean[][] tdata = shapes[k].getRotation(l);

                shape_bitmaps[0][k][l] = Bitmap.createBitmap(block_size * tdata[0].length, block_size * tdata.length, Bitmap.Config.ARGB_8888);
                shape_bitmaps[1][k][l] = Bitmap.createBitmap(block_size * tdata[0].length, block_size * tdata.length, Bitmap.Config.ARGB_8888);
                shape_bitmaps[2][k][l] = Bitmap.createBitmap(block_size * tdata[0].length, block_size * tdata.length, Bitmap.Config.ARGB_8888);
                Canvas tcanvas = new Canvas(shape_bitmaps[0][k][l]);
                Canvas dcanvas = new Canvas(shape_bitmaps[1][k][l]);
                Canvas ccanvas = new Canvas(shape_bitmaps[2][k][l]);
                for(int i = 0; i < tdata.length; i++){
                    for(int j = 0; j < tdata[0].length; j++){
                        if(tdata[i][j]){
                            tcanvas.drawRect(j*block_size, i*block_size, (j + 1) *  block_size, (i + 1) * block_size, this.paints[k]);
                            dcanvas.drawRect(j*block_size1, i*block_size1, (j + 1) *  block_size1, (i + 1) * block_size1, this.paints[k]);
                            ccanvas.drawRect(j*block_size2, i*block_size2, (j + 1) *  block_size2, (i + 1) * block_size2, this.paints[k]);
                        }
                    }
                }
            }
        }
    }

    public void initShapes(){

        shapes = new Shape[7];
        boolean tdata[][][] ={
                {
                        {false, true, false},
                        {true, true, true},
                        {false, false, false}
                },
                {
                        {false, true, false},
                        {false, true, true},
                        {false, true, false}
                },
                {
                        {false, false, false},
                        {true, true, true},
                        {false, true, false}
                },
                {
                        {false, true, false},
                        {true, true, false},
                        {false, true, false}
                }
        };
        shapes[0] = new Shape(tdata);
        shapes[1] = new Shape(null);
        shapes[1].addData(new boolean[][][]{
                {
                        {false, true, true},
                        {true, true, false},
                        {false, false, false}
                },
                {
                        {false, true, false},
                        {false, true, true},
                        {false, false, true}
                },
                {
                        {false, false, false},
                        {false, true, true},
                        {true, true, false}
                },
                {
                        {true, false, false},
                        {true, true, false},
                        {false, true, false}
                }
        });
        shapes[2] = new Shape(null);
        shapes[2].addData(new boolean[][][]{
                {
                        {true, true, false},
                        {false, true, true},
                        {false, false, false}
                },
                {
                        {false, false, true},
                        {false, true, true},
                        {false, true, false}
                },
                {
                        {false,  false, false},
                        {true, true, false},
                        {false, true, true}
                },
                {
                        {false, true, false},
                        {true, true, false},
                        {true, false, false}
                }
        });
        shapes[3] = new Shape(null);
        shapes[3].addData(new boolean[][][]{
                {
                        {true, false, false},
                        {true, true, true},
                        {false, false, false}
                },
                {
                        {false, true, true},
                        {false, true, false},
                        {false, true, false}
                },
                {
                        {false, false, false},
                        {true, true, true},
                        {false, false, true}
                },
                {
                        {false, true, false},
                        {false, true, false},
                        {true, true, false}
                }
        });
        shapes[4] = new Shape(null);
        shapes[4].addData(new boolean[][][]{
                {
                        {false, true, false},
                        {false, true, false},
                        {false, true, true}
                },
                {
                        {false, false, false},
                        {true, true, true},
                        {true, false, false}
                },
                {
                        {true, true, false},
                        {false, true, false},
                        {false, true, false}
                },
                {
                        {false, false, true},
                        {true, true, true},
                        {false, false, false}
                }
        });
        shapes[5] = new Shape(null);
        shapes[5].addData(new boolean[][][]{
                {
                        {true, true},
                        {true, true}
                },{
                {true, true},
                {true, true}
        },{
                {true, true},
                {true, true}
        },{
                {true, true},
                {true, true}
        }
        });
        shapes[6] = new Shape(null);
        shapes[6].addData(new boolean[][][]{
                {
                        {false, false, true, false},
                        {false, false, true, false},
                        {false, false, true, false},
                        {false, false, true, false}
                },
                {
                        {false, false, false, false},
                        {false, false, false, false},
                        {true, true, true, true},
                        {false, false, false, false}
                },
                {
                        {false, true, false, false},
                        {false, true, false, false},
                        {false, true, false, false},
                        {false, true, false, false}
                },
                {
                        {false, false, false, false},
                        {true, true, true, true},
                        {false, false, false, false},
                        {false, false, false, false}
                }
        });
    }

}
