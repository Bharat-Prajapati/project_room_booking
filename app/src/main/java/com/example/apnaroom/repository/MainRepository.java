package com.example.apnaroom.repository;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.apnaroom.Domains.BannerModel;
import com.example.apnaroom.Domains.CategoryModel;
import com.example.apnaroom.Domains.PopularModel;
import com.example.apnaroom.Domains.RecommendedModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainRepository {
    private Context context;
    FirebaseDatabase database = FirebaseDatabase.getInstance();

    public MainRepository(Context context) {
        this.context = context;
    }

    public LiveData<ArrayList<BannerModel>> loadBannerData(){
        MutableLiveData<ArrayList<BannerModel>> listData = new MutableLiveData<>();
        DatabaseReference reference = database.getReference("Banner");

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<BannerModel> list = new ArrayList<>();
                for (DataSnapshot childrenSnapshot: snapshot.getChildren()){
                    BannerModel item = childrenSnapshot.getValue(BannerModel.class);
                    if (item != null){
                        list.add(item);
                    }
                }
                listData.setValue(list);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(context, "Data is not loaded", Toast.LENGTH_SHORT).show();
            }
        });
        return listData;
    }

    public LiveData<ArrayList<CategoryModel>> loadCategoryData(){
        MutableLiveData<ArrayList<CategoryModel>> listData = new MutableLiveData<>();
        DatabaseReference reference = database.getReference("Category");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<CategoryModel> list = new ArrayList<>();
                for (DataSnapshot childrenSnapshot: snapshot.getChildren()){
                    CategoryModel item = childrenSnapshot.getValue(CategoryModel.class);
                    if (item != null){
                        list.add(item);
                    }
                }
                listData.setValue(list);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(context, "Data is not loaded", Toast.LENGTH_SHORT).show();
            }
        });

        return listData;
    }

    public LiveData<ArrayList<PopularModel>> loadPopularData(){
        MutableLiveData<ArrayList<PopularModel>> listData = new MutableLiveData<>();
        DatabaseReference reference = database.getReference("Popular");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<PopularModel> list = new ArrayList<>();
                for (DataSnapshot childrenSnapshot: snapshot.getChildren()){
                    PopularModel item = childrenSnapshot.getValue(PopularModel.class);
                    if (item != null){
                        list.add(item);
                    }
                }
                listData.setValue(list);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(context, "Data is not loaded", Toast.LENGTH_SHORT).show();
            }
        });

        return listData;
    }

    public LiveData<ArrayList<RecommendedModel>> loadRecommendedData(){
        MutableLiveData<ArrayList<RecommendedModel>> listData = new MutableLiveData<>();
        DatabaseReference reference = database.getReference("Recommended");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<RecommendedModel> list = new ArrayList<>();
                for (DataSnapshot childrenSnapshot: snapshot.getChildren()){
                    RecommendedModel item = childrenSnapshot.getValue(RecommendedModel.class);
                    if (item != null){
                        list.add(item);
                    }
                }
                listData.setValue(list);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(context, "Data is not loaded", Toast.LENGTH_SHORT).show();
            }
        });

        return listData;
    }
}
