package com.example.admin.simplesimon.simon;


import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.support.v4.app.NavUtils;

import com.example.admin.simplesimon.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class SimonGame extends Activity {

    /***************************************/
    /*           DEFINITIONS               */
    /***************************************/

    private static final String LOGTAG = "[FuckOysters] - "; // special tag to filter out log printouts to what I want them to be.

//    private TextView message; // the message at the top of the screen
    private MiddleButton middleText;

    // TO-DO: remove static below this message, fuck if I know why but it causes a mem leak. To do this scramble needs to be not be called from SimonButton class.
    // the layouts (serve as buttons (outer) and flashing shades of color (inner))
    private SimonButton rightButton, topButton, bottomButton, leftButton;




    private static final int MAX_DELAY = 500; // in mSec
    private static final int MIN_DELAY = 100; // in mSec
    private static int delay;

    private List<Integer> randSequence; // computer generated sequence
    private List<Integer> playerInput ; // players attempt at solution
    private static boolean listen; // only collect data from user when true.
    private static int level; // how many steps in sequence?
    private Random generator; // this chooses next color
    private static boolean gameOver; // when flase game is ongoing
    private static boolean generating;


    private static boolean scramble = false;
    private static boolean byLocation_NotByColor = true;    // true = by location, false = by color


    /***************************************/
    /*            END DEFINITIONS          */
    /***************************************/

    protected static void setMode(boolean mode){
        byLocation_NotByColor = mode;
        if (!mode)
            scramble = true;
        else
            scramble = false;
    }

    /**
     * generate next color for our player to remember
     */
    private void generateRandom(){

        int nextInSequence = generator.nextInt(4);
        randSequence.add(nextInSequence);
        level++;

        middleText.setText(((Integer)level).toString());

        delay = Math.max(MAX_DELAY - 10 * level, MIN_DELAY);

        System.out.println(LOGTAG + "Random sequence is now: " + randSequence);

    }



    /**
     * set all corner/layers to default colors and define pointer to teh layers define din the xml file.
     */
    private void initGameState(){

//        message = (TextView)findViewById(R.id.scoreCount);
//        message.setText("Click Me To Start");

        middleText = new MiddleButton((TextView)findViewById(R.id.level));
        middleText.setSymbol(MiddleButton.START);



        //button number 1 - top
        topButton = new SimonButton((Button)findViewById(R.id.topButton), SimonButton.BLUE, SimonButton.TOP_POS);

        //button number 1 - right
        rightButton = new SimonButton((Button)findViewById(R.id.rightButton), SimonButton.RED, SimonButton.RIGHT_POS);

        //button number 3 - bottom
        bottomButton = new SimonButton((Button)findViewById(R.id.bottomButton), SimonButton.GREEN, SimonButton.BOTTOM_POS);

        //button number 4 - left
        leftButton = new SimonButton((Button)findViewById(R.id.leftButton), SimonButton.YELLOW, SimonButton.LEFT_POS);

    }

    protected void scrambleColors(){

        Integer temp[] = {SimonButton.BLUE, SimonButton.RED, SimonButton.YELLOW, SimonButton.GREEN};
        List<Integer> permuteMe = Arrays.asList(temp);
        java.util.Collections.shuffle(permuteMe);


        topButton.setColorPair(permuteMe.get(0));       topButton.getButton().setBackgroundColor(topButton.getMainColor());
        rightButton.setColorPair(permuteMe.get(1));     rightButton.getButton().setBackgroundColor(rightButton.getMainColor());
        bottomButton.setColorPair(permuteMe.get(2));    bottomButton.getButton().setBackgroundColor(bottomButton.getMainColor());
        leftButton.setColorPair(permuteMe.get(3));      leftButton.getButton().setBackgroundColor(leftButton.getMainColor());


    }



    /**
     * after computer generates a new color we want to show the whole sequance from start to finish.
     */

    private int count = 0;
    private void displayCurrentLevelSequence () {

        final Handler h = new Handler();
        Runnable r = new Runnable() {
            @Override
            public void run() {

                generating = true;

                if (count == randSequence.size()) {
                    count = 0;
                    generating = false;
                    return;
                }

                if (byLocation_NotByColor)
                    locationToButton(randSequence.get(count++)).flashButton();
                else
                    colorToButton(randSequence.get(count++)).flashButton();



                h.postDelayed(this, delay); // not sure why I have two of these, one here and one bellow
            }
        };
        h.postDelayed( r, delay);

    }


    private SimonButton locationToButton(int loc){
        switch (loc) {
            case SimonButton.RIGHT_POS:
                return rightButton;

            case SimonButton.TOP_POS:
                return topButton;

            case SimonButton.BOTTOM_POS:
                return bottomButton;

            case SimonButton.LEFT_POS:
                return leftButton;
        }
        System.out.println(LOGTAG + "Location To Button method error - no button matches this location: " + loc);
        return null;
    }

    private SimonButton colorToButton(int color){
        // assumes color can only ever belong to one button at a time
        if(topButton.getColor() == color){
            return topButton;
        }
        else if(rightButton.getColor() == color){
            return rightButton;
        }
        else if(bottomButton.getColor() == color){
            return bottomButton;
        }
        else if(leftButton.getColor() == color){
            return leftButton;
        }
        else{
            System.out.println(LOGTAG + "Color To Button method error - no button matches this color: " + color);
        }
        return null;
    }

    /**
     * listen for clicks on the four corners and save inputs until we have a sequence eof the required length.
     */
    private void setInputListeners (){

/*        message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!listen){
                    if (gameOver) {
                        gameOver = false;
                        randSequence.clear();
                        level = 0;
                        initGameState();
                    }
                    else{
                        if (scramble) {
                            scrambleColors();
                        }
                    }



                    generateRandom(); // adds another color and raises level by one
                    message.setText("Level " + level); // display current level ( = length of sequence)
                    listen = true;
                    displayCurrentLevelSequence(); // shows player the current sequence


                    playerInput.clear(); // new guess, remove whatever we had before

                }
            }
        });*/

        middleText.getView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int mode = middleText.getMode();
                switch(mode){

                    case MiddleButton.START:
                        gameOver = false;
                        randSequence.clear();
                        level = 0;
                        initGameState();        // No break on purpose!

                    case MiddleButton.NEXT:
                        generateRandom(); // adds another color and raises level by one
                        middleText.setText(((Integer)level).toString()); // display current level ( = length of sequence)
                        listen = true;
                        displayCurrentLevelSequence(); // shows player the current sequence
                        playerInput.clear(); // new guess, remove whatever we had before
                        if (scramble) {
                            scrambleColors();
                        }
                        break;

                    case MiddleButton.RESTART:
                        gameOver = false;
                        randSequence.clear();
                        level = 0;
                        initGameState();
                        break;

                    case MiddleButton.TEXT: break;

                }


            }
        });


        setClickListener(rightButton);

        setClickListener(topButton);

        setClickListener(bottomButton);

        setClickListener(leftButton);



    }

    private void playGame(int choice){

        if (generating)
            return;

        playerInput.add(choice);
        if (randSequence.get(playerInput.size()-1) != choice){
            gameOver();
            return;
        }

        if (playerInput.size() >= level) {

//            message.setText("Good Job! - Click Me To Continue!");

            middleText.setSymbol(MiddleButton.NEXT);

            listen = false; // player guessed the correct length, do not accept anymore data till next level

        }



    }

    private void gameOver(){
        gameOver = true;
        level = 0;
        randSequence.clear();
//        message.setText("GAME OVER! - Click Me To Continue");
        middleText.setSymbol(MiddleButton.RESTART);
        listen = false;
    }

    private void setClickListener(final SimonButton b){

        b.getButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listen && !gameOver && !generating) {

                    int temp = locationToButton(b.getLocation()).getColor();    // use this to get none final access to button for current data

                    System.out.println(LOGTAG + "Chosen button color for button object: " + locationToButton(b.getLocation()) + " is: " + temp);

                    locationToButton(b.getLocation()).flashButton();

                    if (byLocation_NotByColor)
                        playGame(b.getLocation());
                    else
                        playGame(temp);
                }

            }
        });
    }


    private void initVariables(){
        randSequence = new ArrayList<Integer>(); // computer generated sequence
        playerInput = new ArrayList<Integer>(); // players attempt at solution
        listen = false; // only collect data from user when true.
        level = 0; // how many steps in sequence?
        generator = new Random(); // this chooses next color
        gameOver = true; // when flase game is ongoing
        generating = false;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simon_game);
        //mContext = this.getApplicationContext();
        getActionBar().setDisplayHomeAsUpEnabled(true);

        initVariables();
        initGameState();
        setInputListeners(); // listeners for clicks, will only listen if listen = true.


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
