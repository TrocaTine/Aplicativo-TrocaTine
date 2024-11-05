package com.example.trocatine.ui.product.productTrade;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.trocatine.R;
import com.example.trocatine.adapter.AdapterChat;
import com.example.trocatine.adapter.RecycleViewModels.MessageChat;
import com.example.trocatine.ui.database.MemoryData;
import com.example.trocatine.util.ChatUtil;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class Chat extends AppCompatActivity {

    private final DatabaseReference databaseReference = FirebaseDatabase.getInstance()
            .getReferenceFromUrl("https://trocatine-a226a-default-rtdb.firebaseio.com/");
    private String chatKey;
    String getUserName = "";
    private RecyclerView chattingRecyclerView;
    private boolean loadingFirstTime= true;
    private final List<MessageChat> chatLists = new ArrayList<>();
    private AdapterChat chatAdapter;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);


        final ImageView backBtn = findViewById(R.id.backBtn);
        final ImageView sendBtn = findViewById(R.id.sendBtn);
        final TextView nameTv = findViewById(R.id.name);
        final EditText messageEditText = findViewById(R.id.messageEditText);
        final CircleImageView profilePic = findViewById(R.id.profilePic);

        chattingRecyclerView = findViewById(R.id.chattingRecyclerview);
        final String getName = ChatUtil.name;
        final String getProfilePic = getIntent().getStringExtra("profilePic");//pegar do metodo
        chatKey = ChatUtil.chatKey;
        getUserName = ChatUtil.send;

        nameTv.setText(getName);
        if(getProfilePic != null){
            Picasso.get().load(getProfilePic).into(profilePic);
        }
        chattingRecyclerView.setHasFixedSize(true);
        chattingRecyclerView.setLayoutManager(new LinearLayoutManager(Chat.this));
        chatAdapter = new AdapterChat(chatLists, Chat.this, getUserName);
        chattingRecyclerView.setAdapter(chatAdapter);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (chatKey != null && chatKey.isEmpty()) {
                    chatKey = "1";
                    if (snapshot.hasChild("chat")) {
                        chatKey = String.valueOf(snapshot.child("chat").getChildrenCount());

                    }
                }

                if (snapshot.hasChild("chat")) {
                    Log.e("chat", "id chat: "+chatKey);
                    if (snapshot.child("chat").child(chatKey).hasChild("messages")) {
                        chatLists.clear();
                        for (DataSnapshot messagesSnapshot : snapshot.child("chat").child(chatKey).child("messages").getChildren()) {
                            if (messagesSnapshot.hasChild("msg") && messagesSnapshot.hasChild("mobile")) {
                                final String messageTimestamps = messagesSnapshot.getKey();
                                final String getMobile = messagesSnapshot.child("mobile").getValue(String.class);
                                final String getMsg = messagesSnapshot.child("msg").getValue(String.class);
                                MessageChat chatList = new MessageChat(getMobile, getName, getMsg);
                                chatLists.add(chatList);

                                MemoryData.saveLastMsgTs(messageTimestamps, chatKey, Chat.this);
                                chatAdapter.updateChat(chatLists);
                                loadingFirstTime = false;
                                chattingRecyclerView.scrollToPosition(chatLists.size()-1);


                            }
                        }

                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v){
                final String getTxtMessage = messageEditText.getText().toString();
                String currentTimestamp = String.valueOf(System.currentTimeMillis()).substring(0, 10);

                databaseReference.child("chat").child(chatKey).child("user_1").setValue(getUserName);
                databaseReference.child("chat").child(chatKey).child("user_2").setValue(getName);
                databaseReference.child("chat").child(chatKey).child("messages").child(currentTimestamp).child("msg").setValue(getTxtMessage);
                databaseReference.child("chat").child(chatKey).child("messages").child(currentTimestamp).child("mobile").setValue(getUserName);
                messageEditText.setText("");

                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.hasChild("chat")) {
                            if (snapshot.child("chat").child(chatKey).hasChild("messages")) {
                                chatLists.clear();
                                for (DataSnapshot messagesSnapshot : snapshot.child("chat").child(chatKey).child("messages").getChildren()) {
                                    if (messagesSnapshot.hasChild("msg") && messagesSnapshot.hasChild("mobile")) {
                                        final String messageTimestamps = messagesSnapshot.getKey();
                                        final String getMobile = messagesSnapshot.child("mobile").getValue(String.class);
                                        final String getMsg = messagesSnapshot.child("msg").getValue(String.class);
                                        MessageChat chatList = new MessageChat(getMobile, getName, getMsg);
                                        chatLists.add(chatList);
                                        MemoryData.saveLastMsgTs(messageTimestamps, chatKey, Chat.this);
                                        chatAdapter.updateChat(chatLists);
                                        chattingRecyclerView.scrollToPosition(chatLists.size() -1);


                                    }
                                }

                            }

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v){
                finish();
            }
        });

    }
}
