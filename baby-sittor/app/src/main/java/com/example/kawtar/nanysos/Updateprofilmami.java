package com.example.kawtar.nanysos;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;

public class Updateprofilmami extends AppCompatActivity {
    private Firebase mRef,conn1;
    private ArrayList<String> musernames=new ArrayList<>();
    private EditText pass;
    private TextView name;
    String id;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update);
        Intent intent = getIntent();
        id=intent.getStringExtra("key");
        name=(TextView) findViewById(R.id.textView4);
        pass=(EditText) findViewById(R.id.textView5);
        mRef=new Firebase("https://babysittingapp-e382d.firebaseio.com/Users");
        mRef.child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot noteSnapshot: dataSnapshot.getChildren()){
                    String user=noteSnapshot.getKey();
                    String valeur=noteSnapshot.getValue(String.class);
                    if (user.equals("Name")){
                        Log.e("valeur",valeur);
                        name.setText(valeur);

                    }
                    else if (user.equals("Password")){
                        Log.e("valeur",valeur);
                        pass.setText(valeur);

                    }
                    ;

                }

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }
    public void modifydata(View v){
        conn1=new Firebase("https://mikla-35939.firebaseio.com/Users");
        Log.e("bdeif","false");

        String passwd=pass.getText().toString();
        Firebase attribut1 =conn1.child(id).child("Name");
        Firebase attribut2=conn1.child(id).child("Password");
        Firebase attribut3=conn1.child(id).child("Type");

        attribut2.setValue(passwd);
        attribut3.setValue("Parents");
        Intent i = new Intent(getApplicationContext(), FourthActivity.class);
        i.putExtra("key",id);
        startActivity(i);
    }
}