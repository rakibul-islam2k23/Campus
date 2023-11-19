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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class BuyBooksFragment extends Fragment {

    public BuyBooksFragment() {
        // Required empty public constructor
    }
    RecyclerView buyBooksRecyclerView;
    BuyBooksAdapter buyBooksAdapter;
    ArrayList<SellBooksModelClass> arrayList = new ArrayList<>();
    int booksCount;
    TextView todayTime;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_buy_books, container, false);

        buyBooksRecyclerView = view.findViewById(R.id.buyBookRecyclerView);
        todayTime = view.findViewById(R.id.todayTime);

        //        todayTime textView design work------------------------------------------------------------
        Calendar calendar;
        calendar = Calendar.getInstance();
        String currentData = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());

//      --------------------------------------------------------------------------------------------
        todayTime.setText(currentData);


        buyBooksAdapter = new BuyBooksAdapter(getContext(),arrayList);
        buyBooksRecyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        buyBooksRecyclerView.setLayoutManager(linearLayoutManager);


        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("computer")
                .child("sellingBookList");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayList.clear();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    SellBooksModelClass sellBooksModelClass = dataSnapshot.getValue(SellBooksModelClass.class);
                    arrayList.add(sellBooksModelClass);
                }
                buyBooksRecyclerView.setAdapter(buyBooksAdapter);
                buyBooksAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        return view;
    }


}