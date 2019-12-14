package app.tets.gameObjects;

/**
 * Created by Jacek on 2018-09-03.
 */


import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.provider.Settings;

public class Block {
    public Shape shapes[] = null;

    public int rotation = 0;
    public int block = 6;

    public boolean[][] tdata = null;

    public int[] pos = {0, 0};
    public int[] blocks_starting = {0, 0};

    public int fps = 1000;
    public long lastNano = 0;

    public Ground ground_obj;

    public Paint bPaint;
    public int color = 0;

    public float block_size;
    public Hud hud_obj;

    public boolean gameOver;

    public void setHud_obj(Hud hud){
        this.hud_obj = hud;
    }
    public int left, top;

    public void init(){

        this.tdata = this.shapes[this.block].getRotation(this.rotation);

        check_where_are_block_starting();
        int moveabit = ground_obj.check_if_in_bounds(this.pos, this.tdata, this.blocks_starting);
        this.pos[0] -= moveabit;
    }

    public void addScore(int sc){
        this.hud_obj.addScore(sc);
    }

    public void check_where_are_block_starting(){
        int lstarting = 100;
        int rstarting = 0;

        for(int i = 0; i < this.tdata.length; i++){
            for(int j = 0; j < this.tdata[0].length; j++){
                if(this.tdata[i][j]){
                    lstarting = Math.min(lstarting, j);
                    rstarting = Math.max(rstarting, j);
                }
            }
        }

        this.blocks_starting[0] = lstarting;
        this.blocks_starting[1] = rstarting;
    }

    public void check_if_hold_tapped(int x, int y){
        if(this.hud_obj.check_if_in_hold(x, y)){
            this.holdBlock();
        }
    }

    public void draw(Canvas canvas){
        for(int i = 0; i < this.tdata.length; i++){
            int y = this.pos[1] + i;
            for(int j = 0; j < this.tdata[0].length; j++){
                if(this.tdata[i][j]){
                    int x = this.pos[0] + j;
                    canvas.drawRect(x * block_size + this.left, y * block_size + this.top, (x + 1) * block_size + this.left, (y + 1) * block_size + this.top, this.paints[this.color]);

                }
            }
        }
    }

    public void update(){
        long tnano = System.nanoTime();
        long dtime = (tnano - lastNano) / 1000000;
        if(dtime >= fps || this.fallVal){
            this.fallVal = false;
            // Update here

            boolean ret = ground_obj.try_add(this.pos, this.tdata, this.color);
            if(ret && this.pos[1] == 0){
                this.gameOver = true;
            }
            if(!ret){
                this.pos[1]++;
            }else{
                this.resetBlock();
               /* this.pos[1] = 0;
                this.pos[0] = this.ground_obj.w / 2;
                this.block = (int)(Math.random() * 7);
                this.rotation = 0;
                this.init();*/
            }


            lastNano = tnano;
        }
    }

    public void drop(){
        if(this.ground_obj.drop(this.pos, this.tdata, this.color)){
            this.resetBlock();
        }
    }

    private boolean fallVal = false;
    public void fallBlock(){
        fallVal = true;
    }

    public void resetBlock()
    {
        int[] bl = hud_obj.getNextBlock();
        this.color = bl[1];
        this.pos[1] = 0;
        this.pos[0] = this.ground_obj.w / 2;
        this.block = bl[0];
        this.rotation = 0;
        this.init();
        lastNano = System.nanoTime();
    }

    public void holdBlock(){
        this.tryHold();
    }

    public void swapHold(){
        int holdvalue = this.hud_obj.setGetHold(this.block);
        int newBlock = holdvalue;
        if(holdvalue == -1){
            newBlock = hud_obj.getNextBlock()[0];
        }

        this.color = newBlock;
        this.block = newBlock;
        this.rotation = 0;
        this.init();
    }

    public void tryHold(){
        if(this.hud_obj.peekHold() >= 0) {
            boolean[][] hdata = this.shapes[this.hud_obj.peekHold()].getRotation(0);
            //int[] hstart = this.shapes[this.hud_obj.peekHold()].getBlocksStarting(0);
            if(this.ground_obj.checkIntersect(this.pos, hdata)){//if (!this.ground_obj.checkIntersect(pos, hdata)) {
                //int[] fits = ground_obj.check_if_fits(this.pos, hdata);
                //this.pos[0] += fits[0];
                //this.pos[1] += fits[1];
                this.swapHold();
            }
        }else{
            this.swapHold();
        }
    }

    public void tryMove(int dir) {
        switch (dir) {
            case 1: {
                if (this.ground_obj.check_side(this.pos, 0, this.tdata, this.blocks_starting)) {//if(this.pos[0] + this.blocks_starting[1] < this.ground_obj.w){
                    this.pos[0]--;
                    int mov = ground_obj.check_if_in_bounds(this.pos, this.tdata, this.blocks_starting);
                    this.pos[0] -= mov;
                }
            }
            break;
            case 0: {
                if (this.ground_obj.check_side(this.pos, 1, this.tdata, this.blocks_starting)) {//if(this.pos[0] + this.blocks_starting[0] > 0)
                    this.pos[0]++;
                    int mov = ground_obj.check_if_in_bounds(this.pos, this.tdata, this.blocks_starting);
                    this.pos[0] -= mov;
                }
                break;
            }
        }
    }

    public void try_rotate(int rotation){
        this.rotation = rotation;
        boolean[][] tdat = this.shapes[this.block].getRotation(this.rotation);
        if(this.ground_obj.checkIntersect(this.pos, tdat)){
            this.tdata = tdat;
            this.init();
        }
    }

    private Paint[] paints;

    public Block(Ground ground){
        this.gameOver = false;
        bPaint = new Paint();
        bPaint.setColor(Color.rgb(50, 255, 50));
        paints = new Paint[7];
        for(int i = 0; i < 7; i++){
            paints[i] = new Paint();
            paints[i].setColor(Color.HSVToColor(new float[]{ ((float)i / 7f) * 360f, 1f, 1f }));
        }

        this.ground_obj = ground;
        this.block_size = ground.block_size;

        this.left = this.ground_obj.frame_l;
        this.top = this.ground_obj.frame_t;

        lastNano = System.nanoTime();

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
        this.tdata = shapes[this.block].getRotation(this.rotation);
        this.init();
        System.out.println("BLOCK INITED");
    }

}

