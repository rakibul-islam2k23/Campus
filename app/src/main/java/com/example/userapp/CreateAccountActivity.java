package com.example.userapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class CreateAccountActivity extends AppCompatActivity {
    TextView signInAccount;
    EditText userName, userEmail, userPassword, userSemister, userNumber, userDepartment;
    TextView createAccount;
    FirebaseAuth firebaseAuth;
    private ProgressBar progressBar;
    DatabaseReference databaseReference;
    String uid;
    String name, email, password, semister, department, number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        signInAccount = findViewById(R.id.signInAccount);
        userName = findViewById(R.id.userName);
        userEmail = findViewById(R.id.userEmail);
        userPassword = findViewById(R.id.userPassword);
        userSemister = findViewById(R.id.userSemister);
        userDepartment = findViewById(R.id.userDepartment);
        userNumber = findViewById(R.id.userNumber);
        createAccount = findViewById(R.id.createAccount);
        progressBar = findViewById(R.id.progressBar);

        firebaseAuth = FirebaseAuth.getInstance();
        uid = FirebaseAuth.getInstance().getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference("computer");
        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateAccount();
            }
        });
        signInAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreateAccountActivity.this, LogInActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    }

    private void CreateAccount() {
        name = userName.getText().toString().trim();
        email = userEmail.getText().toString().trim();
        password = userPassword.getText().toString().trim();
        semister = userSemister.getText().toString().trim();
        department = userDepartment.getText().toString().toUpperCase();
        number = userNumber.getText().toString().trim();

        if (name.isEmpty()) {
            userName.setError("Enter username");
            userName.requestFocus();
        } else if (email.isEmpty()) {
            userEmail.setError("Enter email");
            userEmail.requestFocus();
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            userEmail.setError("invalid email");
            userEmail.requestFocus();
        } else if (password.isEmpty()) {
            userPassword.setError("Enter password");
            userPassword.requestFocus();
        } else if (password.length() < 6) {
            userPassword.setError("minimum length should be 6");
            userPassword.requestFocus();
        } else if (semister.isEmpty()) {
            userSemister.setError("Semester should be 01 to 08");
            userSemister.requestFocus();
        } else if (department.isEmpty()) {
            userDepartment.setError("Department should be short form");
            userDepartment.requestFocus();
        } else if (number.length() != 11) {
            userNumber.setError("Invalid number");
            userNumber.requestFocus();
        } else {
            createAccount.setVisibility(View.INVISIBLE);
            progressBar.setVisibility(View.VISIBLE);
            firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        firebaseAuth.getCurrentUser().sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                SentDataToFirebase();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                progressBar.setVisibility(View.GONE);
                                createAccount.setVisibility(View.VISIBLE);
                                Toast.makeText(CreateAccountActivity.this, "" + e.getMessage().toString(), Toast.LENGTH_LONG).show();
                            }
                        });
                    } else {
                        if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                            progressBar.setVisibility(View.GONE);
                            createAccount.setVisibility(View.VISIBLE);
                            Toast.makeText(CreateAccountActivity.this, "Already registered", Toast.LENGTH_LONG).show();
                        }
                    }
                }


            });
        }

    }

    private void SentDataToFirebase() {
        UserDetailsModelClass userDetailsModelClass = new UserDetailsModelClass(name, email, password,
                semister, number, uid, department);
        databaseReference.child("userDetails")
                .child(FirebaseAuth.getInstance().getUid())
                .child("user")
                .setValue(userDetailsModelClass).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        SentDetailsToFirebase();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressBar.setVisibility(View.GONE);
                        createAccount.setVisibility(View.VISIBLE);
                        Toast.makeText(CreateAccountActivity.this, "" + e.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void SentDetailsToFirebase() {
        String url = "https://cdn0.iconfinder.com/data/icons/small-n-flat/24/678099-profile-filled-512.png";
        ProfileModelClass profileModelClass = new ProfileModelClass(name, department, semister, number, url);
        DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference("computer")
                .child("profile")
                .child(FirebaseAuth.getInstance().getUid())
                .child("user");
        databaseReference1.setValue(profileModelClass).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                progressBar.setVisibility(View.GONE);
                createAccount.setVisibility(View.VISIBLE);
                userName.setText("");
                userEmail.setText("");
                userPassword.setText("");
                userSemister.setText("");
                userDepartment.setText("");
                userNumber.setText("");
                Toast.makeText(CreateAccountActivity.this, "Please verify Email !", Toast.LENGTH_LONG).show();
                boolean verified = firebaseAuth.getCurrentUser().isEmailVerified();
                if(verified == true){
                    Intent intent = new Intent(CreateAccountActivity.this,MainActivity.class);
                    startActivity(intent);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                progressBar.setVisibility(View.GONE);
                createAccount.setVisibility(View.VISIBLE);
                Toast.makeText(CreateAccountActivity.this, ""+e.getMessage().toString(), Toast.LENGTH_LONG).show();
            }
        });
    }
}

