package com.example.userapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class BookDetailsActivity extends AppCompatActivity {
    ImageButton backImageButton;
    TextView oneBook, twoBook, threeBook, fourBook, fiveBook, sixBook, problem;
    String buyerName;
    String buyerNumber;
    TextView placeRequest;
    String ownerName;
    String ownerPrice;
    String ownerDateAndTime;
    String ownerNumber;
    String buyerPrice;
    String buyerTimeAndDate;
    String userUid;
    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_details_activity);
        backImageButton = findViewById(R.id.backImageButton);
        oneBook = findViewById(R.id.oneBook);
        twoBook = findViewById(R.id.twoBook);
        threeBook = findViewById(R.id.threeBook);
        fourBook = findViewById(R.id.fourBook);
        fiveBook = findViewById(R.id.fiveBook);
        sixBook = findViewById(R.id.sixBook);
        problem = findViewById(R.id.problem);
        placeRequest = findViewById(R.id.placeRequest);

        LoadBuyerData();

        backImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        String problemText = getIntent().getStringExtra("problem");
        String bookOne = getIntent().getStringExtra("one");
        String bookTwo = getIntent().getStringExtra("two");
        String bookThree = getIntent().getStringExtra("three");
        String bookFour = getIntent().getStringExtra("four");
        String bookFive = getIntent().getStringExtra("five");
        String bookSix = getIntent().getStringExtra("six");

        ownerPrice = getIntent().getStringExtra("price");
        ownerName = getIntent().getStringExtra("name");
        ownerDateAndTime = getIntent().getStringExtra("date");
        ownerNumber = getIntent().getStringExtra("number");
        userUid = getIntent().getStringExtra("userUid");

        oneBook.setText(bookOne);
        twoBook.setText(bookTwo);
        threeBook.setText(bookThree);
        fourBook.setText(bookFour);
        fiveBook.setText(bookFive);
        sixBook.setText(bookSix);
        problem.setText(problemText);

        placeRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = new Dialog(BookDetailsActivity.this);
                dialog.setContentView(R.layout.request_confirm_layout);
                dialog.setCancelable(false);
                TextView closeDialog = dialog.findViewById(R.id.dialogClose);
                EditText buyerEditText = dialog.findViewById(R.id.buyerPrice);
                closeDialog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();
                    }
                });
                TextView okDialog = dialog.findViewById(R.id.dialogOk);
                okDialog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        buyerPrice = buyerEditText.getText().toString().trim();
                        if (buyerPrice.isEmpty()) {
                            buyerEditText.setError("Enter Price");
                            buyerEditText.requestFocus();
                        } else {
                            LoadTime();
                            SendDataToFirebase();
                        }
                    }
                });
                dialog.show();
            }
        });


    }

    private void LoadTime() {

        //        todayTime textView design work------------------------------------------------------------
        Calendar calendar;
        calendar = Calendar.getInstance();
        buyerTimeAndDate = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());

//      --------------------------------------------------------------------------------------------
    }

    private void SendDataToFirebase() {

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("computer")
                .child("bookRequest")
                .child(userUid)
                .child(FirebaseAuth.getInstance().getUid());
        RequestModelClass requestModelClass = new RequestModelClass(
                ownerName, ownerNumber, ownerPrice, ownerDateAndTime, buyerName, buyerNumber, buyerPrice, buyerTimeAndDate
        );
        databaseReference.setValue(requestModelClass).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(BookDetailsActivity.this, "Request Sent", Toast.LENGTH_SHORT).show();
                dialog.cancel();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(BookDetailsActivity.this, "Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void LoadBuyerData() {

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("computer")
                .child("profile")
                .child(FirebaseAuth.getInstance().getUid());

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    ProfileModelClass profileModelClass = snapshot1.getValue(ProfileModelClass.class);
                    buyerName = profileModelClass.getName();
                    buyerNumber = profileModelClass.getNumber();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}