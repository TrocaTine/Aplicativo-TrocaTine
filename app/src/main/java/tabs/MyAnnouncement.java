package tabs;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.trocatine.R;
import com.example.trocatine.adapter.AdapterMyProduct;
import com.example.trocatine.adapter.AdapterProduct;
import com.example.trocatine.models.Product;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MyAnnouncement#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyAnnouncement extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private RecyclerView productRv;
    List<Product> listProduct = new ArrayList<>();


    public MyAnnouncement() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MyAnnouncement.
     */
    // TODO: Rename and change types and number of parameters
    public static MyAnnouncement newInstance(String param1, String param2) {
        MyAnnouncement fragment = new MyAnnouncement();
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
        View view = inflater.inflate(R.layout.fragment_my_announcement, container, false);
        productRv = view.findViewById(R.id.productRv);
        productRv.setLayoutManager(new GridLayoutManager(view.getContext(), 2));
        //Populando a recycle view
        listProduct.add(new Product(1, 1, "nome", "descricao", 2.99, 5, "05/12/2007", true));
        listProduct.add(new Product(1, 1, "nome", "descricao", 2.99, 5, "05/12/2007", true));
        listProduct.add(new Product(1, 1, "nome", "descricao", 2.99, 5, "05/12/2007", true));
        listProduct.add(new Product(1, 1, "nome", "descricao", 2.99, 5, "05/12/2007", true));
        listProduct.add(new Product(1, 1, "nome", "descricao", 2.99, 5, "05/12/2007", true));
        listProduct.add(new Product(1, 1, "nome", "descricao", 2.99, 5, "05/12/2007", true));

        AdapterMyProduct adapterMyProduct = new AdapterMyProduct(listProduct);
        productRv.setAdapter(adapterMyProduct);
        productRv.setLayoutManager(new GridLayoutManager(view.getContext(), 2));
        // Inflate the layout for this fragment
        return view;
    }
}