package com.example.userapp;

import android.content.Context;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class AssignmentAdapter extends RecyclerView.Adapter<AssignmentAdapter.ViewHolder> {

    Context context;
    ArrayList<AssignmentModelClass> arrayList = new ArrayList();

    public AssignmentAdapter(Context context, ArrayList<AssignmentModelClass> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public AssignmentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.assignment_sample_layout,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AssignmentAdapter.ViewHolder holder, int position) {
        AssignmentModelClass assignmentModelClass = arrayList.get(position);


        holder.subject.setText(assignmentModelClass.getSubject());
        holder.teacher.setText("Teacher : "+assignmentModelClass.getTeacher());
        holder.job.setText("Job No : "+assignmentModelClass.getJobNumber());
        holder.page.setText("Page No : ("+assignmentModelClass.getPageNo()+")");
        holder.in.setText(assignmentModelClass.getCheckInDate());
        holder.out.setText(assignmentModelClass.getCheckOutDate());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView subject;
        TextView teacher;
        TextView job;
        TextView page;
        TextView in;
        TextView out;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            subject = itemView.findViewById(R.id.subject);
            teacher = itemView.findViewById(R.id.teacher);
            job = itemView.findViewById(R.id.jobNo);
            page = itemView.findViewById(R.id.page);
            in = itemView.findViewById(R.id.checkIn);
            out = itemView.findViewById(R.id.checkOut);
        }
    }
}
