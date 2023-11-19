package com.example.userapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class AssignmentFragment extends Fragment {

    public AssignmentFragment() {
        // Required empty public constructor
    }
    RecyclerView assignmentRecyclerView;
    ArrayList<AssignmentModelClass> arrayList = new ArrayList<>();
    AssignmentAdapter assignmentAdapter;
    TextView todayTime;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_assignment, container, false);
        todayTime = view.findViewById(R.id.todayTime);

        //        todayTime textView design work------------------------------------------------------------
        Calendar calendar;
        calendar = Calendar.getInstance();
        String currentData = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());

//      --------------------------------------------------------------------------------------------
        todayTime.setText(currentData);

        assignmentRecyclerView = view.findViewById(R.id.assignmentRecyclerView);
         assignmentAdapter = new AssignmentAdapter(getContext(),arrayList);
        assignmentRecyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        assignmentRecyclerView.setLayoutManager(linearLayoutManager);

        LoadAssignment();



        return view;
    }

    private void LoadAssignment() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("computer")
                .child("assignment");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayList.clear();
                for(DataSnapshot snapshot1 : snapshot.getChildren()){
                    AssignmentModelClass assignmentModelClass = snapshot1.getValue(AssignmentModelClass.class);
                    arrayList.add(assignmentModelClass);
                }

                assignmentRecyclerView.setAdapter(assignmentAdapter);
                assignmentAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}