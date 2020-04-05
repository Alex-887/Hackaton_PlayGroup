package com.example.hackathon_playgroupapp.ui.home;

import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.hackathon_playgroupapp.PaintActivity;
import com.example.hackathon_playgroupapp.R;
import com.example.hackathon_playgroupapp.ui.drawinggame.LoginGameFragment;
import com.example.hackathon_playgroupapp.ui.register.RegisterFragment;

public class HomeFragment extends Fragment {

    private HomeViewModel mViewModel;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.home_fragment, container, false);
        ImageButton button = root.findViewById(R.id.imageButton3);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentTransaction transaction;
                transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.home_fragment, new LoginGameFragment()).commit();

            }
        });


        if (container != null) {
            container.removeAllViews();
        }


        setHasOptionsMenu(true);


        return root;
    }



    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        // TODO: Use the ViewModel
    }

}
