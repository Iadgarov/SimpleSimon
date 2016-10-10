package com.example.admin.simplesimon.battleShip;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Handler;
import android.support.v4.content.res.ResourcesCompat;
import android.util.AttributeSet;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.admin.simplesimon.R;

/**
 * Created by David on 08-Oct-16.
 */

public class Tile extends ImageButton {

    final static int EMPTY = 0, HIT = 1, MISS = 2, SHIP = 3;
    private int state = EMPTY;

    private final Drawable HIT_MARK =  ResourcesCompat.getDrawable(getResources(), R.drawable.hit, null);
    private final Drawable SHIP_MARK =  ResourcesCompat.getDrawable(getResources(), R.drawable.ship, null);

    private final int SEA_COLOR = getResources().getColor(R.color.LightBlue);

    public Tile(Context c){
        super(c);
    }

    // needed for inflation in xml
    protected Tile(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    // needed for inflation in xml
    public Tile(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

    }

    public void initTile(final int s){



        this.setState(s);


        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                switch(s){
                    case EMPTY: Tile.this.setState(MISS); break;
                    case SHIP:  Tile.this.setState(HIT); break;
                }
            }
        });






    }

    private void setState(int s){

        this.state = s;

        switch(s){
            case EMPTY: this.setBackgroundColor(SEA_COLOR); break;

            case HIT:   this.flashButton(HIT_MARK, getResources().getColor(R.color.Red));

                        this.setClickable(false);
                        break;

            case MISS:  this.flashButton(getResources().getColor(R.color.White), getResources().getColor(R.color.Gray));
                        this.setClickable(false);
                        break;

            case SHIP:  this.setBackground(SHIP_MARK);

                        break;

        }
    }

    // c is the background color of the button, a black border will be set around it
    private void addBorder(int c){

        GradientDrawable gd = new GradientDrawable();
        gd.setColor(c); // Changes this drawbale to use a single color instead of a gradient
        gd.setCornerRadius(0);
        gd.setStroke(4, 0xFF000000);

        super.setBackground(gd);
    }

     @Override public void setBackground(Drawable drawable){
         this.setImageDrawable(drawable);
         this.addBorder(SEA_COLOR);
     }

    @Override public void setBackgroundColor(int color){
        this.addBorder(color);
    }



    void flashButton(int c1, final int c2){

        Tile.this.setBackgroundColor(c1);


        new Handler().postDelayed(new Runnable() {

            public void run() {
                //Tile.this.setBackgroundColor(c2); // delay is over, turn color back to default.
                Tile.this.addBorder(c2);

            }
        }, 100);

    }

    void flashButton(final Drawable d, final int c){

        Tile.this.setBackgroundColor(c);


        new Handler().postDelayed(new Runnable() {

            public void run() {
                Tile.this.setBackground(d); // delay is over, turn color back to default.


            }
        }, 100);

    }


    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        WindowManager wm = (WindowManager) this.getContext().getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();

        Point size = new Point();
        display.getSize(size);
        int w = size.x;
        int h = size.y;


        int width = ((w)/10);//getMeasuredWidth() / 2;
        int height = getMeasuredHeight() / 2;
        int squareLen = width;
        if (height > width) {
            squareLen = height;
        }
        super.onMeasure(MeasureSpec.makeMeasureSpec(squareLen, MeasureSpec.EXACTLY), MeasureSpec.makeMeasureSpec(squareLen, MeasureSpec.EXACTLY));
    }


}
