package com.example.kawtar.nanysos;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,GoogleMap.OnMarkerClickListener {

    private Firebase mRef;
    String longi,latit;
    private GoogleMap mMap;
    double longd,latd;
    String lattisitter,longitsitter,namesitter;
    boolean bl=false;
    String iddusitter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        Intent intent = getIntent();
        longi=intent.getStringExtra("long");
        latit=intent.getStringExtra("lat");
        longd=Double.valueOf(longi);
        latd=Double.valueOf(latit);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mRef=new Firebase("https://babysittingapp-e382d.firebaseio.com/Users");

       LatLng sydney = new LatLng(longd, latd);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Moi").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));



     mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot idSnapshot: dataSnapshot.getChildren()){
                    for(DataSnapshot donSnapshot:idSnapshot.getChildren()){
                        iddusitter=idSnapshot.getKey();
                    String userdn=donSnapshot.getKey();
                    String valeur=donSnapshot.getValue(String.class);
                        if(userdn.equals("Attitude")){lattisitter=valeur;
                        Log.e("Attit",lattisitter);}
                        if(userdn.equals("Langitude")){longitsitter=valeur;
                        Log.e("Longit",longitsitter);}
                        if(userdn.equals("Name")){namesitter=valeur;
                        Log.e("nom",namesitter);}
                        if (valeur.equals("Babysitter")){
                            LatLng babysitter = new LatLng(Double.valueOf(longitsitter), Double.valueOf(lattisitter));
                            mMap.addMarker(new MarkerOptions().position(babysitter).title(namesitter));
                            mMap.moveCamera(CameraUpdateFactory.newLatLng(babysitter));



                        }



                }}

            }


            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
        mMap.setOnMarkerClickListener(this);

    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        Log.e("testest","hellofromlistenner");

        Intent intent = new Intent(MapsActivity.this,Profilsitter.class);
        LatLng position=marker.getPosition();
        String title=marker.getTitle();
        String snippet=marker.getSnippet();
        intent.putExtra("key",iddusitter);
        startActivity(intent);
        return false;
    }
}
