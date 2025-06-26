package com.medico.medicoroot;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class BookAppointmentActivity extends AppCompatActivity {

    private EditText etSearch;
    private LinearLayout btnBack;
    private LinearLayout[] specialtyCards;
    private LinearLayout btnBookNow1, btnBookNow2, btnViewProfile1, btnViewProfile2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_appointment);

        initViews();
        setupClickListeners();
        setupSearch();
    }

    private void initViews() {
        etSearch = findViewById(R.id.et_search);
        btnBack = findViewById(R.id.btn_back);

        // Specialty cards
        specialtyCards = new LinearLayout[]{
                findViewById(R.id.specialty_cardiology),
                findViewById(R.id.specialty_dermatology),
                findViewById(R.id.specialty_orthopedic),
                findViewById(R.id.specialty_pediatrics),
                findViewById(R.id.specialty_neurology),
                findViewById(R.id.specialty_general)
        };

        // Doctor action buttons
        btnBookNow1 = findViewById(R.id.btn_book_now_1);
        btnBookNow2 = findViewById(R.id.btn_book_now_2);
        btnViewProfile1 = findViewById(R.id.btn_view_profile_1);
        btnViewProfile2 = findViewById(R.id.btn_view_profile_2);
    }

    private void setupClickListeners() {
        // Back button
        btnBack.setOnClickListener(v -> {
            onBackPressed();
        });

        // Specialty cards
        String[] specialties = {"Cardiology", "Dermatology", "Orthopedic", "Pediatrics", "Neurology", "General"};

        for (int i = 0; i < specialtyCards.length; i++) {
            final String specialty = specialties[i];
            specialtyCards[i].setOnClickListener(v -> {
                // Add selection animation
                v.animate().scaleX(0.95f).scaleY(0.95f).setDuration(100)
                        .withEndAction(() -> {
                            v.animate().scaleX(1.0f).scaleY(1.0f).setDuration(100);
                            filterDoctorsBySpecialty(specialty);
                        });
            });
        }

        // Doctor booking buttons
        btnBookNow1.setOnClickListener(v -> bookAppointment("Dr. Sarah Wilson", "Cardiologist"));
        btnBookNow2.setOnClickListener(v -> bookAppointment("Dr. Michael Brown", "Dermatologist"));

        // View profile buttons
        btnViewProfile1.setOnClickListener(v -> viewDoctorProfile("Dr. Sarah Wilson"));
        btnViewProfile2.setOnClickListener(v -> viewDoctorProfile("Dr. Michael Brown"));
    }

    private void setupSearch() {
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                searchDoctors(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    private void filterDoctorsBySpecialty(String specialty) {
        Toast.makeText(this, "Filtering doctors by " + specialty, Toast.LENGTH_SHORT).show();
        // TODO: Implement filtering logic
        // This would typically filter the doctor list based on specialty
    }

    private void searchDoctors(String query) {
        if (query.length() > 2) {
            // TODO: Implement search functionality
            // This would search through doctors based on name, specialty, etc.
        }
    }

    private void bookAppointment(String doctorName, String specialty) {
        // Create appointment booking intent
        Intent intent = new Intent(this, AppointmentBookingActivity.class);
        intent.putExtra("doctor_name", doctorName);
        intent.putExtra("specialty", specialty);
        startActivity(intent);
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }

    private void viewDoctorProfile(String doctorName) {
        Toast.makeText(this, "Viewing profile for " + doctorName, Toast.LENGTH_SHORT).show();
        // TODO: Implement doctor profile view
        // Intent intent = new Intent(this, DoctorProfileActivity.class);
        // intent.putExtra("doctor_name", doctorName);
        // startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }
}