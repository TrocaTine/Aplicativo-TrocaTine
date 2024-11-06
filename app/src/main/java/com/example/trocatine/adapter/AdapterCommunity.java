package com.example.trocatine.adapter;

import android.content.Intent;
import android.media.audiofx.BassBoost;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trocatine.R;
import com.example.trocatine.adapter.RecycleViewModels.Community;
import com.example.trocatine.ui.community.CommunityComeInFragment;
import com.example.trocatine.ui.community.CommunityFragment;
import com.example.trocatine.ui.home.HomeNavBar;
import com.example.trocatine.ui.product.productTrade.Chat;
import com.example.trocatine.util.ChatCommunityUtil;
import com.example.trocatine.util.ChatUtil;
import com.example.trocatine.util.UserUtil;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.JsonParseException;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterCommunity extends RecyclerView.Adapter<AdapterCommunity.ViewHolder>{
    private List<Community> listCommunity;
    private NavController navController;
    private boolean comeIn = false;
    String nameUser1, nameUser2, chatKey;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://trocatine-a226a-default-rtdb.firebaseio.com/");

    public AdapterCommunity(List<Community> arg, NavController navController) {
        this.listCommunity = arg;
        this.navController = navController;
    }

    @NonNull
    @Override
    public AdapterCommunity.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_community_rv, parent, false);
        return new AdapterCommunity.ViewHolder(viewItem);
    }

    public int getItemCount() {
        return listCommunity.size();
    }

    public void onBindViewHolder(@NonNull AdapterCommunity.ViewHolder holder, int position) {

        Community community = listCommunity.get(position);
        boolean communityType = community.getCommunity();

        if (community != null) {
            holder.name.setText(community.getName());
            Picasso.get().load(community.getPhoto()).into(holder.image);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (communityType) {
                    ChatCommunityUtil.name = community.getName();
                    ChatCommunityUtil.userNickname = UserUtil.userName;

                    databaseReference.child("comunity").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            comeIn = false;

                            // Loop through all communities to find a matching community
                            for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                                String name = snapshot1.child("name").getValue(String.class);
                                if (name != null && name.equals(community.getName())) {
                                    ChatCommunityUtil.chatKey = snapshot1.getKey();

                                    // Check if the user is already a participant
                                    for (DataSnapshot userSnapshot : snapshot1.child("users").getChildren()) {
                                        if (userSnapshot.getKey().equals(ChatCommunityUtil.userNickname)) {
                                            comeIn = true;
                                            break;
                                        }
                                    }
                                    break; // Break the loop once we find the community
                                }
                            }

                            // Navigate based on `comeIn` result
                            if (comeIn) {
                                Log.e("name com", "name" + navController + " " + community.getName());
                                navController.navigate(R.id.community_to_community_chat);
                            } else {
                                Log.e("name com", "name" + navController + " " + community.getName());
                                navController.navigate(R.id.community_to_community_two);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Log.e("CHAT COMUNITY", "Erro ao carregar dados: " + error.getMessage());
                        }
                    });
                } else {
                    // Existing code for private chat
                    nameUser2 = community.getName();
                    nameUser1 = UserUtil.userName;
                    databaseReference.child("chat").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            boolean chatFound = false;
                            chatKey = ""; // Reset chatKey

                            for (DataSnapshot dataSnapshot1 : snapshot.getChildren()) {
                                final String getUserOne = dataSnapshot1.child("user_1").getValue(String.class);
                                final String getUserTwo = dataSnapshot1.child("user_2").getValue(String.class);

                                // Check if users match
                                if ((getUserOne.equals(nameUser2) && getUserTwo.equals(nameUser1)) ||
                                        (getUserOne.equals(nameUser1) && getUserTwo.equals(nameUser2))) {
                                    chatKey = dataSnapshot1.getKey(); // Use the existing chat key
                                    chatFound = true;
                                    break;
                                }
                            }

                            // If chat not found, create a new chat key
                            if (!chatFound) {
                                chatKey = String.valueOf(snapshot.getChildrenCount() + 1); // New key
                            }

                            // Start Chat activity with the correct chatKey
                            Intent intent = new Intent(v.getContext(), Chat.class);
                            ChatUtil.name = nameUser2;
                            ChatUtil.send = nameUser1;
                            ChatUtil.chatKey = chatKey;
                            v.getContext().startActivity(intent);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Log.e("DatabaseError", "Erro ao acessar o banco de dados: " + error.getMessage());
                        }
                    });
                }
            }
        });

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        CircleImageView image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.communityName);
            image = itemView.findViewById(R.id.imageView25);

        }
    }
}
