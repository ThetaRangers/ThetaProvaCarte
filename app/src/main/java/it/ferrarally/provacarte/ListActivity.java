package it.ferrarally.provacarte;

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
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        new Holder();
    }

    class Holder {
        final RecyclerView rvCards;
        Holder(){
            rvCards = findViewById(R.id.rvCards);

            RecyclerView.LayoutManager layoutManager = new GridLayoutManager(ListActivity.this, 1);
            rvCards.setLayoutManager(layoutManager);

            CardAdapter mAdapter = new CardAdapter(createList());
            rvCards.setAdapter(mAdapter);
        }

        private List<PowerRanger> createList(){
            List<PowerRanger> list = new ArrayList<>();

            list.add(new PowerRanger("Power Ranger Rosso", R.drawable.rosso, "E' un power ranger rossooooooooooooooo" +
                    "ooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo"));
            list.add(new PowerRanger("Power Ranger Verde", R.drawable.verde, "E' un power ranger verde"));
            list.add(new PowerRanger("Power Ranger Blu", R.drawable.blu, "E' un power ranger blu"));
            list.add(new PowerRanger("Power Ranger Giallo", R.drawable.giallo, "E' un power ranger giallo"));

            return list;
        }
    }

    class CardAdapter extends RecyclerView.Adapter<CardAdapter.Holder>{
        private List<PowerRanger> list;

        CardAdapter(List<PowerRanger> list){
            this.list = list;
        }

        @NonNull
        @Override
        public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            ConstraintLayout cl;
            //Inflate row of recycle view
            cl = (ConstraintLayout) LayoutInflater
                    .from(parent.getContext())
                    .inflate(R.layout.element, parent, false);

            return new Holder(cl);
        }

        @Override
        public void onBindViewHolder(@NonNull Holder holder, int position) {
            holder.tvName.setText(list.get(position).name);
            holder.tvDescription.setText(list.get(position).description);
            holder.ivPower.setImageResource(list.get(position).image);

            final TextView tv = holder.tvDescription;
            final MaterialCardView card = holder.card;

            holder.btnExpand.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(tv.getVisibility() == View.VISIBLE){
                        tv.setVisibility(View.GONE);
                    } else {
                        TransitionManager.beginDelayedTransition(card);
                        tv.setVisibility(View.VISIBLE);
                    }
                }
            });

            //Se si vogliono fare cliccabili le carte
/*            card.setOnClickListener(new MaterialCardView.OnClickListener(){
                @Override
                public void onClick(View v) {
                    card.setChecked(!card.isChecked());
                }
            });*/

        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        class Holder extends RecyclerView.ViewHolder{

            final TextView tvName;
            final TextView tvDescription;
            final ImageView ivPower;
            final MaterialButton btnExpand;
            final MaterialCardView card;

            public Holder(@NonNull View itemView) {
                super(itemView);

                tvName = itemView.findViewById(R.id.tvText);
                tvDescription = itemView.findViewById(R.id.tvDescription);
                ivPower = itemView.findViewById(R.id.ivPower);
                btnExpand = itemView.findViewById(R.id.btnExpand);
                card = itemView.findViewById(R.id.cdPower);

            }
        }
    }

    class PowerRanger {
        public String name;
        public int image;
        public String description;

        PowerRanger(String name, int image, String description){
            this.name = name;
            this.image = image;
            this.description = description;
        }
    }
}
