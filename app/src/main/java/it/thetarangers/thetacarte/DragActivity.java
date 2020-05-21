package it.thetarangers.thetacarte;

import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class DragActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstance) {

        super.onCreate(savedInstance);
        setContentView(R.layout.activity_drag);

        new Holder();
    }

    class DragHelper extends ItemTouchHelper.Callback {

        private boolean cardPicked = true;
        private boolean reset = false;
        private Holder holder;

        DragHelper(Holder holder) {
            this.holder = holder;
        }

        @Override
        public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView,
                                @NonNull RecyclerView.ViewHolder viewHolder, float dx, float dy,
                                int actionState, boolean isCurrentlyActive ) {

            //card taken
            if (cardPicked) {
                MaterialCardView card = (MaterialCardView)
                        ((ViewGroup)viewHolder.itemView).getChildAt(0) ;
                card.setDragged(true);
                Log.v("Dr","start drag");
                cardPicked = false;
            }

            //card released
            if (reset) {
                MaterialCardView card = (MaterialCardView)
                        ((ViewGroup)viewHolder.itemView).getChildAt(0) ;
                card.setDragged(false);
                cardPicked = true;
                reset = false;
                Log.v("Dr","end drag");

            }
            super.onChildDraw(c, recyclerView, viewHolder, dx, dy, actionState, isCurrentlyActive);
        }

        @Override
        public void clearView(@NonNull RecyclerView recyclerView,
                              @NonNull RecyclerView.ViewHolder viewHolder) {
            super.clearView(recyclerView, viewHolder);

            reset = true;
        }

        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView,
                              @NonNull RecyclerView.ViewHolder dragged,
                              @NonNull RecyclerView.ViewHolder target) {

            int position_dragged =dragged.getAdapterPosition();
            int position_target = target.getAdapterPosition();
            holder.move(position_dragged,position_target);

            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder swiped, int direction) {

        }

        @Override
        public int getMovementFlags(@NonNull RecyclerView recyclerView,
                                    @NonNull RecyclerView.ViewHolder viewHolder){
            int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN |
                    ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT;
            return makeMovementFlags(dragFlags,0);
        }

    }

    class Holder implements View.OnClickListener {

        final Button btnGenerate;
        final RecyclerView rvPower;
        List<List<Integer>> vec;
        final CardAdapter cd;

        void move(int position_dragged, int position_target) {
            Collections.swap(vec, position_dragged, position_target);
            cd.notifyItemMoved(position_dragged, position_target);
        }

        Holder() {

            btnGenerate = findViewById(R.id.btnGenerate);
            btnGenerate.setOnClickListener(this);

            rvPower = findViewById(R.id.rvPower);

            GridLayoutManager layout = new GridLayoutManager(DragActivity.this, 2);

            rvPower.setLayoutManager(layout);

            vec = new ArrayList<>();

            cd = new CardAdapter(vec);
            rvPower.setAdapter(cd);

            ItemTouchHelper helper = new ItemTouchHelper(new DragHelper(this));

            helper.attachToRecyclerView(rvPower);

        }


        @Override
        public void onClick(View view) {
            if(view.getId() == R.id.btnGenerate) {
                generateCard();
            }
        }


        void generateCard() {
            Random rnd = new Random();
            int red = rnd.nextInt(256);
            int green = rnd.nextInt(256);
            int blue = rnd.nextInt(256);
            List<Integer> col = new ArrayList<>();
            col.add(red);
            col.add(green);
            col.add(blue);
            vec.add(col);
            cd.notifyItemInserted(vec.size() - 1);
        }
    }

    private static class CardAdapter extends RecyclerView.Adapter<CardAdapter.Holder> {

       //change type to cards
       private final List<List<Integer>> colors;

       CardAdapter(List<List<Integer>> colors) {
           this.colors = colors;
       }

       @NonNull
       @Override
       public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
           ConstraintLayout cl;
           cl= (ConstraintLayout) LayoutInflater.from(parent.getContext())
                   .inflate(R.layout.item_color, parent, false);
           return new Holder(cl);
       }

       @Override
       public void onBindViewHolder(@NonNull Holder holder, int position) {
           List<Integer> col = this.colors.get(position);
           int red = col.get(0);
           int green = col.get(1);
           int blue = col.get(2);
           holder.ivPower.setColorFilter(Color.argb(255, red, green, blue));
           holder.tvPower.setText( String.format("#%02x%02x%02x", red, green, blue).toUpperCase());

       }

       @Override
       public int getItemCount() {return colors.size();}


       static class Holder extends RecyclerView.ViewHolder {

           final ImageView ivPower;
           final TextView tvPower;
           final MaterialCardView cdPower;

           Holder(@NonNull View itemView) {
               super(itemView);
               ivPower = itemView.findViewById(R.id.ivPower);
               tvPower = itemView.findViewById(R.id.tvPower);
               cdPower = itemView.findViewById(R.id.cdPower);
           }

       }

    }

}
