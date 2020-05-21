package it.thetarangers.provacarte;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class EventDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);

        Intent data = getIntent();
        Event event =  data.getParcelableExtra("Event");
        new Holder(event);
    }

    class Holder {
        final ImageView ivEvent;
        final TextView tvName;
        final TextView tvLocation;
        final TextView tvDescription;

        Holder(Event event){
            ivEvent = findViewById(R.id.ivEvent);
            tvName = findViewById(R.id.tvName);
            tvLocation = findViewById(R.id.tvLocation);
            tvDescription = findViewById(R.id.tvDescription);

            ivEvent.setImageResource(event.imageId);
            tvName.setText(event.eventName);
            tvLocation.setText(event.eventLocation);
            tvDescription.setText(event.eventDescription);
        }
    }
}
