package com.example.userapp;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity<val> extends AppCompatActivity {
    // Replace the below with your own ONESIGNAL_APP_ID
    private static final String ONESIGNAL_APP_ID = "9af081ee-c2b7-476d-9b31-ac8cfb3f75c5";
    private LinearLayout homeLayout,classTimeLayout,assignmentLayout,profileLayout;
    private ImageView home_icon,classTime_icon,assignment_icon,profile_icon;
    private TextView home_text,classTime_text,assignment_text,profile_text;

    private DrawerLayout drawerLayout;
     NavigationView navigationView;
     Toolbar toolBarOfMain;

    static final float END_SCALE = 0f;
    private RelativeLayout contentView;

    String headerName;
    String headerDepartment;
    String headerRoll;
    String headerSemister;
    String headerNumber;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        homeLayout = findViewById(R.id.home_layout);
        classTimeLayout = findViewById(R.id.classTime_layout);
        assignmentLayout = findViewById(R.id.assignment_layout);
        profileLayout = findViewById(R.id.profile_layout);


        home_icon = findViewById(R.id.home_icon);
        classTime_icon = findViewById(R.id.classTime_icon);
        assignment_icon = findViewById(R.id.assignment_icon);
        profile_icon = findViewById(R.id.profile_icon);

        home_text = findViewById(R.id.home_text);
        classTime_text = findViewById(R.id.classTime_text);
        assignment_text = findViewById(R.id.assignment_text);
        profile_text = findViewById(R.id.profile_text);


        toolBarOfMain = findViewById(R.id.toolBarOfMain);
        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navigationView);

        contentView = findViewById(R.id.contentView);



//        toolbar design----------------------------------------------------------------------------
        setSupportActionBar(toolBarOfMain);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        toolBarOfMain.setVisibility(View.VISIBLE);




//        navigation drawer design
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(MainActivity.this
                ,drawerLayout,toolBarOfMain
        ,R.string.OpenNavigationDrawer,R.string.CloseNavigationDrawer);

        //After instantiating your ActionBarDrawerToggle
        toggle.setDrawerIndicatorEnabled(false);
        Drawable drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.drawermenu_icon,MainActivity.this.getTheme());
        toggle.setHomeAsUpIndicator(drawable);
        toggle.setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawerLayout.isDrawerVisible(GravityCompat.START)) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else {
                    drawerLayout.openDrawer(GravityCompat.START);
                }
            }
        });
        LoadData();





        drawerLayout.addDrawerListener(toggle);
        navigationView.bringToFront();
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int navItemValue = item.getItemId();
                if(navItemValue == R.id.home){
                    getSupportFragmentManager().beginTransaction()
                            .setReorderingAllowed(true)
                            .replace(R.id.frameLayout,HomeFragment.class,null)
                            .commit();
                    drawerLayout.closeDrawers();
                }
                else if(navItemValue == R.id.buyBooks){
                    getSupportFragmentManager().beginTransaction()
                            .setReorderingAllowed(true)
                            .replace(R.id.frameLayout, BuyBooksFragment.class,null)
                            .commit();
                    drawerLayout.closeDrawers();
                }
                else if(navItemValue == R.id.sellBooks){
                    getSupportFragmentManager().beginTransaction()
                            .setReorderingAllowed(true)
                            .replace(R.id.frameLayout, SellBooksFragment.class,null)
                            .commit();
                    drawerLayout.closeDrawers();
                }
                else if(navItemValue == R.id.teacher){
                    getSupportFragmentManager().beginTransaction()
                            .setReorderingAllowed(true)
                            .replace(R.id.frameLayout,TeacherFragment.class,null)
                            .commit();
                    drawerLayout.closeDrawers();
                }
                else if(navItemValue == R.id.videoLectures){
                    getSupportFragmentManager().beginTransaction()
                            .setReorderingAllowed(true)
                            .replace(R.id.frameLayout,VideoLectureFragment.class,null)
                            .commit();
                    drawerLayout.closeDrawers();
                }
                else if(navItemValue == R.id.saved){
                    getSupportFragmentManager().beginTransaction()
                            .setReorderingAllowed(true)
                            .replace(R.id.frameLayout,SavedFragment.class,null)
                            .commit();
                    drawerLayout.closeDrawers();
                }
                else if(navItemValue == R.id.payment){
                    getSupportFragmentManager().beginTransaction()
                            .setReorderingAllowed(true)
                            .replace(R.id.frameLayout,PaySemisterFeeFragment.class,null)
                            .commit();
                    drawerLayout.closeDrawers();
                }
                else if(navItemValue == R.id.setting){
                    getSupportFragmentManager().beginTransaction()
                            .setReorderingAllowed(true)
                            .replace(R.id.frameLayout,SettingFragment.class,null)
                            .commit();
                    drawerLayout.closeDrawers();
                }
                else{
                    getSupportFragmentManager().beginTransaction()
                            .setReorderingAllowed(true)
                            .replace(R.id.frameLayout,DeveloperFragment.class,null)
                            .commit();
                    drawerLayout.closeDrawers();
                }




                return true;
            }
        });





