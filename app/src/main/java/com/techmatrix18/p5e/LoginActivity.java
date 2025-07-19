package com.techmatrix18.p5e;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.concurrent.atomic.AtomicReference;

public class LoginActivity extends AppCompatActivity {

    EditText editTextUsername, editTextPassword;
    Button buttonLogin, buttonRegistr;

    // Для примера — жёстко закодированные данные от СБУ
    private final String correctUsername = "user";
    private final String correctPassword = "12345";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Проверяем есть ли token
        SharedPreferences prefs = getSharedPreferences("Auth", MODE_PRIVATE);
        AtomicReference<String> token = new AtomicReference<>(prefs.getString("token", null)); // null — значение по умолчанию
        if (token.get() != null) {
            // Токен успешно получен
            Log.d("TOKEN", "Token: " + token);

            // Переход на главную активити (например, MainActivity)
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }

        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonLogin = findViewById(R.id.buttonLogin);
        buttonRegistr = findViewById(R.id.buttonRegistr);

        // login
        buttonLogin.setOnClickListener(v -> {
            String username = editTextUsername.getText().toString().trim();
            String password = editTextPassword.getText().toString().trim();

            if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
                Toast.makeText(LoginActivity.this, "Введите логин и пароль", Toast.LENGTH_SHORT).show();
                return;
            }

            if (username.equals(correctUsername) && password.equals(correctPassword)) {
                Toast.makeText(LoginActivity.this, "Успешный вход", Toast.LENGTH_SHORT).show();

                // Добавляем token
                token.set("1111-2222-3333-4444");
                //SharedPreferences prefs = getSharedPreferences("Auth", MODE_PRIVATE);
                prefs.edit().putString("token", token.get()).apply();

                // Переход на главную активити (например, MainActivity)
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(LoginActivity.this, "Неверный логин или пароль", Toast.LENGTH_SHORT).show();
            }
        });

        // go to registr
        buttonRegistr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Создаём Intent для запуска SecondActivity
                Intent intent = new Intent(LoginActivity.this, RegistrActivity.class);
                startActivity(intent);
            }
        });
    }
}