package io.dylanmcguire.afitar;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class DisplayResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_result);
        Intent intent = getIntent();
        String msg = intent.getStringExtra(ExerciseAlreadyActivity.EXTRA_MESSAGE);
        TextView view = (TextView) findViewById(R.id.result_view);
        view.setText(msg);
    }

    public void returnHome(View view) {
        Intent intent = new Intent(this, AfitarActivity.class);
        startActivity(intent);
    }
}
