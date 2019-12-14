package app.tets;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import app.tets.gameObjects.Block;
import app.tets.gameObjects.Controls;
import app.tets.gameObjects.Ground;
import app.tets.gameObjects.Hud;
import app.tets.gameObjects.LayoutManager;
import app.tets.gameObjects.LogicTracker;
import app.tets.gameObjects.Pause;
import app.tets.gameObjects.ThatOneFuckingBitmapThahIHadToMakeClassFor;

public class MainActivity extends AppCompatActivity {
    public gameView gv;// = new gameView(this);


    Ground ground_obj;
    Block block_obj;
    Hud hud_obj;
    Pause pause_obj;
    Controls controls_obj;
    LayoutManager layout_obj;
    LogicTracker logic_obj;

    int width, height;

    ThatOneFuckingBitmapThahIHadToMakeClassFor bitmaps_obj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_main);
        LinearLayout cl = (LinearLayout)findViewById(R.id.canvasLayout);

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        String move_vals = sharedPref.getString("move_val", "40");
        String drop_vals = sharedPref.getString("drop_val", "120");
        int move_val = Integer.parseInt(move_vals);
        int drop_val = Integer.parseInt(drop_vals);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        this.width = size.x;
        this.height = size.y;

        this.layout_obj = new LayoutManager();

        Bitmap[] bitmaps = new Bitmap[1];
        bitmaps[0] = BitmapFactory.decodeResource(getResources(), R.mipmap.pause_button);
        this.bitmaps_obj = new ThatOneFuckingBitmapThahIHadToMakeClassFor(bitmaps);


        gv = new gameView(this);
        gv.setBitmaps(this.bitmaps_obj);
        this.init_objs();
        logic_obj = new LogicTracker(this.block_obj, this.ground_obj);
        logic_obj.init_stats(this);
        this.ground_obj.setLogicObj(this.logic_obj);
        this.hud_obj.setLogicObj(this.logic_obj);

        gv.ground_obj = this.ground_obj;
        gv.block_obj = this.block_obj;
        gv.hud_obj = this.hud_obj;
        gv.pause_obj = this.pause_obj;

        this.controls_obj.moveThresh = this.controls_obj.fallThresh = move_val;
        this.controls_obj.dropThresh = drop_val;

        gv.controls_obj = this.controls_obj;
        gv.layout_obj = this.layout_obj;
        gv.logic_obj = this.logic_obj;
        gv.inited = true;
        //
        this.setContentView(gv);
    }


    public float[] groundDims = {0f, 0.1f, 0.75f, 0.9f};
    public float[] hudDims = {0.8f, 0f, 1f - groundDims[2] - 0.05f, 1f};
    public float[] scoreDims = {0f, 0f, groundDims[2], groundDims[1]};
    
    public void init_objs(){
        controls_obj = new Controls();

        int glob_margin = (int) (this.width * 0.05f);

        this.layout_obj.setScreenMargins(glob_margin, glob_margin, glob_margin, glob_margin);
        this.layout_obj.setScreenDims(this.width, this.height);


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

        Paint blackPaint = new Paint();
        blackPaint.setColor(Color.BLACK);
        blackPaint.setStrokeWidth(2f);

        int[] score_dims_calc = new int[]{
                (int) (scoreDims[0] * this.width),
                (int) (scoreDims[1] * this.height),
                (int) (scoreDims[2] * this.width),
                (int) (scoreDims[3] * this.height)
        };

        /*this.hud_obj = new Hud(
                (int)(hudDims[0] * this.width),
                (int)(hudDims[1] * this.height),
                (int)(hudDims[2] * this.width),
                (int)(hudDims[3] * this.height),
                score_dims_calc,
                bitmaps);*/
        int[] hud_dims = this.layout_obj.getHudDims();
        this.hud_obj = new Hud(
                hud_dims[0],
                hud_dims[1],
                hud_dims[2],
                hud_dims[3],
                this.layout_obj.getScoreDims(),
                this.bitmaps_obj);

        this.hud_obj.init(this.block_obj.shapes);
        this.hud_obj.setBitmaps(this.bitmaps_obj);
        block_obj.setHud_obj(this.hud_obj);
        ground_obj.setBlockObj(this.block_obj);

        System.out.println("view dims: " + this.width + ", " + this.height);

        // boolean block_gameOver = false;
        //gameOverPaint = new Paint();
        //gameOverPaint.setTextSize(100);

        //
        //  END OF INITING THINGS
        //

    }

    @Override
    public void onPause(){
        super.onPause();
        gv.pause();
    }

    @Override
    public void onResume(){
        super.onResume();
        gv.resume();
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        gv = null;
    }

    public void leftClick(View v){
        gv.clickLeft();
    }
    public void upClick(View v){
        gv.clickUp();
    }
    public void downClick(View v){
        gv.clickDown();
    }
    public void rightClick(View v){
        gv.clickRight();
    }
    public void stopFunc(View v) { gv.stop(); }
    public void holdFunc(View v){gv.clickHold();}
    public void pauseFunc(View v){gv.clickPause();}
}
