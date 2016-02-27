package io.dylanmcguire.afitar;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.microsoft.band.BandClient;
import com.microsoft.band.BandClientManager;
import com.microsoft.band.BandException;
import com.microsoft.band.BandIOException;
import com.microsoft.band.BandInfo;
import com.microsoft.band.ConnectionState;
import com.microsoft.band.UserConsent;
import com.microsoft.band.sensors.BandHeartRateEvent;
import com.microsoft.band.sensors.BandHeartRateEventListener;
import com.microsoft.band.sensors.HeartRateConsentListener;

import java.lang.ref.WeakReference;

public class UpdateExerciseActivity extends AppCompatActivity {

                            private BandClient client = null;
                            private Button btnStart, btnConsent;
                            private TextView txtStatus;
                            private int heartRate = 0;
                            public static String EXTRA_MESSAGE = "uniquemsg123";

                            private BandHeartRateEventListener mHeartRateEventListener = new BandHeartRateEventListener() {

                                @Override
                                public void onBandHeartRateChanged(final BandHeartRateEvent event) {
                                    if (event != null) {
                                        heartRate = event.getHeartRate();
                                    }
                                }
                            };

                            @Override
                            protected void onCreate(Bundle savedInstanceState) {
                                super.onCreate(savedInstanceState);
                                setContentView(R.layout.activity_update_exercise);
                                TextView heartRateView = (TextView) findViewById(R.id.resting_heart_rate);
                                heartRateView.setText(Integer.toString(heartRate));

                                final WeakReference<Activity> reference = new WeakReference<Activity>(this);

                                btnConsent = (Button) findViewById(R.id.btnConsent);
                                btnConsent.setOnClickListener(new View.OnClickListener() {
                                    @SuppressWarnings("unchecked")
                                    @Override
                                    public void onClick(View v) {
                                        new HeartRateConsentTask().execute(reference);
                                    }
                                });
                            }

                            @Override
                            protected void onPause() {
                                super.onPause();
                                if (client != null) {
                                    try {
                                        client.getSensorManager().unregisterHeartRateEventListener(mHeartRateEventListener);
                                    } catch (BandIOException e) {
                                        appendToUI(e.getMessage());
                                    }
                                }
                            }

                            @Override
                            protected void onDestroy() {
                                if (client != null) {
                                    try {
                                        client.disconnect().await();
                                    } catch (InterruptedException e) {
                                        // Do nothing as this is happening during destroy
                                    } catch (BandException e) {
                                        // Do nothing as this is happening during destroy
                                    }
                                }
                                super.onDestroy();
                            }

                            public void setRestingHeartRate(View view) {
                                new HeartRateSubscriptionTask().execute();
                                TextView tView = (TextView) findViewById(R.id.resting_heart_rate);
                                tView.setText(Integer.toString(heartRate));
                                Context context = getBaseContext();
                                SharedPreferences sharedPref = context.getSharedPreferences(
                                        getString(R.string.preference_file_key), Context.MODE_PRIVATE);
                                SharedPreferences.Editor sharedPrefEditor = sharedPref.edit();
                                sharedPrefEditor.putInt(getString(R.string.resting_heart_rate_key), heartRate);
                                sharedPrefEditor.commit();
                            }

                            public void exerciseAlready(View view) {
                                Intent intent = new Intent(this, ExerciseAlreadyActivity.class);
                                String msg = Integer.toString(heartRate);
                                intent.putExtra(EXTRA_MESSAGE, msg);
                                startActivity(intent);
                            }

                            private class HeartRateSubscriptionTask extends AsyncTask<Void, Void, Void> {
                                @Override
                                protected Void doInBackground(Void... params) {
                                    try {
                                        if (getConnectedBandClient()) {
                                            if (client.getSensorManager().getCurrentHeartRateConsent() == UserConsent.GRANTED) {
                                                client.getSensorManager().registerHeartRateEventListener(mHeartRateEventListener);
                                            } else {
                                                appendToUI("You have not given this application consent to access heart rate data yet."
                                                        + " Please press the Heart Rate Consent button.\n");
                                            }
                                        } else {
                                            appendToUI("Band isn't connected. Please make sure bluetooth is on and the band is in range.\n");
                                        }
                                    } catch (BandException e) {
                                        String exceptionMessage="";
                                        switch (e.getErrorType()) {
                                            case UNSUPPORTED_SDK_VERSION_ERROR:
                                                exceptionMessage = "Microsoft Health BandService doesn't support your SDK Version. Please update to latest SDK.\n";
                                                break;
                                            case SERVICE_ERROR:
                                                exceptionMessage = "Microsoft Health BandService is not available. Please make sure Microsoft Health is installed and that you have the correct permissions.\n";
                                                break;
                                            default:
                                                exceptionMessage = "Unknown error occured: " + e.getMessage() + "\n";
                                                break;
                                        }
                                        appendToUI(exceptionMessage);

                                    } catch (Exception e) {
                                        appendToUI(e.getMessage());
                                    }
                                    return null;
                                }
                            }

                            private class HeartRateConsentTask extends AsyncTask<WeakReference<Activity>, Void, Void> {
                                @Override
                                protected Void doInBackground(WeakReference<Activity>... params) {
                                    try {
                                        if (getConnectedBandClient()) {

                                            if (params[0].get() != null) {
                                                client.getSensorManager().requestHeartRateConsent(params[0].get(), new HeartRateConsentListener() {
                                                    @Override
                                                    public void userAccepted(boolean consentGiven) {
                                                    }
                                                });
                                            }
                                        } else {
                                            appendToUI("Band isn't connected. Please make sure bluetooth is on and the band is in range.\n");
                                        }
                                    } catch (BandException e) {
                                        String exceptionMessage="";
                                        switch (e.getErrorType()) {
                                            case UNSUPPORTED_SDK_VERSION_ERROR:
                                                exceptionMessage = "Microsoft Health BandService doesn't support your SDK Version. Please update to latest SDK.\n";
                                                break;
                                            case SERVICE_ERROR:
                                                exceptionMessage = "Microsoft Health BandService is not available. Please make sure Microsoft Health is installed and that you have the correct permissions.\n";
                                                break;
                                            default:
                                                exceptionMessage = "Unknown error occured: " + e.getMessage() + "\n";
                                                break;
                                        }
                                        appendToUI(exceptionMessage);

                                    } catch (Exception e) {
                                        appendToUI(e.getMessage());
                                    }
                                    return null;
                                }
                            }

                            private void appendToUI(final String string) {
                                this.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                         //               txtStatus.setText(string);
                                    }
                                });
                            }

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
}
