package com.myapp.geetikabhawan.ui.gallery;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.myapp.geetikabhawan.R;

import java.util.ArrayList;
import java.util.List;


public class GalleryFragment extends Fragment {

    RecyclerView weddingRecyclerView,receptionRecyclerView,partyRecyclerView,eventRecyclerView;
    GalleryAdapter galleryAdapter;
    ShimmerFrameLayout shimmerWedding,shimmerReception,shimmerParty,shimmerEvent;

    DatabaseReference reference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_gallery, container, false);

        weddingRecyclerView=view.findViewById(R.id.weddingRecyclerView);
        receptionRecyclerView=view.findViewById(R.id.receptionRecyclerView);
        partyRecyclerView=view.findViewById(R.id.partyRecyclerView);
        eventRecyclerView=view.findViewById(R.id.eventRecyclerView);
        reference= FirebaseDatabase.getInstance().getReference().child("Gallery");

        shimmerWedding=view.findViewById(R.id.shimmerWedding);
        shimmerReception=view.findViewById(R.id.shimmerReception);
        shimmerParty=view.findViewById(R.id.shimmerParty);
        shimmerEvent=view.findViewById(R.id.shimmerEvent);


        getWeddingImages();
        getReceptionImages();
        getPartyImages();
        getEventImages();
        return view;
    }

    private void getPartyImages() {

        reference.child("Party").addValueEventListener(new ValueEventListener() {

            List<String> imageList=new ArrayList<>();
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    String data= (String) dataSnapshot.getValue();
                    imageList.add(data);
                }

                galleryAdapter=new GalleryAdapter(getContext(),imageList);
                partyRecyclerView.setLayoutManager(new GridLayoutManager(getContext(),4));
                partyRecyclerView.setAdapter(galleryAdapter);
                shimmerParty.stopShimmer();
                shimmerParty.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getEventImages() {
        reference.child("Event").addValueEventListener(new ValueEventListener() {

            List<String> imageList=new ArrayList<>();
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    String data= (String) dataSnapshot.getValue();
                    imageList.add(data);
                }

                galleryAdapter=new GalleryAdapter(getContext(),imageList);
                eventRecyclerView.setLayoutManager(new GridLayoutManager(getContext(),4));
                eventRecyclerView.setAdapter(galleryAdapter);
                shimmerEvent.stopShimmer();
                shimmerEvent.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void getReceptionImages() {
        reference.child("Reception").addValueEventListener(new ValueEventListener() {

            List<String> imageList=new ArrayList<>();
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    String data= (String) dataSnapshot.getValue();
                    imageList.add(data);
                }

                galleryAdapter=new GalleryAdapter(getContext(),imageList);
                receptionRecyclerView.setLayoutManager(new GridLayoutManager(getContext(),4));
                receptionRecyclerView.setAdapter(galleryAdapter);
                shimmerReception.stopShimmer();
                shimmerReception.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void getWeddingImages() {
        reference.child("Wedding").addValueEventListener(new ValueEventListener() {

            List<String> imageList=new ArrayList<>();
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    String data= (String) dataSnapshot.getValue();
                    imageList.add(data);
                }

                galleryAdapter=new GalleryAdapter(getContext(),imageList);
                weddingRecyclerView.setLayoutManager(new GridLayoutManager(getContext(),4));
                weddingRecyclerView.setAdapter(galleryAdapter);
                shimmerWedding.stopShimmer();
                shimmerWedding.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onPause() {
        shimmerWedding.stopShimmer();
        shimmerReception.stopShimmer();
        shimmerParty.stopShimmer();
        shimmerEvent.stopShimmer();
        super.onPause();
    }

    @Override
    public void onResume() {
        shimmerWedding.startShimmer();
        shimmerReception.startShimmer();
        shimmerParty.startShimmer();
        shimmerEvent.startShimmer();
        super.onResume();
    }
}