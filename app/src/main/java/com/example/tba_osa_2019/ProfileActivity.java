package com.example.tba_osa_2019;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class ProfileActivity extends LoginActivity {

    private Button changeAddress;
    private Button changePass;
    private TextView email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        email = (TextView) findViewById(R.id.emailTestView);
        //email.setText(onlineCustomer.email);

        changeAddress = (Button) findViewById(R.id.changeAddressProfile);
        changeAddress.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //changeAddressInfo();
            }
        });

        changePass = (Button) findViewById(R.id.changePasswordProfile);
        changePass.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //changePassword();
            }
        });
    }

    public void goCart(){
        Intent intent = new Intent(this, ShoppingCartActivity.class);
        startActivity(intent);
    }
}