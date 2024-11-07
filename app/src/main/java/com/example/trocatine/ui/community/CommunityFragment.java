package com.example.trocatine.ui.community;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.trocatine.R;
import com.example.trocatine.adapter.RecycleViewModels.Community;
import com.example.trocatine.adapter.AdapterCommunity;
import com.example.trocatine.util.UserUtil;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CommunityFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CommunityFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private RecyclerView communityRv;
    private ImageView back;

    private RecyclerView messageTradeRv;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://trocatine-a226a-default-rtdb.firebaseio.com/");
    List<Community> listCommunity = new ArrayList<>();
    List<Community> listMessageTrade = new ArrayList<>();
    NavController navController;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CommunityFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @
     *               [param param2 Parameter 2.
     * @return A new instance of fragment CommunityFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CommunityFragment newInstance(String param1, String param2) {
        CommunityFragment fragment = new CommunityFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_community, container, false);

        Log.e("USER INFO", "USER: " + UserUtil.token);
        Log.e("USER INFO", "USER: " + UserUtil.userName);
        Log.e("USER INFO", "USER: " + UserUtil.fullName);

        back = view.findViewById(R.id.backSet);



        messageTradeRv = view.findViewById(R.id.tradeMessagesRv);

        messageTradeRv.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false));

        communityRv = view.findViewById(R.id.communityRv);
        communityRv.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false));

        navController = NavHostFragment.findNavController(this);

        AdapterCommunity adapterCommunity = new AdapterCommunity(listCommunity, navController);
        communityRv.setAdapter(adapterCommunity);

        AdapterCommunity adapterTrade = new AdapterCommunity(listMessageTrade, navController);
        messageTradeRv.setAdapter(adapterTrade);

        databaseReference.child("comunity").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listCommunity.clear();
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    Community itemList = new Community(snapshot1.child("name").getValue(String.class),
                            snapshot1.child("photo").getValue(String.class), true);
                    listCommunity.add(itemList);
                    Log.e("CHAT COMUNITY", "LISTA Nome: " + itemList.getName() + ", Foto: " + itemList.getPhoto());
                }


                adapterCommunity.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("CHAT COMUNITY", "Erro ao carregar dados: " + error.getMessage());
            }
        });
        databaseReference.child("chat").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listMessageTrade.clear();

                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    String user1 = snapshot1.child("user_1").getValue(String.class);
                    String user2 = snapshot1.child("user_2").getValue(String.class);

                    if (UserUtil.userName != null &&
                            ((user1 != null && user1.equals(UserUtil.userName)) || (user2 != null && user2.equals(UserUtil.userName)))) {

                        // Cria o item a ser adicionado
                        Community itemList = new Community();

                        // Define o nome do outro usuário na conversa
                        itemList.setName(user1.equals(UserUtil.userName) ? user2 : user1);
                        itemList.setPhoto("https://media.istockphoto.com/id/1399395748/pt/foto/cheerful-business-woman-with-glasses-posing-with-her-hands-under-her-face-showing-her-smile-in.jpg?s=612x612&w=0&k=20&c=V2hdZm-cPTPXYT4U7VEsXr9M4CR3QqxOCMY_2qqJQAI=");
                        itemList.setCommunity(false);

                        // Adiciona à lista de mensagens
                        listMessageTrade.add(itemList);

                        Log.e("CHAT message trade", "LISTA Nome: " + itemList.getName() + ", Foto: " + itemList.getPhoto());
                    }
                }

                // Notifica o adapter que os dados foram atualizados
                adapterTrade.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("CHAT COMMUNITY", "Erro ao carregar dados: " + error.getMessage());
            }
        });





        return view;
    }

}