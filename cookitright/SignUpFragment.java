package com.example.cookitright.fragments;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.cookitright.databinding.FragmentSignUpBinding;
import com.example.cookitright.datamodels.Users;
import com.example.cookitright.utils.Constant;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;


public class SignUpFragment extends Fragment {
    private FirebaseAuth myChef;
    private FirebaseFirestore fireStoreDb;
    private FragmentSignUpBinding mBinding;
    private Dialog dialog;

    public SignUpFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = FragmentSignUpBinding.inflate(inflater);
        inIt();
        return mBinding.getRoot();
    }
    private void inIt() {
        //Firebase Auth initialization
        myChef = FirebaseAuth.getInstance();
        fireStoreDb = FirebaseFirestore.getInstance();
        dialog = Constant.getDialog(requireContext());
        mBinding.btnSignUp.setOnClickListener(this::checkCredentials);
    }

    private void checkCredentials(View view) {

        String userName = mBinding.etUsername.getText().toString().trim();
        String userEmail = mBinding.etEmail.getText().toString().trim();
        String userPassword = mBinding.etPassword.getText().toString().trim();
        if (userName.isEmpty()) {
            mBinding.etUsername.setError("Username is required");
            mBinding.etUsername.requestFocus();
            return;
        }
        if (userEmail.isEmpty()) {
            mBinding.etEmail.setError("Email is required");
            mBinding.etEmail.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(userEmail).matches()) {
            mBinding.etEmail.setError("ivalid email!");
            mBinding.etEmail.requestFocus();
            return;
        }

        if (userPassword.isEmpty()) {
            mBinding.etPassword.setError("Password is empty!");
            mBinding.etPassword.requestFocus();
        } else {
            signUpUser(view, userName, userEmail, userPassword);
        }
    }

    private void signUpUser(View view, String userName, String userEmail, String userPassword) {
        dialog.show();
        myChef.createUserWithEmailAndPassword(userEmail, userPassword).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                saveUserDataToFireStore(view, userName, userEmail, userPassword);
            } else {
                dialog.dismiss();
                Toast.makeText(requireContext(), "Authentication Failed", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                dialog.dismiss();
                Toast.makeText(requireContext(), e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }});}
    private void saveUserDataToFireStore(View view, String userName, String userEmail, String userPassword) {
        Users user = new Users(userName, userEmail, userPassword);
        fireStoreDb.collection("Users").document(myChef.getCurrentUser().getUid()).set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                dialog.dismiss();
                NavDirections action =
                        SignUpFragmentDirections.
                                actionSignUpFragmentToWelcomeFragment();
                Navigation.findNavController(view).navigate(action);
            }
        }).addOnFailureListener(e -> {
            dialog.dismiss();
            Toast.makeText(requireContext(), e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
        }); }}