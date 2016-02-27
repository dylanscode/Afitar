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
import com.microsoft.band.sensors.BandAccelerometerEvent;
import com.microsoft.band.sensors.BandAccelerometerEventListener;
import com.microsoft.band.sensors.BandCaloriesEvent;

import com.microsoft.band.sensors.BandHeartRateEvent;
import com.microsoft.band.sensors.BandHeartRateEventListener;
import com.microsoft.band.sensors.HeartRateConsentListener;
import com.microsoft.band.sensors.SampleRate;

import java.sql.Connection;

public class MainActivity extends AppCompatActivity {

    public final static String EXTRA_MESSAGE = "io.dylanmcguire.afitar.MESSAGE";
    static String message = "not even close";
    String disconnectedMsg = "Band is not connected.";
    private TextView txtStatus;
    private BandClient client = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtStatus = (TextView) findViewById(R.id.txtStatus);
        new CheckConnectionTask().execute();
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
                    String fwVersion = client.getFirmwareVersion().await();
                    appendToUI("Firmware version is " + fwVersion);
                } else {
                    appendToUI(disconnectedMsg);
                }
            } catch (Exception e) {
                appendToUI(e.getMessage());
            }
            return null;
        }
    }

    /***********************************************************************************************
     * ACCELEROMETER
     **********************************************************************************************/
    private BandAccelerometerEventListener mAccelerometerEventListener = new BandAccelerometerEventListener() {
        @Override
        public void onBandAccelerometerChanged(BandAccelerometerEvent bandAccelerometerEvent) {
            if (bandAccelerometerEvent != null) {
                // TODO implement this functionality
            }
        }
    };

    private class AccelerometerSubscriptionTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            try {
                if (getConnectedBandClient()) {
                    client.getSensorManager().registerAccelerometerEventListener(
                            mAccelerometerEventListener, SampleRate.MS128);
                } else {
                    appendToUI(disconnectedMsg);
                }
            } catch (Exception e) {
                appendToUI(e.getMessage());
            }
            return null;
        }
    }

    /***********************************************************************************************
     * HEART RATE
     **********************************************************************************************/

    /***********************************************************************************************
     * BAND CONTACT STATE
     **********************************************************************************************/

    /***********************************************************************************************
     * GSR
     **********************************************************************************************/

    /***********************************************************************************************
     * DISTANCE
     **********************************************************************************************/
}
