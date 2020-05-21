package it.thetarangers.provacarte;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Holder();
    }

    Boolean isNightMode() {
        return (getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK)
                == Configuration.UI_MODE_NIGHT_YES;
    }

    class Holder implements View.OnClickListener{
        Holder() {

            final Button buttonList = findViewById(R.id.btnList);
            buttonList.setOnClickListener(this);

            final Button buttonDrag = findViewById(R.id.btnDrag);
            buttonDrag.setOnClickListener(this);

            final Button buttonEvents = findViewById(R.id.btnEvents);
            buttonEvents.setOnClickListener(this);

            final Button buttonSwipe = findViewById(R.id.btnSwipe);
            buttonSwipe.setOnClickListener(this);

            final ImageButton buttonTheme = findViewById(R.id.btnTheme);
            buttonTheme.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(v.getId() == R.id.btnList){
                Intent intent = new Intent(MainActivity.this, ListActivity.class);

                startActivity(intent);
            } else if(v.getId() == R.id.btnEvents){
                Intent intent = new Intent(MainActivity.this, EventsActivity.class);

                startActivity(intent);
            } else if(v.getId() == R.id.btnDrag){
                Intent intent = new Intent(MainActivity.this, DragActivity.class);

                startActivity(intent);
            } else if(v.getId() == R.id.btnSwipe){
                Intent intent = new Intent(MainActivity.this, SwipeCard.class);

                startActivity(intent);
            } else if(v.getId() == R.id.btnTheme){
                if (isNightMode()) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                }
            }
        }
    }
}
