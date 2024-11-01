package com.example.trocatine.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;


import com.example.trocatine.ui.userProfile.MyUserProfileFragment;

import androidx.viewpager2.adapter.FragmentStateAdapter;

import tabs.FavAnnouncement;
import tabs.MyAnnouncement;

public class ViewPagerAdapter extends FragmentStateAdapter {
    public ViewPagerAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new FavAnnouncement();
            case 1:
                return new MyAnnouncement();
            default:
                return new MyUserProfileFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
