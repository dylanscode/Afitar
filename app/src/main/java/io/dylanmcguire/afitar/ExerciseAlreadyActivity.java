package io.dylanmcguire.afitar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ExerciseAlreadyActivity extends AppCompatActivity {

    private long start;
    public static String EXTRA_MESSAGE = "omgsouniq";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_already);
        Intent intent = getIntent();
        String heartRate = intent.getStringExtra(UpdateExerciseActivity.EXTRA_MESSAGE);
        TextView textView = new TextView(this);
        textView.setText("Keep your heart rate above " + heartRate + " BPM!");
        LinearLayout layout = (LinearLayout) findViewById(R.id.exercise_already);
        layout.addView(textView);
        start = System.currentTimeMillis();
    }

    public void doneExercising(View view) {
        long minsPassed = (System.currentTimeMillis() - start) / 60000;
        long pts = minsPassed / 3;
        String msg;
        if (pts <= 0) {
            msg = "You're going to have to exercise a little longer than that!";
        } else {
            msg = "Congratulations! You earned " + Long.toString(pts) + " points!";
        }
        Intent intent = new Intent(this, DisplayResultActivity.class);
        intent.putExtra(EXTRA_MESSAGE, msg);
        startActivity(intent);
    }
}
