package com.example.admin.simplesimon.battleShip;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.GridView;

import com.example.admin.simplesimon.R;



public class BattleShipGame extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battle_ship_game);



        GridLayout tileGrid = (GridLayout)(findViewById(R.id.activity_battle_ship_game));



        for (int i = 0; i < 100; i++){
            Tile t = new Tile(this.getApplicationContext());
            t.initTile((i % 2 == 0 ? Tile.SHIP : Tile.EMPTY));
            tileGrid.addView(t);
        }


    }
}
