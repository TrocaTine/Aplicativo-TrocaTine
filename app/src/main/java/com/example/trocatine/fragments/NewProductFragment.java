package com.example.trocatine.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.trocatine.R;
import com.example.trocatine.newProduct.NewProductTrade1;
import com.example.trocatine.util.AndroidUtil;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NewProductFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewProductFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    // TODO: Rename and change types of parameters
    private Button buttonTrade;
    private String mParam1;
    String emailUser, token;
    private String mParam2;

    public NewProductFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NewProductFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NewProductFragment newInstance(String param1, String param2) {
        NewProductFragment fragment = new NewProductFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("New Product Fragment", "Email do usuario: "+AndroidUtil.email+"- Token: "+AndroidUtil.token);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_new_product, container, false);
        buttonTrade = view.findViewById(R.id.buttonTrade);

        Log.e("NEW PRODUCT FRAGMENT", "emailuser"+emailUser+" - token: "+token);
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