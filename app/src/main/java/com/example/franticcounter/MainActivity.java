package com.example.franticcounter;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.isNull;

public class MainActivity extends AppCompatActivity {

    List<String> player1, player2, player3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        player1 = new ArrayList<>();
        player2 = new ArrayList<>();
        player3 = new ArrayList<>();

    }
    public static void hideKeyboardFrom(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }


    public void clickFunction(View view) {

        EditText playerInt1 = findViewById(R.id.player1Int);
        EditText playerInt2 = findViewById(R.id.player2Int);
        EditText playerInt3 = findViewById(R.id.player3Int);
        TextView playerOld1 = findViewById(R.id.oldIntegersPlayer1);
        TextView playerOld2 = findViewById(R.id.oldIntegersPlayer2);
        TextView playerOld3 = findViewById(R.id.oldIntegersPlayer3);

        hideKeyboardFrom(getApplicationContext(), playerInt1);

        if (!(playerInt1.getText().toString()).isEmpty() && !(playerInt2.getText().toString().isEmpty())
                && !(playerInt3.getText().toString()).isEmpty()) {

            playerOld1.setText(null);
            playerOld2.setText(null);
            playerOld3.setText(null);

            player1.add(playerInt1.getText().toString());
            player2.add(playerInt2.getText().toString());
            player3.add(playerInt3.getText().toString());

            for (int i = 0; i < player1.size(); i++) {
                playerOld1.append(player1.get(i) + "\n");
                playerOld2.append(player2.get(i) + "\n");
                playerOld3.append(player3.get(i) + "\n");
            }

            playerInt1.setText(null);
            playerInt2.setText(null);
            playerInt3.setText(null);
        }
        //if Field is null
        Toast.makeText(getApplicationContext(), "Please add value to All player numbers", Toast.LENGTH_SHORT).show();
        return;
    }
    }
