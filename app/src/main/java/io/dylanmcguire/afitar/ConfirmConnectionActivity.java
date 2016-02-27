package io.dylanmcguire.afitar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ConfirmConnectionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_message);

        // get the string
        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);

        // make a textview with the string
        TextView textView = new TextView(getApplicationContext());
        textView.setTextSize(40);
        textView.setText(message);

        // add the textview to the view
        LinearLayout layoutZZZ = (LinearLayout) findViewById(R.id.contentStuff);
        layoutZZZ.addView(textView);

    }
}
