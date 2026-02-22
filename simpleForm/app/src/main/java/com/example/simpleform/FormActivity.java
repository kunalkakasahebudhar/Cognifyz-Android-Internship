package com.example.simpleform;

import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class FormActivity extends AppCompatActivity {

    private TextInputEditText nameEditText;
    private TextInputEditText emailEditText;
    private MaterialButton submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_form);
        
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize UI components
        nameEditText = findViewById(R.id.nameEditText);
        emailEditText = findViewById(R.id.emailEditText);
        submitButton = findViewById(R.id.submitButton);

        // Set Click Listener for Submit Button
        submitButton.setOnClickListener(v -> {
            String name = nameEditText.getText().toString().trim();
            String email = emailEditText.getText().toString().trim();

            if (name.isEmpty()) {
                nameEditText.setError(getString(R.string.enter_name));
                return;
            }

            if (email.isEmpty()) {
                emailEditText.setError(getString(R.string.enter_email));
                return;
            }

            // Display Toast on successful submission
            String message = getString(R.string.submission_success) + "\nName: " + name;
            Toast.makeText(FormActivity.this, message, Toast.LENGTH_LONG).show();
            
            // Clear fields after submission
            nameEditText.setText("");
            emailEditText.setText("");
            nameEditText.clearFocus();
            emailEditText.clearFocus();
        });
    }
}
