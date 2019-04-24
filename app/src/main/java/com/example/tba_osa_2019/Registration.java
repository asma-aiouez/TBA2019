package com.example.tba_osa_2019;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import com.example.tba_osa_2019.helper.CheckNetworkStatus;
import com.example.tba_osa_2019.helper.HttpJsonParser;


public class Registration extends LoginActivity {
    private static final String KEY_SUCCESS = "success";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_DOB = "dob";
    private static final String KEY_ADDRESS = "address";
    private static final String BASE_URL = "http://10.225.121.175/tba-db/";
    private static String STRING_EMPTY = "";
    private EditText emailEditText;
    private EditText passwordEditText;
    private EditText dobEditText;
    private EditText addressEditText;
    private String email;
    private String password;
    private String dob;
    private String address;
    private Button registerButton;
    private int success;
    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        emailEditText = (EditText) findViewById(R.id.txtEmail);
        passwordEditText = (EditText) findViewById(R.id.txtPassword);
        dobEditText = (EditText) findViewById(R.id.txtDob);
        addressEditText = (EditText) findViewById(R.id.txtAddress);
        registerButton = (Button) findViewById(R.id.btnRegister);
        registerButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {
                    AddCustomer();
                } else {
                    Toast.makeText(Registration.this,
                            "Unable to connect to internet",
                            Toast.LENGTH_LONG).show();

                }

            }

        });
    }

    public void AddCustomer(){
        //Intent intent = new Intent(this, LoginActivity.class);
        //startActivity(intent);

        if (!STRING_EMPTY.equals(emailEditText.getText().toString()) &&
                !STRING_EMPTY.equals(passwordEditText.getText().toString()) &&
                !STRING_EMPTY.equals(dobEditText.getText().toString()) &&
                !STRING_EMPTY.equals(addressEditText.getText().toString())) {

            email = emailEditText.getText().toString();
            password = passwordEditText.getText().toString();
            dob = dobEditText.getText().toString();
            address = addressEditText.getText().toString();
            new AddCustomerAsyncTask().execute();
        } else {
            Toast.makeText(Registration.this,
                    "One or more fields left empty!",
                    Toast.LENGTH_LONG).show();

        }


    }

    /**
     * AsyncTask for adding a movie
     */
    private class AddCustomerAsyncTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Display proggress bar
            pDialog = new ProgressDialog(Registration.this);
            pDialog.setMessage("Adding Customer. Please wait...");
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
            httpParams.put(KEY_DOB, dob);
            httpParams.put(KEY_ADDRESS, address);
            JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                    BASE_URL + "customers/add_customer.php", "GET", httpParams);
            Log.w("AfterGettin", "git json");
            try {
                Log.w("jsonObj", ""+jsonObject);
                success = jsonObject.getInt(KEY_SUCCESS);
                Log.w("AfterGettin", "git json");
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
                        Toast.makeText(Registration.this,
                                "Customer Added", Toast.LENGTH_LONG).show();
                        Intent i = getIntent();
                        //send result code 20 to notify about movie update
                        setResult(20, i);
                        //Finish ths activity and go back to listing activity

                        finish();

                    } else {
                        Toast.makeText(Registration.this,
                                "Some error occurred while adding user",
                                Toast.LENGTH_LONG).show();

                    }
                }
            });
        }
    }

}
