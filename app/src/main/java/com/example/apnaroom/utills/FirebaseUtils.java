package com.example.apnaroom.utills;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseUtils {

    public static Void deleteFromFirebase(String uId, int ItemId, String key, OnCompleteListener<Void> listener) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Favourite")
                .child(uId)
                .child(key)
                .child(String.valueOf(ItemId));
        databaseReference.removeValue().addOnCompleteListener(listener);
        return null;
    }
}
