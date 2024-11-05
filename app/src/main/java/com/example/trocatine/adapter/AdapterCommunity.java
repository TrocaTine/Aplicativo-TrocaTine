package com.example.trocatine.adapter;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trocatine.R;
import com.example.trocatine.adapter.RecycleViewModels.Community;
import com.example.trocatine.ui.community.CommunityFragment;
import com.example.trocatine.ui.product.productTrade.Chat;
import com.example.trocatine.util.ChatCommunityUtil;
import com.example.trocatine.util.ChatUtil;
import com.example.trocatine.util.UserUtil;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterCommunity extends RecyclerView.Adapter<AdapterCommunity.ViewHolder>{
    private List<Community> listCommunity;
    private NavController navController;
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
                if(communityType){
                    ChatCommunityUtil.name = community.getName();
                    ChatCommunityUtil.userNickname = UserUtil.userName;
                    Log.e("FRAGMENT", "entrou");



                    navController.navigate(R.id.community_to_community_two);
                }else{
                    nameUser2 = community.getName();
                    nameUser1 = UserUtil.userName;
                    databaseReference.child("chat").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            boolean chatFound = false;
                            chatKey = ""; // Reseta a chatKey

                            for (DataSnapshot dataSnapshot1 : snapshot.getChildren()) {
                                final String getUserOne = dataSnapshot1.child("user_1").getValue(String.class);
                                final String getUserTwo = dataSnapshot1.child("user_2").getValue(String.class);

                                // Verifica se os usuários correspondem
                                if ((getUserOne.equals(nameUser2) && getUserTwo.equals(nameUser1)) ||
                                        (getUserOne.equals(nameUser1) && getUserTwo.equals(nameUser2))) {
                                    chatKey = dataSnapshot1.getKey(); // Usa a chave do chat existente
                                    chatFound = true;
                                    break; // Sai do loop se o chat for encontrado
                                }
                            }

                            // Se não encontrar, cria uma nova chave para o chat
                            if (!chatFound) {
                                chatKey = String.valueOf(snapshot.getChildrenCount() + 1); // Nova chave
                            }

                            // Inicie a atividade Chat após definir chatKey
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
