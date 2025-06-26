package com.medico.medicoroot;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.medico.medicoroot.models.User;

public class LoginActivity extends AppCompatActivity {

    private EditText etEmail, etPassword;
    private LinearLayout chipPatient, chipDoctor, btnLogin;
    private TextView togglePassword;
    private ProgressBar progressBar;
    private boolean isPatientSelected = true;
    private boolean isPasswordVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initViews();
        setupClickListeners();
    }

    private void initViews() {
        etEmail = findViewById(R.id.et_email);
        etPassword = findViewById(R.id.et_password);
        chipPatient = findViewById(R.id.chip_patient);
        chipDoctor = findViewById(R.id.chip_doctor);
        btnLogin = findViewById(R.id.btn_login);
        togglePassword = findViewById(R.id.toggle_password);
        progressBar = findViewById(R.id.progress_bar);
    }

    private void setupClickListeners() {
        chipPatient.setOnClickListener(v -> selectUserType(true));
        chipDoctor.setOnClickListener(v -> selectUserType(false));
        togglePassword.setOnClickListener(v -> togglePasswordVisibility());
        btnLogin.setOnClickListener(v -> attemptLogin());

        findViewById(R.id.tv_register).setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });

        findViewById(R.id.tv_forgot_password).setOnClickListener(v -> {
            Toast.makeText(this, "Forgot password coming soon!", Toast.LENGTH_SHORT).show();
        });
    }

    private void selectUserType(boolean isPatient) {
        isPatientSelected = isPatient;

        if (isPatient) {
            chipPatient.setBackgroundResource(R.drawable.chip_selected_background);
            chipDoctor.setBackgroundResource(R.drawable.chip_unselected_background);
        } else {
            chipDoctor.setBackgroundResource(R.drawable.chip_selected_background);
            chipPatient.setBackgroundResource(R.drawable.chip_unselected_background);
        }
    }

    private void togglePasswordVisibility() {
        if (isPasswordVisible) {
            etPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            togglePassword.setText("👁️");
            isPasswordVisible = false;
        } else {
            etPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            togglePassword.setText("🙈");
            isPasswordVisible = true;
        }
        etPassword.setSelection(etPassword.getText().length());
    }

    private void attemptLogin() {
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Please enter email", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please enter password", Toast.LENGTH_SHORT).show();
            return;
        }

        if (password.length() < 6) {
            Toast.makeText(this, "Password must be at least 6 characters", Toast.LENGTH_SHORT).show();
            return;
        }

        showLoading(true);
        simulateLogin();
    }

    private void showLoading(boolean show) {
        if (show) {
            progressBar.setVisibility(View.VISIBLE);
            btnLogin.setEnabled(false);
            btnLogin.setAlpha(0.7f);
        } else {
            progressBar.setVisibility(View.GONE);
            btnLogin.setEnabled(true);
            btnLogin.setAlpha(1.0f);
        }
    }

    private void simulateLogin() {
        new Thread(() -> {
            try {
                Thread.sleep(2000);
                runOnUiThread(() -> {
                    showLoading(false);
                    Toast.makeText(this, "Welcome to Medico! 🎉", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(this, PatientDashboardActivity.class);
                    intent.putExtra("userType", isPatientSelected ? "PATIENT" : "DOCTOR");
                    startActivity(intent);
                    finish();
                });
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}