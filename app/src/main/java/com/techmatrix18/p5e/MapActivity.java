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
import com.techmatrix18.p5e.chat.MessagesActivity;

public class MapActivity extends AppCompatActivity {

    private Intent intent;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ActionBarDrawerToggle drawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);
        //Toolbar toolbar = findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        navigationView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();

            if (id == R.id.nav_home) {
                Toast.makeText(this, item.getTitle(), Toast.LENGTH_SHORT).show();
                // переходим в Сообщения
                intent = new Intent(MapActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            } else if (id == R.id.nav_map) {
                Toast.makeText(this, item.getTitle(), Toast.LENGTH_SHORT).show();
                // переходим в Сообщения
                intent = new Intent(MapActivity.this, MapActivity.class);
                startActivity(intent);
                finish();
            } else if (id == R.id.nav_messages) {
                Toast.makeText(this, item.getTitle(), Toast.LENGTH_SHORT).show();
                // переходим в Сообщения
                Intent intent = new Intent(MapActivity.this, MessagesActivity.class);
                startActivity(intent);
                finish();
            } else if (id == R.id.nav_taps) {
                Toast.makeText(this, item.getTitle(), Toast.LENGTH_SHORT).show();
                // переходим в Сообщения
                Intent intent = new Intent(MapActivity.this, TapActivity.class);
                startActivity(intent);
                finish();
            /*} else if (id == R.id.nav_settings) {
                Toast.makeText(this, "Настройки", Toast.LENGTH_SHORT).show();*/
            } else if (id == R.id.nav_logout) {
                Toast.makeText(this, item.getTitle(), Toast.LENGTH_SHORT).show();

                SharedPreferences preferences = getSharedPreferences("Auth", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.remove("token");
                editor.apply(); // или editor.commit();

                Intent intent = new Intent(MapActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }

            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        });
    }
}
