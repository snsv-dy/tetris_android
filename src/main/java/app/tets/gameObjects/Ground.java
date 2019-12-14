package app.tets.gameObjects;

/**
 * Created by Jacek on 2018-09-03.
 */


import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.provider.Settings;
import android.util.Log;

import java.util.Vector;

public class Ground {

    public int width;
    public int height;
    public int frame_l;
    public int frame_t;

    public int num_blocks = 12;
    public int horizontal_blocks = 20;
    public int w = num_blocks;
    public int h;

    public float block_size;

    public boolean data[][];
    public int colors[][];

    private Paint okPaint;
    private Paint bdPaint;

    private Paint tPaint;
    public Bitmap groundBitmap;

    private Paint[] paints;

    public Block block_obj;
    public LogicTracker logic_obj;
    public Bitmap linesBitmap;

    public Ground(int frame_l, int frame_t, int width, int height){
        this.width = width;
        this.height = height;
        this.frame_l = frame_l;
        this.frame_t = frame_t;

        this.init();
    }

    public void setBlockObj(Block blobj){
        this.block_obj = blobj;
    }
    public void setLogicObj(LogicTracker logobj){ this.logic_obj = logobj; }

    public void pubinit(){
        this.init();
    }

    private void init() {

        paints = new Paint[7];
        for(int i = 0; i < 7; i++){
            paints[i] = new Paint();
            paints[i].setColor(Color.HSVToColor(new float[]{ ((float)i / 7f) * 360f, 1f, 1f }));
        }

        block_size = width / num_blocks;
        horizontal_blocks = (int)Math.min(Math.floor(height / block_size), horizontal_blocks);
        this.height = (int)(block_size * horizontal_blocks);
        h = horizontal_blocks;

        System.out.println("GR SET: " + this.width + ", " + this.height + ", " + this.w + ", " + this.h);

        //h = (int) Math.floor(height / block_size);
        //this.height = (int)(this.h * block_size);
        this.data = new boolean[h][w];
        for (int i = 0; i < h; i++){
            for (int j = 0; j < w; j++) {
               /* if(i < this.h - 5 || j > this.w - 2)
                    this.data[i][j] = false;
                else
                    this.data[i][j] = true;*/
                this.data[i][j] = false;
            }
        }

        this.colors = new int[h][w];


        okPaint = new Paint();
        okPaint.setColor(Color.GREEN);

        bdPaint = new Paint();
        bdPaint.setColor(Color.RED);

        tPaint = new Paint();

        groundBitmap = Bitmap.createBitmap(this.width, this.height, Bitmap.Config.ARGB_8888);


        // Making lines bitmap
        linesBitmap = Bitmap.createBitmap(this.width, this.height, Bitmap.Config.ARGB_8888);
        Canvas tcan = new Canvas(this.linesBitmap);
        //int grval = 240;
        tcan.drawRGB(19, 18, 89);
        Paint grPaint = new Paint();
        grPaint.setStrokeWidth(5);
        //grPaint.setColor(Color.HSVToColor(new float[]{0f, 0f, 0.75f}));
        grPaint.setColor(Color.rgb(30, 28, 141));

        for(int i = 0; i < this.h; i++){
            tcan.drawLine(0, i * this.block_size, this.width, i * this.block_size, grPaint);
        }
        for(int i = 0; i < this.w; i++){
            tcan.drawLine(i * this.block_size, 0, i* this.block_size, this.height, grPaint);
        }
        
        this.data = new boolean[][]{
                {false, false, false, false, false, false, false, false, false, false, false, false},{false, false, false, false, false, false, false, false, false, false, false, false},{false, false, false, false, false, false, false, false, false, false, false, false},{false, false, false, false, false, false, false, false, false, false, false, false},{false, false, false, false, false, false, false, false, false, false, false, false},{false, false, false, false, false, false, false, false, false, false, false, false},{false, false, false, false, false, false, false, false, false, false, false, false},{false, false, false, false, false, false, false, false, false, false, false, false},{false, false, false, false, false, false, false, false, false, false, false, false},{false, false, false, false, false, false, false, false, false, false, false, false},{false, false, false, false, false, false, false, false, false, false, false, false},{true, true, false, false, false, false, false, false, false, false, false, false},{true, true, false, true, false, false, false, true, false, false, false, false},{true, true, true, true, true, false, false, true, false, true, true, false},{true, true, true, true, true, true, true, true, true, true, true, false},{true, true, true, true, true, true, true, true, true, true, true, false},{true, true, true, true, true, true, true, true, true, true, true, false},{true, true, true, true, true, true, true, true, true, true, true, false},{true, true, true, true, true, true, true, true, true, true, true, false},{true, true, true, true, true, true, true, true, true, true, true, false}
        };
        
        this.colors = new int[][]{
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},{5, 5, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},{5, 5, 0, 0, 0, 0, 0, 6, 0, 0, 0, 0},{5, 5, 0, 0, 0, 0, 0, 6, 0, 1, 1, 0},{5, 5, 6, 6, 6, 6, 4, 6, 1, 1, 4, 0},{2, 2, 6, 6, 6, 6, 4, 6, 4, 4, 4, 0},{4, 2, 2, 1, 4, 4, 4, 4, 6, 4, 4, 0},{4, 2, 2, 1, 1, 4, 5, 5, 6, 4, 4, 0},{4, 4, 2, 2, 1, 4, 5, 5, 6, 4, 4, 0},{0, 0, 0, 0, 6, 6, 6, 6, 6, 4, 4, 0}

        };
        
