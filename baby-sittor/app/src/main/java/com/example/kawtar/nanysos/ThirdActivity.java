package com.example.kawtar.nanysos;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;

public class ThirdActivity extends AppCompatActivity {
    public String nom;
    public String mdp;
    private Button send;
    private Firebase conn,conn1;
    private EditText name;
    private EditText password;
    boolean b=false;
    private ArrayList<String> musernames=new ArrayList<>();
    Context context;
    double latitude, longitude;
    private FusedLocationProviderClient mFusedLocationClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION,android.Manifest.permission.ACCESS_COARSE_LOCATION},
                    123);
        }
        mFusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {

                            longitude=location.getLongitude();
                            latitude=location.getLatitude();
                        }
                    }
                });

    }

    public void adddata(View v){
        Firebase.setAndroidContext(this);
        conn=new Firebase("https://mikla-35939.firebaseio.com/Users");
        name=(EditText) findViewById(R.id.textView);
        password=(EditText) findViewById(R.id.textView2);
        conn.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot noteSnapshot : dataSnapshot.getChildren()) {
                     String key=noteSnapshot.getKey();

                    for (DataSnapshot noteSna : noteSnapshot.getChildren()) {
                        String id = noteSna.getValue(String.class);

                        musernames.add(id);



                    } }

                 senddata(musernames);


            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }

        });

    }

        public void senddata(ArrayList<String> aray){
            for(String s:aray){
                if (name.getText().toString().equals(s)){
                    Log.e("name",name.getText().toString());
                    b=true;


                }
                else{}
            }
            if(b==false){
                String longit=String.valueOf(longitude);
                String latit=String.valueOf(latitude);
                conn1=new Firebase("https://babysittingapp-e382d.firebaseio.com/Users");
                Log.e("bdeif","false");
                String userId = conn1.push().getKey();
                String nom=name.getText().toString();
                String pass=password.getText().toString();
                Firebase attribut1 =conn1.child(userId).child("Name");
                Firebase attribut2=conn1.child(userId).child("Password");
                Firebase attribut3=conn1.child(userId).child("Type");
                Firebase attribut4=conn1.child(userId).child("Attitude");
                Firebase attribut5=conn1.child(userId).child("Langitude");

                attribut1.setValue(nom);
                attribut2.setValue(pass);
                attribut3.setValue("Parents");
                attribut4.setValue(longit);
                attribut5.setValue(latit);

                Log.e("fin","still false");
                Intent i = new Intent(getApplicationContext(), FourthActivity.class);
                i.putExtra("key",userId);
                startActivity(i);
               // startActivity(new Intent(ThirdActivity.this,FourthActivity.class));
            }
            else{



            }


        }
}
