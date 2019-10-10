package com.example.franticcounter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
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

    private static final String ROUND = "Aktuelle Runde: ";
    TinyDB saveListOfPlayers;
    private ArrayList<String> playerNames;
    private int roundNumber;
    private PlayerModel playerOne;
    private PlayerModel playerTwo;
    private PlayerModel playerThree;
    private PlayerModel playerFour;
    private EditText playerInt1;
    private EditText playerInt2;
    private EditText playerInt3;
    private EditText playerInt4;
    private TextView playerOld1;
    private TextView playerOld2;
    private TextView playerOld3;
    private TextView playerOld4;
    private EditText playerName1;
    private EditText playerName2;
    private EditText playerName3;
    private EditText playerName4;

    @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
    public static void hideKeyboardFrom(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_with_menu_bar);

        playerOne = new PlayerModel("", 0);
        playerTwo = new PlayerModel("", 0);
        playerThree = new PlayerModel("", 0);
        playerFour = new PlayerModel("", 0);

        TextView roundName = findViewById(R.id.round);
        TextView roundInteger = findViewById(R.id.roundInteger);

        playerNames = new ArrayList<>();
        roundNumber = 0;
        roundName.setText(ROUND);
        roundInteger.setText(String.valueOf(roundNumber));
        saveListOfPlayers = new TinyDB(getApplicationContext());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_loadData) {

            playerOne.setPointHistory(saveListOfPlayers.getListInt("Player1SaveData"));
            playerTwo.setPointHistory(saveListOfPlayers.getListInt("Player2SaveData"));
            playerThree.setPointHistory(saveListOfPlayers.getListInt("Player3SaveData"));
            playerFour.setPointHistory(saveListOfPlayers.getListInt("Player4SaveData"));

            playerNames = saveListOfPlayers.getListString("PlayerNames");

            if (!playerOne.getPointHistory().isEmpty() || !playerOne.getName().isEmpty()) {
                updateListOfOldPoints(playerOne.getPointHistory(), playerTwo.getPointHistory(), playerThree.getPointHistory(), playerFour.getPointHistory());
                updateRoundField(playerOne.getPointHistory().size());
                Toast.makeText(this, "Last data has been loaded", Toast.LENGTH_SHORT).show();
                fillInTheNamesAfterLoading(playerNames);

                return true;
            } else {
                Toast.makeText(this, "There is no saved data", Toast.LENGTH_SHORT).show();
                return true;

            }
        }
        if (id == R.id.action_deleteData) {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setTitle("Löschen");
            builder.setMessage("Sollen alle Daten gelöscht werden?");

            builder.setPositiveButton("Ja sicher", new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialog, int which) {
                    deleteAllData();
                    dialog.dismiss();
                }
            });

            builder.setNegativeButton("Nein", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {

                    // Do nothing
                    dialog.dismiss();
                }
            });

            AlertDialog alert = builder.create();
            alert.show();
        }
        if (id == R.id.action_identityTheft) {

            if (!playerOne.getPointHistory().isEmpty()) {
                //players have data in history. switch biggest and smallest.


            }

            Toast.makeText(this, "There is no saved data", Toast.LENGTH_SHORT).show();

        }

        return super.onOptionsItemSelected(item);
    }

    public void deleteAllData() {

        saveListOfPlayers.clear();
        setAllFieldsToNull();
        Toast.makeText(this, "Data has been deleted", Toast.LENGTH_SHORT).show();

    }

    public void clickFunction(View view) {

        playerInt1 = findViewById(R.id.player1Int);
        playerInt2 = findViewById(R.id.player2Int);
        playerInt3 = findViewById(R.id.player3Int);
        playerInt4 = findViewById(R.id.player4Int);
        playerOld1 = findViewById(R.id.oldIntegersPlayer1);
        playerOld2 = findViewById(R.id.oldIntegersPlayer2);
        playerOld3 = findViewById(R.id.oldIntegersPlayer3);
        playerOld4 = findViewById(R.id.oldIntegersPlayer4);
        playerName1 = findViewById(R.id.player1Name);
        playerName2 = findViewById(R.id.player2Name);
        playerName3 = findViewById(R.id.player3Name);
        playerName4 = findViewById(R.id.player4Name);

        playerNames.clear();
        playerNames.add(playerName1.getText().toString());
        playerNames.add(playerName2.getText().toString());
        playerNames.add(playerName3.getText().toString());
        playerNames.add(playerName4.getText().toString());

        hideKeyboardFrom(getApplicationContext(), playerInt1);

        if (!(playerInt1.getText().toString()).isEmpty() && !(playerInt2.getText().toString().isEmpty())
                && !(playerInt3.getText().toString()).isEmpty() && !(playerInt4.getText().toString().isEmpty())) {

            playerOld1.setText(null);
            playerOld2.setText(null);
            playerOld3.setText(null);
            playerOld4.setText(null);

            playerOne.getPointHistory().add(Integer.parseInt(playerInt1.getText().toString()));
            playerTwo.getPointHistory().add(Integer.parseInt(playerInt2.getText().toString()));
            playerThree.getPointHistory().add(Integer.parseInt(playerInt3.getText().toString()));
            playerFour.getPointHistory().add(Integer.parseInt(playerInt4.getText().toString()));

            updateListOfOldPoints(playerOne.getPointHistory(), playerTwo.getPointHistory(), playerThree.getPointHistory(), playerFour.getPointHistory());

            playerInt1.setText(null);
            playerInt2.setText(null);
            playerInt3.setText(null);
            playerInt4.setText(null);
            updateRoundField(playerOne.getPointHistory().size());

            saveListOfPlayers.putListInt("Player1SaveData", playerOne.getPointHistory());
            saveListOfPlayers.putListInt("Player2SaveData", playerTwo.getPointHistory());
            saveListOfPlayers.putListInt("Player3SaveData", playerThree.getPointHistory());
            saveListOfPlayers.putListInt("Player4SaveData", playerFour.getPointHistory());
            saveListOfPlayers.putListString("PlayerNames", playerNames);
        } else {
            //if Field is null
            Toast.makeText(getApplicationContext(), "Please add value to All player numbers", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateListOfOldPoints(ArrayList player1, ArrayList player2,
                                       ArrayList player3, ArrayList player4) {

        playerOld1 = findViewById(R.id.oldIntegersPlayer1);
        playerOld2 = findViewById(R.id.oldIntegersPlayer2);
        playerOld3 = findViewById(R.id.oldIntegersPlayer3);
        playerOld4 = findViewById(R.id.oldIntegersPlayer4);
        TextView resultPlayer1 = findViewById(R.id.resultPlayer1);
        TextView resultPlayer2 = findViewById(R.id.resultPlayer2);
        TextView resultPlayer3 = findViewById(R.id.resultPlayer3);
        TextView resultPlayer4 = findViewById(R.id.resultPlayer4);

        resultPlayer1.setMovementMethod(new ScrollingMovementMethod());
        resultPlayer2.setMovementMethod(new ScrollingMovementMethod());
        resultPlayer3.setMovementMethod(new ScrollingMovementMethod());
        resultPlayer4.setMovementMethod(new ScrollingMovementMethod());

        resultPlayer1.setText(String.valueOf(calculateResultAndAddPointsToList(player1, playerOld1)));
        resultPlayer2.setText(String.valueOf(calculateResultAndAddPointsToList(player2, playerOld2)));
        resultPlayer3.setText(String.valueOf(calculateResultAndAddPointsToList(player3, playerOld3)));
        resultPlayer4.setText(String.valueOf(calculateResultAndAddPointsToList(player4, playerOld4)));
    }

    private void fillInTheNamesAfterLoading(ArrayList<String> names) {

        if (!names.get(0).isEmpty()) {

            playerName1 = findViewById(R.id.player1Name);
            playerName2 = findViewById(R.id.player2Name);
            playerName3 = findViewById(R.id.player3Name);
            playerName4 = findViewById(R.id.player4Name);
            playerName1.setText(names.get(0));
            playerName2.setText(names.get(1));
            playerName3.setText(names.get(2));
            playerName4.setText(names.get(3));

        } else {
            Toast.makeText(getApplicationContext(), "There are no saved names!", Toast.LENGTH_SHORT).show();
        }
    }

    private int calculateResultAndAddPointsToList(List<Integer> listOfPoints, TextView oldNumberList) {

        int zwischenzahl = 0;
        int resultTotal = 0;

        for (int i = 0; i < listOfPoints.size(); i++) {
            zwischenzahl = listOfPoints.get(i) + zwischenzahl;
            resultTotal = zwischenzahl + resultTotal;
            zwischenzahl = 0;

            oldNumberList.append(listOfPoints.get(i) + "\n");
        }
        return resultTotal;
    }

    private void updateRoundField(Integer round) {

        TextView roundInteger = findViewById(R.id.roundInteger);

        round++;
        roundInteger.setText(String.valueOf(round));
        Log.i("updateRoundField_msg", "UpdateRoundField to " + round);
    }

    public void setAllFieldsToNull() {

        playerInt1 = findViewById(R.id.player1Int);
        playerInt2 = findViewById(R.id.player2Int);
        playerInt3 = findViewById(R.id.player3Int);
        playerInt4 = findViewById(R.id.player4Int);
        playerOld1 = findViewById(R.id.oldIntegersPlayer1);
        playerOld2 = findViewById(R.id.oldIntegersPlayer2);
        playerOld3 = findViewById(R.id.oldIntegersPlayer3);
        playerOld4 = findViewById(R.id.oldIntegersPlayer4);
        playerName1 = findViewById(R.id.player1Name);
        playerName2 = findViewById(R.id.player2Name);
        playerName3 = findViewById(R.id.player3Name);
        playerName4 = findViewById(R.id.player4Name);

        playerOne.getPointHistory().clear();
        playerTwo.getPointHistory().clear();
        playerThree.getPointHistory().clear();
        playerFour.getPointHistory().clear();
        playerNames.clear();

        playerName1.setText("");
        playerName2.setText("");
        playerName3.setText("");
        playerName4.setText("");
        playerOld1.setText(null);
        playerOld2.setText(null);
        playerOld3.setText(null);
        playerOld4.setText(null);
        playerInt1.setText(null);
        playerInt2.setText(null);
        playerInt3.setText(null);
        playerInt4.setText(null);
    }
}