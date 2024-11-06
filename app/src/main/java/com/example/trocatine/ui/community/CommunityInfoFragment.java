package com.example.trocatine.ui.community;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.trocatine.R;
import com.example.trocatine.util.ChatCommunityUtil;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CommunityInfoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CommunityInfoFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters

    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://trocatine-a226a-default-rtdb.firebaseio.com/");

    private TextView
            creationDate, participantCount, communityDescription, communityTitle;

    private CircleImageView communityIcon;
    private String mParam1;
    private String mParam2;

    public CommunityInfoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CommunityInfoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CommunityInfoFragment newInstance(String param1, String param2) {
        CommunityInfoFragment fragment = new CommunityInfoFragment();
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
        View view = inflater.inflate(R.layout.fragment_community_info, container, false);


        creationDate = view.findViewById(R.id.creationDate);
        participantCount = view.findViewById(R.id.participantCount);
        communityDescription = view.findViewById(R.id.communityDescription);
        communityTitle = view.findViewById(R.id.communityTitle);
        communityIcon = view.findViewById(R.id.communityIcon);
        databaseReference.child("comunity").child(ChatCommunityUtil.chatKey).get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                creationDate.setText(dataSnapshot.child("created_date").getValue(String.class));
                participantCount.setText(String.valueOf(dataSnapshot.child("users").getChildrenCount()));
                communityDescription.setText(dataSnapshot.child("description").getValue(String.class));
                communityTitle.setText(ChatCommunityUtil.name);
                String photoUrl = dataSnapshot.child("photo").getValue(String.class);

                if(photoUrl != null){
                    Picasso.get().load(photoUrl).into(communityIcon);
                }


            }

        });
        return view;


    }
}