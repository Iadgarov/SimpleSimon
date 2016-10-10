package com.example.admin.simplesimon.simon;

import android.os.Handler;
import android.widget.Button;

import com.example.admin.simplesimon.R;

import main.App;

/**
 * Created by David on 20-Sep-16.
 *
 */

  class SimonButton{

    private Button button;
    private int colorPair;
    private int location;

    static final int BLUE = 0, RED = 1, YELLOW = 2, GREEN = 3;
    static final int TOP_POS = 0, RIGHT_POS = 1,  BOTTOM_POS = 2, LEFT_POS = 3;

    private final static int FLASH_DURATION = 100; // in mSec


    private static final ColorPair[] COLOR_PAIRS;

    static {
        COLOR_PAIRS = new ColorPair[]{

                new ColorPair(App.getAppContext().getResources().getColor(R.color.Blue), App.getAppContext().getResources().getColor(R.color.DodgerBlue)),
                new ColorPair(App.getAppContext().getResources().getColor(R.color.Red), App.getAppContext().getResources().getColor(R.color.DarkRed)),
                new ColorPair(App.getAppContext().getResources().getColor(R.color.Yellow), App.getAppContext().getResources().getColor(R.color.Gold)),
                new ColorPair(App.getAppContext().getResources().getColor(R.color.LawnGreen), App.getAppContext().getResources().getColor(R.color.Olive))};
    }


    SimonButton(Button b, int colorPair, int loc){
        this.button = b;
        this.colorPair = colorPair;
        this.location = loc;

        this.button.setBackgroundColor(this.getMainColor());

    }

    void flashButton(){

        this.button.setBackgroundColor(this.getSelectionColor());

        new Handler().postDelayed(new Runnable() {

            public void run() {
                button.setBackgroundColor(getMainColor()); // delay is over, turn color back to default.

            }
        }, FLASH_DURATION);



    }

    Button getButton(){
        return this.button;
    }

    int getLocation(){
        return this.location;
    }

    int getColor(){
        return this.colorPair;
    }

    int getMainColor(){
        return COLOR_PAIRS[this.colorPair].getMainColor();
    }

    private int getSelectionColor(){
        return COLOR_PAIRS[this.colorPair].getSubColor();
    }

    void setColorPair(int pair){
        this.colorPair = pair;
    }

    private static class ColorPair {

        private int mainColor, subColor;

        private ColorPair(int main, int sub) {
            this.mainColor = main;
            this.subColor = sub;
        }

        int getMainColor() {
            return mainColor;
        }

        int getSubColor() {
            return subColor;
        }
    }
}
