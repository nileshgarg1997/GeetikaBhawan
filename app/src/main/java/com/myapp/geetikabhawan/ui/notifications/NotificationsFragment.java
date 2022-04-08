package com.myapp.geetikabhawan.ui.notifications;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.myapp.geetikabhawan.R;

import java.util.ArrayList;


public class NotificationsFragment extends Fragment {

    private RecyclerView showNotificationRecyclerView;
    private ProgressBar progressBar;
    private ArrayList<NotificationData> list;
    private NotificationAdapter notificationAdapter;

    private DatabaseReference reference;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_notifications, container, false);

        reference= FirebaseDatabase.getInstance().getReference().child("Notifications");

        showNotificationRecyclerView=view.findViewById(R.id.showNotificationRecyclerView);
        progressBar=view.findViewById(R.id.progressBar);

        showNotificationRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        showNotificationRecyclerView.setHasFixedSize(true);

        getNotification();

        return view;
    }

    private void getNotification() {

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list=new ArrayList<>();
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    NotificationData data=dataSnapshot.getValue(NotificationData.class);
                    list.add(data);             // list.add(0,data);        Add latest data into 0th index in recyclerview. If we use this line then we don't need line no. 67,68,69,75.

                }

                LinearLayoutManager layoutManager=new LinearLayoutManager(getContext());
                layoutManager.setReverseLayout(true);                                       //For Reverse RecyclerView
                layoutManager.setStackFromEnd(true);

                notificationAdapter=new NotificationAdapter(getContext(),list);
                notificationAdapter.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);

                showNotificationRecyclerView.setLayoutManager(layoutManager);

                showNotificationRecyclerView.setAdapter(notificationAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}