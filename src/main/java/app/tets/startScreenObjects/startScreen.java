package app.tets.startScreenObjects;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.prefs.Preferences;

import app.tets.MainActivity;
import app.tets.Preferences.Prefs;
import app.tets.R;
import app.tets.Stats;
import app.tets.gameObjects.Shape;

public class startScreen extends AppCompatActivity {

    private startScreenSurface surface_obj;

    public Shape[] shapes;
    public Bitmap[][][] shape_bitmaps;
    Paint[] paints;
    private BackgroundFallingBlocks fallingBlocks_obj;
    private MenuOptions menuOptions_obj;

    private int width, height;

    LinearLayout main_screen;
    LinearLayout statistics_screen;
    Stats stats_obj;
    boolean stats_inited = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();


        setContentView(R.layout.activity_start_screen);
        this.stats_obj = new Stats(this);


        main_screen = (LinearLayout)findViewById(R.id.start_screen_main);
        statistics_screen = (LinearLayout)findViewById(R.id.start_screen_statistics);

        System.out.println("PRE LOOP");

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        this.width = size.x;
        this.height = size.y;

        //this.width = this.getWindow().getDecorView().getWidth();
        //this.height = this.getWindow().getDecorView().getHeight();

        System.out.println("WIDTH " + this.width);

        init_objs();

        //Button gem = (Button)findViewById(R.id.gem_button);
        surface_obj = new startScreenSurface(this);
        surface_obj.shapes = this.shapes;
        surface_obj.shape_bitmaps = this.shape_bitmaps;
        surface_obj.paints = this.paints;
        surface_obj.fallingBlocks_obj = this.fallingBlocks_obj;
        surface_obj.menuOptions_obj = this.menuOptions_obj;
        surface_obj.inited();

        RelativeLayout bg_l = (RelativeLayout)findViewById(R.id.background_layout);
        bg_l.addView(surface_obj);

        //this.setContentView(surface_obj);

    }

    public void updateStatictics(){
        TextView hiscore = (TextView)findViewById(R.id.scr_main_score);
        TextView hilevel = (TextView)findViewById(R.id.scr_main_level);
        TextView histreak = (TextView)findViewById(R.id.scr_main_streak);
        hiscore.setText(this.stats_obj.getHiScore() + "");
        hilevel.setText(this.stats_obj.getHiLevel() + "");
        histreak.setText(this.stats_obj.getHiClearedStreak() + "");
    }

    public void goBackToMain(View view){
        this.statistics_screen.setVisibility(View.GONE);
        this.main_screen.setVisibility(View.VISIBLE);
    }

    public void showStatistics(View view){
        this.main_screen.setVisibility(View.GONE);
        this.statistics_screen.setVisibility(View.VISIBLE);
        this.updateStatictics();
    }

    public void goToSettings(View view){
        Intent i = new Intent(getApplication(), Prefs.class);
        startActivity(i);
    }

    public void init_objs(){
        this.initShapes();
        this.initBitmaps();
        this.fallingBlocks_obj = new BackgroundFallingBlocks(new int[]{this.width / 15, this.width / 16 / 1, this.width / 17 / 1}, this.width, this.height, shape_bitmaps);
        this.menuOptions_obj = new MenuOptions(this.width, this.height);
    }

    @Override
    public void onPause(){
        super.onPause();
        this.surface_obj.pause();
    }

    @Override
    public void onResume(){
        super.onResume();
        this.surface_obj.resume();
    }

    public void clickPlay(View v){
        Intent i = new Intent(getApplication(), MainActivity.class);
        startActivity(i);
    }

    public void gotogame(View vw){
        Intent i = new Intent(getApplication(), MainActivity.class);
        startActivity(i);
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

        int block_size = this.width / 15;
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

        this.shapes = new Shape[7];
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
