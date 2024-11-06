package com.example.trocatine.ui.community;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.renderscript.ScriptGroup;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.trocatine.R;
import com.example.trocatine.adapter.AdapterChatCommunity;
import com.example.trocatine.adapter.RecycleViewModels.ChatCommunity;
import com.example.trocatine.adapter.RecycleViewModels.MessageChat;
import com.example.trocatine.ui.database.MemoryData;
import com.example.trocatine.ui.product.productTrade.Chat;
import com.example.trocatine.util.ChatCommunityUtil;
import com.example.trocatine.util.UserUtil;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;



public class CommunityChatFragment extends Fragment {

    private RecyclerView recyclerView;
    private ImageView sendBtn;
    private NavController navController;
    private TextView txt;
    private CircleImageView photo;
    private List<ChatCommunity> listCommunity = new ArrayList<>();


    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://trocatine-a226a-default-rtdb.firebaseio.com/");

    public CommunityChatFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_community_chat, container, false);

        navController = NavHostFragment.findNavController(this);

        txt = view.findViewById(R.id.name);
        photo = view.findViewById(R.id.profilePic);
        sendBtn = view.findViewById(R.id.sendBtn);
        final EditText messageEditText = view.findViewById(R.id.messageEditText);

        databaseReference.child("comunity").child(ChatCommunityUtil.chatKey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                txt.setText(ChatCommunityUtil.name);

                String photoUrl = snapshot.child("photo").getValue(String.class);
                if (photoUrl != null) {
                    Picasso.get().load(photoUrl).into(photo);
                }

                ChatCommunityUtil.photo = photoUrl;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        recyclerView = view.findViewById(R.id.chattingRecyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false));
        AdapterChatCommunity adapterChatCommunity = new AdapterChatCommunity(listCommunity);
        recyclerView.setAdapter(adapterChatCommunity);

        databaseReference.child("comunity").child(ChatCommunityUtil.chatKey).child("post").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listCommunity.clear();
                for (DataSnapshot dataSnapshot1 : snapshot.getChildren()) {
                    ChatCommunity itemList = new ChatCommunity(
                            dataSnapshot1.child("nickname").getValue(String.class),
                            dataSnapshot1.child("photo").getValue(String.class),
                            dataSnapshot1.child("message").getValue(String.class)
                    );

                    listCommunity.add(itemList);
                }


                Log.d("CommunityComeInFragment", "Número de itens carregados: " + listCommunity.size());


                adapterChatCommunity.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("CommunityComeInFragment", "Erro ao carregar dados: " + error.getMessage());
            }
        });



//        sendBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (ChatCommunityUtil.chatKey != null) {
//                    String message = messageEditText.getText().toString();
//                    String messageKey = String.valueOf(listCommunity.size() + 1);
//                    databaseReference.child("comunity").child(ChatCommunityUtil.chatKey).child("post").child(messageKey).child("nickname").setValue(UserUtil.userName);
//                    databaseReference.child("comunity").child(ChatCommunityUtil.chatKey).child("post").child(messageKey).child("message").setValue(message);
//                    databaseReference.child("comunity").child(ChatCommunityUtil.chatKey).child("post").child(messageKey).child("photo").setValue("https://media.istockphoto.com/id/1399395748/pt/foto/cheerful-business-woman-with-glasses-posing-with-her-hands-under-her-face-showing-her-smile-in.jpg?s=612x612&w=0&k=20&c=V2hdZm-cPTPXYT4U7VEsXr9M4CR3QqxOCMY_2qqJQAI=");
//
//                    MemoryData.saveData("nickname", v.getContext());
//                    MemoryData.saveData("message", v.getContext());
//                    MemoryData.saveData("photo", v.getContext());
//
//                    messageEditText.setText("");
//
//                } else {
//                    Log.e("CommunityComeInFragment", "chatKey está nulo ao tentar entrar na comunidade.");
//                }
//            }
//        });


        return view;
    }
}