package it.ferrarally.provacarte;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Canvas;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

            final List<City> cities = new ArrayList<>();
            cities.add(new City("Patrica", R.drawable.patrica));
            cities.add(new City("Campoli Appennino", R.drawable.campoli));
            cities.add(new City("Vetralla", R.drawable.vetralla));

            final Adapter adapter = new Adapter(cities);
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
                        int position = viewHolder.getAdapterPosition();
                        City city = cities.get(position);

                        if(city.favorite){
                            Toast.makeText(SwipeCard.this, String.format("%s removed from favorites", city.name), Toast.LENGTH_SHORT).show();
                            cities.get(position).favorite = false;
                        } else {
                            Toast.makeText(SwipeCard.this, String.format("%s added to favorites", city.name), Toast.LENGTH_SHORT).show();
                            cities.get(position).favorite = true;
                        }

                    }

                    //Makes the item reappear after swipe
                    adapter.notifyItemChanged(viewHolder.getAdapterPosition());
                }

                @Override
                public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
                    if (viewHolder != null){
                        final View foregroundView = ((Adapter.CityHolder) viewHolder).cdSwipe;

                        getDefaultUIUtil().onSelected(foregroundView);
                    }
                }

                @Override
                public void onChildDraw(Canvas c, RecyclerView recyclerView,
                                        RecyclerView.ViewHolder viewHolder, float dX, float dY,
                                        int actionState, boolean isCurrentlyActive) {
                    final View foregroundView = ((Adapter.CityHolder) viewHolder).cdSwipe;

                    drawBackground(viewHolder, dX, actionState);

                    getDefaultUIUtil().onDraw(c, recyclerView, foregroundView, dX, dY,
                            actionState, isCurrentlyActive);
                }

                @Override
                public void onChildDrawOver(Canvas c, RecyclerView recyclerView,
                                            RecyclerView.ViewHolder viewHolder, float dX, float dY,
                                            int actionState, boolean isCurrentlyActive) {
                    final View foregroundView = ((Adapter.CityHolder) viewHolder).cdSwipe;

                    drawBackground(viewHolder, dX, actionState);

                    getDefaultUIUtil().onDrawOver(c, recyclerView, foregroundView, dX, dY,
                            actionState, isCurrentlyActive);
                }

                @Override
                public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder){
                    final View backgroundView = ((Adapter.CityHolder) viewHolder).cdBackground;
                    final View foregroundView = ((Adapter.CityHolder) viewHolder).cdSwipe;

                    // TODO: should animate out instead. how?
                    backgroundView.setRight(0);


                    getDefaultUIUtil().clearView(foregroundView);
                }

                private void drawBackground(RecyclerView.ViewHolder viewHolder, float dX, int actionState) {
                    final View backgroundView = ((Adapter.CityHolder) viewHolder).cdBackground;

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
        private List<City> cityList;

        Adapter(List<City> cities){
            this.cityList = cities;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            ConstraintLayout cl;
            //Inflate row of recycle view
            cl = (ConstraintLayout) LayoutInflater
                    .from(parent.getContext())
                    .inflate(R.layout.item_swipe_card, parent, false);

            return new CityHolder(cl);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            ((CityHolder) holder).tvText.setText(cityList.get(position).name);
            ((CityHolder) holder).ivCity.setImageResource(cityList.get(position).imageId);

            if(cityList.get(position).favorite){
                ((CityHolder) holder).ivFavorite.setVisibility(View.VISIBLE);
            } else {
                ((CityHolder) holder).ivFavorite.setVisibility(View.GONE);
            }
        }

        @Override
        public int getItemCount() {
            return cityList.size();
        }

        class CityHolder extends RecyclerView.ViewHolder{
            public final MaterialCardView cdBackground;
            public final MaterialCardView cdSwipe;
            public ImageView ivFavorite;
            public final ImageView ivCity;
            final TextView tvText;

            public CityHolder(@NonNull View itemView) {
                super(itemView);

                tvText = itemView.findViewById(R.id.tvName);
                ivFavorite = itemView.findViewById(R.id.ivFavorite);
                ivCity = itemView.findViewById(R.id.ivCity);
                cdBackground = itemView.findViewById(R.id.cdBackground);
                cdSwipe = itemView.findViewById(R.id.cdSwipe);
            }
        }
    }

    class City {
        public String name;
        public boolean favorite = false;
        public int imageId;

        public City(String name, int imageId) {
            this.name = name;
            this.imageId = imageId;
        }
    }
}
