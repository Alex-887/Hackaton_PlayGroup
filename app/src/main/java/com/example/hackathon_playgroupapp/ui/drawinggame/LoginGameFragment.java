package com.example.hackathon_playgroupapp.ui.drawinggame;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.hackathon_playgroupapp.R;
import com.example.hackathon_playgroupapp.ui.register.RegisterFragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginGameFragment extends Fragment {


    private EditText editText;
    private Button button;

    String playerName = "";

    FirebaseDatabase database;
    DatabaseReference playerRef;

    FragmentTransaction transaction;

    public static LoginGameFragment newInstance(String param1, String param2) {
        LoginGameFragment fragment = new LoginGameFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_login_game_, container, false);

                    editText = root.findViewById(R.id.username_drawinggame);
                    button = root.findViewById(R.id.play);

                    database = FirebaseDatabase.getInstance();

                    button.setOnClickListener(new View.OnClickListener(){
                        @Override
                        public void onClick(View v) {
                            playerName = editText.getText().toString();
                            editText.setText("");
                            if(!playerName.equals("")){
                                button.setEnabled(false);
                                playerRef = database.getReference("players/" + playerName);
                                addEventListener();
                                playerRef.setValue("");
                            }


                        }
        });


        // Inflate the layout for this fragment
        return root;
    }

    public void addEventListener(){

        playerRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(!playerName.equals("")){

                    SharedPreferences preferences = getActivity().getSharedPreferences("PREFS", 0);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("playerName", playerName);
                    editor.apply();


                    transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.login_fragment, new JoinCreateRoomFragment()).commit();
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                button.setEnabled(true);
                Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
            }
        });

    }

}
