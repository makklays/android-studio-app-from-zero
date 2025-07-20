package com.techmatrix18.p5e.chat;

import java.util.List;
import java.util.ArrayList;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.navigation.NavigationView;
import com.techmatrix18.p5e.LoginActivity;
import com.techmatrix18.p5e.MainActivity;
import com.techmatrix18.p5e.MapActivity;
import com.techmatrix18.p5e.R;
import com.techmatrix18.p5e.TapActivity;

public class MessagesActivity extends AppCompatActivity {

    private List<Message> messages = new ArrayList<>();
    private MessageAdapter adapter;
    private Intent intent;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ActionBarDrawerToggle drawerToggle;
    private RecyclerView recyclerView;
    private EditText editMessage;
    private ImageButton buttonSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);

        // messages
        recyclerView = findViewById(R.id.messagesRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MessageAdapter(messages);
        recyclerView.setAdapter(adapter);

        EditText editMessage = findViewById(R.id.editMessage);
        ImageButton buttonSend = findViewById(R.id.buttonSend);

        buttonSend.setOnClickListener(v -> {
            String text = editMessage.getText().toString().trim();
            if (!text.isEmpty()) {
                messages.add(new Message(text, true)); // от пользователя
                adapter.notifyItemInserted(messages.size() - 1);
                recyclerView.scrollToPosition(messages.size() - 1);
                editMessage.setText("");

                // Пример ответа
                new Handler().postDelayed(() -> {
                    messages.add(new Message("Ответ: " + text, false));
                    adapter.notifyItemInserted(messages.size() - 1);
                    recyclerView.scrollToPosition(messages.size() - 1);
                }, 1000);
            }
        });

        // other
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);
        //Toolbar toolbar = findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        navigationView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();

            if (id == R.id.nav_home) {
                Toast.makeText(this, item.getTitle(), Toast.LENGTH_SHORT).show();
                // переходим в Сообщения
                intent = new Intent(MessagesActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            } else if (id == R.id.nav_map) {
                Toast.makeText(this, item.getTitle(), Toast.LENGTH_SHORT).show();
                // переходим в Сообщения
                intent = new Intent(MessagesActivity.this, MapActivity.class);
                startActivity(intent);
                finish();
            } else if (id == R.id.nav_messages) {
                Toast.makeText(this, item.getTitle(), Toast.LENGTH_SHORT).show();
                // переходим в Сообщения
                intent = new Intent(MessagesActivity.this, MessagesActivity.class);
                startActivity(intent);
                finish();
            } else if (id == R.id.nav_taps) {
                Toast.makeText(this, item.getTitle(), Toast.LENGTH_SHORT).show();
                // переходим в Сообщения
                Intent intent = new Intent(MessagesActivity.this, TapActivity.class);
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

                intent = new Intent(MessagesActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }

            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        });
    }
}

