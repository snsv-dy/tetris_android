package app.tets.startScreenObjects;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Created by Jacek on 2018-09-18.
 */


public class BackgroundFallingBlocks{

    private int num_blocks_l1 = 10;
    private int num_blocks_l2 = 18;
    private int num_blocks_l3 = 32;

    private FallingBlock[] l1_blocks;
    private FallingBlock[] l2_blocks;
    private FallingBlock[] l3_blocks;

    public int width, height;

    public int[] layers_blocks_sizes;
    public int[][] layers_dims;

    public Bitmap[][][] shape_bitmaps;

    public BackgroundFallingBlocks(int[] sizes, int width, int height, Bitmap[][][] bitmaps){
        this.lastTime = System.nanoTime();
        this.width = width;
        this.height = height;
        this.layers_blocks_sizes = sizes;
        block_spacing = new int[] {layers_blocks_sizes[0] * 4, layers_blocks_sizes[1] * 4, layers_blocks_sizes[2] * 4};
        this.layers_dims = new int[][]{
                {this.width / layers_blocks_sizes[0], this.height / layers_blocks_sizes[0]},
                {this.width / layers_blocks_sizes[1], this.height / layers_blocks_sizes[1]},
                {this.width / layers_blocks_sizes[2], this.height / layers_blocks_sizes[2]}
        };
        this.shape_bitmaps = bitmaps;


        l1_blocks = new FallingBlock[num_blocks_l1];
        for(int i = 0; i < num_blocks_l1; i++)
            l1_blocks[i] = new FallingBlock(0);
        l2_blocks = new FallingBlock[num_blocks_l2];
        for(int i = 0; i < num_blocks_l2; i++)
            l2_blocks[i] = new FallingBlock(1);
        l3_blocks = new FallingBlock[num_blocks_l3];
        for(int i = 0; i < num_blocks_l3; i++)
            l3_blocks[i] = new FallingBlock(2);
    }

    private long lastTime;
    int l2_counter;
    int l3_counter;

    public int[] falling_speeds = new int[]{3, 2, 1};

    public void update(){
        long curTime = System.nanoTime();
        long elapsed = (curTime - lastTime) / 1000000;

        if(elapsed >= 35){
            for(int i = 0; i < num_blocks_l1; i++){
                l1_blocks[i].pos[1] += falling_speeds[0];
                if(l1_blocks[i].checkIfBelow(this.height)){
                    l1_blocks[i] = new FallingBlock(0);
                    l1_blocks[i].initOnTop();
                }
            }
            for(int i = 0; i < num_blocks_l2; i++){
                l2_blocks[i].pos[1] += falling_speeds[1];
                if(l2_blocks[i].checkIfBelow(this.height)){
                    l2_blocks[i] = new FallingBlock(1);
                    l2_blocks[i].initOnTop();
                }
            }
            for(int i = 0; i < num_blocks_l3; i++){
                l3_blocks[i].pos[1] += falling_speeds[2];
                if(l3_blocks[i].checkIfBelow(this.height)){
                    l3_blocks[i] = new FallingBlock(2);
                    l3_blocks[i].initOnTop();
                }
            }
            this.lastTime = curTime;
        }
    }

    public void draw(Canvas canvas){

        this.update();

        for(int i = 0; i < num_blocks_l3; i++){
            int[] pos = l3_blocks[i].pos;
            canvas.drawBitmap(this.shape_bitmaps[2][l3_blocks[i].type][l3_blocks[i].rotation],
                    pos[0],
                    pos[1],
                    null);
        }

        for(int i = 0; i < num_blocks_l2; i++){
            int[] pos = l2_blocks[i].pos;
            canvas.drawBitmap(this.shape_bitmaps[1][l2_blocks[i].type][l2_blocks[i].rotation],
                    pos[0],
                    pos[1],
                    null);
        }

        for(int i = 0; i < num_blocks_l1; i++){
            int[] pos = l1_blocks[i].pos;
            canvas.drawBitmap(this.shape_bitmaps[0][l1_blocks[i].type][l1_blocks[i].rotation], pos[0], pos[1], null);
        }
    }

    public int[] lastx = new int[] {0, 0, 0};
    public int[] lasty = new int[] {0, 0, 0};
    public int[] block_spacing;// = new int[] {layers_blocks_sizes[0] * 4 * 2, layers_blocks_sizes[1] * 4 * 3, layers_blocks_sizes[2] * 4 * 4};
    //public int[] layers_blocks_sizes;

    class FallingBlock{
        public int pos[];
        public int type;
        public int rotation;
        public int layer;

        public FallingBlock(int layer){
            this.layer = layer;
            this.pos = new int[]{
                    (int)(lastx[layer] + block_spacing[layer] * Math.random()) % layers_dims[layer][0] * layers_blocks_sizes[layer],
                    (int)(lasty[layer] + block_spacing[layer] * Math.random()) % layers_dims[layer][1] * layers_blocks_sizes[layer]
            };
            lastx[layer] = pos[0];
            lasty[layer] = pos[1];
            //this.pos = new int[]{(int)(Math.random() * layers_dims[layer][0]), (int)(Math.random() * layers_dims[layer][1])};
            this.type = (int)(Math.random() * 7);
            this.rotation = (int)(Math.random() * 10) % 4;
        }

        public void initOnTop(){
            this.pos[1] = -layers_blocks_sizes[layer] * 4;
        }

        public boolean checkIfBelow(int h){
            return this.pos[1] > h;
        }
    }
}