package com.example.userapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ThuFragment extends Fragment {
    public ThuFragment() {
        // Required empty public constructor
    }
    RecyclerView thuDayRecyclerView;
    ArrayList<ClassTimeModelClass> arrayList;
    ThuDayAdapter thuDayAdapter;
    DatabaseReference databaseReference;
    SwipeRefreshLayout swipeRefreshLayout;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_thu, container, false);
        thuDayRecyclerView = view.findViewById(R.id.thuDayRecyclerView);
        swipeRefreshLayout = view.findViewById(R.id.thuRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                LoadMainData();
                swipeRefreshLayout.setRefreshing(false);

            }



        });
        LoadMainData();


        return view;
    }

    private void LoadMainData() {
        arrayList = new ArrayList<>();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        thuDayAdapter = new ThuDayAdapter(getContext(),arrayList);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        thuDayRecyclerView.setLayoutManager(linearLayoutManager);
        thuDayRecyclerView.setHasFixedSize(true);
        databaseReference = FirebaseDatabase.getInstance().getReference("computer")
                .child("class").child(FirebaseAuth.getInstance().getUid()).child("Thu");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayList.clear();
                for(DataSnapshot snapshot1 : snapshot.getChildren()){
                    ClassTimeModelClass classTimeModelClass = snapshot1.getValue(ClassTimeModelClass.class);
                    arrayList.add(classTimeModelClass);
                }
                thuDayRecyclerView.setAdapter(thuDayAdapter);
                thuDayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

    }
}