package com.example.courier.admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.courier.Login;
import com.example.courier.MainActivity;
import com.example.courier.R;
import com.example.courier.RegisterActivity;

public class AdminHomeActivity extends AppCompatActivity {
    ConstraintLayout btn_order, btn_user, btn_history;
    TextView tv_logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        tv_logout = findViewById(R.id.tv_logout);

        btn_order = findViewById(R.id.btn_order_approve);
        btn_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminHomeActivity.this, PendingOrdersActivity.class));
            }
        });

        btn_user = findViewById(R.id.btn_user);
        btn_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminHomeActivity.this, AllUserActivity.class));
            }
        });

        btn_history = findViewById(R.id.btn_history);
        btn_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminHomeActivity.this, AllFeedbackActivity.class));
            }
        });

        tv_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminHomeActivity.this, MainActivity.class));
            }
        });
    }
}