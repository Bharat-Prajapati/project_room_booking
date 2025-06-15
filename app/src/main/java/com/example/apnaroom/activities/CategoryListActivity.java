package com.example.apnaroom.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.SearchView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.apnaroom.Domains.ItemsModel;
import com.example.apnaroom.R;
import com.example.apnaroom.adapters.SelectedCategoryAdapter;
import com.example.apnaroom.databinding.ActivityCategoryListBinding;
import com.example.apnaroom.viewmodel.MainViewmodel;

import java.util.ArrayList;
import java.util.List;

public class CategoryListActivity extends AppCompatActivity {
    ActivityCategoryListBinding binding;
    SelectedCategoryAdapter adapter;
    MainViewmodel viewmodel = new MainViewmodel(this);
    List<ItemsModel> fullItemList = new ArrayList<>();
    private String title = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityCategoryListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        bundle();
        initList();
        setUpSearching();
        binding.listBackBtn.setOnClickListener(v->{
            finish();
        });
    }

    private void setUpSearching() {
        binding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterList(newText);
                return false;
            }

            private void filterList(String newText) {
                ArrayList<ItemsModel> filteredList = new ArrayList<>();
                for (ItemsModel item: fullItemList){
                    if (item.getName().toLowerCase().contains(newText.toLowerCase())){
                        filteredList.add(item);
                    }
                }
                adapter.updateList(filteredList);
            }
        });
    }

    private void initList() {
        binding.listProgressbar.setVisibility(View.VISIBLE);
        binding.rvCatSelectedList.setLayoutManager(new LinearLayoutManager(this));
        viewmodel.loadItemsByCategory(title).observe(this, itemList->{
            binding.listProgressbar.setVisibility(View.GONE);
            fullItemList = itemList;
            adapter = new SelectedCategoryAdapter(this, itemList);
            binding.rvCatSelectedList.setAdapter(adapter);
        });
    }

    private void bundle() {
//        id = Integer.parseInt(getIntent().getStringExtra("id"));
        title = getIntent().getStringExtra("title");

        binding.listTitle.setText(title);
    }
}