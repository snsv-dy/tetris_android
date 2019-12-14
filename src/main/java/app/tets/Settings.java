package app.tets;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Scanner;

public class Settings extends AppCompatActivity {
    EditText drag_sens;
    EditText drop_sens;
    EditText tap_time;

    String settings_filename;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        this.settings_filename = "settings.txt";

        drag_sens = (EditText)findViewById(R.id.drag_sens);
        drop_sens = (EditText)findViewById(R.id.drop_sens);
        tap_time = (EditText)findViewById(R.id.tap_time);

        int[] settings = loadSettings(this);
        drag_sens.setText(settings[0] + "");
        drop_sens.setText(settings[1] + "");
        tap_time.setText(settings[2] + "");
    }

    protected void saveSettings(int drag, int drop, int tap){
        try(Writer writer = new BufferedWriter(new OutputStreamWriter((
                new FileOutputStream(this.settings_filename)), "utf-8"
        ))){
            writer.write(drag + "\n");
            writer.write(drop + "\n");
            writer.write(tap + "\n");
        }catch (Exception e){

        }
    }


    protected int[] loadSettings(Context context){
        File set_file = new File(context.getFilesDir(), this.settings_filename);
        int[] params = new int[]{40, 120, 100};
        if(set_file.exists()) {
            try {
                Scanner f_scan = new Scanner(set_file);
                int line = 0;
                while (f_scan.hasNextLine()) {
                    params[line] = Integer.parseInt(f_scan.nextLine());
                    line++;
                }

            } catch (Exception e) {

            }
        }else{
            try(Writer writer = new BufferedWriter(new OutputStreamWriter((
                    new FileOutputStream(this.settings_filename)), "utf-8"
                    ))){
                writer.write(params[0] + "\n");
                writer.write(params[0] + "\n");
                writer.write(params[0] + "\n");
            }catch (Exception e){

            }
        }

        return params;
    }

}
