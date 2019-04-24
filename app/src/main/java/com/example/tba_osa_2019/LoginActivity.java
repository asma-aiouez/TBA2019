package com.example.tba_osa_2019;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tba_osa_2019.helper.CheckNetworkStatus;
import com.example.tba_osa_2019.helper.HttpJsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    private static final String KEY_SUCCESS = "success";
    private static final String KEY_DATA = "data";
    private static final String KEY_CUSTOMER_ID = "customer_ID";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_DOB = "dob";
    private static final String KEY_ADDRESS = "address";
    private static final String KEY_ISLOGGED = "isLogged";
    private static final String BASE_URL = "http://10.225.121.175/tba-db/";
    private static String STRING_EMPTY = "";
    private EditText emailEditText;
    private EditText passwordEditText;
    private String email;
    private String password;
    private String address;
    private String dob;
    private String isLogged;
    private Button loginButton;
    private int success;
    private ProgressDialog pDialog;
    public static Customer onlineCustomer ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailEditText = (EditText) findViewById(R.id.emailEditText);
        passwordEditText = (EditText) findViewById(R.id.passwordEditText);
        loginButton = (Button) findViewById(R.id.LogButton);
        loginButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {
                    LogIn();
                } else {
                    Toast.makeText(LoginActivity.this,
                            "Unable to connect to internet",
                            Toast.LENGTH_LONG).show();

                }

            }

        });
    }

    public void LogIn(){
        //Intent intent = new Intent(this, LoginActivity.class);
        //startActivity(intent);
        Log.w("user", "crash here");
        if (!STRING_EMPTY.equals(emailEditText.getText().toString()) &&
                !STRING_EMPTY.equals(passwordEditText.getText().toString())) {

            email = emailEditText.getText().toString();Log.w("user", "crash here2");
            password = passwordEditText.getText().toString();Log.w("user", "crash here3");
            new LoginActivity.LoginAsyncTask().execute();
        } else {
            Toast.makeText(LoginActivity.this,
                    "One or more fields left empty!",
                    Toast.LENGTH_LONG).show();

        }


    }

    /**
     * AsyncTask for adding a movie
     */
    private class LoginAsyncTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Display proggress bar
            pDialog = new ProgressDialog(LoginActivity.this);
            pDialog.setMessage("Logging in. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            HttpJsonParser httpJsonParser = new HttpJsonParser();
            Map<String, String> httpParams = new HashMap<>();
            //Populating request parameters
            httpParams.put(KEY_EMAIL, email);
            httpParams.put(KEY_PASSWORD, password);
            JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                    BASE_URL + "customers/login_customer.php", "GET", httpParams);

            try {
                success = jsonObject.getInt(KEY_SUCCESS);
                Log.w("success", success+"");
                JSONObject user;
                if (success == 1) {
                    //Parse the JSON response
                    user = jsonObject.getJSONObject(KEY_DATA);
                    email = user.getString(KEY_EMAIL);
                    password = user.getString(KEY_PASSWORD);
                    address = user.getString(KEY_ADDRESS);
                    dob = user.getString(KEY_DOB);
                    isLogged = user.getString(KEY_ISLOGGED);
                    Log.w("user", ""+user);
                    onlineCustomer = new Customer (email, password,dob,address );
                    Log.w("online", onlineCustomer+"");

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(String result) {
            Log.w("AfterGettin", "Inside onPost");
            pDialog.dismiss();
            runOnUiThread(new Runnable() {
                public void run() {
                    if (success == 1) {
                        //Display success message
                        Toast.makeText(LoginActivity.this,
                                "Welcome :)", Toast.LENGTH_LONG).show();
                        Intent i = getIntent();
                        //send result code 20 to notify about movie update
                        setResult(20, i);
                        //Finish ths activity and go back to listing activity
                        goHomePage();
                    } else {
                        Toast.makeText(LoginActivity.this,
                                "Sorry you need to register first :(",
                                Toast.LENGTH_LONG).show();

                    }
                }
            });
        }
    }

    public void goHomePage(){
        // Method to go user's profile
        Intent intent = new Intent(this, HomePageActivity.class);
        startActivity(intent);
    }


}


