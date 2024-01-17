package com.example.cookitright.fragments;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.example.cookitright.R;
import com.example.cookitright.databinding.FragmentWelcomeBinding;
import com.example.cookitright.utils.CustomMediaController;


public class WelcomeFragment extends Fragment {

    private MediaController mediaController;
    private FragmentWelcomeBinding mBinding;
    public WelcomeFragment() {

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = FragmentWelcomeBinding.inflate(inflater);
        inIt();
        return mBinding.getRoot();
    }
    private void inIt() {
        playVideo();
        mBinding.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavDirections action =
                        WelcomeFragmentDirections.actionWelcomeFragmentToMainFragment();
                Navigation.findNavController(view).navigate(action);
            }});
    }
    private void playVideo() {
        String videoPath = "android.resource://" + requireActivity().getPackageName() + "/" + R.raw.book;
        Uri videoUri = Uri.parse(videoPath);
        mBinding.videoView.setVideoURI(videoUri);

        CustomMediaController mediaController = new CustomMediaController(requireContext());
        mBinding.videoView.setMediaController(mediaController);
        mediaController.setAnchorView(mBinding.videoView);

        mBinding.videoView.start();

        mBinding.videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                mBinding.videoView.start();
            }
        });

    }


}