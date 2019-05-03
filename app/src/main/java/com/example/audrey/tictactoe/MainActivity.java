package com.example.audrey.tictactoe;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button[][] buttons = new Button[3][3];

    private boolean playerATurn = true;

    private int roundCount;

    private int playerAPoints;
    private int playerBPoints;

    private TextView textViewPlayerA;
    private TextView textViewPlayerB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewPlayerA = findViewById(R.id.text_view_playerA);
        textViewPlayerB = findViewById(R.id.text_view_playerB);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                String buttonID = "button_" + i + j;
                int resourceID = getResources().getIdentifier(buttonID, "id", getPackageName());
                buttons[i][j] = findViewById(resourceID);
                buttons[i][j].setOnClickListener(this);
            }
        }

        Button buttonReset = findViewById(R.id.button_reset);
        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetGame();
            }
        });


    }

    @Override
    public void onClick(View v) {
        if (!((Button) v).getText().toString().equals("")) {
            return;
        }

        if (playerATurn) {
            ((Button) v).setText("X");
        } else {
            ((Button) v).setText("O");
        }

        roundCount++;

        if (checkForWin()) {
            if (playerATurn) {
                playerAWins();
            } else {
                playerBWins();
            }
        } else if (roundCount == 9) {
            draw();
        } else {
            playerATurn = !playerATurn;     // switch turns
        }
    }

    private boolean checkForWin() {
        String[][] field = new String[3][3];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                field[i][j] = buttons[i][j].getText().toString();
            }
        }

        // checks rows
        for (int i = 0; i < 3; i++) {
            if (field[i][0].equals(field[i][1])
                    && field[i][0].equals(field[i][2])
                    && !field[i][0].equals("")) {
                return true;
            }
        }

        // checks columns
        for (int i = 0; i < 3; i++) {
            if (field[0][i].equals(field[1][i])
                    && field[0][i].equals(field[2][i])
                    && !field[0][i].equals("")) {
                return true;
            }
        }

        // checks diagonal upper left to lower right
        if (field[0][0].equals(field[1][1])
                && field[0][0].equals(field[2][2])
                && !field[0][0].equals("")) {
            return true;
        }

        // checks diagonal upper right to lower left
        if (field[0][2].equals(field[1][1])
                && field[0][2].equals(field[2][0])
                && !field[0][2].equals("")) {
            return true;
        }

        return false; // none of the above cases is true, so not yet a winner

    }

    private void playerAWins() {
        playerAPoints++;
        Toast.makeText(this, "Player A wins!", Toast.LENGTH_SHORT).show();
        updatePointsText();
        resetBoard();
    }
    private void playerBWins() {
        playerBPoints++;
        Toast.makeText(this, "Player B wins!", Toast.LENGTH_SHORT).show();
        updatePointsText();
        resetBoard();
    }

    private void draw() {
        Toast.makeText(this, "A Tie!", Toast.LENGTH_SHORT).show();
        resetBoard();
    }

    private void updatePointsText() {
        textViewPlayerA.setText("Player A: " + playerAPoints);
        textViewPlayerB.setText("Player B: " + playerBPoints);
    }

    private void resetBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setText(""); // resets text to empty string (blank)
            }
        }

        roundCount = 0;
        playerATurn = true;
    }

    private void resetGame() {
        playerAPoints = 0;
        playerBPoints = 0;
        updatePointsText();
        resetBoard();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("roundCount", roundCount);
        outState.putInt("playerAPoints", playerAPoints);
        outState.putInt("playerBPoints", playerBPoints);
        outState.putBoolean("playerATurn", playerATurn);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        roundCount = savedInstanceState.getInt("roundCount");
        playerAPoints = savedInstanceState.getInt("playerAPoints");
        playerBPoints = savedInstanceState.getInt("playerBPoints");

        playerATurn = savedInstanceState.getBoolean("playerATurn");
    }
}
