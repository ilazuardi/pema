package com.usu.pema.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;
import com.usu.pema.R;
import com.usu.pema.model.Lostfound;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class AdapterLostfound extends RecyclerView.Adapter<AdapterLostfound.LostfoundViewHolder> {

    private Context context;
    private List<Lostfound> listLostfound;
    private OnItemClickListener onItemClickListener;

    public AdapterLostfound(Context context, List<Lostfound> listLostfound) {
        this.context = context;
        this.listLostfound = listLostfound;
    }


    @NonNull
    @Override
    public LostfoundViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item_lost, viewGroup, false);
        return new LostfoundViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LostfoundViewHolder lostfoundViewHolder, int i) {
        final Lostfound mLostFound = listLostfound.get(i);

        lostfoundViewHolder.tvJudulLostfound.setText(mLostFound.getJudul());
        lostfoundViewHolder.tvKontakLostfound.setText(mLostFound.getKontakpenemu());
        lostfoundViewHolder.tvDetailLostfound.setText(mLostFound.getDetail());

        Picasso.with(context)
                .load(mLostFound.getGambarBarang())
                .fit()
                .centerCrop()
                .placeholder(R.drawable.logokabinetambilperan)
                .into(lostfoundViewHolder.ivLostfound);
    }

    @Override
    public int getItemCount() {
        return listLostfound.size();
    }

    public class LostfoundViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnCreateContextMenuListener,
            MenuItem.OnMenuItemClickListener {

        public TextView tvJudulLostfound, tvKontakLostfound, tvDetailLostfound;
        public ImageView ivLostfound;

        public LostfoundViewHolder(@NonNull View itemView) {
            super(itemView);

            tvJudulLostfound = itemView.findViewById(R.id.tv_judul_lostfound);
            tvKontakLostfound = itemView.findViewById(R.id.tv_kontak_lostfound);
            tvDetailLostfound = itemView.findViewById(R.id.tv_detail_lostfound);
            ivLostfound = itemView.findViewById(R.id.iv_lostfound);

            itemView.setOnClickListener(this);
            itemView.setOnCreateContextMenuListener(this);
        }


        @Override
        public void onClick(View view) {
            if (onItemClickListener != null) {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    onItemClickListener.OnItemClick(position);
                }
            }
        }

        @Override
        public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
            contextMenu.setHeaderTitle("Select Action");
            MenuItem showItem = contextMenu.add(contextMenu.NONE, 1, 1, "Show");

            showItem.setOnMenuItemClickListener(this);
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {

            if (onItemClickListener != null) {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {

                    switch(menuItem.getItemId()) {

                        case 1 :
                            onItemClickListener.OnShowItemClick(position);
                            return true;
                    }

                }
            }
            return false;
        }
    }

    public interface OnItemClickListener {
        void OnItemClick(int position);
        void OnShowItemClick(int position);
    }

    public void setOnItemClickListener (OnItemClickListener listener) {
        onItemClickListener = listener;

    }

    private String getDateToday() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/mm/dd");
        Date date = new Date();
        String today = dateFormat.format(date);

        return today;
    }
}
