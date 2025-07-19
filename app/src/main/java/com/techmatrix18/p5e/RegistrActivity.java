package com.techmatrix18.p5e;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Calendar;

public class RegistrActivity extends AppCompatActivity {

    EditText editTextUsername, editTextGender, editTextAge, editTextEmail, editTextMobile, editTextPassword;
    Button buttonLogin, buttonRegistr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registr);

        editTextUsername = findViewById(R.id.editTextUsername);
        //editTextGender = findViewById(R.id.editTextGender);
        //editTextAge = findViewById(R.id.editTextAge);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextMobile = findViewById(R.id.editTextMobile);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonLogin = findViewById(R.id.buttonLogin);
        buttonRegistr = findViewById(R.id.buttonRegistr);

        // Номер телефона
        //editTextMobile.requestFocus();
        //InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        //imm.showSoftInput(editTextMobile, InputMethodManager.SHOW_IMPLICIT);

        // Дата рождения
        EditText editTextBirthDate = findViewById(R.id.editTextBirthDate);
        editTextBirthDate.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    this,
                    android.R.style.Theme_Holo_Light_Dialog_NoActionBar, // стиль без календаря
                    (view, selectedYear, selectedMonth, selectedDay) -> {
                        String date = String.format("%02d.%02d.%d", selectedDay, selectedMonth + 1, selectedYear);
                        editTextBirthDate.setText(date);
                    },
                    year, month, day
            );
            // Включить режим спиннеров
            datePickerDialog.getDatePicker().setCalendarViewShown(false);
            datePickerDialog.getDatePicker().setSpinnersShown(true);
            datePickerDialog.show();
        });

        // Пол
        Spinner genderSpinner = findViewById(R.id.genderSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.gender_options,
                R.layout.spinner_selected_item  // Файл с нужным цветом
        );
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        genderSpinner.setAdapter(adapter);

        // registr
        buttonRegistr.setOnClickListener(v -> {
            String username = editTextUsername.getText().toString().trim();
            String gender = editTextGender.getText().toString().trim();
            String age = editTextAge.getText().toString().trim();
            String email = editTextEmail.getText().toString().trim();
            String mobile = editTextMobile.getText().toString().trim();
            String password = editTextPassword.getText().toString().trim();

            if (TextUtils.isEmpty(username)) {
                Toast.makeText(RegistrActivity.this, "Введите логин", Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(email)) {
                Toast.makeText(RegistrActivity.this, "Введите E-mail", Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(mobile)) {
                Toast.makeText(RegistrActivity.this, "Введите мобильный", Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(gender)) {
                Toast.makeText(RegistrActivity.this, "Введите пол", Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(age)) {
                Toast.makeText(RegistrActivity.this, "Введите возраст", Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(password)) {
                Toast.makeText(RegistrActivity.this, "Введите пароль", Toast.LENGTH_SHORT).show();
                return;
            }

            // send data to server /api/v1/mob-app-registr (method POST)

            try {
                URL url = new URL("http://localhost:8080/api/v1/mob-app-registr"); // Замени на свой URL
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                conn.setDoOutput(true);

                String postData = "username=" + URLEncoder.encode(username, "UTF-8") +
                        "&gender=" + URLEncoder.encode(gender, "UTF-8") +
                        "&age=" + URLEncoder.encode(age, "UTF-8") +
                        "&email=" + URLEncoder.encode(email, "UTF-8") +
                        "&mobile=" + URLEncoder.encode(mobile, "UTF-8") +
                        "&password=" + URLEncoder.encode(password, "UTF-8");

                try (OutputStream os = conn.getOutputStream()) {
                    os.write(postData.getBytes());
                    os.flush();
                }

                StringBuilder response = new StringBuilder();
                try (BufferedReader reader = new BufferedReader(
                        new InputStreamReader(conn.getInputStream()))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                }

                // maybe need
                String res = response.toString();
                Toast.makeText(RegistrActivity.this, "Ответ сервера: " + res, Toast.LENGTH_LONG).show();
                //return;

            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(RegistrActivity.this, "Ошибка: " + e.getMessage(), Toast.LENGTH_LONG).show();
                return;
            }

            Toast.makeText(RegistrActivity.this, "Успешный вход", Toast.LENGTH_SHORT).show();

            // Переход на главную активити (например, MainActivity)
            Intent intent = new Intent(RegistrActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });

        // go to login
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegistrActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}

