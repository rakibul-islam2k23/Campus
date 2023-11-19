package com.example.userapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.util.Calendar;

public class ClassTimeFragment extends Fragment {

    public ClassTimeFragment() {
        // Required empty public constructor
    }
    RecyclerView classTimeRecyclerView;
    TextView todayTime;
    LinearLayout addTaskLayout;
    View bottomSheet;
    String recentTime;
    String startClass;
    String endClass;
    String subject;
    String week;
    String teacher;
    String place;
    TabLayout tabLayout;
    TabItem satTabItem;
    TabItem sunTabItem;
    TabItem monTabItem;
    TabItem theTabItem;
    TabItem wedTabItem;
    TabItem thuTabItem;
    ViewPager viewPager;
    ClassPagerAdapter classPagerAdapter;
    TextView clearAll;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("computer")
            .child("class");

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_class_time, container, false);

        todayTime = view.findViewById(R.id.todayTime);
        addTaskLayout = view.findViewById(R.id.addTaskLayout);
        tabLayout = view.findViewById(R.id.tabLayout);
        satTabItem = view.findViewById(R.id.satTabItem);
        sunTabItem = view.findViewById(R.id.sunTabItem);
        monTabItem = view.findViewById(R.id.monTabItem);
        theTabItem = view.findViewById(R.id.theTabItem);
        wedTabItem = view.findViewById(R.id.wedTabItem);
        thuTabItem = view.findViewById(R.id.thuTabItem);
        viewPager = view.findViewById(R.id.viewPager);
        clearAll = view.findViewById(R.id.clearAll);


        classPagerAdapter = new ClassPagerAdapter(getParentFragmentManager(),tabLayout.getTabCount());
        viewPager.setAdapter(classPagerAdapter);
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                 if(tab.getPosition() == 0 || tab.getPosition() == 1 ||
                 tab.getPosition() == 2 || tab.getPosition() == 3 ||
                 tab.getPosition() == 4 ||tab.getPosition() == 5){
                   classPagerAdapter.notifyDataSetChanged();
                 }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewPager.setOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));






//        todayTime textView design work------------------------------------------------------------
        Calendar calendar;
        calendar = Calendar.getInstance();
        String currentData = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());

//      --------------------------------------------------------------------------------------------


        todayTime.setText(currentData);



//        addTaskLayout design work-----------------------------------------------------------------
        addTaskLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(v.getContext(),R.style.BottomSheetTheme);
                bottomSheet = LayoutInflater.from(getContext()).inflate(R.layout.addclass_bottomsheet_layout,null);
                bottomSheetDialog.setContentView(bottomSheet);
                Week();
                StartClass();
                EndClass();
                Subject();
                Teacher();
                Place();
                LinearLayout addClassLayout = bottomSheet.findViewById(R.id.addClassLayout);
                TextView time = bottomSheet.findViewById(R.id.todayTime);
                time.setText(currentData);
                addClassLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SentClassDataToServer();
                    }
                });
                bottomSheetDialog.show();
            }
        });
//        ------------------------------------------------------------------------------------------




        //        clear all  design work-----------------------------------------------------------------
        clearAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClearAllClass();
            }
        });
