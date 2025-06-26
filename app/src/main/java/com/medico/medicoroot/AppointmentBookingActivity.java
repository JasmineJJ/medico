package com.medico.medicoroot;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AppointmentBookingActivity extends AppCompatActivity {

    private TextView tvDoctorName, tvDoctorSpecialty;
    private LinearLayout btnBack, btnConfirmBooking;
    private LinearLayout[] dateCards, timeSlots;
    private String selectedDate = "Today, June 26";
    private String selectedTime = "11:00 AM";
    private String doctorName, specialty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_booking);

        initViews();
        setupClickListeners();
        loadDoctorInfo();
    }

    private void initViews() {
        tvDoctorName = findViewById(R.id.tv_doctor_name);
        tvDoctorSpecialty = findViewById(R.id.tv_doctor_specialty);
        btnBack = findViewById(R.id.btn_back);
        btnConfirmBooking = findViewById(R.id.btn_confirm_booking);

        // Date cards
        dateCards = new LinearLayout[]{
                findViewById(R.id.date_today),
                findViewById(R.id.date_tomorrow),
                findViewById(R.id.date_day3),
                findViewById(R.id.date_day4)
        };

        // Time slots
        timeSlots = new LinearLayout[]{
                findViewById(R.id.time_9am),
                findViewById(R.id.time_10am),
                findViewById(R.id.time_11am),
                findViewById(R.id.time_2pm),
                findViewById(R.id.time_3pm),
                findViewById(R.id.time_4pm)
        };
    }

    private void setupClickListeners() {
        // Back button
        btnBack.setOnClickListener(v -> onBackPressed());

        // Date cards
        String[] dates = {"Today, June 26", "Tomorrow, June 27", "Friday, June 28", "Saturday, June 29"};

        for (int i = 0; i < dateCards.length; i++) {
            final int index = i;
            final String date = dates[i];
            dateCards[i].setOnClickListener(v -> selectDate(index, date));
        }

        // Time slots
        String[] times = {"9:00 AM", "10:00 AM", "11:00 AM", "2:00 PM", "3:00 PM", "4:00 PM"};

        for (int i = 0; i < timeSlots.length; i++) {
            final int index = i;
            final String time = times[i];

            // Skip unavailable slot (4:00 PM)
            if (index == 5) continue;

            timeSlots[i].setOnClickListener(v -> selectTime(index, time));
        }

        // Confirm booking
        btnConfirmBooking.setOnClickListener(v -> confirmBooking());
    }

    private void loadDoctorInfo() {
        // Get doctor info from intent
        doctorName = getIntent().getStringExtra("doctor_name");
        specialty = getIntent().getStringExtra("specialty");

        if (doctorName != null) {
            tvDoctorName.setText(doctorName);
        }
        if (specialty != null) {
            tvDoctorSpecialty.setText(specialty);
        }
    }

    private void selectDate(int index, String date) {
        selectedDate = date;

        // Update UI - reset all dates
        for (int i = 0; i < dateCards.length; i++) {
            final int currentIndex = i; // Make variable final for lambda
            if (i == index) {
                dateCards[i].setBackgroundResource(R.drawable.date_selected_background);
                // Add selection animation
                dateCards[i].animate().scaleX(1.05f).scaleY(1.05f).setDuration(200)
                        .withEndAction(new Runnable() {
                            @Override
                            public void run() {
                                dateCards[currentIndex].animate().scaleX(1.0f).scaleY(1.0f).setDuration(200);
                            }
                        });
            } else {
                dateCards[i].setBackgroundResource(R.drawable.date_unselected_background);
                dateCards[i].setScaleX(1.0f);
                dateCards[i].setScaleY(1.0f);
            }
        }

        Toast.makeText(this, "Selected: " + date, Toast.LENGTH_SHORT).show();
    }

    private void selectTime(int index, String time) {
        selectedTime = time;

        // Update UI - reset all time slots
        for (int i = 0; i < timeSlots.length; i++) {
            final int currentIndex = i; // Make variable final for lambda
            if (i == index) {
                timeSlots[i].setBackgroundResource(R.drawable.time_slot_selected_background);
                // Add selection animation
                timeSlots[i].animate().scaleX(1.05f).scaleY(1.05f).setDuration(200)
                        .withEndAction(new Runnable() {
                            @Override
                            public void run() {
                                timeSlots[currentIndex].animate().scaleX(1.0f).scaleY(1.0f).setDuration(200);
                            }
                        });
            } else if (i != 5) { // Skip unavailable slot
                timeSlots[i].setBackgroundResource(R.drawable.time_slot_background);
                timeSlots[i].setScaleX(1.0f);
                timeSlots[i].setScaleY(1.0f);
            }
        }

        Toast.makeText(this, "Selected time: " + time, Toast.LENGTH_SHORT).show();
    }

    private void confirmBooking() {
        // Add confirmation animation
        btnConfirmBooking.animate().scaleX(0.95f).scaleY(0.95f).setDuration(100)
                .withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        btnConfirmBooking.animate().scaleX(1.0f).scaleY(1.0f).setDuration(100)
                                .withEndAction(new Runnable() {
                                    @Override
                                    public void run() {
                                        processBooking();
                                    }
                                });
                    }
                });
    }

    private void processBooking() {
        // Show confirmation dialog
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this);
        builder.setTitle("Confirm Appointment")
                .setMessage("Book appointment with " + doctorName + " on " + selectedDate + " at " + selectedTime + "?")
                .setPositiveButton("Confirm", (dialog, which) -> {
                    // Simulate booking process
                    simulateBooking();
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void simulateBooking() {
        // Show loading toast
        Toast.makeText(this, "Booking appointment...", Toast.LENGTH_SHORT).show();

        // Simulate network delay
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            bookingSuccess();
                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void bookingSuccess() {
        // Show success message
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this);
        builder.setTitle("🎉 Booking Confirmed!")
                .setMessage("Your appointment has been successfully booked!\n\n" +
                        "Doctor: " + doctorName + "\n" +
                        "Date: " + selectedDate + "\n" +
                        "Time: " + selectedTime + "\n\n" +
                        "You will receive a confirmation SMS shortly.")
                .setPositiveButton("Done", (dialog, which) -> {
                    // Go back to dashboard
                    Intent intent = new Intent(this, PatientDashboardActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                })
                .setCancelable(false)
                .show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }
}