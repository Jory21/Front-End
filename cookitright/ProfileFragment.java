package com.example.cookitright.fragments;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.cookitright.MainActivity;
import com.example.cookitright.databinding.FragmentProfileBinding;
import com.example.cookitright.utils.Constant;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;



public class ProfileFragment extends Fragment {

    private FirebaseFirestore db;
    private DocumentReference profileRef;
    private FirebaseAuth myChef;
    private MainActivity activity;
    private FragmentProfileBinding mBinding;
    public ProfileFragment() {

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = FragmentProfileBinding.inflate(inflater);
        inIt();
        return mBinding.getRoot();
    }

    private void inIt() {

        myChef = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        profileRef = db.collection("Users").document(myChef.getCurrentUser().getUid());

        mBinding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).popBackStack();
            }
        });

        fetchProfileData();

    }


    private void fetchProfileData() {

        Dialog dialog = Constant.getDialog(requireContext());
        dialog.show();

        profileRef.get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        dialog.dismiss();
                        if (documentSnapshot.exists()) {

                            String name = documentSnapshot.getString("name");
                            String email = documentSnapshot.getString("email");

                            mBinding.tvUserName.setText(name);
                            mBinding.tvUserEmail.setText(email);

                        } else {
                            Toast.makeText(activity, "Data not exist", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        dialog.dismiss();
                        Toast.makeText(activity, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activity = (MainActivity) context;
    }

}