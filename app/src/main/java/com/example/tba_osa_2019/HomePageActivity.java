package com.example.tba_osa_2019;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HomePageActivity extends LoginActivity {
    private Button addToCart;
    private Button goToProfile;
    private Button goToCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        addToCart = (Button) findViewById(R.id.addToCart);
        addToCart.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                addCart();
            }
        });

        goToProfile = (Button) findViewById(R.id.ProfileButton);
        goToProfile.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                goProfile();
            }
        });

        goToCart = (Button) findViewById(R.id.cartButton);
        goToCart.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                goCart();
            }
        });
    }

    public void addCart(){
        // Method to add products to cart
    }

    public void goProfile(){
        // Method to go user's profile
        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);
    }

    public void goCart(){
        // Method to view shopping cart.
        Intent intent = new Intent(this, ShoppingCartActivity.class);
        startActivity(intent);
    }
}
