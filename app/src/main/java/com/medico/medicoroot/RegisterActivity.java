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

import com.medico.medicoroot.models.Doctor;
import com.medico.medicoroot.models.Patient;
import com.medico.medicoroot.models.User;

public class RegisterActivity extends AppCompatActivity {

    // UI Components
    private LinearLayout chipPatient, chipDoctor, btnRegister, llDoctorFields;
    private EditText etFirstName, etLastName, etEmail, etPhone, etPassword, etConfirmPassword;
    private EditText etSpecialization, etLicense, etExperience;
    private TextView togglePassword;
    private ProgressBar progressBar;

    private boolean isPatientSelected = true;
    private boolean isPasswordVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initViews();
        setupClickListeners();
    }

    private void initViews() {
        // User type chips
        chipPatient = findViewById(R.id.chip_patient);
        chipDoctor = findViewById(R.id.chip_doctor);
        llDoctorFields = findViewById(R.id.ll_doctor_fields);

        // Common fields
        etFirstName = findViewById(R.id.et_first_name);
        etLastName = findViewById(R.id.et_last_name);
        etEmail = findViewById(R.id.et_email);
        etPhone = findViewById(R.id.et_phone);
        etPassword = findViewById(R.id.et_password);
        etConfirmPassword = findViewById(R.id.et_confirm_password);

        // Doctor-specific fields
        etSpecialization = findViewById(R.id.et_specialization);
        etLicense = findViewById(R.id.et_license);
        etExperience = findViewById(R.id.et_experience);

        // Controls
        btnRegister = findViewById(R.id.btn_register);
        togglePassword = findViewById(R.id.toggle_password);
        progressBar = findViewById(R.id.progress_bar);
    }

    private void setupClickListeners() {
        // User type selection
        chipPatient.setOnClickListener(v -> selectUserType(true));
        chipDoctor.setOnClickListener(v -> selectUserType(false));

        // Password visibility toggle
        togglePassword.setOnClickListener(v -> togglePasswordVisibility());

        // Register button
        btnRegister.setOnClickListener(v -> attemptRegistration());

        // Login link
        findViewById(R.id.tv_login).setOnClickListener(v -> {
            finish(); // Go back to login
        });
    }

    private void selectUserType(boolean isPatient) {
        isPatientSelected = isPatient;

        if (isPatient) {
            chipPatient.setBackgroundResource(R.drawable.chip_selected_background);
            chipDoctor.setBackgroundResource(R.drawable.chip_unselected_background);
            llDoctorFields.setVisibility(View.GONE);

            // Smooth slide up animation
            llDoctorFields.animate()
                    .alpha(0f)
                    .translationY(-20f)
                    .setDuration(200)
                    .start();
        } else {
            chipDoctor.setBackgroundResource(R.drawable.chip_selected_background);
            chipPatient.setBackgroundResource(R.drawable.chip_unselected_background);
            llDoctorFields.setVisibility(View.VISIBLE);

            // Smooth slide down animation
            llDoctorFields.setAlpha(0f);
            llDoctorFields.setTranslationY(-20f);
            llDoctorFields.animate()
                    .alpha(1f)
                    .translationY(0f)
                    .setDuration(300)
                    .start();
        }
    }

    private void togglePasswordVisibility() {
        if (isPasswordVisible) {
            etPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            etConfirmPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            togglePassword.setText("👁️");
            isPasswordVisible = false;
        } else {
            etPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            etConfirmPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            togglePassword.setText("🙈");
            isPasswordVisible = true;
        }

        // Move cursor to end
        etPassword.setSelection(etPassword.getText().length());
        etConfirmPassword.setSelection(etConfirmPassword.getText().length());
    }

    private void attemptRegistration() {
        // Get input values
        String firstName = etFirstName.getText().toString().trim();
        String lastName = etLastName.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String confirmPassword = etConfirmPassword.getText().toString().trim();

        // Validate inputs
        if (!validateInputs(firstName, lastName, email, phone, password, confirmPassword)) {
            return;
        }

        if (!isPatientSelected && !validateDoctorFields()) {
            return;
        }

        // Show loading
        showLoading(true);

        // Create user object
        User user = createUserObject(firstName, lastName, email, phone, password);

        // Simulate registration
        simulateRegistration(user);
    }

    private boolean validateInputs(String firstName, String lastName, String email,
                                   String phone, String password, String confirmPassword) {

        if (TextUtils.isEmpty(firstName)) {
            etFirstName.setError("First name is required");
            etFirstName.requestFocus();
            return false;
        }

        if (TextUtils.isEmpty(lastName)) {
            etLastName.setError("Last name is required");
            etLastName.requestFocus();
            return false;
        }

        if (TextUtils.isEmpty(email)) {
            etEmail.setError("Email is required");
            etEmail.requestFocus();
            return false;
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.setError("Please enter a valid email");
            etEmail.requestFocus();
            return false;
        }

        if (TextUtils.isEmpty(phone)) {
            etPhone.setError("Phone number is required");
            etPhone.requestFocus();
            return false;
        }

        if (TextUtils.isEmpty(password)) {
            etPassword.setError("Password is required");
            etPassword.requestFocus();
            return false;
        }

        if (password.length() < 6) {
            etPassword.setError("Password must be at least 6 characters");
            etPassword.requestFocus();
            return false;
        }

        if (!password.equals(confirmPassword)) {
            etConfirmPassword.setError("Passwords do not match");
            etConfirmPassword.requestFocus();
            return false;
        }

        return true;
    }

    private boolean validateDoctorFields() {
        String specialization = etSpecialization.getText().toString().trim();
        String license = etLicense.getText().toString().trim();
        String experience = etExperience.getText().toString().trim();

        if (TextUtils.isEmpty(specialization)) {
            etSpecialization.setError("Specialization is required");
            etSpecialization.requestFocus();
            return false;
        }

        if (TextUtils.isEmpty(license)) {
            etLicense.setError("License number is required");
            etLicense.requestFocus();
            return false;
        }

        if (TextUtils.isEmpty(experience)) {
            etExperience.setError("Experience is required");
            etExperience.requestFocus();
            return false;
        }

        return true;
    }

    private User createUserObject(String firstName, String lastName, String email,
                                  String phone, String password) {
        if (isPatientSelected) {
            Patient patient = new Patient();
            patient.setFirstName(firstName);
            patient.setLastName(lastName);
            patient.setEmail(email);
            patient.setPhoneNumber(phone);
            patient.setPassword(password);
            return patient;
        } else {
            Doctor doctor = new Doctor();
            doctor.setFirstName(firstName);
            doctor.setLastName(lastName);
            doctor.setEmail(email);
            doctor.setPhoneNumber(phone);
            doctor.setPassword(password);

            // Set doctor-specific fields
            doctor.setSpecialization(etSpecialization.getText().toString().trim());
            doctor.setLicenseNumber(etLicense.getText().toString().trim());
            doctor.setExperience(etExperience.getText().toString().trim());
            doctor.setAvailable(true);

            return doctor;
        }
    }

    private void showLoading(boolean show) {
        if (show) {
            progressBar.setVisibility(View.VISIBLE);
            btnRegister.setEnabled(false);
            btnRegister.setAlpha(0.7f);
        } else {
            progressBar.setVisibility(View.GONE);
            btnRegister.setEnabled(true);
            btnRegister.setAlpha(1.0f);
        }
    }

    private void simulateRegistration(User user) {
        // Simulate network delay
        new Thread(() -> {
            try {
                Thread.sleep(2500); // 2.5 second delay

                runOnUiThread(() -> {
                    showLoading(false);
                    registrationSuccess(user);
                });
            } catch (InterruptedException e) {
                e.printStackTrace();
                runOnUiThread(() -> {
                    showLoading(false);
                    registrationFailed();
                });
            }
        }).start();
    }

    private void registrationSuccess(User user) {
        // Success animation
        btnRegister.animate()
                .scaleX(1.1f)
                .scaleY(1.1f)
                .setDuration(150)
                .withEndAction(() -> {
                    btnRegister.animate()
                            .scaleX(1.0f)
                            .scaleY(1.0f)
                            .setDuration(150);
                });

        Toast.makeText(this, "🎉 Welcome to Medico! Registration successful!", Toast.LENGTH_LONG).show();

        // Navigate back to login with success message
        Intent intent = new Intent(this, LoginActivity.class);
        intent.putExtra("registration_success", true);
        intent.putExtra("user_email", user.getEmail());
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();

        // Smooth transition
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }

    private void registrationFailed() {
        // Error shake animation
        btnRegister.animate()
                .translationX(-25f)
                .setDuration(100)
                .withEndAction(() -> {
                    btnRegister.animate()
                            .translationX(25f)
                            .setDuration(100)
                            .withEndAction(() -> {
                                btnRegister.animate()
                                        .translationX(0f)
                                        .setDuration(100);
                            });
                });

        Toast.makeText(this, "Registration failed. Please try again.", Toast.LENGTH_SHORT).show();
    }
}