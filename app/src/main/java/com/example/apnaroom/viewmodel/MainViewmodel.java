package com.example.apnaroom.viewmodel;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.apnaroom.Domains.BannerModel;
import com.example.apnaroom.Domains.CategoryModel;
import com.example.apnaroom.Domains.ItemsModel;
import com.example.apnaroom.Domains.Users;
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

   public LiveData<ArrayList<ItemsModel>> loadNearByData(){
        return repository.loadNearByData();
   }


   public LiveData<ArrayList<ItemsModel>> loadPopularData(){
        return repository.loadPopularData();
   }

   public LiveData<ArrayList<ItemsModel>> loadRecommendedData(){
        return repository.loadRecommendedData();
   }

   public LiveData<ArrayList<ItemsModel>> loadItemsByCategory(String categoryName){
        return repository.loadItemsByCategory(categoryName);
   }

   public LiveData<ArrayList<ItemsModel>> loadFavData(){
        return repository.loadFavData();
   }

    public LiveData<ArrayList<Users>> loadUserDetails(){
            return repository.loadUserDetails();
    }

    public LiveData<ArrayList<ItemsModel>> loadBookingDetails(){
            return repository.loadBookingDetails();
    }

}
