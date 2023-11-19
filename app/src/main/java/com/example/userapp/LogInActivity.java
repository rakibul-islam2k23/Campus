package com.example.userapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;

import org.w3c.dom.Text;

public class LogInActivity extends AppCompatActivity {
    EditText email, password;
    TextView create;
    FirebaseAuth firebaseAuth;
    ProgressBar progressBar;
    TextView createAccount,signInWithGoogle;
    TextView forgetPassword;
    String stringEmail,stringPassword;
    String iEmail,iPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        create = findViewById(R.id.create);
        createAccount = findViewById(R.id.createAccount);
        signInWithGoogle = findViewById(R.id.signInWithGoogle);
        forgetPassword = findViewById(R.id.forgetPassword);
        progressBar = findViewById(R.id.progressBar);


        Intent intent = getIntent();
        iEmail = intent.getStringExtra("email");
        iPassword = intent.getStringExtra("password");

        email.setText(iEmail);
        password.setText(iPassword);


        firebaseAuth = FirebaseAuth.getInstance();
        forgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LogInActivity.this,ForgetPasswordActivity.class);
                startActivity(intent);
            }
        });
        signInWithGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(LogInActivity.this, "Coming soon", Toast.LENGTH_SHORT).show();
            }
        });
        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LogInActivity.this, CreateAccountActivity.class);
                startActivity(intent);
                finish();
            }
        });

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                stringEmail = email.getText().toString().trim();
                stringPassword = password.getText().toString().trim();
                if (stringEmail.isEmpty()) {
                    email.setError("Enter email");
                    email.requestFocus();
                } else if (!Patterns.EMAIL_ADDRESS.matcher(stringEmail).matches()) {
                    email.setError("invalid email");
                    email.requestFocus();
                } else if (stringPassword.isEmpty()) {
                    password.setError("Enter password");
                    password.requestFocus();
                } else if (stringPassword.length() < 6) {
                    password.setError("minimum length of password should be 6");
                    password.requestFocus();
                } else {
                    progressBar.setVisibility(View.VISIBLE);
                    create.setVisibility(View.GONE);
                    firebaseAuth.signInWithEmailAndPassword(stringEmail, stringPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                boolean verified = firebaseAuth.getCurrentUser().isEmailVerified();
                                if(verified == true){
                                    progressBar.setVisibility(View.GONE);
                                    Intent intent = new Intent(LogInActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                }else{
                                    create.setVisibility(View.VISIBLE);
                                    progressBar.setVisibility(View.GONE);
                                    Toast.makeText(LogInActivity.this, "Verify Your email!", Toast.LENGTH_SHORT).show();
                                }

                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            create.setVisibility(View.VISIBLE);
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(LogInActivity.this, "" + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
                }


            }
        });
    }



    @Override
    public void onStart() {
        super.onStart();
        // Check if the user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        // do your stuff
        if (currentUser != null) {
            Intent intent = new Intent(LogInActivity.this, MainActivity.class);
            startActivity(intent);
        }

    }

}