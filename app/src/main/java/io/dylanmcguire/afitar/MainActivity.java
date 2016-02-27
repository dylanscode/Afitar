package io.dylanmcguire.afitar;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.microsoft.band.BandClient;
import com.microsoft.band.BandClientManager;
import com.microsoft.band.BandException;
import com.microsoft.band.BandInfo;
import com.microsoft.band.ConnectionState;
import com.microsoft.band.*;

public class MainActivity extends AppCompatActivity {

    public final static String EXTRA_MESSAGE = "io.dylanmcguire.afitar.MESSAGE";
    static String message = "not even close";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void checkConnection(View view) {
        Intent intent = new Intent(this, ConfirmConnectionActivity.class);

        // connect to the band
        BandInfo[] pairedBands = BandClientManager.getInstance().getPairedBands();
        final BandClient bandClient = BandClientManager.getInstance().create(this, pairedBands[0]);

        new Thread(new Runnable() {

            @Override
            public void run() {

                TextView textView = new TextView(getApplicationContext());
                textView.setTextSize(40);

                // connect to the Band
                BandPendingResult<ConnectionState> pendingResult = bandClient.connect();

                try {
                    // check whether we're connected
                    ConnectionState state = pendingResult.await();
                    if (state == ConnectionState.CONNECTED) {
                        textView.setText("connected!");
                    } else {
                        textView.setText("Not connected :(");
                    }
                } catch (InterruptedException e) {
                    textView.setText("interrupted");
                } catch (BandException e) {
                    textView.setText("band exception");
                }

                LinearLayout layoutZZZ = (LinearLayout) findViewById(R.id.mcontentStuff);
                layoutZZZ.addView(textView);
            }
        });

        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }
}
