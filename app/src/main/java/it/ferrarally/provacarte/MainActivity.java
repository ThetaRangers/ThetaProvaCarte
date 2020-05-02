package it.ferrarally.provacarte;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;

import com.google.android.material.card.MaterialCardView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Holder();
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
                   // Intent intent = new Intent(MainActivity.this, DragExample.class);

                   //Intent intent = new Intent(MainActivity.this, DragSecondExample.class);

                    Intent intent = new Intent(MainActivity.this, DragRecExample.class);

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
        }
    }
}
