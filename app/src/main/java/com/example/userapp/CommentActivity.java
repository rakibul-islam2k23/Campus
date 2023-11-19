package com.example.userapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;

public class CommentActivity extends AppCompatActivity {
    ImageButton backImageButton;
    TextView backTextView;
    RecyclerView commentRecyclerView;
    String commenterName;
    EditText commentEditText;
    TextView postCommentButton;
    String comment;
    String postId;
    CommentAdapter commentAdapter;
    String departmentName;
    CircleImageView userProfileCirImage;

    String imageUrl;

    ArrayList<CommentModelClass> modelClassArrayList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        commentRecyclerView = findViewById(R.id.commentRecyclerView);
        backImageButton = findViewById(R.id.backImageButton);
        backTextView = findViewById(R.id.backTextView);
        commentEditText = findViewById(R.id.commentEditText);
        postCommentButton = findViewById(R.id.postCommentButton);
        userProfileCirImage = findViewById(R.id.userProfileCirImage);

        commentEditText.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(s.toString().trim().length()==0){
                    postCommentButton.setVisibility(View.INVISIBLE);
                } else {
                    postCommentButton.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub

            }
        });


        backImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        backTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backTextView.setClickable(false);
            }
        });

        Intent intent = getIntent();
        postId = intent.getStringExtra("postId");
        LoadName();
        postCommentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comment = commentEditText.getText().toString().trim();
                        PostComment();
            }
        });


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        commentRecyclerView.setHasFixedSize(true);
        commentRecyclerView.setLayoutManager(linearLayoutManager);
        commentAdapter = new CommentAdapter(getApplicationContext(),modelClassArrayList);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        LoadComments();
    }

    private void LoadComments() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("computer")
                .child("posts")
                .child(postId)
                .child("comments");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                modelClassArrayList.clear();
                for(DataSnapshot snapshot1 : snapshot.getChildren()){
                    CommentModelClass commentModelClass = snapshot1.getValue(CommentModelClass.class);
                    modelClassArrayList.add(commentModelClass);
                }
                commentRecyclerView.setAdapter(commentAdapter);
                commentAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    private void PostComment() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("computer");
        Calendar calendar;
        SimpleDateFormat simpleDateFormat;
        String commentTime;
        calendar = Calendar.getInstance();
        simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        commentTime = simpleDateFormat.format(calendar.getTime());
        String commentUserId = FirebaseAuth.getInstance().getUid();
        String randomKey = databaseReference.push().getKey();
        CommentModelClass commentModelClass = new CommentModelClass(
                comment,commentTime,commenterName,postId,randomKey,departmentName,commentUserId,imageUrl);
        databaseReference.child("posts").child(postId).child("comments").child(randomKey)
                .setValue(commentModelClass);
        Toast.makeText(this, "Done", Toast.LENGTH_SHORT).show();
        commentEditText.setText("");
    }

    private void LoadName() {

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("computer")
                .child("profile")
                .child(FirebaseAuth.getInstance().getUid());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snapshot1 : snapshot.getChildren()){
                    ProfileModelClass profileModelClass = snapshot1.getValue(ProfileModelClass.class);
                    assert profileModelClass != null;
                    commenterName = profileModelClass.getName();
                    departmentName = profileModelClass.getDepartment();
                    imageUrl = profileModelClass.getUrl();
                    Picasso.get().load(imageUrl).placeholder(R.drawable.icon24).into(userProfileCirImage);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
}