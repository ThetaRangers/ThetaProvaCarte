package it.ferrarally.provacarte;

import android.graphics.Canvas;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class DragRecExample extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstance ){

        super.onCreate(savedInstance);
        setContentView(R.layout.drag_activity_din);

        new Holder();
    }

    class DragHelper extends ItemTouchHelper.Callback{

        private boolean cardPicked = true;
        private boolean reset = false;
        private Holder holder;

        public DragHelper(Holder holder){
            this.holder = holder;
        }

        @Override
        public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dx, float dy, int actionState, boolean isCurrentlyActive ){

            //card taken
            if(cardPicked){
                MaterialCardView card = (MaterialCardView) ((ViewGroup)viewHolder.itemView).getChildAt(0) ;
                card.setDragged(true);
                Log.v("Dr","start drag");
                cardPicked = false;
            }

            //card released
            if(reset){
                MaterialCardView card = (MaterialCardView) ((ViewGroup)viewHolder.itemView).getChildAt(0) ;
                card.setDragged(false);
                cardPicked = true;
                reset = false;
                Log.v("Dr","end drag");

            }
            super.onChildDraw(c, recyclerView, viewHolder, dx, dy, actionState, isCurrentlyActive);
        }

        @Override
        public void clearView(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder){
            super.clearView(recyclerView, viewHolder);

            reset = true;
        }

        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder dragged, @NonNull RecyclerView.ViewHolder target) {

            int position_dragged =dragged.getAdapterPosition();
            int position_target = target.getAdapterPosition();
            holder.move(position_dragged,position_target);

            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder swiped, int direction) {

        }

        @Override
        public int getMovementFlags(@NonNull RecyclerView recyclerView,RecyclerView.ViewHolder viewHolder){
            int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT;
            return makeMovementFlags(dragFlags,0);
        }

    }

    class Holder implements View.OnClickListener{

        final Button btnRedpower;
        final Button btnBluepower;
        final Button btnYellowpower;
        final Button btnGreenpower;
        final RecyclerView rvPower;
        List<String> vec;
        final CardAdapter cd;

        public void move(int position_dragged, int position_target){
            Collections.swap(vec,position_dragged, position_target);
            cd.notifyItemMoved(position_dragged, position_target);
        }

        public Holder(){

            btnBluepower=findViewById(R.id.btnBluepower);
            btnRedpower=findViewById(R.id.btnRedpower);
            btnYellowpower=findViewById(R.id.btnYellowpower);
            btnGreenpower=findViewById(R.id.btnGreenpower);

            btnBluepower.setOnClickListener(this);
            btnRedpower.setOnClickListener(this);
            btnYellowpower.setOnClickListener(this);
            btnGreenpower.setOnClickListener(this);

            rvPower=findViewById(R.id.rvPower);

            GridLayoutManager layout=new GridLayoutManager(DragRecExample.this, 2);

            rvPower.setLayoutManager(layout);

            vec=new ArrayList<>();

            cd=new CardAdapter(vec);
            rvPower.setAdapter(cd);

            DividerItemDecoration itemDec=new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL);
            rvPower.addItemDecoration(itemDec);

            itemDec=new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.HORIZONTAL);
            rvPower.addItemDecoration(itemDec);

            ItemTouchHelper helper = new ItemTouchHelper(new DragHelper(this));


            helper.attachToRecyclerView(rvPower);



        }


        @Override
        public void onClick(View view){
                if(view.getId()==R.id.btnRedpower){
                   createPowerRanger("r");
                }
                if(view.getId()==R.id.btnBluepower){
                    createPowerRanger("b");
                }
                if(view.getId()==R.id.btnYellowpower){
                    createPowerRanger("y");
                }
                if(view.getId()==R.id.btnGreenpower){
                    createPowerRanger("g");
                }
        }

        private void createPowerRanger(String type){

            vec.add(type);
            cd.notifyItemInserted(vec.size()-1);

        }

    }

    private  class CardAdapter extends RecyclerView.Adapter<CardAdapter.Holder>{

       //change type to cards
       private final List<String> colors;

       CardAdapter(List<String> colors){
           this.colors=colors;
       }

       @NonNull
       @Override
       public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
           ConstraintLayout cl;
           cl= (ConstraintLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.power_ranger, parent, false);
           Holder ret=new Holder(cl);
           return ret;
       }

       @Override
       public void onBindViewHolder(@NonNull Holder holder, int position){
            String col=colors.get(position);
            switch(col){
                case "g":
                    holder.ivPower.setImageResource(R.drawable.verde);
                    holder.tvPower.setText(R.string.green_name);
                    break;
                case "y":
                    holder.ivPower.setImageResource(R.drawable.giallo);
                    holder.tvPower.setText(R.string.yellow_name);
                    break;
                case "r":
                    holder.ivPower.setImageResource(R.drawable.rosso);
                    holder.tvPower.setText(R.string.red_name);
                    break;
                case "b":
                    holder.ivPower.setImageResource(R.drawable.blu);
                    holder.tvPower.setText(R.string.blue_name);
                    break;
            }

       }

       @Override
       public int getItemCount(){return colors.size();}


       class Holder extends RecyclerView.ViewHolder{

           final ImageView ivPower;
           final TextView tvPower;
           final public MaterialCardView cdPower;

           public Holder(@NonNull View itemView){
               super(itemView);
               ivPower=itemView.findViewById(R.id.ivPower);
               tvPower=itemView.findViewById(R.id.tvPower);
               cdPower = itemView.findViewById(R.id.cdPower);
           }

       }

    }


}
