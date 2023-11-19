package com.example.userapp;

import android.os.Bundle;

import androidx.annotation.LongDef;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.util.Calendar;

public class SellBooksFragment extends Fragment {

    public SellBooksFragment() {
        // Required empty public constructor
    }

    View view;
    String oneBook;
    String twoBook;
    String threeBook;
    String fourBook;
    String fiveBook;
    String sixBook;
    String problemText;
    String totalBooks;
    String priceText;
    TextView clearBookList;

    EditText problemEditText;
    EditText priceEditText;
    String userUid;

    String userName;
    String userDepartment;

    String semister;

    TextView todayTime;

    AutoCompleteTextView oneBookACTV;
    AutoCompleteTextView twoBookACTV;
    AutoCompleteTextView threeBookACTV;
    AutoCompleteTextView fourBookACTV;
    AutoCompleteTextView fiveBookACTV;
    AutoCompleteTextView sixBookACTV;
    AutoCompleteTextView totalBookACTV;
    String currentData;
    String number;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_sell_books, container, false);
        OneBook();
        TwoBook();
        ThreeBook();
        FourBook();
        FiveBook();
        SixBook();
        TotalBooks();
        Semister();
        problemEditText = view.findViewById(R.id.problemEditText);
        priceEditText = view.findViewById(R.id.priceEditText);
        todayTime = view.findViewById(R.id.todayTime);
        clearBookList = view.findViewById(R.id.clearBookList);
        clearBookList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClearBookList();
            }
        });

        //        todayTime textView design work------------------------------------------------------------
        Calendar calendar;
        calendar = Calendar.getInstance();
        currentData = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());

