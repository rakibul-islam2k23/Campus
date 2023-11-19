package com.example.userapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GestureDetectorCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager2.widget.ViewPager2;

import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class HomeFragment extends Fragment {

    public HomeFragment() {
        // Required empty public constructor
    }
    RecyclerView mainRecyclerView;
    MainAdapter mainAdapter;
    View textArea;
    TextView welcomeName;
    SwipeRefreshLayout swipeRefreshLayout;


    ArrayList<NoticeModelClass> arrayList;

    DatabaseReference databaseReference;
    TextView todayTime;
    ProgressBar progressBarOfMain;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        mainRecyclerView = view.findViewById(R.id.mainRecyclerView);
        textArea = view.findViewById(R.id.textArea);
        arrayList = new ArrayList<>();
        mainRecyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        mainRecyclerView.setLayoutManager(linearLayoutManager);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        mainAdapter = new MainAdapter(getContext(),arrayList);
        welcomeName = view.findViewById(R.id.welcomeName);
        todayTime = view.findViewById(R.id.todayTime);
        swipeRefreshLayout = view.findViewById(R.id.mainRefreshLayout);
        progressBarOfMain = view.findViewById(R.id.progressBarOfMain);


        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                LoadMainData();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

//        todayTime textView design work------------------------------------------------------------
        Calendar calendar;
        calendar = Calendar.getInstance();
        String currentData = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
//      --------------------------------------------------------------------------------------------
        todayTime.setText(currentData);
        LoadMainData();
        LoadData();





        return view;

    }

    private void LoadMainData() {
        databaseReference = FirebaseDatabase.getInstance().getReference("computer").child("posts");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayList.clear();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    welcomeName.setVisibility(View.VISIBLE);
                    mainRecyclerView.setVisibility(View.VISIBLE);
                    NoticeModelClass noticeModelClass = dataSnapshot.getValue(NoticeModelClass.class);
                    assert noticeModelClass != null;
                    noticeModelClass.setPostId(dataSnapshot.getKey());
                    arrayList.add(noticeModelClass);
                    progressBarOfMain.setVisibility(View.GONE);
                }
                mainRecyclerView.setAdapter(mainAdapter);
                mainAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Server Error", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void LoadData() {
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("computer")
                    .child("profile").child(FirebaseAuth.getInstance().getUid());
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for(DataSnapshot snapshot1 : snapshot.getChildren()){
                        ProfileModelClass profileModelClass = snapshot1.getValue(ProfileModelClass.class);
                        welcomeName.setText(profileModelClass.getName());
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(getContext(), "Profile Error", Toast.LENGTH_SHORT).show();
                }
            });
        }


}