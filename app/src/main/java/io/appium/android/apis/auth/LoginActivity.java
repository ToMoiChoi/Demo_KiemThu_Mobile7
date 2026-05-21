package io.appium.android.apis.auth;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import io.appium.android.apis.R;

public class LoginActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.auth_login);

        EditText etUsername = findViewById(R.id.et_login_username);
        EditText etPassword = findViewById(R.id.et_login_password);
        Button btnSubmit = findViewById(R.id.btn_login_submit);
        Button btnBack = findViewById(R.id.btn_login_back);

        btnBack.setOnClickListener(v -> finish());

        btnSubmit.setOnClickListener(v -> {
            String username = etUsername.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            // Reset errors
            etUsername.setError(null);
            etPassword.setError(null);

            // Sequential Validation
            if (username.isEmpty()) {
                etUsername.setError("Username cannot be empty");
                etUsername.requestFocus();
                return;
            }

            if (password.isEmpty()) {
                etPassword.setError("Password cannot be empty");
                etPassword.requestFocus();
                return;
            }
            
            // Tight password rules
            if (password.length() < 8) {
                etPassword.setError("Password must be at least 8 characters");
                etPassword.requestFocus();
                return;
            }
            if (!password.matches(".*\\d.*")) {
                etPassword.setError("Password must contain at least one number");
                etPassword.requestFocus();
                return;
            }

            if (UserStorage.checkLogin(this, username, password)) {
                Toast.makeText(this, "Login Successful!", Toast.LENGTH_SHORT).show();
                finish(); // Auto navigate back to AuthMain
            } else {
                Toast.makeText(this, "Invalid Username or Password", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