//        ------------------------------------------------------------------------------------------
        

        return view;
    }

    private void ClearAllClass() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("computer")
                .child("class");
        databaseReference.child(FirebaseAuth.getInstance().getUid()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(getContext(), "Clear", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void SentClassDataToServer() {
        if(week == null){
            Toast.makeText(getContext(), "Week Empty", Toast.LENGTH_SHORT).show();
        }else if (startClass == null && startClass== null){
            Toast.makeText(getContext(), "Class Time Empty", Toast.LENGTH_SHORT).show();
        }else if (subject == null){
            Toast.makeText(getContext(), "Subject Empty", Toast.LENGTH_SHORT).show();
        }else if(teacher == null){
            Toast.makeText(getContext(), "Teacher Empty", Toast.LENGTH_SHORT).show();
        }else if(place == null){
            Toast.makeText(getContext(), "Place Empty", Toast.LENGTH_SHORT).show();
        }else{
            ClassTimeModelClass classModelClass = new ClassTimeModelClass(
                    recentTime,week,startClass,endClass,subject,teacher,place
            );
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("computer");
            String userUid = FirebaseAuth.getInstance().getUid();
            assert userUid != null;
            databaseReference.child("class").child(userUid).child(week).child(startClass).setValue(classModelClass).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    Toast.makeText(getContext(), "Class Added", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getContext(), "Adding Failed", Toast.LENGTH_SHORT).show();
                }
            });
        }



    }


    private void Week() {
        String[] array = new String[]{
                "Sat","Sun","Mon","Tue","Wed",
                "Thu"
        };
        ArrayAdapter<String > arrayAdapter = new ArrayAdapter<>(
                getContext(),
                R.layout.week_sample_layout,
                array
        );
        AutoCompleteTextView autoCompleteTextView = bottomSheet.findViewById(R.id.weekTextView);
        autoCompleteTextView.setAdapter(arrayAdapter);
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                week = autoCompleteTextView.getText().toString().trim();
            }
        });
    }
    private void StartClass() {
        String[] array = new String[]{
                "12:30","1:15","2:00","2:45","3:30","4:15"
        };
        ArrayAdapter<String > arrayAdapter = new ArrayAdapter<>(
                getContext(),
                R.layout.startclass_sample_layout,
                array
        );
        AutoCompleteTextView autoCompleteTextView = bottomSheet.findViewById(R.id.startClassTextView);
        autoCompleteTextView.setAdapter(arrayAdapter);
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startClass = autoCompleteTextView.getText().toString().trim();
            }
        });
    }
    private void EndClass() {
        String[] array = new String[]{
                "01:15","02:00","02:45","03:30","04:15","05:00"
        };
        ArrayAdapter<String > arrayAdapter = new ArrayAdapter<>(
                getContext(),
                R.layout.endclass_sample_layout,
                array
        );
        AutoCompleteTextView autoCompleteTextView = bottomSheet.findViewById(R.id.endClassTextView);
        autoCompleteTextView.setAdapter(arrayAdapter);
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                endClass = autoCompleteTextView.getText().toString().trim();
            }
        });
    }
    private void Subject() {
        String[] array = new String[]{
                "Physics-2 (25922)","Mathematics-3 (25931)","Digital Electronics (26831)","Social Science (25811)",
                "Application Dev Using Python (28531)",
                "Computer Graphics Design-2 (28532)","IT Support Services (28533)"
        };
        ArrayAdapter<String > arrayAdapter = new ArrayAdapter<>(
                getContext(),
                R.layout.subject_sample_layout,
                array
        );
        AutoCompleteTextView autoCompleteTextView = bottomSheet.findViewById(R.id.subjectTextView);
        autoCompleteTextView.setAdapter(arrayAdapter);
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                subject = autoCompleteTextView.getText().toString().trim();
            }
        });
    }
    private void Teacher() {
        String[] array = new String[]{
                "MR-Y","MKA","KCM","RRR","MR","RA","MEH"
        };
        ArrayAdapter<String > arrayAdapter = new ArrayAdapter<>(
                getContext(),
                R.layout.teacher_sample_layout,
                array
        );
        AutoCompleteTextView autoCompleteTextView = bottomSheet.findViewById(R.id.teacherTextView);
        autoCompleteTextView.setAdapter(arrayAdapter);
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                teacher = autoCompleteTextView.getText().toString().trim();
            }
        });
    }
    private void Place() {
        String[] array = new String[]{
                "Class Room","Phy/Lab","S/Lab","N/Lab","H/Lab"
        };
        ArrayAdapter<String > arrayAdapter = new ArrayAdapter<>(
                getContext(),
                R.layout.place_sample_layout,
                array
        );
        AutoCompleteTextView autoCompleteTextView = bottomSheet.findViewById(R.id.placeTextView);
        autoCompleteTextView.setAdapter(arrayAdapter);
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                place = autoCompleteTextView.getText().toString().trim();
            }
        });
    }
}