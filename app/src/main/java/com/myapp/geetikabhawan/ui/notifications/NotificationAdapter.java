package com.myapp.geetikabhawan.ui.notifications;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.myapp.geetikabhawan.FullImageView;
import com.myapp.geetikabhawan.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationViewAdapter> {

    private Context context;
    private ArrayList<NotificationData> list;

    public NotificationAdapter(Context context, ArrayList<NotificationData> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public NotificationViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.notifications_item_layout, parent, false);
        return new NotificationViewAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationViewAdapter holder, int position) {

        NotificationData currentItem = list.get(position);

        holder.showNotificationTitle.setText(currentItem.getTitle());
        holder.date.setText(currentItem.getDate());
        holder.time.setText(currentItem.getTime());

        try {
            if (currentItem.getImage() != null)
                Glide.with(context).load(currentItem.getImage()).into(holder.showNotificationImage);
        } catch (Exception e) {
            e.printStackTrace();
        }

        holder.showNotificationImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, FullImageView.class);
                intent.putExtra("image",currentItem.getImage());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class NotificationViewAdapter extends RecyclerView.ViewHolder {

        private TextView showNotificationTitle, date, time;
        private ImageView showNotificationImage;

        public NotificationViewAdapter(@NonNull View itemView) {
            super(itemView);

            date = itemView.findViewById(R.id.date);
            time = itemView.findViewById(R.id.time);
            showNotificationTitle = itemView.findViewById(R.id.showNotificationTitle);
            showNotificationImage = itemView.findViewById(R.id.showNotificationImage);
        }
    }
}
