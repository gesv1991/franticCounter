package com.example.franticcounter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
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

    private ArrayList<Integer> listOfPointsPlayer1, listOfPointsPlayer2, listOfPointsPlayer3;
    private ArrayList<String> playerNames;
    private int result1 = 0, result2 = 0, result3 = 0;
    private int roundNumber;

    private EditText playerInt1;
    private EditText playerInt2;
    private EditText playerInt3;
    private TextView playerOld1;
    private TextView playerOld2;
    private TextView playerOld3;
    private EditText playerName1;
    private EditText playerName2;
    private EditText playerName3;
    private TextView resultPlayer1;
    private TextView resultPlayer2;
    private TextView resultPlayer3;
    private TextView roundName;
    private TextView roundInteger;
    private TinyDB saveListOfPlayers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_with_menu_bar);

        roundName = findViewById(R.id.round);
        roundInteger = findViewById(R.id.roundInteger);

        listOfPointsPlayer1 = new ArrayList<>();
        listOfPointsPlayer2 = new ArrayList<>();
        listOfPointsPlayer3 = new ArrayList<>();
        playerNames = new ArrayList<>();
        roundNumber = 0;
        roundName.setText("Aktuelle Runde: ");
        roundInteger.setText(String.valueOf(roundNumber));
        saveListOfPlayers = new TinyDB(getApplicationContext());
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
        if (id == R.id.action_loadData) {

            listOfPointsPlayer1=saveListOfPlayers.getListInt("Player1SaveData");
            listOfPointsPlayer2=saveListOfPlayers.getListInt("Player2SaveData");
            listOfPointsPlayer3=saveListOfPlayers.getListInt("Player3SaveData");
            playerNames= saveListOfPlayers.getListString("PlayerNames");

            if (!listOfPointsPlayer1.isEmpty()|| !playerNames.isEmpty()) {
                updateListOfOldPoints(listOfPointsPlayer1, listOfPointsPlayer2, listOfPointsPlayer3);
                updateRoundField(listOfPointsPlayer1.size());
                Toast.makeText(this, "Last data has been loaded", Toast.LENGTH_SHORT).show();
                fillInTheNamesAfterLoading(playerNames);

                return true;
            }
            else{
                Toast.makeText(this, "There is no saved data", Toast.LENGTH_SHORT).show();
                return true;

            }
        }
        if (id == R.id.action_deleteData) {

            saveListOfPlayers.clear();
            setAllFieldsToNull();
            updateRoundField(listOfPointsPlayer1.size());
            updateListOfOldPoints(listOfPointsPlayer1, listOfPointsPlayer2, listOfPointsPlayer3);
            fillInTheNamesAfterLoading(playerNames);
            Toast.makeText(this, "Data has been deleted", Toast.LENGTH_SHORT).show();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public static void hideKeyboardFrom(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }


    public void clickFunction(View view) {

        playerInt1 = findViewById(R.id.player1Int);
        playerInt2 = findViewById(R.id.player2Int);
        playerInt3 = findViewById(R.id.player3Int);
        playerOld1 = findViewById(R.id.oldIntegersPlayer1);
        playerOld2 = findViewById(R.id.oldIntegersPlayer2);
        playerOld3 = findViewById(R.id.oldIntegersPlayer3);
        playerName1 = findViewById(R.id.player1Name);
        playerName2 = findViewById(R.id.player2Name);
        playerName3 = findViewById(R.id.player3Name);

        playerNames.clear();
        playerNames.add(playerName1.getText().toString());
        playerNames.add(playerName2.getText().toString());
        playerNames.add(playerName3.getText().toString());

        hideKeyboardFrom(getApplicationContext(), playerInt1);

        if (!(playerInt1.getText().toString()).isEmpty() && !(playerInt2.getText().toString().isEmpty())
                && !(playerInt3.getText().toString()).isEmpty()) {

            playerOld1.setText(null);
            playerOld2.setText(null);
            playerOld3.setText(null);

            listOfPointsPlayer1.add(Integer.parseInt(playerInt1.getText().toString()));
            listOfPointsPlayer2.add(Integer.parseInt(playerInt2.getText().toString()));
            listOfPointsPlayer3.add(Integer.parseInt(playerInt3.getText().toString()));

            updateListOfOldPoints(listOfPointsPlayer1, listOfPointsPlayer2, listOfPointsPlayer3);

            playerInt1.setText(null);
            playerInt2.setText(null);
            playerInt3.setText(null);
            updateRoundField(listOfPointsPlayer1.size());

            saveListOfPlayers.putListInt("Player1SaveData", listOfPointsPlayer1);
            saveListOfPlayers.putListInt("Player2SaveData", listOfPointsPlayer2);
            saveListOfPlayers.putListInt("Player3SaveData", listOfPointsPlayer3);
            saveListOfPlayers.putListString("PlayerNames", playerNames);
        }
        else {
            //if Field is null
            Toast.makeText(getApplicationContext(), "Please add value to All player numbers", Toast.LENGTH_SHORT).show();
            return;
        }
    }
    private void updateListOfOldPoints(ArrayList listOfPointsPlayer1, ArrayList listOfPointsPlayer2,
                                       ArrayList listOfPointsPlayer3){

        playerOld1 = findViewById(R.id.oldIntegersPlayer1);
        playerOld2 = findViewById(R.id.oldIntegersPlayer2);
        playerOld3 = findViewById(R.id.oldIntegersPlayer3);
        resultPlayer1 = findViewById(R.id.resultPlayer1);
        resultPlayer2 = findViewById(R.id.resultPlayer2);
        resultPlayer3 = findViewById(R.id.resultPlayer3);


        result1 = calculateResultAndAddPointsToList(listOfPointsPlayer1, playerOld1);
        result2 = calculateResultAndAddPointsToList(listOfPointsPlayer2, playerOld2);
        result3 = calculateResultAndAddPointsToList(listOfPointsPlayer3, playerOld3);


        resultPlayer1.setText(String.valueOf(result1));
        resultPlayer2.setText(String.valueOf(result2));
        resultPlayer3.setText(String.valueOf(result3));
    }

    private void fillInTheNamesAfterLoading(ArrayList listOfNames){

        if(!listOfNames.isEmpty()) {

            playerName1 = findViewById(R.id.player1Name);
            playerName2 = findViewById(R.id.player2Name);
            playerName3 = findViewById(R.id.player3Name);
            playerName1.setText(listOfNames.get(0).toString());
            playerName2.setText(listOfNames.get(1).toString());
            playerName3.setText(listOfNames.get(2).toString());

            if (playerNames.get(2).equalsIgnoreCase("studer")) {
                playerName3.setText("STUDER");
                playerName3.setTextColor(Color.parseColor("#f442df"));
                Toast.makeText(getApplicationContext(), "Haaa Gaay", Toast.LENGTH_LONG).show();

            }
            if (playerNames.get(1).equalsIgnoreCase("studer")) {
                playerName2.setText("STUDER");
                playerName2.setTextColor(Color.parseColor("#f442df"));
                Toast.makeText(getApplicationContext(), "Haaa Gaay", Toast.LENGTH_LONG).show();
            }

            if (playerNames.get(0).equalsIgnoreCase("studer")) {
                playerName1.setText("STUDER");
                playerName1.setTextColor(Color.parseColor("#f442df"));
                Toast.makeText(getApplicationContext(), "Haaa Gaay", Toast.LENGTH_LONG).show();
            }
        }

    }
    private int calculateResultAndAddPointsToList(List<Integer> listOfPoints, TextView oldNumberList){

        int zwischenzahl = 0;
        int resultTotal = 0;

        for (int i=0; i< listOfPoints.size();i++){
            zwischenzahl=listOfPoints.get(i)+zwischenzahl;
            resultTotal=zwischenzahl+resultTotal;
            zwischenzahl=0;

            oldNumberList.append(listOfPoints.get(i) + "\n");
        }
        return resultTotal;
    }

    private void updateRoundField(Integer round){

        TextView roundInteger = findViewById(R.id.roundInteger);

        round++;
        roundInteger.setText(String.valueOf(round));
        Log.i("updateRoundField_msg", "UpdateRoundField to " + round);
    }

    private void setAllFieldsToNull(){

        playerInt1 = findViewById(R.id.player1Int);
        playerInt2 = findViewById(R.id.player2Int);
        playerInt3 = findViewById(R.id.player3Int);
        playerOld1 = findViewById(R.id.oldIntegersPlayer1);
        playerOld2 = findViewById(R.id.oldIntegersPlayer2);
        playerOld3 = findViewById(R.id.oldIntegersPlayer3);
        playerName1 = findViewById(R.id.player1Name);
        playerName2 = findViewById(R.id.player2Name);
        playerName3 = findViewById(R.id.player3Name);

        listOfPointsPlayer1.clear();
        listOfPointsPlayer2.clear();
        listOfPointsPlayer3.clear();
        playerNames.clear();

        playerName1.setText("");
        playerName2.setText("");
        playerName3.setText("");
        playerOld1.setText(null);
        playerOld2.setText(null);
        playerOld3.setText(null);
        playerInt1.setText(null);
        playerInt2.setText(null);
        playerInt3.setText(null);
    }
}