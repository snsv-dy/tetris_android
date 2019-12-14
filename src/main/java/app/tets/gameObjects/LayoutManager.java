package app.tets.gameObjects;

import android.view.LayoutInflater;

/**
 * Created by Jacek on 2018-09-14.
 */

public class LayoutManager {

    public int left, top, right, bottom;
    public boolean marginsInited = false;
    public int s_width, s_height;
    public boolean dimsInited = false;

    public float left_width_p;
    public float right_width_p;

    public float ground_height_p;
    public float score_height_p;
    public float hud_height_p;

    public float basic_margin;

    public float[] groundDims = {0f, 0.1f, 0.75f, 0.9f};
    public float[] hudDims = {0.8f, groundDims[1], 1f - groundDims[2] - 0.05f, groundDims[3]};
    public float[] scoreDims = {0f, 0f, groundDims[2], groundDims[1]};

    public LayoutManager(){
        this.basic_margin = 0.05f;
        this.left_width_p = 0.75f;
        this.right_width_p = 1f - basic_margin - left_width_p;
        this.ground_height_p = 0.9f - basic_margin;
        this.score_height_p = 0.1f;
        this.hud_height_p = 1f;
    }

    public void setScreenDims(int w, int h){
        this.s_width = w;
        this.s_height = h;

        this.s_width -= left + right;
        this.s_height -= top + bottom;

        this.dimsInited = true;

        //System.out.println("LMNG: " + this.s_width + ", " + this.s_height);
    }

    public void setScreenMargins(int l, int t, int r, int b){
        this.left = l;
        this.top = t;
        this.right = r;
        this.bottom = b;
        this.marginsInited = true;
    }

    public int[] getGroundDims(){
        return new int[]{
                (int)(this.left),
                (int)(this.top + (this.score_height_p + this.basic_margin) * this.s_height),
                (int)(this.left_width_p * this.s_width),
                (int)(this.ground_height_p * this.s_height)
        };
    }

    public int[] getScoreDims(){
        return new int[]{
                (int)(this.left),
                (int)(this.top),
                (int)(this.left_width_p * this.s_width),
                (int)(this.score_height_p * this.s_height),
        };
    }

    public int[] getHudDims(){
        return new int[]{
                (int)(this.left + (this.left_width_p + this.basic_margin) * this.s_width),
                (int)(this.top + (this.score_height_p + this.basic_margin) * 0),
                (int)(right_width_p * this.s_width),
                (int)(this.s_height * this.hud_height_p)
        };
    }
}

class DimClass{
    public int left;
    public int top;
    public int width;
    public int height;

    public DimClass(int l, int t, int w, int h){
        this.left = l;
        this.top = t;
        this.width = w;
        this.height = h;
    }

    public DimClass(){

    }

    public DimClass(float lp, float tp, float wp, float hp, float width, float height){
        this.left = (int)(lp * width);
        this.top = (int)(tp * height);
        this.width = (int)(width * wp);
        this.height = (int)(height * hp);
    }
}