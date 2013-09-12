package com.waldm.MastermindAndroid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HomeActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);

        Button newGame = (Button) findViewById(R.id.new_game);
        newGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newGame();
            }
        });

        Button challengeComputer = (Button) findViewById(R.id.challenge_computer);
        challengeComputer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                challengeComputer();
            }
        });

        Button settings = (Button) findViewById(R.id.settings);
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                settings();
            }
        });
    }

    private void newGame() {
        Intent intent = new Intent(this, HumanGameActivity.class);
        startActivity(intent);
    }

    private void challengeComputer() {
        Intent intent = new Intent(this, ChallengeComputerActivity.class);
        startActivity(intent);
    }

    private void settings() {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }
}
