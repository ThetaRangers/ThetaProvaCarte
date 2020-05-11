package it.ferrarally.provacarte;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class SwipeCard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe_card);

        new Holder();
    }

    class Holder {
        final RecyclerView rvSwipe;

        Holder(){
            rvSwipe = findViewById(R.id.rvSwipe);
            rvSwipe.setLayoutManager(new LinearLayoutManager(SwipeCard.this));
            rvSwipe.setHasFixedSize(true);

            List<String> strings = new ArrayList<>();
            strings.add("Eila");
            strings.add("gamberone");

            final Adapter adapter = new Adapter(strings);
            rvSwipe.setAdapter(adapter);

            ItemTouchHelper itemTouchHelper = null;

            ItemTouchHelper.SimpleCallback callback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                @Override
                public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                    return false;
                }

                @Override
                public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                    if (direction == ItemTouchHelper.RIGHT) {
                        Log.w("THETA", "Hey");
                    } else if (direction == ItemTouchHelper.LEFT) {
                        Log.w("THETA", "Hey Gamberone");
                    }

                    adapter.notifyItemChanged(viewHolder.getAdapterPosition());
                }
            };

            itemTouchHelper = new ItemTouchHelper(callback);

            itemTouchHelper.attachToRecyclerView(rvSwipe);
        }
    }

    class Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
        private List<String> stringList;

        Adapter(List<String> stringList){
            this.stringList = stringList;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            ConstraintLayout cl;
            //Inflate row of recycle view
            cl = (ConstraintLayout) LayoutInflater
                    .from(parent.getContext())
                    .inflate(R.layout.swipe_card, parent, false);

            return new Holder(cl);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            ((Holder) holder).tvText.setText(stringList.get(position));
        }

        @Override
        public int getItemCount() {
            return stringList.size();
        }

        class Holder extends RecyclerView.ViewHolder{

            final TextView tvText;

            public Holder(@NonNull View itemView) {
                super(itemView);

                tvText = itemView.findViewById(R.id.tvText);
            }
        }
    }
}
