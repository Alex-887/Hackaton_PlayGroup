package com.example.hackathon_playgroupapp.ui.register;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.hackathon_playgroupapp.R;
import com.example.hackathon_playgroupapp.ui.login.LoginFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterFragment extends Fragment {

    private RegisterViewModel mViewModel;

    public static RegisterFragment newInstance() {
        return new RegisterFragment();
    }
    FirebaseAuth fAuth;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.register_fragment, container, false);


        fAuth = FirebaseAuth.getInstance();

        if (container != null) {
            container.removeAllViews();
        }




        return root;



    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(RegisterViewModel.class);
        // TODO: Use the ViewModel


        //Get the text of the EditText
//        EditText username = getView().findViewById(R.id.username);
//        EditText phoneNumber = getView().findViewById(R.id.phonenumber);



    }





}
