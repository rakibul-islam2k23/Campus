package com.example.userapp;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class ClassPagerAdapter extends FragmentPagerAdapter {
    int tabCount;
    public ClassPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
        tabCount = behavior;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new SatFragment();
            case 1:
                return new SunFragment();
            case 2:
                return new MonFragment();
            case 3:
                return new TueFragment();
            case 4:
                return new WedFragment();
            case 5:
                return new ThuFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}