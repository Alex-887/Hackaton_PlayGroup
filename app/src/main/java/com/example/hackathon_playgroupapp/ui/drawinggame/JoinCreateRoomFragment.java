package com.example.hackathon_playgroupapp.ui.drawinggame;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.hackathon_playgroupapp.PaintActivity;
import com.example.hackathon_playgroupapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class JoinCreateRoomFragment extends Fragment {

    Button join;
    Button create;
    EditText code;

    String playerName = "";
    String roomName = "";

    FirebaseDatabase database;
    DatabaseReference roomRef;


    public static JoinCreateRoomFragment newInstance(String param1, String param2) {
        JoinCreateRoomFragment fragment = new JoinCreateRoomFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_join_create_room, container, false);

        database = FirebaseDatabase.getInstance();

        //Get the player name and assign the player name to the room
        SharedPreferences preferences = getActivity().getSharedPreferences("PREFS", 0);
        playerName = preferences.getString("playerName", "");


        join = root.findViewById(R.id.join);
        create = root.findViewById(R.id.create);
        code = root.findViewById(R.id.edit_tokken);


        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Create room and add the first player as player 1



                roomName = playerName;
                roomRef = database.getReference("rooms/" + roomName + "/player1");
                addRoomEventListener();
                roomRef.setValue(playerName);
            }
        });

        join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                roomName = code.getText().toString();
                roomRef = database.getReference("rooms/" + roomName + "/player2");
                addRoomEventListener();
                roomRef.setValue(playerName);
            }
        });

        return root;
    }

    private void addRoomEventListener(){

        roomRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                create.setEnabled(true);
                Intent intent = new Intent(getActivity().getApplicationContext(), PaintActivity.class);
                intent.putExtra("roomName", roomName);
                startActivity(intent);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                create.setEnabled(true);
                Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
            }
        });

    }


}
