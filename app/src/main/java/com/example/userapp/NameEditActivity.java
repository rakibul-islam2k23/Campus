package com.example.userapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class NameEditActivity extends AppCompatActivity {
    ImageButton backImageButton;
    EditText nameEditText,semisterEditText,numberEditText;
    TextView saveTextView,departmentEditText;
    String name,department,semister,number;
    String stringName,stringDepartment,stringSemister,stringNumber,stringUrl;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name_edit);
        backImageButton = findViewById(R.id.backImageButton);
        nameEditText = findViewById(R.id.nameEditText);
        departmentEditText = findViewById(R.id.departmentEditText);
        semisterEditText = findViewById(R.id.semisterEditText);
        numberEditText  = findViewById(R.id.numberEditText);

        saveTextView = findViewById(R.id.saveChnageTextView);

        Intent intent1 = getIntent();
        Bundle bundle = intent1.getExtras();

        if (bundle != null) {
            stringName = bundle.getString("name");
            stringDepartment = bundle.getString("department");
            stringSemister = bundle.getString("semister");
            stringNumber = bundle.getString("number");
            stringUrl = bundle.getString("url");

            nameEditText.setText(stringName);
            departmentEditText.setText(stringDepartment);
            semisterEditText.setText(stringSemister);
            numberEditText.setText(stringNumber);
        }

        backImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        saveTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = nameEditText.getText().toString().trim();
                semister = semisterEditText.getText().toString().trim();
                number = numberEditText.getText().toString().trim();

                ChangeDataToServer();
            }
        });
    }

    private void ChangeDataToServer() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("computer")
                .child("profile")
                .child(FirebaseAuth.getInstance().getUid())
                .child("user");
        ProfileModelClass profileModelClass = new ProfileModelClass(name,stringDepartment,semister,number,stringUrl);
        databaseReference.setValue(profileModelClass).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(NameEditActivity.this, "Saved", Toast.LENGTH_SHORT).show();
            }
        });
    }
}