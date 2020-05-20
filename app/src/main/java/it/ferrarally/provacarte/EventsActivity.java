package it.ferrarally.provacarte;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import com.google.android.material.card.MaterialCardView;

public class EventsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);

        new Holder();
    }

    class Holder {
        final RecyclerView rvEvents;

        public Holder(){
            rvEvents = findViewById(R.id.rvEvents);


            RecyclerView.LayoutManager layoutManager = new GridLayoutManager(EventsActivity.this, 2);
            rvEvents.setLayoutManager(layoutManager);

            rvEvents.setAdapter(new EventsAdapter(getEvents()));
        }

        private List<Event> getEvents(){
            List<Event> eventsList = new ArrayList<>();

            eventsList.add(new Event("Evento1", "bello questo evento 1", R.drawable.sushisen, "Roma"));
            eventsList.add(new Event("Evento2", "bello questo evento 2", R.drawable.osteria, "Campoli Appennino"));

            return eventsList;
        }
    }

    class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.Holder>{
        List<Event> eventsList;

        EventsAdapter(List<Event> eventsList){
            this.eventsList = eventsList;
        }


        @NonNull
        @Override
        public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            ConstraintLayout cl;
            //Inflate row of recycle view
            cl = (ConstraintLayout) LayoutInflater
                    .from(parent.getContext())
                    .inflate(R.layout.item_event, parent, false);

            return new Holder(cl);
        }

        @Override
        public void onBindViewHolder(@NonNull Holder holder, int position) {
            holder.tvName.setText(eventsList.get(position).eventName);
            holder.tvLocation.setText(eventsList.get(position).eventLocation);
            holder.ivPreview.setImageResource(eventsList.get(position).imageId);

            final int i = position;
            final ImageView iv = holder.ivPreview;
            final TextView tv = holder.tvName;
            final TextView tv1 = holder.tvLocation;

            holder.card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent data = new Intent(EventsActivity.this, EventDetailActivity.class);
                    data.putExtra("Event", eventsList.get(i));


                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        Pair<View, String> p1 = Pair.create((View)iv, "imageExpansion");
                        Pair<View, String> p2 = Pair.create((View)tv, "nameTransition");
                        Pair<View, String> p3 = Pair.create((View)tv1, "locationTransition");
                        ActivityOptions options = ActivityOptions
                                .makeSceneTransitionAnimation(EventsActivity.this, p1, p2, p3);
                        startActivity(data, options.toBundle());
                    } else {
                        startActivity(data);
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return eventsList.size();
        }

        class Holder extends RecyclerView.ViewHolder{
            final TextView tvName;
            final TextView tvLocation;
            final ImageView ivPreview;
            final MaterialCardView card;

            public Holder(@NonNull View itemView) {
                super(itemView);

                tvName = itemView.findViewById(R.id.tvName);
                tvLocation = itemView.findViewById(R.id.tvLocation);
                ivPreview = itemView.findViewById(R.id.ivPreview);
                card = itemView.findViewById(R.id.card);
            }
        }
    }

}
