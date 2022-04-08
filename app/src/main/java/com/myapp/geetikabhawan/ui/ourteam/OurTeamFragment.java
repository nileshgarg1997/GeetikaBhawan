package com.myapp.geetikabhawan.ui.ourteam;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.myapp.geetikabhawan.R;

import java.util.ArrayList;
import java.util.List;


public class OurTeamFragment extends Fragment {

    private RecyclerView membersList;
    private LinearLayout noDataInList;
    private List<MemberData> list;
    private MemberAdapter adapter;

    private DatabaseReference reference, dbRef;
    private ProgressDialog progressDialog;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_our_team, container, false);
        membersList = view.findViewById(R.id.membersList);
        noDataInList = view.findViewById(R.id.noDataInList);

        progressDialog=new ProgressDialog(getContext());

        reference = FirebaseDatabase.getInstance().getReference();

        viewMembers();
        return view;
    }

    private void viewMembers() {

        progressDialog.setMessage("Fetching Data...");
        progressDialog.show();
        dbRef = reference.child("Members");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                list = new ArrayList<>();
                if (!snapshot.exists()) {
                    noDataInList.setVisibility(View.VISIBLE);
                    membersList.setVisibility(View.GONE);
                    progressDialog.dismiss();
                } else {

                    noDataInList.setVisibility(View.GONE);
                    membersList.setVisibility(View.VISIBLE);
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        MemberData data = dataSnapshot.getValue(MemberData.class);
                        list.add(data);
                    }

                    membersList.setHasFixedSize(true);
                    membersList.setLayoutManager(new LinearLayoutManager(getContext()));
                    adapter = new MemberAdapter(list, getContext());
                    membersList.setAdapter(adapter);
                    progressDialog.dismiss();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}