package main;

import android.app.Activity;
import android.content.Intent;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.admin.simplesimon.R;
import com.example.admin.simplesimon.battleShip.BattleShipGame;
import com.example.admin.simplesimon.simon.SimonGameMode;


public class MainActivity extends Activity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        startSimonButton_Init();
        startBattleShipButton_Init();

    }

    private void startSimonButton_Init(){
        Button startSimon = (Button)findViewById(R.id.launchSimonButton);
        startSimon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), SimonGameMode.class);
                startActivity(intent);
            }
        });
    }


    private void startBattleShipButton_Init(){
        Button startSimon = (Button)findViewById(R.id.launchBattleShip);
        startSimon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), BattleShipGame.class);
                startActivity(intent);
            }
        });
    }

    protected void onStart(){
        super.onStart();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
