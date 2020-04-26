package it.ferrarally.provacarte;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

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
                    Intent intent = new Intent(MainActivity.this, DragExample.class);

                    startActivity(intent);
                }
            });

            final Button buttonContacts = findViewById(R.id.btnContacts);
            buttonContacts.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, EventsActivity.class);

                    startActivity(intent);
                }
            });
        }
    }
}
