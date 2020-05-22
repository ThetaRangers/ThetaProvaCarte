package it.thetarangers.thetacarte;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ActivityOptions;
import android.content.Intent;
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

        private Holder() {
            rvEvents = findViewById(R.id.rvEvents);

            RecyclerView.LayoutManager layoutManager = new GridLayoutManager(EventsActivity.this, 2);
            rvEvents.setLayoutManager(layoutManager);

            rvEvents.setAdapter(new EventsAdapter(getEvents()));
        }

        private List<Event> getEvents() {
            List<Event> eventsList = new ArrayList<>();

            eventsList.add(new Event("San Rocco", "La festa di San Rocco, patrono di Patrica, è " +
                    "la più seguita dalla popolazione locale. Il 16 agosto di ogni anno la processione con la stauta del santo " +
                    "si snoda lungo le strade del paese con i portatori in veste biancaù" +
                    " e le donne che trasportano enormi candele. La sera si festeggia in piazza con la banda musicale.",
                    R.drawable.patrica, "Patrica"));
            eventsList.add(new Event("Festa del tartufo", "A Campoli Appennino (Fr) il 9 e 10 e il 16 " +
                    "e 17 Novembre 2019 si rinnova il doppio appuntamento con la Fiera del Tartufo di Campoli Appennino " +
                    "bianco e nero pregiato, il re dei prodotti tipici della Valle di Comino, sul versante laziale del " +
                    "Parco Nazionale d’Abruzzo, Lazio e Molise. Una festa dedicata a tutte le famiglie con animazione per " +
                    "bambini, musica popolare, stand enogastronomici, artigianato artistico e tante sorprese. Prezioso e " +
                    "pregiato, raro e delicato, intenso e fragrante, il tartufo di Campoli Appennino in passato veniva spesso" +
                    " dato in dono a principi e re quando si recavano in viaggio in Ciociaria. La storia di questo prodotto " +
                    "spontaneo si fonde nelle origini del piccolo centro ciociaro, tanto da poter essere considerato il simbolo della cultura e della tradizione del paese. Le varietà che nascono spontanee " +
                    "nei boschi del territorio possono essere il noto nero pregiato e il più raro bianco.",
                    R.drawable.campoli, "Campoli"));

            return eventsList;
        }
    }

    class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.Holder> {
        List<Event> eventsList;

        EventsAdapter(List<Event> eventsList) {
            this.eventsList = eventsList;
        }

        @NonNull
        @Override
        public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            ConstraintLayout cl;

            //Inflate row of RecyclerView
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

            holder.card.setOnClickListener(holder);
        }

        @Override
        public int getItemCount() {
            return eventsList.size();
        }

        class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {
            final TextView tvName;
            final TextView tvLocation;
            final ImageView ivPreview;
            final MaterialCardView card;

            private Holder(@NonNull View itemView) {
                super(itemView);

                tvName = itemView.findViewById(R.id.tvName);
                tvLocation = itemView.findViewById(R.id.tvLocation);
                ivPreview = itemView.findViewById(R.id.ivPreview);
                card = itemView.findViewById(R.id.card);
            }

            @Override
            public void onClick(View v) {
                Intent data = new Intent(EventsActivity.this, EventDetailActivity.class);
                int position = this.getAdapterPosition();
                data.putExtra("Event", eventsList.get(position));

                Pair<View, String> p1 = Pair.create((View) ivPreview, "imageExpansion");
                Pair<View, String> p2 = Pair.create((View) tvName, "nameTransition");
                Pair<View, String> p3 = Pair.create((View) tvLocation, "locationTransition");
                ActivityOptions options = ActivityOptions
                        .makeSceneTransitionAnimation(EventsActivity.this, p1, p2, p3);
                startActivity(data, options.toBundle());

            }
        }
    }

}
