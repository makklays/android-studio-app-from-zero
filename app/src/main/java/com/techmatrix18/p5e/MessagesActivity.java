package com.techmatrix18.p5e;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import com.google.android.material.navigation.NavigationView;

public class MessagesActivity extends AppCompatActivity {

    private Intent intent;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ActionBarDrawerToggle drawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);
        //Toolbar toolbar = findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        navigationView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();

            if (id == R.id.nav_home) {
                Toast.makeText(this, "Главная", Toast.LENGTH_SHORT).show();
                // переходим в Сообщения
                intent = new Intent(MessagesActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            } else if (id == R.id.nav_map) {
                Toast.makeText(this, "Карта", Toast.LENGTH_SHORT).show();
                // переходим в Сообщения
                intent = new Intent(MessagesActivity.this, MapActivity.class);
                startActivity(intent);
                finish();
            } else if (id == R.id.nav_messages) {
                Toast.makeText(this, "Сообщения", Toast.LENGTH_SHORT).show();
                // переходим в Сообщения
                intent = new Intent(MessagesActivity.this, MessagesActivity.class);
                startActivity(intent);
                finish();
            } else if (id == R.id.nav_taps) {
                Toast.makeText(this, "Тапки", Toast.LENGTH_SHORT).show();
                // переходим в Сообщения
                Intent intent = new Intent(MessagesActivity.this, TapActivity.class);
                startActivity(intent);
                finish();
            /*} else if (id == R.id.nav_settings) {
                Toast.makeText(this, "Настройки", Toast.LENGTH_SHORT).show();*/
            } else if (id == R.id.nav_logout) {
                Toast.makeText(this, "Выход", Toast.LENGTH_SHORT).show();

                SharedPreferences preferences = getSharedPreferences("Auth", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.remove("token");
                editor.apply(); // или editor.commit();

                intent = new Intent(MessagesActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }

            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        });
    }
}

