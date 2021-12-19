package com.haythem.miniprojet;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class AddActivity extends AppCompatActivity {

        EditText marque,modele,specs,image;
        Button btnAdd , btnBack;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_add);

            marque =(EditText) findViewById(R.id.Marque);
            modele =(EditText) findViewById(R.id.Modele);
            specs  =(EditText) findViewById(R.id.Specs);
            image  =(EditText) findViewById(R.id.Img);
            btnAdd =(Button) findViewById(R.id.btnAdd);
            btnBack =(Button) findViewById(R.id.btnBack);

            btnAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    insertData();
                    ClearAll();
                }
            });

            btnBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }
        private void insertData()
        {
            Map<String,Object> map = new HashMap<>();
            map.put("Marque",marque.getText().toString());
            map.put("Modele",modele.getText().toString());
            map.put("Specs",specs.getText().toString());
            map.put("Image",image.getText().toString());

            FirebaseDatabase.getInstance().getReference().child("Phones").push()
                    .setValue(map)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(AddActivity.this,"Data inserted Successfully",Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(Exception e) {
                            Toast.makeText(AddActivity.this,"Error while Inserting",Toast.LENGTH_SHORT).show();
                        }
                    });
        }

        private void ClearAll()
        {
            marque.setText("");
            modele.setText("");
            specs.setText("");
            image.setText("");
        }

    }

