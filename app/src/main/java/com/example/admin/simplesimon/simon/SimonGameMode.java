package com.example.admin.simplesimon.simon;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.admin.simplesimon.R;

public class SimonGameMode extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simon_game_mode);

        byColorSimonButton_Init();
        classicSimonButton_Init();
    }

    private void byColorSimonButton_Init(){
        Button byColor = (Button)findViewById(R.id.byColorButton);
        byColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SimonGame.setMode(false);
                Intent intent = new Intent(view.getContext(), SimonGame.class);
                startActivity(intent);
            }
        });
    }

    private void classicSimonButton_Init(){
        Button byLocation = (Button)findViewById(R.id.classicSimonButton);
        byLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SimonGame.setMode(true);
                Intent intent = new Intent(view.getContext(), SimonGame.class);
                startActivity(intent);
            }
        });
    }

}
