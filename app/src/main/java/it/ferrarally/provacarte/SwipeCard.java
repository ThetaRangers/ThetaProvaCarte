package it.ferrarally.provacarte;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Canvas;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.card.MaterialCardView;

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
            strings.add("Bello Questo Paesaggio zio");
            strings.add("gamberone");

            final Adapter adapter = new Adapter(strings);
            rvSwipe.setAdapter(adapter);

            ItemTouchHelper itemTouchHelper;

            ItemTouchHelper.SimpleCallback callback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
                @Override
                public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                    return false;
                }

                @Override
                public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                    if (direction == ItemTouchHelper.RIGHT) {
                        Log.w("THETA", "Hey Gamberone");
                    }

                    //Makes the item reappear after swipe
                    adapter.notifyItemChanged(viewHolder.getAdapterPosition());
                }

                @Override
                public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
                    if (viewHolder != null){
                        final View foregroundView = ((Adapter.Holder) viewHolder).cdSwipe;

                        getDefaultUIUtil().onSelected(foregroundView);
                    }
                }

                @Override
                public void onChildDraw(Canvas c, RecyclerView recyclerView,
                                        RecyclerView.ViewHolder viewHolder, float dX, float dY,
                                        int actionState, boolean isCurrentlyActive) {
                    final View foregroundView = ((Adapter.Holder) viewHolder).cdSwipe;

                    drawBackground(viewHolder, dX, actionState);

                    getDefaultUIUtil().onDraw(c, recyclerView, foregroundView, dX, dY,
                            actionState, isCurrentlyActive);
                }

                @Override
                public void onChildDrawOver(Canvas c, RecyclerView recyclerView,
                                            RecyclerView.ViewHolder viewHolder, float dX, float dY,
                                            int actionState, boolean isCurrentlyActive) {
                    final View foregroundView = ((Adapter.Holder) viewHolder).cdSwipe;

                    drawBackground(viewHolder, dX, actionState);

                    getDefaultUIUtil().onDrawOver(c, recyclerView, foregroundView, dX, dY,
                            actionState, isCurrentlyActive);
                }

                @Override
                public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder){
                    final View backgroundView = ((Adapter.Holder) viewHolder).cdBackground;
                    final View foregroundView = ((Adapter.Holder) viewHolder).cdSwipe;

                    // TODO: should animate out instead. how?
                    backgroundView.setRight(0);


                    getDefaultUIUtil().clearView(foregroundView);
                }

                private void drawBackground(RecyclerView.ViewHolder viewHolder, float dX, int actionState) {
                    final View backgroundView = ((Adapter.Holder) viewHolder).cdBackground;

                    if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
                        backgroundView.setRight((int) Math.max(dX, 0));

                        /*if (dX > 0) backgroundView.setRight((int) dX);
                        else backgroundView.setRight(backgroundView.getWidth() - (int) dX);*/
                    }
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
            public final MaterialCardView cdBackground;
            public final MaterialCardView cdSwipe;
            final TextView tvText;

            public Holder(@NonNull View itemView) {
                super(itemView);

                tvText = itemView.findViewById(R.id.tvName);
                cdBackground = itemView.findViewById(R.id.cdBackground);
                cdSwipe = itemView.findViewById(R.id.cdSwipe);
            }
        }
    }
}
