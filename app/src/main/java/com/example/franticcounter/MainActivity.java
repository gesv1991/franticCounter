package com.example.franticcounter;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<String> player1, player2, player3;
    private int result1 = 0, result2 = 0, result3 = 0;
    private int roundNumber;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_with_menu_bar);

        TextView roundName = findViewById(R.id.round);
        TextView roundInteger = findViewById(R.id.roundInteger);

        player1 = new ArrayList<>();
        player2 = new ArrayList<>();
        player3 = new ArrayList<>();
        roundNumber = 0;
        roundName.setText("Runde: ");
        roundInteger.setText(String.valueOf(roundNumber));
  /*       SharedPreferences sharedPreferences = this.getSharedPreferences("com.example.franticcounter", Context.MODE_PRIVATE);
       sharedPreferences.edit().putInt("player1Value", Integer.parseInt(resultPlayer1.getText().toString()));
        sharedPreferences.edit().putInt("player2Value", Integer.parseInt(resultPlayer2.getText().toString()));
        sharedPreferences.edit().putInt("player3Value", Integer.parseInt(resultPlayer3.getText().toString()));*/
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
        TextView resultPlayer1 = findViewById(R.id.resultPlayer1);
        TextView resultPlayer2 = findViewById(R.id.resultPlayer2);
        TextView resultPlayer3 = findViewById(R.id.resultPlayer3);

        hideKeyboardFrom(getApplicationContext(), playerInt1);

        if (!(playerInt1.getText().toString()).isEmpty() && !(playerInt2.getText().toString().isEmpty())
                && !(playerInt3.getText().toString()).isEmpty()) {

            playerOld1.setText(null);
            playerOld2.setText(null);
            playerOld3.setText(null);

            player1.add(playerInt1.getText().toString());
            player2.add(playerInt2.getText().toString());
            player3.add(playerInt3.getText().toString());

            result1 = result1+(Integer.parseInt(playerInt1.getText().toString()));
            result2 = result2+(Integer.parseInt(playerInt2.getText().toString()));
            result3 = result3+(Integer.parseInt(playerInt3.getText().toString()));

            for (int i = 0; i < player1.size(); i++) {
                playerOld1.append(player1.get(i) + "\n");
                playerOld2.append(player2.get(i) + "\n");
                playerOld3.append(player3.get(i) + "\n");
            }

            resultPlayer1.setText(String.valueOf(result1));
            resultPlayer2.setText(String.valueOf(result2));
            resultPlayer3.setText(String.valueOf(result3));

            playerInt1.setText(null);
            playerInt2.setText(null);
            playerInt3.setText(null);
            updateRoundField(player1.size());

        }
        else {
            //if Field is null
            Toast.makeText(getApplicationContext(), "Please add value to All player numbers", Toast.LENGTH_SHORT).show();
            return;
        }
    }

    private void updateRoundField(Integer round){

        TextView roundInteger = findViewById(R.id.roundInteger);

        roundInteger.setText(String.valueOf(round));
        Log.i("updateRoundField_msg", "UpdateRoundField to " + round);
    }
}