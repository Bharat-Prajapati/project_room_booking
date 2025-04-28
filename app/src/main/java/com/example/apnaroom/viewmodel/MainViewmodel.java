package com.example.apnaroom.viewmodel;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.apnaroom.Domains.BannerModel;
import com.example.apnaroom.Domains.CategoryModel;
import com.example.apnaroom.Domains.PopularModel;
import com.example.apnaroom.Domains.RecommendedModel;
import com.example.apnaroom.repository.MainRepository;

import java.util.ArrayList;

public class MainViewmodel extends ViewModel{
    private final MainRepository repository;

    public MainViewmodel(Context context) {
        repository = new MainRepository(context);
    }

    public LiveData<ArrayList<BannerModel>> loadBannerData(){
        return repository.loadBannerData();
    }

   public LiveData<ArrayList<CategoryModel>> loadCategoryData(){
        return repository.loadCategoryData();
   }

   public LiveData<ArrayList<PopularModel>> loadPopularData(){
        return repository.loadPopularData();
   }

   public LiveData<ArrayList<RecommendedModel>> loadRecommendedData(){
        return repository.loadRecommendedData();
   }

}
