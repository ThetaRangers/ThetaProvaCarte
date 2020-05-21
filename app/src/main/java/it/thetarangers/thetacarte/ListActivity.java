package it.thetarangers.thetacarte;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import androidx.transition.TransitionManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {
    private RecyclerView rvCards;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        new Holder();
    }

    class Holder {
        Holder() {
            rvCards = findViewById(R.id.rvCards);

            RecyclerView.LayoutManager layoutManager = new GridLayoutManager(ListActivity.this, 1);
            rvCards.setLayoutManager(layoutManager);

            CardAdapter mAdapter = new CardAdapter(createList());
            rvCards.setAdapter(mAdapter);
        }

        private List<Restaurant> createList() {
            List<Restaurant> list = new ArrayList<>();

            list.add(new Restaurant("Sushisen", R.drawable.sushisen, "Locale semplice ed accogliente, ispirato al classico stile zen.\n" +
                    "Disponiamo di due sale, una tipica con tavoli (40 posti a sedere), ed una più innovativa con  \"Kaitenzushi\" (letteralmente sushi rotante), un nastro " +
                    "che fa scorrere piattini e singole porzioni di piatti che spaziano dal sushi al roll, nonchè prelibatezze speciali o del mese.", 1123, 5, "06 12315312"));
            list.add(new Restaurant("Osteria Francescana", R.drawable.osteria, "Uhmmm buono questo ristorante", 8, 5, "06 5342523"));
            list.add(new Restaurant("Ristorante Puccini", R.drawable.puccini, "Yumm", 100, 2, "07 12346354"));
            list.add(new Restaurant("Risorante Pizzeria Da Marco", R.drawable.pizzeria, "Buonissima pizzeria di Marco", 1, 2.5f, "06123 4323"));

            return list;
        }

    }


    class CardAdapter extends RecyclerView.Adapter<CardAdapter.Holder> {
        private List<Restaurant> list;
        private int mExpandedPosition = -1;

        CardAdapter(List<Restaurant> list) {
            this.list = list;
        }

        @NonNull
        @Override
        public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            ConstraintLayout cl;
            //Inflate row of RecyclerView
            cl = (ConstraintLayout) LayoutInflater
                    .from(parent.getContext())
                    .inflate(R.layout.item_restaurant, parent, false);

            return new Holder(cl);
        }

        @Override
        public void onBindViewHolder(@NonNull Holder holder, int position) {
            final boolean isExpanded = position == mExpandedPosition;
            holder.clDetails.setVisibility(isExpanded ? View.VISIBLE : View.GONE);

            holder.tvNumber.setText(list.get(position).telephone);
            holder.tvName.setText(list.get(position).name);
            holder.tvDescription.setText(list.get(position).description);
            holder.ivPower.setImageResource(list.get(position).image);

            holder.tvRatingNumbers.setText(String.valueOf(list.get(position).ratingNumbers));
            holder.rbReview.setRating(list.get(position).numberOfStars);

            if (isExpanded) {
                holder.ivExpand.setRotation(180);
            } else {
                holder.ivExpand.setRotation(0);
            }

        }

        @Override
        public int getItemCount() {
            return list.size();
        }



        class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {

            final TextView tvName;
            final TextView tvDescription;
            final ImageView ivPower;
            final ImageView ivExpand;
            final MaterialCardView card;
            final ConstraintLayout clDetails;
            final RatingBar rbReview;
            final TextView tvRatingNumbers;
            final TextView tvNumber;

            Holder(@NonNull View itemView) {
                super(itemView);

                tvName = itemView.findViewById(R.id.tvName);
                tvDescription = itemView.findViewById(R.id.tvLocation);
                ivPower = itemView.findViewById(R.id.ivPower);
                ivExpand = itemView.findViewById(R.id.ivExpand);
                card = itemView.findViewById(R.id.cdPower);
                rbReview = itemView.findViewById(R.id.rbReview);
                tvRatingNumbers = itemView.findViewById(R.id.tvRatingNumbers);
                clDetails = itemView.findViewById(R.id.clDetails);
                tvNumber = itemView.findViewById(R.id.tvNumber);

                card.setOnClickListener(this);
            }

            @Override
            public void onClick(View v) {
                int position =  getAdapterPosition();
                boolean isExpanded = position == mExpandedPosition;
                mExpandedPosition = isExpanded ? -1 : position;
                TransitionManager.beginDelayedTransition(rvCards);
                notifyDataSetChanged();

            }

        }
    }

    static class Restaurant {
        String name;
        int image;
        String description;
        int ratingNumbers;
        float numberOfStars;
        String telephone;

        Restaurant(String name, int image, String description, int ratingNumbers,
                   float numberOfStars, String telephone) {
            this.name = name;
            this.image = image;
            this.description = description;
            this.numberOfStars = numberOfStars;
            this.ratingNumbers = ratingNumbers;
            this.telephone = telephone;
        }
    }
}
