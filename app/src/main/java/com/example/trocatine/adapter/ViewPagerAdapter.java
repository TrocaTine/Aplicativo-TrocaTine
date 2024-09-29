package com.example.trocatine.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;


import com.example.trocatine.fragments.UserProfileFragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.List;

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
                return new UserProfileFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