//        bottom navigationView design--------------------------------------------------------------
        LoadDefault();
        homeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction()
                        .setReorderingAllowed(true)
                        .replace(R.id.frameLayout,HomeFragment.class,null)
                        .commit();

                toolBarOfMain.setVisibility(View.VISIBLE);
                homeLayout.setBackground(getDrawable(R.drawable.home_round));
                home_icon.setImageDrawable(getDrawable(R.drawable.home_icon_selected));
                home_text.setVisibility(View.VISIBLE);

                classTimeLayout.setBackground(getDrawable(android.R.color.transparent));
                classTime_icon.setImageDrawable(getDrawable(R.drawable.classtime_icon));
                classTime_text.setVisibility(View.GONE);

                assignmentLayout.setBackground(getDrawable(android.R.color.transparent));
                assignment_icon.setImageDrawable(getDrawable(R.drawable.assigenment_icon));
                assignment_text.setVisibility(View.GONE);

                profileLayout.setBackground(getDrawable(android.R.color.transparent));
                profile_icon.setImageDrawable(getDrawable(R.drawable.profile_icon));
                profile_text.setVisibility(View.GONE);


//            make animation on bottom icon change
                ScaleAnimation scaleAnimation = new ScaleAnimation(0.5f,1.0f,1f,1f,
                        Animation.RELATIVE_TO_SELF,1f,Animation.RELATIVE_TO_SELF,1f);
                scaleAnimation.setDuration(400);
                scaleAnimation.setFillAfter(true);
                homeLayout.setAnimation(scaleAnimation);
            }
        });

        classTimeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction()
                        .setReorderingAllowed(true)
                        .replace(R.id.frameLayout,ClassTimeFragment.class,null)
                        .commit();

                toolBarOfMain.setVisibility(View.VISIBLE);

                classTimeLayout.setBackground(getDrawable(R.drawable.classtime_round));
                classTime_icon.setImageDrawable(getDrawable(R.drawable.classtime_icon));
                classTime_text.setVisibility(View.VISIBLE);

                homeLayout.setBackground(getDrawable(android.R.color.transparent));
                home_icon.setImageDrawable(getDrawable(R.drawable.home_icon));
                home_text.setVisibility(View.GONE);

                assignmentLayout.setBackground(getDrawable(android.R.color.transparent));
                assignment_icon.setImageDrawable(getDrawable(R.drawable.assigenment_icon));
                assignment_text.setVisibility(View.GONE);

                profileLayout.setBackground(getDrawable(android.R.color.transparent));
                profile_icon.setImageDrawable(getDrawable(R.drawable.profile_icon));
                profile_text.setVisibility(View.GONE);


//            make animation on bottom icon change
                ScaleAnimation scaleAnimation = new ScaleAnimation(0.0f,1.0f,1f,1f,
                        Animation.RELATIVE_TO_SELF,1f,Animation.RELATIVE_TO_SELF,1f);
                scaleAnimation.setDuration(400);
                scaleAnimation.setFillAfter(true);
                classTimeLayout.setAnimation(scaleAnimation);
            }
        });

        assignmentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction()
                        .setReorderingAllowed(true)
                        .replace(R.id.frameLayout,AssignmentFragment.class,null)
                        .commit();
                toolBarOfMain.setVisibility(View.VISIBLE);

                assignmentLayout.setBackground(getDrawable(R.drawable.assigenment_round));
                assignment_icon.setImageDrawable(getDrawable(R.drawable.assigenment_icon_selected));
                assignment_text.setVisibility(View.VISIBLE);

                homeLayout.setBackground(getDrawable(android.R.color.transparent));
                home_icon.setImageDrawable(getDrawable(R.drawable.home_icon));
                home_text.setVisibility(View.GONE);

                classTimeLayout.setBackground(getDrawable(android.R.color.transparent));
                classTime_icon.setImageDrawable(getDrawable(R.drawable.classtime_icon));
                classTime_text.setVisibility(View.GONE);

                profileLayout.setBackground(getDrawable(android.R.color.transparent));
                profile_icon.setImageDrawable(getDrawable(R.drawable.profile_icon));
                profile_text.setVisibility(View.GONE);


