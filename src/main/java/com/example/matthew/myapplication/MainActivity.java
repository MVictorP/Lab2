package com.example.matthew.myapplication;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {
    public static final String PREFS_NAME = "MyPrefsFile";

    Button btnStrike;
    Button btnBall;

    TextView txtStrikeCount;
    TextView txtBallCount;
    TextView txtOutCount;

    int intStrikeValue;
    int intBallValue;
    int intOutValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        intOutValue = settings.getInt("silentMode", 0);
        txtOutCount = (TextView)findViewById(R.id.textViewOuts);
        txtOutCount.setText(String.valueOf(intOutValue));

        btnStrike = (Button)findViewById(R.id.buttonStrike);
        btnBall = (Button)findViewById(R.id.buttonBall);

        txtStrikeCount = (TextView)findViewById(R.id.textViewStrike);
        txtBallCount = (TextView)findViewById(R.id.textViewBall);


        btnStrike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strikeValue = txtStrikeCount.getText().toString();
                intStrikeValue = Integer.parseInt(strikeValue);
                intStrikeValue++;
                if (intStrikeValue == 3){
                    showAlertOut();
                    intBallValue = 0;
                    intStrikeValue = 0;
                }
                txtStrikeCount.setText(String.valueOf(intStrikeValue));
                txtBallCount.setText(String.valueOf(intBallValue));
            }
        });
        btnBall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ballValue = txtBallCount.getText().toString();
                intBallValue = Integer.parseInt(ballValue);
                intBallValue++;
                if(intBallValue == 4){
                    showAlertBall();
                    intBallValue = 0;
                    intStrikeValue = 0;
                }
                txtBallCount.setText(String.valueOf(intBallValue));
                txtStrikeCount.setText(String.valueOf(intStrikeValue));
            }
        });
    }

    public void showAlertOut(){

        intOutValue++;
        txtOutCount.setText(String.valueOf(intOutValue));

        AlertDialog.Builder myAlert = new AlertDialog.Builder(this);
        myAlert.setMessage("")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setTitle("Out!")
                .create();
        myAlert.show();
    }
    public void showAlertBall(){
        AlertDialog.Builder myAlert = new AlertDialog.Builder(this);
        myAlert.setMessage("")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setTitle("Walk!")
                .create();
        myAlert.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_about) {
            Intent intent = new Intent("com.example.matthew.myapplication.SecondActivity");
            startActivity(intent);

            return true;
        }
        if (id == R.id.action_reset) {
            intBallValue = 0;
            intStrikeValue = 0;
            intOutValue = 0;
            txtStrikeCount.setText(String.valueOf(intStrikeValue));
            txtBallCount.setText(String.valueOf(intBallValue));
            txtOutCount.setText(String.valueOf(intOutValue));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onStop(){
        super.onStop();
        String outValue = txtOutCount.getText().toString();
        intOutValue = Integer.parseInt(outValue);
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt("silentMode", intOutValue);
        editor.commit();
    }

}
