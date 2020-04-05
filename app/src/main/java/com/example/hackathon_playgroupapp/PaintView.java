package com.example.hackathon_playgroupapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PaintView extends View {

    String playerName = "";
    String roomName = "";
    String role = "";

    FirebaseDatabase database;
    DatabaseReference pathRef1;
    DatabaseReference pathRef2;

    public LayoutParams params;
    private Path pathLocal;
    private Path pathExternal;
    private Paint brush = new Paint();
    private int cnt = 0;

    public PaintView(Context context, String roomName) {
        super(context);

        this.roomName = roomName;

        database = FirebaseDatabase.getInstance();

        if (pathLocal == null){
            pathLocal = new Path();
        }
        if (pathExternal == null){
            pathExternal = new Path();
        }

        //Initialize the path ref
        pathRef1 = database.getReference("rooms/" + roomName + "/player1/path" + cnt+1);

        pathRef2 = database.getReference("rooms/" + roomName + "/player2/path" + cnt+1);


        brush.setAntiAlias(true);
        brush.setColor(Color.MAGENTA);
        brush.setStyle(Paint.Style.STROKE);
        brush.setStrokeJoin(Paint.Join.ROUND);
        brush.setStrokeWidth(8f);


        params = new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);


    }

    public boolean onTouchEvent (MotionEvent event) {
        float pointX = event.getX();
        float pointY = event.getY();

        SharedPreferences preferences = getContext().getSharedPreferences("PREFS", 0);
        playerName = preferences.getString("playerName","");

        if(roomName.equals(playerName)){
            role = "player1";
        } else {
            role = "player2";
        }


        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN :
                pathLocal.moveTo(pointX,pointY);
                return true;

            case MotionEvent.ACTION_MOVE :
                pathLocal.lineTo(pointX,pointY);
                break;
            default :
                return false;
        }


        postInvalidate();
        return false;

    }


    protected void onDraw(Canvas canvas){


            //Send the path and draw locally
            canvas.drawPath(pathLocal,brush);


           /*
            if (role.equals("player1")) {

            pathRef1.setValue(pathLocal);
            } else {

            pathRef2.setValue(pathLocal);
            }

                //Listen the path  and draw the other path
                if (role.equals("player1")) {
                    addRoomEventListener(role);
                    canvas.drawPath(pathExternal,brush);
                } else {
                    addRoomEventListener(role);
                    canvas.drawPath(pathExternal,brush);
                }


*/

    }

    /*
    private void addRoomEventListener(String role){

        if (role.equals("player1")){
            pathRef2.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        pathExternal = dataSnapshot.getValue(Path.class);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {



                }
            });
        } else {
            pathRef1.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    pathExternal = dataSnapshot.getValue(Path.class);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {



                }
            });
        }
        }
*/




}
