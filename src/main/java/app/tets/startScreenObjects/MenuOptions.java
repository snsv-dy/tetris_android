package app.tets.startScreenObjects;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

/**
 * Created by Jacek on 2018-09-16.
 */

public class MenuOptions {
    public int width, height;
    public String[] options;

    public float title_p = 0.3f;
    public float title_width_p = 0.9f;
    public float margin_p = 0.05f;
    public float menu_btn_width = 0.65f;
    public int margin;

    public int[] menu_btn_size;
    public int[][] menu_btn_poses;
    public int[][] menu_btn_text_poses;
    public int menu_btn_text_size;
    public Rect[] menu_btn_rects;

    public Paint menu_btn_paint;
    public Paint menu_btn_text_paint;

    public Rect title_pos;
    public int[] title_text_pos;
    public Paint title_paint;
    public Paint title_text_paint;
    public String title_text = "tets";

    public MenuOptions(int width, int height){
        this.options = new String[]{
                "Play",
                "Highscores",
                "Settings",
                "Exit"
        };

        this.width = width;
        this.height = height;

        this.margin = (int)(height * margin_p);


        this.menu_btn_size = new int[]{
                (int)(menu_btn_width * this.width),
                (int) ((1f - (2 * margin_p + title_p) - margin_p * options.length) * this.height) / this.options.length
        };

        this.menu_btn_text_size = this.menu_btn_size[1] / 2;

        menu_btn_poses = new int[this.options.length][2];
        menu_btn_rects = new Rect[options.length];

        for(int i = 0; i < this.options.length; i++){
            menu_btn_poses[i][0] = (this.width - menu_btn_size[0]) / 2;
            menu_btn_poses[i][1] = (int)(this.height * (title_p + 2 * margin_p)) + i * ((int)(margin_p * height) + menu_btn_size[1]);
            menu_btn_rects[i] = new Rect();
            menu_btn_rects[i].set(
                    (this.width - menu_btn_size[0]) / 2,
                    (int)(this.height * (title_p + 2 * margin_p)) + i * ((int)(margin_p * height) + menu_btn_size[1]),
                    menu_btn_poses[i][0] + menu_btn_size[0],
                    menu_btn_poses[i][1] + menu_btn_size[1]);

        }

        menu_btn_paint = new Paint();
        menu_btn_paint.setColor(Color.argb(218, 0, 0, 255));

        menu_btn_text_paint = new Paint();
        menu_btn_text_paint.setColor(Color.WHITE);
        menu_btn_text_paint.setTextSize(this.menu_btn_text_size);

        /*textPaint.getTextBounds(options[i], 0, options[i].length(), tr);
        int textx = (int)((buttonDims[0]  - tr.width()) / 2f  - tr.left);
        int texty = (int)(buttonDims[1] / 2f + tr.height() / 2f - tr.bottom);
        canvas.drawText(options[i], this.left + this.buttonLeft + textx, this.top + y + screenPoses[1] + texty, textPaint);
*/
        this.menu_btn_text_poses = new int[this.options.length][2];
        for(int i = 0; i < this.options.length; i++){
            Rect tr = new Rect();
            this.menu_btn_text_paint.getTextBounds(this.options[i], 0, this.options[i].length(), tr);
            int textx = (int)((menu_btn_size[0] - tr.width()) / 2f - tr.left);
            int texty = (int)((menu_btn_size[1] + tr.height()) / 2f - tr.bottom);
            this.menu_btn_text_poses[i][0] = menu_btn_poses[i][0] + textx;
            this.menu_btn_text_poses[i][1] = menu_btn_poses[i][1] + texty;
        }


        this.title_pos = new Rect();
        this.title_pos.set(
                (int)((1f - title_width_p) * this.width),
                (int)(this.margin_p * this.height),
                (int)(title_width_p * this.width),
                (int)((this.title_p + this.margin_p) * this.height)
        );

        this.title_paint = new Paint();
        this.title_paint.setColor(Color.argb(128, 20, 20, 255));

        this.title_text_paint = new Paint();
        this.title_text_paint.setColor(Color.WHITE);
        this.title_text_paint.setTextSize((int)(title_p * this.height) / 2);

        Rect tr = new Rect();
        this.title_text_paint.getTextBounds(this.title_text, 0, title_text.length(), tr);
        int tex = (int)(title_width_p);
        this.title_text_pos = new int[]{

        };
    }

    public void draw(Canvas canvas){
        canvas.drawRect(this.title_pos, title_paint);

        for(int i = 0; i < this.options.length; i++){
            canvas.drawRect(menu_btn_rects[i], menu_btn_paint);
            canvas.drawText(this.options[i], this.menu_btn_text_poses[i][0], this.menu_btn_text_poses[i][1], this.menu_btn_text_paint);
        }
    }
}
