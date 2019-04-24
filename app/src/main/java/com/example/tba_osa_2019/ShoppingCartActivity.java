package com.example.tba_osa_2019;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ShoppingCartActivity extends LoginActivity {

    private Button removeCart;
    private Button goProfileFromCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);

        removeCart = (Button) findViewById(R.id.removeCart);
        removeCart.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //removeProductFromCart();
            }
        });

        goProfileFromCart = (Button) findViewById(R.id.goToProfileFromCart);
        goProfileFromCart.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                goProfile();
            }
        });
    }

    public void goProfile(){
            // Method to go user's profile
            Intent intent = new Intent(this, ProfileActivity.class);
            startActivity(intent);
    }

}


