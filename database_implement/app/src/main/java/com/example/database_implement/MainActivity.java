package com.example.database_implement;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText editKey, editValue;
    private Button buttonSave, buttonLoad;
    private TextView textResult;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseHelper = new DatabaseHelper(this);

        editKey = findViewById(R.id.editKey);
        editValue = findViewById(R.id.editValue);
        buttonSave = findViewById(R.id.buttonSave);
        buttonLoad = findViewById(R.id.buttonLoad);
        textResult = findViewById(R.id.textResult);

        buttonSave.setOnClickListener(v -> {
            String key = editKey.getText().toString().trim();
            String value = editValue.getText().toString().trim();

            if (key.isEmpty() || value.isEmpty()) {
                Toast.makeText(this, "Please fill both fields", Toast.LENGTH_SHORT).show();
                return;
            }

            boolean success = databaseHelper.savePreference(key, value);
            if (success) {
                Toast.makeText(this, getString(R.string.save_success), Toast.LENGTH_SHORT).show();
                editKey.setText("");
                editValue.setText("");
            } else {
                Toast.makeText(this, getString(R.string.save_error), Toast.LENGTH_SHORT).show();
            }
        });

        buttonLoad.setOnClickListener(v -> {
            String key = editKey.getText().toString().trim();

            if (key.isEmpty()) {
                Toast.makeText(this, "Please enter a key to load", Toast.LENGTH_SHORT).show();
                return;
            }

            String value = databaseHelper.getPreference(key);
            if (value != null) {
                textResult.setText("Value: " + value);
            } else {
                textResult.setText(getString(R.string.not_found));
            }
        });
    }
}