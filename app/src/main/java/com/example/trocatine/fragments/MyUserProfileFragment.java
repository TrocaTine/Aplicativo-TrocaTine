package com.example.trocatine.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.trocatine.R;
import com.example.trocatine.adapter.ViewPagerAdapter;
import com.example.trocatine.database.DatabaseCamera;
import com.example.trocatine.userProfile.EditProfile;
import com.example.trocatine.util.UserUtil;
import com.google.android.material.tabs.TabLayout;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MyUserProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyUserProfileFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private ViewPagerAdapter viewPagerAdapter;
    private TabLayout tabLayout;
    private ViewPager2 viewPager;
    private TextView userName, userEmail, userPhone, userAdress, userCpf, userBirthDate;
    private ImageButton buttonEditProfile;
    private ImageView backSet;
    private ImageView userImg;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    DatabaseCamera databaseCamera;

    public MyUserProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MyUserProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MyUserProfileFragment newInstance(String param1, String param2) {
        MyUserProfileFragment fragment = new MyUserProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_user_profile, container, false);
        backSet = view.findViewById(R.id.backSet);

        userEmail = view.findViewById(R.id.userEmail);
        userName = view.findViewById(R.id.userName);
        userCpf = view.findViewById(R.id.userCpf);
        userPhone = view.findViewById(R.id.userPhone);
        userAdress = view.findViewById(R.id.userAddress);
        userBirthDate = view.findViewById(R.id.userBirthDate);

        userEmail.setText(UserUtil.email);
        userBirthDate.setText(UserUtil.birthDate);
        userAdress.setText(UserUtil.address);
        userPhone.setText(UserUtil.phone);
        userName.setText(UserUtil.fullName);
        userCpf.setText(UserUtil.cpf);

        userImg = view.findViewById(R.id.userImg);
        if (UserUtil.imageProfile != null){
            Glide.with(userImg.getContext())
                    .asBitmap()
                    .load(UserUtil.imageProfile)
                    .circleCrop()
                    .into(userImg);
        } else {
            Log.e("userprofile image profile", "null");
        }

        Log.e("userprofile", UserUtil.email+" "+UserUtil.fullName+" "+UserUtil.address+" "+UserUtil.birthDate+" "+UserUtil.cpf+" "+UserUtil.phone);
        buttonEditProfile = view.findViewById(R.id.openEditProfile);

        buttonEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), EditProfile.class);
                startActivity(intent);
            }
        });
        backSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(view.getContext(), HomeFragment.class);
                startActivity(intent);
            }
        });
        tabLayout = view.findViewById(R.id.tabLayout);
        viewPager = view.findViewById(R.id.viewPager2);
        viewPagerAdapter = new ViewPagerAdapter(this);
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
        return view;
    }
}