package com.example.courier;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.courier.customer.CustomerHomeActivity;
import com.example.courier.customer.OrderDetailsActivity;

public class MainActivity extends AppCompatActivity {
    ConstraintLayout btn_customer,btn_driver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_customer = findViewById(R.id.btn_customer);
        btn_driver = findViewById(R.id.btn_driver);

        btn_customer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Login.class);
                intent.putExtra("Type","C");
                startActivity(intent);
            }
        });

        btn_driver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Login.class);
                intent.putExtra("Type","D");
                startActivity(intent);
            }
        });
    }
}