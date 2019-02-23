package com.example.kawtar.nanysos;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.location.FusedLocationProviderClient;


public class MainActivity extends AppCompatActivity {

    private Button mum;
    private Button sitter;
    Context context;
    private FusedLocationProviderClient mFusedLocationClient;


    double latitude, longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gotosecondActivity();
        gotosecondactivitysitter();}

     public void gotosecondActivity(){
         mum=(Button) findViewById(R.id.button);
         mum.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 startActivity(new Intent(MainActivity.this,SecondeActivity.class));
             }
         });
     }
    public void gotosecondactivitysitter(){
        sitter=(Button) findViewById(R.id.babysitter);
       sitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,SecondActivitysitter.class));
    }
});}}


