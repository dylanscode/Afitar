package io.dylanmcguire.afitar;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class EnterMoodActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_mood);
    }

    public void terribleMoodButton(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getBaseContext());
        builder.setMessage("That's awful! Hopefully you'll feel better soon!")
                .setTitle("Oh no!");
        AlertDialog dialog = builder.create();
        backUp(view);
    }

    public void badMoodButton(View view) {

        backUp(view);
    }

    public void okMoodButton(View view) {

        backUp(view);
    }

    public void goodMoodButton(View view) {

        backUp(view);
    }

    public void greatMoodButton(View view) {

        backUp(view);
    }

    public void backUp(View view) {
        Intent intent = new Intent(this, UpdateMoodActivity.class);
        startActivity(intent);
    }
}
