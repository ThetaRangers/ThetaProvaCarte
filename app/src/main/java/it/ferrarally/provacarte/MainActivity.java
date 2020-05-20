package it.ferrarally.provacarte;

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

    class Holder {
        Holder() {

            final Button buttonList = findViewById(R.id.btnList);
            buttonList.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                        Intent intent = new Intent(MainActivity.this, ListActivity.class);

                        startActivity(intent);



                }
            });

            final Button buttonDrag = findViewById(R.id.btnDrag);
            buttonDrag.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Intent intent = new Intent(MainActivity.this, DragExample.class);

                   //Intent intent = new Intent(MainActivity.this, DragSecondExample.class);

                   Intent intent = new Intent(MainActivity.this, DragActivity.class);

                    startActivity(intent);
                }
            });

            final Button buttonEvents = findViewById(R.id.btnEvents);
            buttonEvents.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, EventsActivity.class);

                    startActivity(intent);
                }
            });

            final Button buttonSwipe = findViewById(R.id.btnSwipe);
            buttonSwipe.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, SwipeCard.class);

                    startActivity(intent);
                }
            });

            final ImageButton buttonTheme = findViewById(R.id.btnTheme);
            buttonTheme.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isNightMode()) {
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    } else {
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    }
                }
            });
        }
    }
}
