package com.example.cookitright.fragments;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.bumptech.glide.Glide;
import com.example.cookitright.MainActivity;
import com.example.cookitright.R;
import com.example.cookitright.databinding.FragmentCookDetailBinding;
import com.example.cookitright.datamodels.AverageDaysData;
import com.example.cookitright.datamodels.CookRating;
import com.example.cookitright.utils.Constant;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.Objects;



public class CookDetailFragment extends Fragment {
    private MainActivity activity;
    private FirebaseAuth mAuth;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private int totalRatings = 0;
    private int sumRatings = 0;
    private int totalDays = 0;
    private int sumDays = 0;
    private boolean isRated = true;
    private boolean isDays = true;
    private FragmentCookDetailBinding mBinding;
    public CookDetailFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = FragmentCookDetailBinding.inflate(inflater);
        inIt();
        return mBinding.getRoot();
    }
    private void inIt() {
        mAuth = FirebaseAuth.getInstance();
        setUpData();
        getRatingFromFirebase();
        mBinding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).popBackStack();
            }
        });


        mBinding.ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                if (isRated){
                    giveRating(rating);
                }
            }
        });

    }


    private void saveTimeRequired(String days) {
        Dialog dialog = Constant.getDialog(requireContext());
        dialog.show();
        String userId = mAuth.getCurrentUser().getUid(); // Replace with the actual user ID
        String cookId = Constant.allCookData.getCookId(); // Replace with the actual cook ID
        AverageDaysData cookRating = new AverageDaysData(userId, cookId, days);
        db.collection("days")
                .add(cookRating)
                .addOnSuccessListener(documentReference -> {
                    dialog.dismiss();
                    isDays = false;
                    Toast.makeText(activity, "Days Added", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    dialog.dismiss();
                    isDays = true;
                    Toast.makeText(activity, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                });
    }
    @SuppressLint("SetTextI18n")
    private void getRatingFromFirebase() {
        Dialog dialog = Constant.getDialog(requireContext());
        dialog.show();
        db.collection("ratings")
                .whereEqualTo("cookId", Constant.allCookData.getCookId())
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        dialog.dismiss();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            CookRating cookRating = document.toObject(CookRating.class);
                            int rating = cookRating.getRating();
                            if (Objects.equals(cookRating.getCookId(), Constant.allCookData.getCookId()) && Objects.equals(cookRating.getUserId(), mAuth.getCurrentUser().getUid())){
                                isRated = false;
                                mBinding.ratingBar.setIsIndicator(true);
                            }
                            sumRatings += rating;
                            totalRatings++;
                        }
                        mBinding.tvAllRating.setText(totalRatings + "");
                        if (totalRatings > 0) {
                            double averageRating = (double) sumRatings / totalRatings;
                            mBinding.ratingBar.setRating((float) averageRating);
                        } else {
                            dialog.dismiss();
                        }
                    } else {
                        dialog.dismiss();
                        Toast.makeText(activity, "Something went wrong", Toast.LENGTH_SHORT).show();
                    }
                });
    }


    private void giveRating(float rating) {
        Dialog dialog = Constant.getDialog(requireContext());
        dialog.show();
        String userId = mAuth.getCurrentUser().getUid();
        String cookId = Constant.allCookData.getCookId();
        CookRating cookRating = new CookRating(userId, cookId, (int) rating);
        db.collection("ratings")
                .add(cookRating)
                .addOnSuccessListener(documentReference -> {
                    // Rating added successfully
                    dialog.dismiss();
                    isRated = false;
                    Toast.makeText(activity, "Rating Added", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    dialog.dismiss();
                    isRated = true;
                    Toast.makeText(activity, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                });
    }
    private void setUpData() {
        if (Constant.allCookData != null){
            Glide.with(activity)
                    .load(Constant.allCookData.getCookCoverImage())
                    .placeholder(R.drawable.placeholder_)
                    .into(mBinding.ivCover);
            mBinding.tvTitle.setText(Constant.allCookData.getCookName());
            mBinding.tvAuthor.setText(Constant.allCookData.getChef());
            mBinding.tvGenre.setText(Constant.allCookData.getCookCategory());
            mBinding.tvDescription.setText(Constant.allCookData.getCookRecipe());
        }
    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activity = (MainActivity) context;
    }
}