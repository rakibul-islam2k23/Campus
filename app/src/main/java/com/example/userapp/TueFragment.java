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

public class TueFragment extends Fragment {
    public TueFragment() {
        // Required empty public constructor
    }
    RecyclerView tueDayRecyclerView;
    ArrayList<ClassTimeModelClass> arrayList;
    TueDayAdapter tueDayAdapter;
    DatabaseReference databaseReference;
    SwipeRefreshLayout swipeRefreshLayout;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_tue, container, false);
        tueDayRecyclerView = view.findViewById(R.id.tueDayRecyclerView);
        swipeRefreshLayout = view.findViewById(R.id.theRefreshLayout);

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
        tueDayAdapter = new TueDayAdapter(getContext(),arrayList);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        tueDayRecyclerView.setLayoutManager(linearLayoutManager);
        tueDayRecyclerView.setHasFixedSize(true);
        databaseReference = FirebaseDatabase.getInstance().getReference("computer")
                .child("class").child(FirebaseAuth.getInstance().getUid()).child("Tue");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayList.clear();
                for(DataSnapshot snapshot1 : snapshot.getChildren()){
                    ClassTimeModelClass classTimeModelClass = snapshot1.getValue(ClassTimeModelClass.class);
                    arrayList.add(classTimeModelClass);
                }
                tueDayRecyclerView.setAdapter(tueDayAdapter);
                tueDayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
}