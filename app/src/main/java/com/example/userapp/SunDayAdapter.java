package com.example.userapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SunDayAdapter extends RecyclerView.Adapter<SunDayAdapter.ViewHolder> {

    Context context;
    ArrayList<ClassTimeModelClass> arrayList = new ArrayList<>();

    public SunDayAdapter(Context context, ArrayList<ClassTimeModelClass> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public SunDayAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.class_sample_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SunDayAdapter.ViewHolder holder, int position) {
        ClassTimeModelClass classTimeModelClass = arrayList.get(position);

        holder.startTime.setText(classTimeModelClass.getStartClass());
        holder.endTime.setText(classTimeModelClass.getEndClass());
        holder.subjectName.setText(classTimeModelClass.getSubject());
        holder.teacherName.setText("Teacher : "+classTimeModelClass.getTeacher());
        holder.placeName.setText("Place : "+classTimeModelClass.getPlace());
        holder.deleteClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("computer")
                        .child("class")
                        .child(FirebaseAuth.getInstance().getUid());
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        databaseReference.child("Sun").child(classTimeModelClass.getStartClass()).removeValue();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView startTime,endTime,subjectName,teacherName,placeName;
        ImageButton deleteClass;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            startTime = itemView.findViewById(R.id.startTime);
            endTime = itemView.findViewById(R.id.endTime);
            subjectName = itemView.findViewById(R.id.subjectName);
            teacherName = itemView.findViewById(R.id.teacherName);
            placeName = itemView.findViewById(R.id.placeName);
            deleteClass = itemView.findViewById(R.id.deleteClassImageButton);
        }
    }
}
