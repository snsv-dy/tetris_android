package app.tets;

/**
 * Created by Jacek on 2018-09-18.
 */

import android.content.Context;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class Stats {
    class stat_class{
        public stat_class(){
            hi_score = 0;
            hi_level = 0;
            l_streak = 0;
        }

        public int l_streak;
        public int hi_score;
        public int hi_level;
    }

    public String stats_path;

    public stat_class stat_obj;
    private Gson gson;
    public boolean stats_loaded = false;

    public Stats(Context context){
        this.gson = new Gson();
        this.stats_path = "stats2.json";
        this.stat_obj = new stat_class();
        this.loadStats(context);
    }

    public boolean changed = false;

    public void setHiScore(int score){
        if(score > this.stat_obj.hi_score){
            this.stat_obj.hi_score = score;
            changed = true;
        }
    }

    public void setHiLevel(int score){
        if(score > this.stat_obj.hi_level){
            this.stat_obj.hi_level = score;
            changed = true;
        }
    }

    public void setHiClearedStreak(int score){
        if(score > this.stat_obj.l_streak){
            this.stat_obj.l_streak = score;
            changed = true;
        }
    }

    public void saveIfNecessary(Context context){
        if(changed){
            System.out.println(" @@@@@@@@@@@@@ SAVING STATS @@@@@@@@@@@@@@@@@@@@@ ");
            this.saveStats(context);
            this.changed = false;
        }
    }

    public int getHiLevel(){
        return this.stat_obj.hi_level;
    }

    public int getHiScore(){
        return this.stat_obj.hi_score;
    }

    public int getHiClearedStreak(){
        return this.stat_obj.l_streak;
    }

    public void loadStats(Context context){
        File path = context.getFilesDir();
        File file = new File(path, stats_path);
        if(file.exists()){
            try{
                String file_str = loadFile(this.stats_path, context);

                stat_class stat = gson.fromJson(file_str, stat_class.class);
                System.out.println("@@@@@@@@@@@@@@@@@ STATS LOADED @@@@@@@@@@@@@@ " + stat.hi_score + ", " + stat.hi_level);
                this.stat_obj = stat;
                stats_loaded = true;
            }catch (IOException e){
                e.printStackTrace();
            }
        }else{
            this.saveStats(context);
            this.loadStats(context);
        }
    }

    public String loadFile(String filename, Context context) throws  IOException{
        String ret = "";

        File path = context.getFilesDir();
        File file = new File(path, filename);
        byte[] bytes = new byte[(int)file.length()];
        FileInputStream in = new FileInputStream(file);
        in.read(bytes);
        in.close();

        return new String(bytes);
    }

    public void saveStats(Context context){
        String json_str = gson.toJson(this.stat_obj);
        try {
            writeToFile(this.stats_path, json_str, context);
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public void writeToFile(String filename, String data, Context context) throws IOException{

        File path = context.getFilesDir();
        File file = new File(path, filename);
        FileOutputStream os = new FileOutputStream(file);
        os.write(data.getBytes());
        os.close();


        /*try{
            OutputStreamWriter os = new OutputStreamWriter(context.openFileOutput(filename, Context.MODE_PRIVATE));
            os.write(data);
            os.close();
        }catch(IOException e){
            e.printStackTrace();
        }*/
    }
}
