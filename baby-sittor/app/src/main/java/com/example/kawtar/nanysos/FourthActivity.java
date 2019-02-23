package com.example.kawtar.nanysos;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;

public class FourthActivity extends AppCompatActivity {
    private Firebase mRef;
    private ArrayList<String> musernames=new ArrayList<>();
    private TextView mytxt;
    String id;
   String longitude;
    String latitude;
  protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fourth);
      Firebase.setAndroidContext(this);
        Intent intent = getIntent();
      id=intent.getStringExtra("key");
     Log.e("testest",id);
      mytxt=(TextView) findViewById(R.id.textView4);
        mRef=new Firebase("https://babysittingapp-e382d.firebaseio.com/Users");
        mRef.child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot noteSnapshot: dataSnapshot.getChildren()){
                    String user=noteSnapshot.getKey();
                    String valeur=noteSnapshot.getValue(String.class);
                    if (user.equals("Name")){
                        Log.e("valeur",valeur);
                        mytxt.setText(valeur);

                    }
                    else if(user.equals("Attitude")){latitude=valeur;}
                    else if(user.equals("Langitude")){longitude=valeur;}

                    //String value=dataSnapshot.getValue(String.class);

                   }

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }
    public void updateprofil(View v){
        Intent i = new Intent(getApplicationContext(), Updateprofilmami.class);
        i.putExtra("key",id);
        startActivity(i);
    }
    public void showmap(View v){
        Intent i = new Intent(getApplicationContext(), MapsActivity.class);
        i.putExtra("long",longitude);
        i.putExtra("lat",latitude);
        startActivity(i);
    }
}