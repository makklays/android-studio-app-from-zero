package com.techmatrix18.p5e;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import com.google.android.material.navigation.NavigationView;
import com.techmatrix18.p5e.chat.MessagesActivity;

import java.util.Random;
import android.view.MotionEvent;

public class TapActivity extends AppCompatActivity {

    private Intent intent;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ActionBarDrawerToggle drawerToggle;
    private RelativeLayout rootLayout;
    private int clickCount = 0;
    private TextView textView;
    private ImageView mainImage;
    private Random random = new Random();
    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tap);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);
        //Toolbar toolbar = findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        rootLayout = findViewById(R.id.rootLayout);
        mainImage = findViewById(R.id.mainImage);
        textView = findViewById(R.id.textView);

        // Загружаем значение кликов из SharedPreferences
        prefs = getSharedPreferences("User", MODE_PRIVATE);
        clickCount = prefs.getInt("clickCount", 0);
        textView.setText(String.valueOf(clickCount));

        rootLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    clickCount++;

                    // Сохраняем в SharedPreferences
                    prefs.edit().putInt("clickCount", clickCount).apply();

                    // Отображаем
                    textView.setText(String.valueOf(clickCount));

                    //spawnStars(event.getX(), event.getY());
                    // Показываем звезду
                    float x = event.getX();
                    float y = event.getY();
                    showStar(x, y);
                }
                return true;
            }
        });

        /*mainImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickCount++;
                textView.setText(String.valueOf(clickCount));
            }
        });*/

        navigationView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();

            if (id == R.id.nav_home) {
                Toast.makeText(this, item.getTitle(), Toast.LENGTH_SHORT).show();
                // переходим в Сообщения
                intent = new Intent(TapActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            } else if (id == R.id.nav_map) {
                Toast.makeText(this, item.getTitle(), Toast.LENGTH_SHORT).show();
                // переходим в Сообщения
                intent = new Intent(TapActivity.this, MapActivity.class);
                startActivity(intent);
                finish();
            } else if (id == R.id.nav_messages) {
                Toast.makeText(this, item.getTitle(), Toast.LENGTH_SHORT).show();
                // переходим в Сообщения
                intent = new Intent(TapActivity.this, MessagesActivity.class);
                startActivity(intent);
                finish();
            } else if (id == R.id.nav_taps) {
                Toast.makeText(this, item.getTitle(), Toast.LENGTH_SHORT).show();
                // переходим в Сообщения
                Intent intent = new Intent(TapActivity.this, TapActivity.class);
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

                intent = new Intent(TapActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }

            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        });
    }

    private void spawnStars(float x, float y) {
        int starCount = 10;

        for (int i = 0; i < starCount; i++) {
            final ImageView star = new ImageView(this);
            star.setImageResource(R.drawable.ic_star); // звезда

            int size = 60;
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(size, size);
            star.setLayoutParams(params);
            star.setX(x - size / 2f);
            star.setY(y - size / 2f);
            rootLayout.addView(star);

            float dx = random.nextInt(400) - 200f;
            float dy = -(random.nextInt(400) + 100f);
            float rotation = random.nextInt(360);

            star.animate()
                    .translationXBy(dx)
                    .translationYBy(dy)
                    .rotationBy(rotation)
                    .alpha(0f)
                    .setDuration(1000)
                    .withEndAction(new Runnable() {
                        @Override
                        public void run() {
                            rootLayout.removeView(star);
                        }
                    })
                    .start();
        }
    }

    private void showStar(float x, float y) {
        ImageView star = new ImageView(this);
        star.setImageResource(R.drawable.ic_star); // убедись, что звезда есть в drawable

        int size = 100;
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(size, size);
        params.leftMargin = (int) x - size / 2;
        params.topMargin = (int) y - size / 2;
        star.setLayoutParams(params);
        rootLayout.addView(star);

        // Анимация: поднимаем вверх на 300 пикселей относительно текущего положения
        ObjectAnimator moveUp = ObjectAnimator.ofFloat(star, "translationY", 0, -300);
        ObjectAnimator fadeOut = ObjectAnimator.ofFloat(star, "alpha", 1f, 0f);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(moveUp, fadeOut);
        animatorSet.setDuration(1000);
        animatorSet.start();

        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                rootLayout.removeView(star);
            }
        });
    }
}

