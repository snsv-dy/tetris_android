package app.tets.gameObjects;

import android.content.Context;

import app.tets.Stats;

/**
 * Created by Jacek on 2018-09-18.
 */

public class LogicTracker {
    public int lines_cleared;
    public int score;
    public int level;

    public int block_speed;

    public Block block_obj;
    public Ground ground_obj;
    public Stats stat_obj;

    public Context context_obj = null;

    public LogicTracker(Block block, Ground ground){
        block_obj = block;

        lines_cleared = 0;
        score = 0;
        level = 1;
        block_speed = 1000;
    }

    public void init_stats(Context context){
        stat_obj = new Stats(context);
        stat_obj.setHiLevel(this.level);
        stat_obj.setHiScore(this.score);
        this.context_obj = context;
    }

    int next_level_thresh = 5;

    public void linesCleared(int num_lines){
        this.last_cleared = true;
        this.cleared_streak += 1;
        this.lines_cleared += num_lines;
        this.score += Math.pow(3, num_lines) * 15;
        if(this.lines_cleared > next_level_thresh){
            this.level++;
            this.next_level_thresh += 5 + Math.pow(1.5, this.level);
            this.block_speed = (int)(this.block_speed * 0.92);
            block_obj.fps = this.block_speed;
        }
    }

    public boolean last_cleared = false;
    public int cleared_streak = 0;
    public int max_cleared_streak = 0;

    public void blocFallen(){
        if(!last_cleared) {
            if(this.max_cleared_streak > this.cleared_streak)
                this.max_cleared_streak = this.cleared_streak;
            this.cleared_streak = 0;
        }
        this.last_cleared = false;
    }

    public void onGameOver(){
        this.updateStats();
    }

    public void updateStats(){
        this.stat_obj.setHiLevel(this.level);
        this.stat_obj.setHiScore(this.score);
        this.stat_obj.setHiClearedStreak(this.max_cleared_streak);
        this.stat_obj.saveIfNecessary(this.context_obj);
    }
}
