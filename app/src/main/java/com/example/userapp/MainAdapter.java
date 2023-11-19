package com.example.userapp;

import android.content.Context;
import android.content.Intent;
import android.provider.ContactsContract;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ortiz.touchview.TouchImageView;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {

    Context context;
    ArrayList<NoticeModelClass> arrayList = new ArrayList<>();

    boolean likeClick = false;
    DatabaseReference databaseReference;
    String commenterName;
    String commentTime;
    String departmentName;
    String imageUrl;
    CircleImageView userProfileCirImage;

    public MainAdapter(Context context, ArrayList<NoticeModelClass> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public MainAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_recyclerview_sample, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MainAdapter.ViewHolder holder, int position) {
        NoticeModelClass noticeModelClass = arrayList.get(position);
        Log.d("serverRes", noticeModelClass.toString());
        holder.statusTextView.setText(noticeModelClass.getNotice());
        holder.statusTimeTextView.setText(noticeModelClass.getNoticeTime());
        Picasso.get().load(noticeModelClass.getUrl()).into(holder.noticeImage);



        holder.commentEdittext.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(s.toString().trim().length()==0){
                    holder.postCommentButton.setVisibility(View.INVISIBLE);
                } else {
                    holder.postCommentButton.setVisibility(View.VISIBLE);
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


        holder.noticeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context.getApplicationContext(),ZoomyImageActivity.class);
                intent.putExtra("url",noticeModelClass.getUrl());
                context.startActivity(intent);
            }
        });


        //        onclick start here

//        more button work--------------------------------------------------------------------------
        holder.moreImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(v.getContext(), R.style.BottomSheetTheme);
                View bottomSheet = LayoutInflater.from(context.getApplicationContext()).inflate(R.layout.more_bottomsheet_layout, null);
                bottomSheetDialog.setContentView(bottomSheet);
                bottomSheetDialog.show();
            }
        });
//        ------------------------------------------------------------------------------------------


//        like button work--------------------------------------------------------------------------
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        assert firebaseUser != null;
        String userId = firebaseUser.getUid();
        String postId = noticeModelClass.postId;
        databaseReference = FirebaseDatabase.getInstance().getReference("computer").child("posts");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child(postId).child("like").hasChild(userId)) {
                    int likeCount = (int) snapshot.child(postId).child("like").getChildrenCount();
                    holder.likesTextView.setText(likeCount + "  Likes ");
                    holder.likeImageButton.setImageResource(R.drawable.selected_love_icon);
                } else {
                    int likeCount = (int) snapshot.child(postId).child("like").getChildrenCount();
                    holder.likesTextView.setText(likeCount + "  Likes ");
                    holder.likeImageButton.setImageResource(R.drawable.love_icon);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        holder.likeImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                likeClick = true;
                databaseReference.child("computer").child("posts");
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (likeClick == true) {
                            if (snapshot.child(postId).child("like").hasChild(userId)) {
                                databaseReference.child(postId).child("like").child(userId).removeValue();
                            } else {
                                databaseReference.child(postId).child("like").child(userId).setValue(true);
                            }
                            likeClick = false;
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
//        ------------------------------------------------------------------------------------------


//        comment button work-----------------------------------------------------------------------
        holder.commentImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context.getApplicationContext(), CommentActivity.class);
                intent.putExtra("postId", postId);
                context.startActivity(intent);
            }
        });
//        ------------------------------------------------------------------------------------------


//        all comment textView work-----------------------------------------------------------------
        holder.allCommentTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context.getApplicationContext(), CommentActivity.class);
                intent.putExtra("postId", postId);
                context.startActivity(intent);
            }
        });
//        ------------------------------------------------------------------------------------------


        LoadData();

//        post comment button work------------------------------------------------------------------
        holder.postCommentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String comment = holder.commentEdittext.getText().toString().trim();
                if (comment.isEmpty()) {
                    Toast.makeText(context, "Comment Empty", Toast.LENGTH_SHORT).show();
                } else {
                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("computer");
                    Calendar calendar;
                    SimpleDateFormat simpleDateFormat;
                    calendar = Calendar.getInstance();
                    simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
                    commentTime = simpleDateFormat.format(calendar.getTime());
                    String randomKey = databaseReference.push().getKey();
                    String commentUserId = FirebaseAuth.getInstance().getUid();
                    CommentModelClass commentModelClass = new CommentModelClass(comment, commentTime, commenterName, postId, randomKey, departmentName, commentUserId,imageUrl);
                    databaseReference.child("posts").child(postId).child("comments").child(randomKey).setValue(commentModelClass).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            holder.commentEdittext.setText("");
                            Toast.makeText(context, "Done!", Toast.LENGTH_SHORT).show();
                        }
                    });
                }


            }
        });
//        ------------------------------------------------------------------------------------------


    }


    private void LoadData() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("computer")
                .child("profile").child(FirebaseAuth.getInstance().getUid());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    ProfileModelClass profileModelClass = snapshot1.getValue(ProfileModelClass.class);
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

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView noticeImage;
        ImageButton moreImageButton, likeImageButton, commentImageButton, savedImageButton;
        TextView statusTextView, likesTextView, allCommentTextView, statusTimeTextView, postCommentButton;
        EditText commentEdittext;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            moreImageButton = itemView.findViewById(R.id.moreImageButton);
            statusTextView = itemView.findViewById(R.id.statusTextView);
            likeImageButton = itemView.findViewById(R.id.likeImageButton);
            commentImageButton = itemView.findViewById(R.id.commentImageButton);
            savedImageButton = itemView.findViewById(R.id.savedImageButton);
            likesTextView = itemView.findViewById(R.id.likesTextView);
            allCommentTextView = itemView.findViewById(R.id.allCommentTextView);
            userProfileCirImage = itemView.findViewById(R.id.userProfileCirImage);
            commentEdittext = itemView.findViewById(R.id.commentEditText);
            postCommentButton = itemView.findViewById(R.id.postCommentButton);
            statusTimeTextView = itemView.findViewById(R.id.statusTimeTextView);
            noticeImage = itemView.findViewById(R.id.noticeImage);

        }
    }
}
