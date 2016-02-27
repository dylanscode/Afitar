package io.dylanmcguire.afitar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MonitorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monitor);
    }

    public void updateFood(View view) {
        Intent intent = new Intent(this, UpdateFoodActivity.class);
        startActivity(intent);
    }

    public void updateExercise(View view) {
        Intent intent = new Intent(this, UpdateExerciseActivity.class);
        startActivity(intent);
    }

    public void updateSleep(View view) {
        Intent intent = new Intent(this, UpdateSleepActivity.class);
        startActivity(intent);
    }

    public void updateMeds(View view) {
        Intent intent = new Intent(this, UpdateMedsActivity.class);
        startActivity(intent);
    }

    public void updateMood(View view) {
        Intent intent = new Intent(this, UpdateMoodActivity.class);
        startActivity(intent);
    }
}
