package com.example.admin.simplesimon.simon;

import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.v4.content.res.ResourcesCompat;
import android.widget.TextView;

import com.example.admin.simplesimon.R;

import main.App;

/**
 * Created by David on 26-Sep-16.
 *
 */

class MiddleButton {

    private static int baseColor = App.getAppContext().getResources().getColor(R.color.Black);
    private static final Drawable NEXT_ICON = ResourcesCompat.getDrawable(App.getAppContext().getResources(), R.drawable.right_arrow, null);
    private static final Drawable RESTART_ICON = ResourcesCompat.getDrawable(App.getAppContext().getResources(), R.drawable.restart_arrow, null);
    private static final Drawable START_ICON = ResourcesCompat.getDrawable(App.getAppContext().getResources(), R.drawable.start, null);

    private TextView mid;
    private int mode;

    private Handler handler;

    static final int NEXT = 0, START = 1, RESTART = 2, TEXT = -1;

    MiddleButton(TextView mid){
        this.mid = mid;
        this.mode = START;
        handler = new Handler();
    }


    TextView getView(){
        return this.mid;
    }

    int getMode(){
        return this.mode;
    }

    void setText(final String text){


        handler.postDelayed(new Runnable() {

            public void run() {

                mid.setBackgroundColor(baseColor); // remove any sign that may be there
                mid.setText(text);
                mode = TEXT;
                return;

            }
        }, 100);
    }

    void  setSymbol(final int symb){


        handler.postDelayed(new Runnable() {

            public void run() {
                mid.setText(""); // remove any text that may be there.
                switch(symb){
                    case TEXT:      break;
                    case NEXT:      mid.setBackground(NEXT_ICON);      mode = NEXT;       break;
                    case START:     mid.setBackground(START_ICON);     mode = START;      break;
                    case RESTART:   mid.setBackground(RESTART_ICON);   mode = RESTART;    break;
                }
                return;

            }
        }, 100);

//
//        mid.setText(""); // remove any text that may be there.
//        switch(symb){
//            case NEXT:      mid.setBackground(NEXT_ICON);      this.mode = NEXT;       break;
//            case START:     mid.setBackground(START_ICON);     this.mode = START;      break;
//            case RESTART:   mid.setBackground(RESTART_ICON);   this.mode = RESTART;    break;
//        }
    }

}
