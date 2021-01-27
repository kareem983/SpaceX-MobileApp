package com.example.spacex.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import com.example.spacex.R;
import com.example.spacex.adapter.ShipsAdapter;
import com.example.spacex.model.ShipsModel;
import java.util.List;

public class ShipsActivity extends AppCompatActivity {

    private RecyclerView ShipsRecyclerView;
    private ProgressBar ShipsProgress;
    private ShipsAdapter adapter;
    private ShipsViewModel shipsViewModel;
    private ShipsAdapter.RecyclerViewClickListener listener;
    private List<ShipsModel> listOfShips;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ships);

        ShipsRecyclerView = findViewById(R.id.shipsRecycler);
        ShipsProgress = findViewById(R.id.ShipsProgress);
        ShipsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        shipsViewModel = ViewModelProviders.of(this).get(ShipsViewModel.class);
        shipsViewModel.getShips();
        shipsViewModel.ShipsMutableLiveData.observe(this, new Observer<List<ShipsModel>>() {
            @Override
            public void onChanged(List<ShipsModel> shipsModels) {
                listOfShips = shipsModels;
                adapter = new ShipsAdapter(ShipsActivity.this,shipsModels, listener);
                ShipsRecyclerView.setAdapter(adapter);

                ShipsRecyclerView.setVisibility(View.VISIBLE);
                ShipsProgress.setVisibility(View.GONE);
            }
        });

        listener = new ShipsAdapter.RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {
                ShipsModel shipsModel = listOfShips.get(position);
                Intent intent = new Intent(ShipsActivity.this, ShipsInfoActivity.class);
                intent.putExtra("Ships Object", shipsModel);
                startActivity(intent);
            }
        };
    }
}