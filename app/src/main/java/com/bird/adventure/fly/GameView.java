package com.bird.adventure.fly;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.CombinedVibration;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.os.VibratorManager;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.HapticFeedbackConstants;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.HashSet;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class GameView extends SurfaceView {

   private Paint bitmapPaint;
    private Bitmap bg;
    private Bitmap sprite;
    private Bitmap heart, money, bomb1, bomb2;
    private SurfaceHolder holder;
    public int score = 0;
    private Paint textPaint;
    private boolean paused;
    private Random random;
    private SharedPreferences preferences;
    private MediaPlayer player;
    private Context context;
    private boolean vibro = false;

    public GameView(final Context context, AttributeSet attributes) {
        super(context, attributes);
        setFocusable(true);
        setFocusableInTouchMode(true);
        SharedPreferences prefs = context.getSharedPreferences("prefs",Context.MODE_PRIVATE);
        this.context = context;
        if(prefs.getBoolean("dark",false)) {
            bg = BitmapFactory.decodeResource(getResources(),R.drawable.dark);
        } else {
            bg = BitmapFactory.decodeResource(getResources(),R.drawable.light);
        }
        if(prefs.getBoolean("vibration",false)) {
            vibro = true;
        } else {

        }
        if(prefs.getBoolean("sound",false)) {
            player = MediaPlayer.create(context,R.raw.back);
            player.setOnCompletionListener(mediaPlayer -> {
                mediaPlayer.reset();
                mediaPlayer.start();
            });
        } else player = null;
        sprite = BitmapFactory.decodeResource(getResources(), prefs.getInt("id",R.mipmap.bird1_foreground));
        heart = BitmapFactory.decodeResource(getResources(),R.mipmap.heart_foreground);
        heart = Bitmap.createScaledBitmap(heart,50,50,true);
        money = BitmapFactory.decodeResource(getResources(),R.mipmap.money_foreground);
        money = Bitmap.createScaledBitmap(money,70,70,true);
        bomb1 = BitmapFactory.decodeResource(getResources(),R.mipmap.bomb1_foreground);
        bomb1  = Bitmap.createScaledBitmap(bomb1,70,70,true);
        bomb2 = BitmapFactory.decodeResource(getResources(),R.mipmap.bomb2_foreground);
        bomb2  = Bitmap.createScaledBitmap(bomb2,70,70,true);
        bitmapPaint = new Paint(Paint.DITHER_FLAG);
        textPaint = new Paint();
        textPaint.setTextSize(50);
        textPaint.setFakeBoldText(true);
        textPaint.setColor(Color.WHITE);
        paused = false;
        cor = 0;
        front = true;
        random = new Random();
        preferences = context.getSharedPreferences("prefs",Context.MODE_PRIVATE);

        holder = getHolder();
        Thread updateThread = new Thread(() -> new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                if (!paused) {
                    try {
                        update.run();
                    } catch (Exception e) {
                    }
                }
            }
        }, 100, 20));
        holder.addCallback(new SurfaceHolder.Callback() {

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                if(player!=null) player.stop();
                updateThread.interrupt();
            }

            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                Canvas canvas = holder.lockCanvas();
                if (canvas != null) {
                    if(player!=null) player.start();
                    yP = canvas.getHeight()/2;
                    for(int i = 0;i<arr.length;i++) arr[i] = new CanvasObject(canvas,random);
                    draw(canvas);
                    holder.unlockCanvasAndPost(canvas);
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            }

        });



        updateThread.start();
    }


//    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
//        super.onSizeChanged(w, h, oldw, oldh);
//        width = w;
//        height = h;
//        if (bitmap == null)
//            bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
//        canvas = new Canvas(bitmap);
//    }

    public boolean onTouchEvent(MotionEvent event) {
         switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                up = true;
                break;
            case MotionEvent.ACTION_UP:
                up = false;
                break;
        }
        postInvalidate();
        return true;
    }
    boolean first = true;
    float yP = 0;
    private int cor;
    private boolean front;
    boolean up = false;
    int count = 3;
    CanvasObject[] arr = new CanvasObject[5];
    Runnable update = new Runnable() { //draws and updates bird and pipes
        @Override
        public void run() {
            try {
                Canvas canvas = holder.lockCanvas();
                canvas.drawBitmap(bg,0,0,bitmapPaint);
                canvas.drawBitmap(money,50,50,bitmapPaint);
                canvas.drawText(score+"", 140, 100, textPaint);
                if (yP<canvas.getHeight()-sprite.getHeight()*2)
                    yP += 3;
                if(up && yP>sprite.getHeight()) yP-=10;
                if(front) {
                    if(cor<canvas.getWidth()/3) cor++;
                    else {
                        front = false;
                    }
                } else {
                    if(cor>0) cor--;
                    else front = true;
                }
                for(int i = 0;i<arr.length;i++) {
                    if(arr[i].x<0) {
                        arr[i] = new CanvasObject(canvas,random);
                    } else arr[i].x -= 3;
                    if(Math.abs(arr[i].x-cor)<=sprite.getWidth()/2 && Math.abs(arr[i].y-yP)<=sprite.getHeight()/2) {
                        if(arr[i].code<=1) {
                            count--;
                           if(vibro) {
                               if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S) {
                                   VibratorManager manager = (VibratorManager) context.getApplicationContext().getSystemService(Context.VIBRATOR_MANAGER_SERVICE);
                                   manager.vibrate(CombinedVibration.createParallel(VibrationEffect.createOneShot(1000,VibrationEffect.DEFAULT_AMPLITUDE)));
                               } else {
                                   Vibrator vibrator = (Vibrator) context.getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);
                                   vibrator.vibrate(VibrationEffect.createOneShot(100,VibrationEffect.DEFAULT_AMPLITUDE));
                               }
                           }
                            if(count<=0) {
                                togglePause();
                                HashSet<String> set = (HashSet<String>) preferences.getStringSet("records", new HashSet<>());
                                HashSet<String> newSet = new HashSet<>();
                                newSet.addAll(set);
                                newSet.add(score+"");
                                preferences.edit().putStringSet("records",newSet).apply();
                            }
                        } else {
                            score += 20;
                        }
                        arr[i] = new CanvasObject(canvas,random);
                    }
                }
                float cor1 = canvas.getWidth() - 100;
                for(int i = 0;i<count;i++) {
                    canvas.drawBitmap(heart,cor1,50,bitmapPaint);
                    cor1 -= heart.getWidth();
                    cor1 -= 20;
                }
                for(int i = 0;i<arr.length;i++) {
                    switch (arr[i].code) {
                        case 0:
                            canvas.drawBitmap(bomb1,arr[i].x,arr[i].y,bitmapPaint);
                            break;
                        case 1:
                            canvas.drawBitmap(bomb2,arr[i].x,arr[i].y,bitmapPaint);
                            break;
                        case 2:
                            canvas.drawBitmap(money,arr[i].x,arr[i].y,bitmapPaint);
                            break;
                    }
                }
                canvas.drawBitmap(sprite,cor,yP,bitmapPaint);
                holder.unlockCanvasAndPost(canvas);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    public void togglePause() {
        paused = !paused;
    }


    private static class CanvasObject {
        float x,y;
        int code;
        CanvasObject(Canvas canvas, Random random) {
            x = canvas.getWidth()+random.nextInt(300); y = random.nextInt(canvas.getHeight()/2)+100;
            code = random.nextInt(3);
        }
    }
}
