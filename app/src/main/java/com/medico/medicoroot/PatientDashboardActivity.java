package com.medico.medicoroot;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class PatientDashboardActivity extends AppCompatActivity {

    private TextView tvPatientName;
    private LinearLayout btnProfile, btnBookAppointment, btnEmergencyCall, btnCallDoctor, btnReschedule;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Add debug log
        android.util.Log.d("DEBUG", "Loading PatientDashboard layout");

        setContentView(R.layout.activity_patient_dashboard);

        // Add another debug log
        android.util.Log.d("DEBUG", "Layout loaded successfully");

        initViews();
        setupClickListeners();
        loadPatientData();
    }

    private void initViews() {
        tvPatientName = findViewById(R.id.tv_patient_name);
        btnProfile = findViewById(R.id.btn_profile);
        btnBookAppointment = findViewById(R.id.btn_book_appointment);
        btnEmergencyCall = findViewById(R.id.btn_emergency_call);
        btnCallDoctor = findViewById(R.id.btn_call_doctor);
        btnReschedule = findViewById(R.id.btn_reschedule);
    }

    private void setupClickListeners() {
        // Profile button
        if (btnProfile != null) {
            btnProfile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openProfile();
                }
            });
        }

        // Book appointment
        if (btnBookAppointment != null) {
            btnBookAppointment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openBookAppointment();
                }
            });
        }

        // Emergency call
        if (btnEmergencyCall != null) {
            btnEmergencyCall.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    makeEmergencyCall();
                }
            });
        }

        // Call doctor
        if (btnCallDoctor != null) {
            btnCallDoctor.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callDoctor();
                }
            });
        }

        // Reschedule appointment
        if (btnReschedule != null) {
            btnReschedule.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    rescheduleAppointment();
                }
            });
        }
    }

    private void loadPatientData() {
        // Get patient data from intent or preferences
        String patientName = getIntent().getStringExtra("patient_name");
        if (tvPatientName != null) {
            if (patientName != null) {
                tvPatientName.setText(patientName);
            } else {
                tvPatientName.setText("John Doe"); // Default for demo
            }
        }
    }

    private void openProfile() {
        Toast.makeText(this, "Profile functionality coming soon!", Toast.LENGTH_SHORT).show();
    }

    private void openBookAppointment() {
        try {
            Intent intent = new Intent(this, BookAppointmentActivity.class);
            startActivity(intent);
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        } catch (Exception e) {
            Toast.makeText(this, "Book appointment coming soon!", Toast.LENGTH_SHORT).show();
        }
    }

    private void makeEmergencyCall() {
        // Show emergency options dialog
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this);
        builder.setTitle("Emergency Call")
                .setMessage("Choose emergency service:")
                .setPositiveButton("Ambulance (911)", (dialog, which) -> {
                    makePhoneCall("911");
                })
                .setNegativeButton("Hospital", (dialog, which) -> {
                    makePhoneCall("+1-555-HOSPITAL");
                })
                .setNeutralButton("Cancel", null)
                .show();
    }

    private void callDoctor() {
        // Call the doctor from the upcoming appointment
        makePhoneCall("+1-555-DR-SARAH");
    }

    private void makePhoneCall(String phoneNumber) {
        try {
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:" + phoneNumber));
            startActivity(callIntent);
        } catch (SecurityException e) {
            // If permission not granted, use dial intent instead
            Intent dialIntent = new Intent(Intent.ACTION_DIAL);
            dialIntent.setData(Uri.parse("tel:" + phoneNumber));
            startActivity(dialIntent);
        } catch (Exception e) {
            Toast.makeText(this, "Unable to make call", Toast.LENGTH_SHORT).show();
        }
    }

    private void rescheduleAppointment() {
        Toast.makeText(this, "Reschedule functionality coming soon!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        // Show confirmation dialog before exiting
        new androidx.appcompat.app.AlertDialog.Builder(this)
                .setTitle("Exit Medico")
                .setMessage("Are you sure you want to exit?")
                .setPositiveButton("Yes", (dialog, which) -> {
                    super.onBackPressed();
                    finishAffinity(); // Close all activities
                })
                .setNegativeButton("No", null)
                .show();
    }
}