        this.updateGroundBitmap();



        System.out.println("GROUND INITED");
    }

    public boolean try_add(int[] pos, boolean[][] data, int color){
        boolean adding = false;

        for(int i = Math.max(0, pos[1]); i < Math.min(pos[1] + data.length, this.h); i++){
            int di = Math.max(0, i - pos[1]);
            for(int j = Math.max(pos[0], 0); j < Math.min(pos[0] + data[0].length, this.w); j++){
                int dj = Math.max(0, j - pos[0]);

                if(data[di][dj] == true && i == this.h - 1){
                    adding = true;
                    break;
                }
                if(i+1 < this.h){
                    if(this.data[i+1][j] == true && data[di][dj] == true){
                        adding = true;
                        break;
                    }
                }
            }
            if(adding) break;
        }

        if(adding){
            this.addBlock(pos, data, color);
            this.check_for_remove();
            this.updateGroundBitmap();
        }

        return adding;
    }

    public boolean checkIntersect(int[] pos, boolean[][] data){
        for(int i = pos[1]; i < Math.min(pos[1] + data.length, this.h); i++){
            int ti = i - pos[1];
            for(int j = Math.max(0, pos[0]); j < Math.min(pos[0] + data[0].length, this.w); j++){
                int tj = Math.max(0, j - pos[0]);
                if(this.data[i][j] && data[ti][tj])
                   return false;
            }
        }
        return true;
    }

    public void dump_data(){
        String strd = "";
        String strc = "";
        for(int i = 0; i < this.data.length; i++){
            strd += "[";
            strc += "[";
            for(int j = 0; j < this.data[0].length; j++){
                strd += this.data[i][j];
                strc += this.colors[i][j];
                if(j < this.data[0].length - 1){
                    strd += ", ";
                    strc += ", ";
                }

            }
            strd += "]";
            strc += "]";
            if(i < this.data.length - 1){
                strd += ",";
                strc += ",";
            }
        }

        System.out.println("DATA: " + strd);
        System.out.println("COLORS: " + strc);
    }


    public boolean drop(int[] pos, boolean[][] data, int color){
        for(int i = pos[1]; i < this.h; i++){
            if(try_add(new int[]{pos[0], i}, data, color)){
                System.out.println("added " + i);
                break;
            }
        }

        return true;
    }

    public boolean check_side(int[] pos, int side, boolean[][] data, int[] start){
        try {
            switch (side) {
                case 0: {
                    if (pos[0] + start[0] <= 0) {
                        return false;
                    }

                    for (int i = Math.max(0, pos[1]); i < Math.min(this.h, pos[1] + data.length); i++) {
                        int di = Math.max(0, i - pos[1]);
                        if (this.data[i][pos[0] + start[0] - 1] == true && data[di][start[0]] == true) {
                            return false;
                        }
                    }
                }
                break;
                case 1: {
                    if (pos[0] + start[1] >= this.w - 1) {
                        return false;
                    }

                    for (int i = Math.max(0, pos[1]); i < Math.min(this.h, pos[1] + data.length); i++) {
                        int di = Math.max(0, i - pos[1]);
                        if (this.data[i][pos[0] + start[1] + 1] == true && data[di][start[1]] == true) {
                            return false;
                        }
                    }
                }
                break;
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return true;
    }

    public void check_for_remove(){
        Vector<boolean[]> tarr = new Vector<boolean[]>();
        for(int i = 0; i < this.data.length; i++){
            tarr.add(this.data[i]);
        }

        Vector<Integer> removeids = new Vector<Integer>();

        for(int i = this.data.length - 1; i > 0; i--){
            int cnt = 0;
            int whitecnt = 0;
            for(int j = 0; j < this.data[0].length; j++){
                if(this.data[i][j]){
                    if(whitecnt == 0)
                        cnt++;
                }else{
                    if(cnt == 0)
                        whitecnt++;
                }
            }
            if(cnt == this.w){
                removeids.add(i);
            }else if(whitecnt == this.w){
                break;
            }
        }

        if(removeids.size() > 0) {
            //this.block_obj.addScore(removeids.size());

            this.logic_obj.linesCleared(removeids.size());


            String str = "";
            for(int i : removeids){
                str += i + ", ";
            }

            //System.out.println(str + "   ");

            Vector<int[]> tcols = new Vector<int[]>();
            for(int i = 0; i < this.colors.length; i++){
                tcols.add(this.colors[i]);
            }

            for (int i = 0; i < removeids.size(); i++) {
                tarr.remove(removeids.get(i) + i);
                tcols.remove(removeids.get(i) + i);

                if(i == 0){
                    System.out.println(this.data.length + ". " + tarr.size() );
                }

                int[] arcln = new int[this.w];
                for(int j = 0; j < this.w; j++)
                    arcln[j] = 0;

                boolean[] clcln = new boolean[this.w];
                for(int j = 0; j < this.w; j++)
                    clcln[j] = false;

                tarr.add(0, clcln);
                tcols.add(0, arcln);

                if(i == 0){
                    System.out.println(this.data.length + ". " + tarr.size() );
                }
            }

            for(int i = 0; i < this.h; i++) {
                this.colors[i] = tcols.get(i);
                this.data[i] = tarr.get(i);
            }
        }

        this.logic_obj.blocFallen();

    }

    public boolean check_intersect_bool(int[] pos, int[] dims, boolean[][] tdata){
        if((pos[0] + dims[1] >= this.w ) || (pos[0] - dims[0] < 0) || (pos[1] + dims[3] >= this.h) || (pos[1] - dims[2] < 0)) {
            System.out.println("OUTSIDE BOUNDS");
            return false;
        }

        for(int i = pos[1]; i < Math.min(this.h, pos[1] + tdata.length); i++){
            int di = i - pos[1];
            for(int j = Math.max(0, pos[0]); j < Math.min(pos[0] + tdata[0].length, this.w); j++){
                int dj = j - pos[0];
                if(this.data[i][j] && tdata[di][dj]){
                    return false;
                }
            }
        }

        return true;
    }

    public boolean check_if_fits(int[] pos, boolean[][] tdata){

        //Getting left, right, top, bot
        int[] dims = {tdata[0].length, 0, tdata.length, 0};
        for(int i = 0; i < tdata.length; i++){
            for(int j = 0; j < tdata[0].length; j++){
                if(tdata[i][j]){
                    if(j < dims[0]) dims[0] = j;
                    else if(j > dims[1]) dims[1] = j;
                    if(i < dims[2]) dims[2] = i;
                    else if(i > dims[3]) dims[3] = i;
                }
            }
        }

        /*System.out.println("DIMS: " + dims[0] + ", " + dims[1] + ", " + dims[2] + ", " + dims[3]);


        int xmove = 0;
        int ymove = 0;
        for(int i = 0; i < pos[1]; i++){
            for(int j = Math.max(pos[0] - 2, 0); j < Math.min(pos[0] + 2, this.w); j++){
                if(j == 0) continue;
                if(check_intersect_bool(new int[]{pos[0] + j, pos[1] - i}, dims, tdata)){
                    ymove = -i;
                    xmove = j;
                    System.out.println("POS: " + pos[0] + ", " + pos[1]);
                    System.out.println(xmove + ", " + ymove + ", FITS!!!");

                }
            }
        }
        System.out.println(xmove + ", " + ymove + ", NOT FITS!!!");*/

        return check_intersect_bool(pos, dims, tdata);
    }

    public int check_if_in_bounds(int[] pos, boolean[][] data, int[] start){
        int move = 0;
        int side = pos[0] < this.w / 2 ? 0 : 1;
        if(side == 0){
            if(pos[0] + start[0] < 0)
                move = pos[0] + start[0];
        }else{
            if(pos[0] + start[1] >= this.w){
                move = this.w - 1 - pos[0] - start[1];
                move *= -1;
            }
        }

        return move;
    }

    public void addBlock(int[] pos, boolean[][] data, int color){
        for(int i = pos[1]; i < Math.min(pos[1] + data.length, this.h); i++){
            int di = i - pos[1];
            for(int j = Math.max(0, pos[0]); j < Math.min(pos[0] + data[0].length, this.w); j++){
                int dj = Math.max(0, j - pos[0]);

                this.data[i][j] |= data[di][dj];

                if(data[di][dj]){
                    this.colors[i][j] = color;
                }
            }
        }

    }


    public void updateGroundBitmap(){
        Canvas bitmapCanvas = new Canvas(this.groundBitmap);

        //bitmapCanvas.drawRGB(255, 255, 255);
        bitmapCanvas.drawBitmap(this.linesBitmap, 0, 0, null);

        Paint bPaint = new Paint();
        bPaint.setColor(Color.LTGRAY);
        bPaint.setStrokeWidth(3);

        for(int i = 0; i < this.colors.length; i++){
            for(int j = 0; j < this.colors[0].length; j++){
                if(this.data[i][j]){
                    //bPaint.setColor(Color.RED);
                    int x = (int)(j * block_size);
                    int y = (int)(i * block_size);
                    bitmapCanvas.drawRect(x, y, x + block_size, y + block_size, paints[this.colors[i][j]]);
                }
            }
        }

        bitmapCanvas.drawLines(new float[]{
                0, 0,
                width-1, 0,
                width-1, 0,
                width-1, height-1,
                width-1, height-1,
                0, height-1,
                0, height-1,
                0, 0
        }, bPaint);
    }

    public void draw(Canvas canvas){
        /*Paint aPaint= new Paint();
        aPaint.setColor(Color.RED);
        for(int i = 0; i < h; i++){
            int y = i * block_size;
            for(int j = 0; j < w; j++){
                int x = j * block_size;
                if(this.data[i][j]){
                    canvas.drawRect(x, y, x + block_size, y + block_size, aPaint);
                }
            }
        }*/
        //System.out.println(Math.floor(System.nanoTime() / 1000000));
        canvas.drawBitmap(this.groundBitmap, this.frame_l, this.frame_t, null);
    }
}
