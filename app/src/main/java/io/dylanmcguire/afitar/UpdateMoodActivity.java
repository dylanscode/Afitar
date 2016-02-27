package io.dylanmcguire.afitar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class UpdateMoodActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_mood);
    }

    public void enterCurrentMood(View view) {
        Intent intent = new Intent(this, EnterMoodActivity.class);
        startActivity(intent);
    }
}
