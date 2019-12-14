package app.tets;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by Jacek on 2018-09-13.
 */
 /*
public class GameSurface extends SurfaceView implements SurfaceHolder.Callback {
   private GameThread thread_obj;

    public GameSurface(Context context){
        super(context);

        this.setFocusable(true);

        this.getHolder().addCallback(this);
        tp = new Paint();
        tp.setColor(Color.BLUE);
    }

    private Paint tp;

    @Override
    public void draw(Canvas canvas){
        super.draw(canvas);

        canvas.drawRect(100, 100, 300, 300, tp);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder){
        this.thread_obj = new GameThread(this.getHolder(), this);
        this.thread_obj.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int w, int h){

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder){

    }


}
*/