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

import com.example.cookitright.R;
import com.example.cookitright.databinding.FragmentSignInBinding;
import com.example.cookitright.utils.Constant;
import com.google.firebase.auth.FirebaseAuth;




public class SignInFragment extends Fragment {
    private FirebaseAuth myChef;
    private Dialog dialog;

    private FragmentSignInBinding mBinding;
    public SignInFragment() {

    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = FragmentSignInBinding.inflate(inflater);
        inIt();
        return mBinding.getRoot();
    }

    private void inIt() {
        myChef = FirebaseAuth.getInstance();
        dialog = Constant.getDialog(requireContext());
        mBinding.btnSignIn.setOnClickListener(this::checkCredentials);
        mBinding.tvSignIn.setOnClickListener(view -> {
            NavDirections action =
                    SignInFragmentDirections.
                            actionSignInFragmentToSignUpFragment();
            Navigation.findNavController(view).navigate(action);
        });}
    private void checkCredentials(View view) {
        String userEmail = mBinding.etEmail.getText().toString().trim();
        String userPassword = mBinding.etPassword.getText().toString().trim();
        if (userEmail.isEmpty()) {
            mBinding.etEmail.setError("Email is empty");
            mBinding.etEmail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(userEmail).matches()) {
            mBinding.etEmail.setError("invalid email");
            mBinding.etEmail.requestFocus();
            return;
        }
        if (userPassword.isEmpty()) {
            mBinding.etPassword.setError("Password is empty");
            mBinding.etPassword.requestFocus();
        } else {
            signInUser(view, userEmail, userPassword);
        }
    }

    private void signInUser(View view, String userEmail, String userPassword) {
        dialog.show();
        myChef.signInWithEmailAndPassword(userEmail, userPassword).addOnCompleteListener(task -> {
            if (task.isSuccessful()){
                dialog.dismiss();
                NavDirections action =
                        SignInFragmentDirections.
                                actionSignInFragmentToWelcomeFragment();
                Navigation.findNavController(view).navigate(action);
            }
        }).addOnFailureListener(e -> {
            dialog.dismiss();
            Toast.makeText(requireContext(), e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
        });
    }
}