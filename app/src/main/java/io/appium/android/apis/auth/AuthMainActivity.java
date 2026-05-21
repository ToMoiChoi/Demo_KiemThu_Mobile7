package io.appium.android.apis.auth;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import io.appium.android.apis.R;

public class AuthMainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.auth_main);

        Button btnNavLogin = findViewById(R.id.btn_nav_login);
        Button btnNavRegister = findViewById(R.id.btn_nav_register);

        btnNavLogin.setOnClickListener(v -> {
            startActivity(new Intent(AuthMainActivity.this, LoginActivity.class));
        });

        btnNavRegister.setOnClickListener(v -> {
            startActivity(new Intent(AuthMainActivity.this, RegisterActivity.class));
        });
    }
}
