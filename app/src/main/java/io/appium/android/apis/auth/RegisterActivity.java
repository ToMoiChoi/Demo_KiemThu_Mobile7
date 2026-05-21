package io.appium.android.apis.auth;

import android.app.Activity;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import io.appium.android.apis.R;

public class RegisterActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.auth_register);

        EditText etUsername = findViewById(R.id.et_reg_username);
        EditText etEmail = findViewById(R.id.et_reg_email);
        EditText etPassword = findViewById(R.id.et_reg_password);
        EditText etConfirmPassword = findViewById(R.id.et_reg_confirm_password);
        Button btnSubmit = findViewById(R.id.btn_reg_submit);
        Button btnBack = findViewById(R.id.btn_reg_back);

        btnBack.setOnClickListener(v -> finish());

        btnSubmit.setOnClickListener(v -> {
            String username = etUsername.getText().toString().trim();
            String email = etEmail.getText().toString().trim();
            String password = etPassword.getText().toString().trim();
            String confirmPass = etConfirmPassword.getText().toString().trim();

            // Reset errors
            etUsername.setError(null);
            etEmail.setError(null);
            etPassword.setError(null);
            etConfirmPassword.setError(null);

            // Sequential / Prioritized Validation
            if (username.isEmpty()) {
                etUsername.setError("Username cannot be empty");
                etUsername.requestFocus();
                return;
            }
            if (username.length() < 5) {
                etUsername.setError("Username must be at least 5 characters");
                etUsername.requestFocus();
                return;
            }
            if (UserStorage.isUsernameTaken(this, username)) {
                etUsername.setError("Username already exists");
                etUsername.requestFocus();
                return;
            }

            if (email.isEmpty()) {
                etEmail.setError("Email cannot be empty");
                etEmail.requestFocus();
                return;
            }
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                etEmail.setError("Invalid email format");
                etEmail.requestFocus();
                return;
            }

            if (password.isEmpty()) {
                etPassword.setError("Password cannot be empty");
                etPassword.requestFocus();
                return;
            }
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
            if (!password.matches(".*[A-Z].*")) {
                etPassword.setError("Password must contain at least one uppercase letter");
                etPassword.requestFocus();
                return;
            }

            if (confirmPass.isEmpty()) {
                etConfirmPassword.setError("Please confirm your password");
                etConfirmPassword.requestFocus();
                return;
            }
            if (!confirmPass.equals(password)) {
                etConfirmPassword.setError("Passwords do not match");
                etConfirmPassword.requestFocus();
                return;
            }

            // Save user
            UserStorage.saveUser(this, username, email, password);
            Toast.makeText(this, "Registration Successful!", Toast.LENGTH_SHORT).show();
            finish();
        });
    }
}
