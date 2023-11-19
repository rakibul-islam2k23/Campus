package com.example.userapp;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {
    Context context;
    ArrayList<CommentModelClass> modelClassArrayList = new ArrayList<>();
    CircleImageView commentImageView;

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("computer")
            .child("posts");

    public CommentAdapter(Context context, ArrayList<CommentModelClass> modelClassArrayList) {
        this.context = context;
        this.modelClassArrayList = modelClassArrayList;
    }

    @NonNull
    @Override
    public CommentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_sample_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentAdapter.ViewHolder holder, int position) {
        CommentModelClass commentModelClass = modelClassArrayList.get(position);
        holder.commenterName.setText(commentModelClass.getCommenterName());
        holder.commentTime.setText(commentModelClass.getCommentTime());
        holder.comment.setText(commentModelClass.getComment());
        holder.department.setText(commentModelClass.getCommentDepartment());
        Picasso.get().load(commentModelClass.getImageUrl()).placeholder(R.drawable.icon24).into(commentImageView);
        String postId = commentModelClass.getCommentPostId();
        String randomKey = commentModelClass.getCommentRandomKey();
        String comment = commentModelClass.getComment();
        String userId = commentModelClass.getCommentUserAut();
        if(userId == FirebaseAuth.getInstance().getUid()){
            holder.fullCommentItem.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    Dialog dialog = new Dialog(v.getContext());
                    dialog.setContentView(R.layout.comment_delete_layout);
                    TextView closeDialog = dialog.findViewById(R.id.closeDialog);
                    TextView yesDialog = dialog.findViewById(R.id.yesDialog);

                    closeDialog.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.cancel();
                        }
                    });
                    yesDialog.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            databaseReference.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    databaseReference.child(postId).child("comments").child(randomKey).removeValue();
                                    dialog.cancel();
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                        }
                    });
                    dialog.show();
                    return true;
                }
            });
        }
        else{
            holder.fullCommentItem.setClickable(false);
        }
    }

    @Override
    public int getItemCount() {
        return modelClassArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView commenterName;
        TextView commentTime;
        TextView comment;
        RelativeLayout fullCommentItem;
        TextView department;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            commenterName = itemView.findViewById(R.id.commenterName);
            commentTime = itemView.findViewById(R.id.commentTime);
            comment = itemView.findViewById(R.id.comment);
            fullCommentItem = itemView.findViewById(R.id.fullCommentItem);
            department = itemView.findViewById(R.id.department);
            commentImageView = itemView.findViewById(R.id.commentImageView);
        }
    }
}
