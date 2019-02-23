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

public class Updateprofilsitter extends AppCompatActivity {
    private Firebase mRef,conn1;
    private ArrayList<String> musernames=new ArrayList<>();
    private EditText pass,email,tel,prenom;
    private TextView name;
    String id;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_sitter);
        Intent intent = getIntent();
        id=intent.getStringExtra("key");
        name=(TextView) findViewById(R.id.textView3);
        pass=(EditText) findViewById(R.id.password);
        email=(EditText)findViewById(R.id.emailModif) ;
        tel=(EditText) findViewById(R.id.telModif) ;
        prenom=(EditText) findViewById(R.id.prenomModif) ;
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
                    else if(user.equals("Tel")){ tel.setText(valeur);}
                    else if(user.equals("Email")){email.setText(valeur);}
                    else if(user.equals("pren")){prenom.setText(valeur);}
                    else if (user.equals("Password")){
                        Log.e("valeur",valeur);
                        pass.setText(valeur);

                    }
                    ;
                    //String value=dataSnapshot.getValue(String.class);

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
        String prenomp=prenom.getText().toString();
        String emailp=email.getText().toString();
        String telp=tel.getText().toString();
        String passwd=pass.getText().toString();
        Firebase attribut1 =conn1.child(id).child("Name");
        Firebase attribut2=conn1.child(id).child("Password");
        Firebase attribut3=conn1.child(id).child("Type");
        Firebase attribut4=conn1.child(id).child("Email");
        Firebase attribut5=conn1.child(id).child("pren");
        Firebase attribut6=conn1.child(id).child("Tel");



        attribut2.setValue(passwd);
        attribut3.setValue("Babysitter");
        attribut4.setValue(emailp);
        attribut5.setValue(prenomp);
        attribut6.setValue(telp);
        Intent i = new Intent(getApplicationContext(), FourthActivitysitter.class);
        i.putExtra("key",id);
        startActivity(i);
    }
}