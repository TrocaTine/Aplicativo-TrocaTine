package com.example.trocatine.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.trocatine.R;
import com.example.trocatine.buy.Buy1;
import com.example.trocatine.newProduct.NewProduct3;
import com.example.trocatine.newProduct.NewProductTrade1;
import com.google.android.material.badge.BadgeUtils;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link newProductFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class newProductFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    // TODO: Rename and change types of parameters
    private Button buttonTrade;
    private String mParam1;
    private String mParam2;

    public newProductFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment newProductFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static newProductFragment newInstance(String param1, String param2) {
        newProductFragment fragment = new newProductFragment();
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

        View view = inflater.inflate(R.layout.fragment_new_product, container, false);
        buttonTrade = view.findViewById(R.id.buttonTrade);
        buttonTrade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), NewProductTrade1.class);
                startActivity(intent);
            }
        });
        return view;


    }
}