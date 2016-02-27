package io.dylanmcguire.afitar;

import android.content.Intent;
import android.os.AsyncTask;
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
import com.microsoft.band.sensors.BandCaloriesEvent;

import java.sql.Connection;

public class MainActivity extends AppCompatActivity {

    public final static String EXTRA_MESSAGE = "io.dylanmcguire.afitar.MESSAGE";
    static String message = "not even close";
    private TextView txtStatus;
    private BandClient client = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        txtStatus = (TextView) findViewById(R.id.txtStatus);
        new CheckConnectionTask().execute();
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
        final Intent intent = new Intent(this, ConfirmConnectionActivity.class);

        // connect to the band
        BandInfo[] pairedBands = BandClientManager.getInstance().getPairedBands();
        BandClient bandClient = BandClientManager.getInstance().create(this, pairedBands[0]);
        final BandPendingResult<ConnectionState> pendingResult = bandClient.connect();

        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    // check whether we're connected
                    ConnectionState state = pendingResult.await();
                    if (state == ConnectionState.CONNECTED) {
                        message = "connected!";
                    } else {
                        message = "Not connected :(";
                    }
                } catch (InterruptedException e) {
                    message = "interrupted";
                } catch (BandException e) {
                    message = "band exception";
                }

                intent.putExtra(EXTRA_MESSAGE, message);
                startActivity(intent);
            }
        });

    }

    // prints a message if necessary
    private void appendToUI(final String str) {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                txtStatus.setText(str);
            }
        });
    }

    // check if the band is connected
    private boolean getConnectedBandClient() throws InterruptedException, BandException {
        if (client == null) {
            BandInfo[] devices = BandClientManager.getInstance().getPairedBands();
            if (devices.length == 0) {
                appendToUI("Band isn't paired with your phone.\n");
                return false;
            }
            client = BandClientManager.getInstance().create(getBaseContext(), devices[0]);
        } else if (ConnectionState.CONNECTED == client.getConnectionState()) {
            return true;
        }

        appendToUI("Band is connecting...\n");
        return ConnectionState.CONNECTED == client.connect().await();
    }

    private class CheckConnectionTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            try {
                if (getConnectedBandClient()) {
                    appendToUI("Band is connected!");
                    String fwVersion = client.getFirmwareVersion().await();
                    appendToUI("Firmware version is " + fwVersion);
                } else {
                    appendToUI("Band is not connected. :(");
                }
            } catch (Exception e) {
                appendToUI(e.getMessage());
            }
            return null;
        }
    }
}
