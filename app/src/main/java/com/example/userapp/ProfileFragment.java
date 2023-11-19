package com.example.userapp;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileFragment extends Fragment {
    public ProfileFragment() {
        // Required empty public constructor
    }
    CircleImageView profileImage;
    TextView profileName;
    TextView profileDepartment;
    TextView profileSemister;
    String name;
    String department = "Computer Department";
    String number;
    String semister;
    String imageUrl;
    ImageButton nameEdit,aboutEdit,skillEdit;
    SwipeRefreshLayout profileRefreshLayout;
    ProgressBar profileProgressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        profileImage = view.findViewById(R.id.profileImageView);
        profileName = view.findViewById(R.id.nameTextView);
        profileDepartment = view.findViewById(R.id.departmentTextView);
        profileSemister = view.findViewById(R.id.semisterTextView);
        nameEdit = view.findViewById(R.id.nameEditImageButton);
        aboutEdit = view.findViewById(R.id.aboutEditImageButton);
        skillEdit = view.findViewById(R.id.skillEditImageButton);
        profileRefreshLayout = view.findViewById(R.id.profileRefreshLayout);
        profileProgressBar = view.findViewById(R.id.profileProgressBar);

        profileRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                LoadProfile();
                profileRefreshLayout.setRefreshing(false);
            }
        });

        aboutEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Coming soon", Toast.LENGTH_SHORT).show();
            }
        });
        skillEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Coming soon", Toast.LENGTH_SHORT).show();
            }
        });

        nameEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext().getApplicationContext(),NameEditActivity.class);
                intent.putExtra("name",name);
                intent.putExtra("department",department);
                intent.putExtra("semister",semister);
                intent.putExtra("url",imageUrl);
                intent.putExtra("number",number);
                startActivity(intent);
            }
        });
        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetUri();
            }
        });
        LoadProfile();


        return view;
    }

    private void LoadProfile() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("computer")
                .child("profile")
                .child(FirebaseAuth.getInstance().getUid());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    ProfileModelClass profileModelClass = dataSnapshot.getValue(ProfileModelClass.class);
                    name = profileModelClass.getName();
                    semister = profileModelClass.getSemister();
                    department = profileModelClass.getDepartment();
                    number = profileModelClass.getNumber();
                    imageUrl = profileModelClass.getUrl();

                    profileName.setText(name);
                    profileDepartment.setText(department);
                    profileSemister.setText(semister);
                    Picasso.get().load(imageUrl).placeholder(R.drawable.profile_big_icon).into(profileImage);
                    profileRefreshLayout.setVisibility(View.VISIBLE);
                    profileProgressBar.setVisibility(View.GONE);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void GetUri() {
        Dexter.withContext(getContext())
                .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                        Intent  intent = new Intent(Intent.ACTION_PICK);
                        intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(intent,111);
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                        permissionToken.continuePermissionRequest();
                    }
                }).check();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && requestCode == 111){
            assert data != null;
            Uri uri = data.getData();
             StorageReference storageReference = FirebaseStorage.getInstance().getReference()
                     .child("profileImage/"+System.currentTimeMillis()+"."+GetFileExtention(uri));
             storageReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                 @Override
                 public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                     storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                         @Override
                         public void onSuccess(Uri uri) {
                             ProfileModelClass profileModelClass = new ProfileModelClass(name,department,semister
                             ,number,uri.toString());
                             DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("computer")
                                     .child("profile")
                                     .child(FirebaseAuth.getInstance().getUid())
                                     .child("user");
                             databaseReference.setValue(profileModelClass).addOnCompleteListener(new OnCompleteListener<Void>() {
                                 @Override
                                 public void onComplete(@NonNull Task<Void> task) {
                                     if(task.isSuccessful()){
                                         Toast.makeText(getContext(), "upload", Toast.LENGTH_SHORT).show();
                                     }
                                 }
                             });
                         }
                     });
                 }
             });

        }
    }

    private String GetFileExtention(Uri imageUrl) {
        ContentResolver contentResolver = getContext().getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(imageUrl));
    }

}