//      --------------------------------------------------------------------------------------------

        todayTime.setText(currentData);




        LinearLayout listBookLayout = view.findViewById(R.id.listBookLayout);

        LoadNameAndDepartment();
        listBookLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SendListOfBooksToFirebase();
            }
        });
        return view;
    }

    private void ClearBookList() {

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("computer")
                .child("sellingBookList")
                .child(FirebaseAuth.getInstance().getUid());
        databaseReference.removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(getContext(), "Clear", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void LoadNameAndDepartment() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("computer")
                .child("profile")
                .child(FirebaseAuth.getInstance().getUid());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    ProfileModelClass profileModelClass = dataSnapshot.getValue(ProfileModelClass.class);
                    userName = profileModelClass.getName();
                    userDepartment = profileModelClass.getDepartment();
                    number = profileModelClass.getNumber();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void SendListOfBooksToFirebase() {
        problemText = problemEditText.getText().toString();
        priceText = priceEditText.getText().toString();
        userUid = FirebaseAuth.getInstance().getUid();
         if (oneBook == null) {
            Toast.makeText(getContext(), "One Book Must", Toast.LENGTH_SHORT).show();
        } else if (totalBooks == null) {
             Toast.makeText(getContext(), "Enter Total", Toast.LENGTH_SHORT).show();
         } else if (semister == null) {
            Toast.makeText(getContext(), "Enter Semister", Toast.LENGTH_SHORT).show();
        } else if (problemText.isEmpty()) {
            problemEditText.setError("Enter Problem / Details");
            problemEditText.requestFocus();
        } else if (priceText.isEmpty()) {
            priceEditText.setError("Enter Price");
            priceEditText.requestFocus();
        }else if(userName == null){
             Toast.makeText(getContext(), "Set Profile", Toast.LENGTH_SHORT).show();
         } else {
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("computer")
                    .child("sellingBookList")
                    .child(FirebaseAuth.getInstance().getUid());

             SellBooksModelClass sellBooksModelClass = new SellBooksModelClass(
                     userName,userDepartment,userUid,semister,problemText,priceText,oneBook,twoBook
                     ,threeBook,fourBook,fiveBook,sixBook,currentData,number,totalBooks
             );
             databaseReference.setValue(sellBooksModelClass).addOnSuccessListener(new OnSuccessListener<Void>() {
                 @Override
                 public void onSuccess(Void unused) {
                     Toast.makeText(getContext(), "Added Successful", Toast.LENGTH_SHORT).show();
                 }
             });


        }


    }


    private void OneBook() {
        String[] array = new String[]{
                "Physics-2 (25922)", "Mathematics-3 (25931)", "Digital Electronics (26831)", "Social Science (25811)",
                "Application Dev Using Python (28531)",
                "Computer Graphics Design-2 (28532)", "IT Support Services (28533)"
        };
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(
                getContext(),
                R.layout.subject_sample_layout,
                array
        );
        AutoCompleteTextView autoCompleteTextView = view.findViewById(R.id.oneBookACTV);
        autoCompleteTextView.setAdapter(arrayAdapter);
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                oneBook = autoCompleteTextView.getText().toString().trim();
            }
        });
    }

    private void TwoBook() {
        String[] array = new String[]{
                "Physics-2 (25922)", "Mathematics-3 (25931)", "Digital Electronics (26831)", "Social Science (25811)",
                "Application Dev Using Python (28531)",
                "Computer Graphics Design-2 (28532)", "IT Support Services (28533)"
        };
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(
                getContext(),
                R.layout.subject_sample_layout,
                array
        );
        AutoCompleteTextView autoCompleteTextView = view.findViewById(R.id.twoBookACTV);
        autoCompleteTextView.setAdapter(arrayAdapter);
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                twoBook = autoCompleteTextView.getText().toString().trim();
            }
        });
    }

    private void ThreeBook() {
        String[] array = new String[]{
                "Physics-2 (25922)", "Mathematics-3 (25931)", "Digital Electronics (26831)", "Social Science (25811)",
                "Application Dev Using Python (28531)",
                "Computer Graphics Design-2 (28532)", "IT Support Services (28533)"
        };
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(
                getContext(),
                R.layout.subject_sample_layout,
                array
        );
        AutoCompleteTextView autoCompleteTextView = view.findViewById(R.id.threeBookACTV);
        autoCompleteTextView.setAdapter(arrayAdapter);
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                threeBook = autoCompleteTextView.getText().toString().trim();
            }
        });
    }

    private void FourBook() {
        String[] array = new String[]{
                "Physics-2 (25922)", "Mathematics-3 (25931)", "Digital Electronics (26831)", "Social Science (25811)",
                "Application Dev Using Python (28531)",
                "Computer Graphics Design-2 (28532)", "IT Support Services (28533)"
        };
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(
                getContext(),
                R.layout.subject_sample_layout,
                array
        );
        AutoCompleteTextView autoCompleteTextView = view.findViewById(R.id.fourBookACTV);
        autoCompleteTextView.setAdapter(arrayAdapter);
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                fourBook = autoCompleteTextView.getText().toString().trim();
            }
        });
    }

    private void FiveBook() {
        String[] array = new String[]{
                "Physics-2 (25922)", "Mathematics-3 (25931)", "Digital Electronics (26831)", "Social Science (25811)",
                "Application Dev Using Python (28531)",
                "Computer Graphics Design-2 (28532)", "IT Support Services (28533)"
        };
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(
                getContext(),
                R.layout.subject_sample_layout,
                array
        );
        AutoCompleteTextView autoCompleteTextView = view.findViewById(R.id.fiveBookACTV);
        autoCompleteTextView.setAdapter(arrayAdapter);
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                fiveBook = autoCompleteTextView.getText().toString().trim();
            }
        });
    }

    private void SixBook() {
        String[] array = new String[]{
                "Physics-2 (25922)", "Mathematics-3 (25931)", "Digital Electronics (26831)", "Social Science (25811)",
                "Application Dev Using Python (28531)",
                "Computer Graphics Design-2 (28532)", "IT Support Services (28533)"
        };
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(
                getContext(),
                R.layout.subject_sample_layout,
                array
        );
        AutoCompleteTextView autoCompleteTextView = view.findViewById(R.id.sixBookACTV);
        autoCompleteTextView.setAdapter(arrayAdapter);
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                sixBook = autoCompleteTextView.getText().toString().trim();
            }
        });
    }

    private void TotalBooks() {
        String[] array = new String[]{
                "01", "02", "03", "04", "05", "06", "07"
        };
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(
                getContext(),
                R.layout.subject_sample_layout,
                array
        );
        AutoCompleteTextView autoCompleteTextView = view.findViewById(R.id.totalBookACTV);
        autoCompleteTextView.setAdapter(arrayAdapter);
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                totalBooks = autoCompleteTextView.getText().toString().trim();
            }
        });
    }

    private void Semister() {
        String[] array = new String[]{
                "01", "02", "03", "04", "05", "06", "07"
        };
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(
                getContext(),
                R.layout.subject_sample_layout,
                array
        );
        AutoCompleteTextView autoCompleteTextView = view.findViewById(R.id.semisterAUTV);
        autoCompleteTextView.setAdapter(arrayAdapter);
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                semister = autoCompleteTextView.getText().toString().trim();
            }
        });
    }
}