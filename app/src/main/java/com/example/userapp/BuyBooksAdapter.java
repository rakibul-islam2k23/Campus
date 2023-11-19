package com.example.userapp;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class BuyBooksAdapter extends RecyclerView.Adapter<BuyBooksAdapter.ViewHolder> {
    @NonNull
    Context context;
    ArrayList<SellBooksModelClass> arrayList = new ArrayList<>();

    public BuyBooksAdapter(@NonNull Context context, ArrayList<SellBooksModelClass> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @Override
    public BuyBooksAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.buy_book_sample_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BuyBooksAdapter.ViewHolder holder, int position) {
        SellBooksModelClass sellBooksModelClass = arrayList.get(position);
        String nameOfUser = sellBooksModelClass.getName();
        String departmentOfUser = sellBooksModelClass.getDepartment();
        String priceOfUser = sellBooksModelClass.getPrice();
        String semisterOfUser = sellBooksModelClass.getSemister();
        String totalBooks = sellBooksModelClass.getTotalBooks();


        holder.name.setText(nameOfUser);
        holder.department.setText(sellBooksModelClass.getDepartment());
        holder.taka.setText(priceOfUser + " Taka");
        holder.semister.setText(semisterOfUser);
        holder.department.setText(departmentOfUser);
        holder.booksCount.setText(totalBooks);

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("computer")
                .child("bookRequest");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    int count = (int) dataSnapshot.getChildrenCount();
                    holder.requestCount.setText(""+count);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




        holder.fullLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, BookDetailsActivity.class);
                intent.putExtra("problem", sellBooksModelClass.getProblem());
                intent.putExtra("one", sellBooksModelClass.getOneBook());
                intent.putExtra("two", sellBooksModelClass.getTwoBook());
                intent.putExtra("three", sellBooksModelClass.getThreeBook());
                intent.putExtra("four", sellBooksModelClass.getFourBook());
                intent.putExtra("five", sellBooksModelClass.getFiveBook());
                intent.putExtra("six", sellBooksModelClass.getSixBook());
                intent.putExtra("price", sellBooksModelClass.getPrice());
                intent.putExtra("name", sellBooksModelClass.getName());
                intent.putExtra("date", sellBooksModelClass.getTimeAndDate());
                intent.putExtra("number", sellBooksModelClass.getNumber());
                intent.putExtra("userUid", sellBooksModelClass.getUid());
                context.startActivity(intent);

            }
        });


    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView department;
        android.widget.Button taka;
        TextView booksCount;
        TextView semister;
        LinearLayout fullLayout;
        TextView requestCount;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.userName);
            department = itemView.findViewById(R.id.userDepartment);
            taka = itemView.findViewById(R.id.userTaka);
            booksCount = itemView.findViewById(R.id.userBookCount);
            semister = itemView.findViewById(R.id.userSemister);
            fullLayout = itemView.findViewById(R.id.fullLayout);
            requestCount = itemView.findViewById(R.id.requestCount);
        }
    }
}
