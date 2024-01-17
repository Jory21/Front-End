package com.example.cookitright.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.cookitright.R;
import com.example.cookitright.databinding.FragmentAboutUsBinding;

public class AboutUsFragment extends Fragment {


    private FragmentAboutUsBinding mBinding;
    public AboutUsFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = FragmentAboutUsBinding.inflate(inflater);
        inIt();
        return mBinding.getRoot();
    }

    private void inIt() {
        mBinding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).popBackStack();
            }
        });
    }
}