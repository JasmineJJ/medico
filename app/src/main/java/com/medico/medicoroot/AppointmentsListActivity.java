package com.medico.medicoroot;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AppointmentsListActivity extends AppCompatActivity {

    private LinearLayout btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // For now, just show a placeholder
        Toast.makeText(this, "Appointments list coming soon!", Toast.LENGTH_LONG).show();

        // Go back to dashboard
        finish();
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }
}