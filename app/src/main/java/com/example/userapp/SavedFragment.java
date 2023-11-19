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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class SavedFragment extends Fragment {
    public SavedFragment() {
        // Required empty public constructor
    }
    RecyclerView pdfRecyclerView;
    PdfAdapter pdfAdapter;
    ArrayList<PdfModelClass> arrayList = new ArrayList();
    TextView todayTime;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_saved, container, false);



        Calendar calendar;
        calendar = Calendar.getInstance();
        String currentData = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
        pdfRecyclerView = view.findViewById(R.id.pdfRecyclerView);
        todayTime = view.findViewById(R.id.todayTime);
        todayTime.setText(currentData);

        pdfAdapter = new PdfAdapter(getContext(),arrayList);
        pdfRecyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager =new LinearLayoutManager(getContext());
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        pdfRecyclerView.setLayoutManager(linearLayoutManager);



        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("computer")
                .child("pdf");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayList.clear();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Log.d("serverRes",dataSnapshot.toString());
                    PdfModelClass pdfModelClass = dataSnapshot.getValue(PdfModelClass.class);
                    arrayList.add(pdfModelClass);
                }
                pdfRecyclerView.setAdapter(pdfAdapter);
                pdfAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });









        return view;
    }
}