package com.myapp.geetikabhawan.ui.ourteam;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.myapp.geetikabhawan.FullImageView;
import com.myapp.geetikabhawan.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MemberAdapter extends RecyclerView.Adapter<MemberAdapter.MemberViewAdapter> {

    private List<MemberData> list;
    private Context context;

    public MemberAdapter(List<MemberData> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public MemberViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.member_item_layout, parent, false);
        return new MemberViewAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MemberViewAdapter holder, int position) {

        MemberData item = list.get(position);
        holder.name.setText(item.getName());
        holder.post.setText(item.getPost());
        try {
            Picasso.get().load(item.getImage()).placeholder(R.drawable.avatar_image).into(holder.imageView);
        } catch (Exception e) {
            e.printStackTrace();
        }

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, FullImageView.class);
                intent.putExtra("image",item.getImage());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MemberViewAdapter extends RecyclerView.ViewHolder {

        private TextView name, post;
        private ImageView imageView;

        public MemberViewAdapter(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.memberName);
            post = itemView.findViewById(R.id.memberPost);
            imageView = itemView.findViewById(R.id.memberImage);

        }
    }
}