//            make animation on bottom icon change
                ScaleAnimation scaleAnimation = new ScaleAnimation(0.0f,1.0f,1f,1f,
                        Animation.RELATIVE_TO_SELF,1f,Animation.RELATIVE_TO_SELF,1f);
                scaleAnimation.setDuration(400);
                scaleAnimation.setFillAfter(true);
                assignmentLayout.setAnimation(scaleAnimation);
            }
        });

        profileLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction()
                        .setReorderingAllowed(true)
                        .replace(R.id.frameLayout,ProfileFragment.class,null)
                        .commit();
                toolBarOfMain.setVisibility(View.VISIBLE);

                profileLayout.setBackground(getDrawable(R.drawable.profile_round));
                profile_icon.setImageDrawable(getDrawable(R.drawable.profile_icon_selected));
                profile_text.setVisibility(View.VISIBLE);

                homeLayout.setBackground(getDrawable(android.R.color.transparent));
                home_icon.setImageDrawable(getDrawable(R.drawable.home_icon));
                home_text.setVisibility(View.GONE);

                classTimeLayout.setBackground(getDrawable(android.R.color.transparent));
                classTime_icon.setImageDrawable(getDrawable(R.drawable.classtime_icon));
                classTime_text.setVisibility(View.GONE);

                assignmentLayout.setBackground(getDrawable(android.R.color.transparent));
                assignment_icon.setImageDrawable(getDrawable(R.drawable.assigenment_icon));
                assignment_text.setVisibility(View.GONE);


//            make animation on bottom icon change
                ScaleAnimation scaleAnimation = new ScaleAnimation(0.0f,1.0f,1f,1f,
                        Animation.RELATIVE_TO_SELF,1f,Animation.RELATIVE_TO_SELF,1f);
                scaleAnimation.setDuration(400);
                scaleAnimation.setFillAfter(true);
                profileLayout.setAnimation(scaleAnimation);
            }
        });






    }

    private void LoadData() {
        View header = navigationView.getHeaderView(0);
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("computer")
                .child("profile").child(FirebaseAuth.getInstance().getUid());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snapshot1 : snapshot.getChildren()){
                    ProfileModelClass profileModelClass = snapshot1.getValue(ProfileModelClass.class);
                    headerName = profileModelClass.getName();
                    headerDepartment = profileModelClass.getDepartment();
                    headerSemister = profileModelClass.getSemister();
                    headerNumber = profileModelClass.getNumber();

                    TextView department = header.findViewById(R.id.department);
                    TextView name = header.findViewById(R.id.headerName2);
                    TextView roll = header.findViewById(R.id.roll2);
                    TextView semister = header.findViewById(R.id.semister2);
                    TextView number = header.findViewById(R.id.number2);
                    department.setText(headerDepartment);
                    name.setText(headerName);
                    roll.setText(headerRoll);
                    semister.setText(headerSemister);
                    number.setText(headerNumber);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    private void LoadDefault() {

        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.frameLayout,HomeFragment.class,null)
                .commit();

        toolBarOfMain.setVisibility(View.VISIBLE);

        homeLayout.setBackground(getDrawable(R.drawable.home_round));
        home_icon.setImageDrawable(getDrawable(R.drawable.home_icon_selected));
        home_text.setVisibility(View.VISIBLE);

        classTimeLayout.setBackground(getDrawable(android.R.color.transparent));
        classTime_icon.setImageDrawable(getDrawable(R.drawable.classtime_icon));
        classTime_text.setVisibility(View.GONE);

        assignmentLayout.setBackground(getDrawable(android.R.color.transparent));
        assignment_icon.setImageDrawable(getDrawable(R.drawable.assigenment_icon));
        assignment_text.setVisibility(View.GONE);

        profileLayout.setBackground(getDrawable(android.R.color.transparent));
        profile_icon.setImageDrawable(getDrawable(R.drawable.profile_icon));
        profile_text.setVisibility(View.GONE);
    }

//    navigation drawer animation
private void animateNavigationDrawer() {
    //Add any color or remove it to use the default one!
    //To make it transparent use Color.Transparent in side setScrimColor();
    //drawerLayout.setScrimColor(Color.TRANSPARENT);
    drawerLayout.setScrimColor(Color.TRANSPARENT);
    drawerLayout.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
        @Override
        public void onDrawerSlide(View drawerView, float slideOffset) {
            // Scale the View based on current slide offset
            final float diffScaledOffset = slideOffset * (1 - END_SCALE);
            final float offsetScale = 1 - diffScaledOffset;
            contentView.setScaleX(offsetScale);
            contentView.setScaleY(offsetScale);
            // Translate the View, accounting for the scaled width
            final float xOffset = drawerView.getWidth() * slideOffset;
            final float xOffsetDiff = contentView.getWidth() * diffScaledOffset / 2;
            final float xTranslation = xOffset - xOffsetDiff;
            contentView.setTranslationX(xTranslation);
        }
    });
}

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }
    }
}

