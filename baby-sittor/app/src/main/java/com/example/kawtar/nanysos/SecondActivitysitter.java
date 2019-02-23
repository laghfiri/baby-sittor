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
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

public class SecondActivitysitter extends AppCompatActivity {
    public String nom,mdp,prenom,tel,mail;
    private Firebase conn;
    private EditText name,password,pren,Tel,Email;
    Button sitt;
    Context context;
    double latite, longite;
    String keyn,b;
    private FusedLocationProviderClient mFusedLocationClient;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_sitter);
        Firebase.setAndroidContext(this);
        conn=new Firebase("https://babysittingapp-e382d.firebaseio.com/Users");
        name=(EditText) findViewById(R.id.textView);
        password=(EditText) findViewById(R.id.textView2);
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

                            longite=location.getLongitude();
                            latite=location.getLatitude();
                        }
                    }
                });


    }
    public void gotoTecondActivity(View v){

        startActivity(new Intent(SecondActivitysitter.this,ThirdActivitysitter.class));

    }
    public void userexist(View v){

        String s=name.getText().toString();
        conn.orderByChild("Name").equalTo(s).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot childSnapshot: dataSnapshot.getChildren()) {
                    keyn =childSnapshot.getKey();
                    b=keyn.toString();
                    updategps(b);
                    Log.e("keyn",keyn);
                    if(!(b.equals(""))){
                        Intent i = new Intent(getApplicationContext(), FourthActivity.class);
                        i.putExtra("key",keyn);
                        startActivity(i);}
                    else {
                        Toast.makeText(SecondActivitysitter.this, "ce compte n'existe pas ", Toast.LENGTH_SHORT).show();
                    }
                }}

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });



    }

    public void updategps(String a){
        String latitstring=String.valueOf(latite);
        String longitstring=String.valueOf(longite);
        Firebase attribut1 =conn.child(a).child("Attitude");
        Firebase attribut2=conn.child(a).child("Langitude");
        attribut1.setValue(longitstring);
        attribut2.setValue(latitstring);

    }

}