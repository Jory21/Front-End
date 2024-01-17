package com.example.cookitright.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.cookitright.databinding.FragmentSplashBinding;
import com.google.firebase.auth.FirebaseAuth;


public class SplashFragment extends Fragment {
    private FirebaseAuth mAuth;
    private FragmentSplashBinding mBinding;
    public SplashFragment() {

    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = FragmentSplashBinding.inflate(inflater);
        inIt();
        return mBinding.getRoot();
    }
    private void inIt() {
        mAuth = FirebaseAuth.getInstance();
        new Handler().postDelayed(() -> {
            if (mAuth.getCurrentUser() != null){
                NavDirections action =
                        SplashFragmentDirections.actionSplashFragmentToWelcomeFragment();
                Navigation.findNavController(mBinding.getRoot()).navigate(action);
            }else {
                NavDirections action =
                        SplashFragmentDirections.
                                actionSplashFragmentToSignInFragment();
                Navigation.findNavController(mBinding.getRoot()).navigate(action);
            }
        },1500);
    }
}