package com.example.trocatine.fragments;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.trocatine.R;
import com.example.trocatine.adapter.AdapterProduct;
import com.example.trocatine.models.Product;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }
    private RecyclerView productRv;
    private TextView initialText;
    private Dialog dialog;
    private Button buttonCancel;
    private ImageButton buttonFilter;

    List<Product> listProduct = new ArrayList<>();

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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

        // Configurando Recycle View

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        productRv = view.findViewById(R.id.productRv);
        buttonFilter = view.findViewById(R.id.buttonFilter);
        productRv.setLayoutManager(new GridLayoutManager(view.getContext(), 2));
        //Populando a recycle view
        listProduct.add(new Product(1, 1, "nome", "descricao", 2.99, 5, "05/12/2007", true));
        listProduct.add(new Product(1, 1, "nome", "descricao", 2.99, 5, "05/12/2007", true));
        listProduct.add(new Product(1, 1, "nome", "descricao", 2.99, 5, "05/12/2007", true));
        listProduct.add(new Product(1, 1, "nome", "descricao", 2.99, 5, "05/12/2007", true));
        listProduct.add(new Product(1, 1, "nome", "descricao", 2.99, 5, "05/12/2007", true));
        listProduct.add(new Product(1, 1, "nome", "descricao", 2.99, 5, "05/12/2007", true));

        AdapterProduct adapterProduct = new AdapterProduct(listProduct);
        productRv.setAdapter(adapterProduct);
        productRv.setLayoutManager(new GridLayoutManager(view.getContext(), 2));

        //Configurando o filtro

        dialog = new Dialog(view.getContext());
        dialog.setContentView(R.layout.card_filter);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.custom_dialog_bg);
        dialog.setCancelable(false);
        buttonCancel = dialog.findViewById(R.id.buttonCancel);
        buttonFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
            }
        });
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        return view;
    }
}