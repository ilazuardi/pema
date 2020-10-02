package com.usu.pema.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.usu.pema.R;
import com.usu.pema.model.Acara;

import java.util.List;

public class AdapterAcara extends RecyclerView.Adapter<AdapterAcara.AcaraViewHolder> {

    private Context context;
    private List<Acara> listAcara;

    public AdapterAcara(Context context, List<Acara> listAcara) {
        this.context = context;
        this.listAcara = listAcara;
    }

    @NonNull
    @Override
    public AcaraViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item, viewGroup, false);
        return new AcaraViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AcaraViewHolder acaraViewHolder, int i) {
        Acara mAcara = listAcara.get(i);

        acaraViewHolder.tvJudulAcara.setText(mAcara.getJudul());
        acaraViewHolder.tvTipeAcara.setText(mAcara.getTipe());
        acaraViewHolder.tvDetailAcara.setText(mAcara.getDetail());

        Picasso.with(context)
                .load(mAcara.getGambarAcara())
                .fit()
                .centerCrop()
                .placeholder(R.drawable.logokabinetambilperan)
                .into(acaraViewHolder.ivAcara);
    }

    @Override
    public int getItemCount() {
        return listAcara.size();
    }

    public class AcaraViewHolder extends RecyclerView.ViewHolder{

        public TextView tvJudulAcara, tvTipeAcara, tvDetailAcara;
        public ImageView ivAcara;

        public AcaraViewHolder(@NonNull View itemView) {
            super(itemView);

            tvJudulAcara = itemView.findViewById(R.id.tv_judul_acara);
            tvTipeAcara = itemView.findViewById(R.id.tv_tipe_acara);
            tvDetailAcara = itemView.findViewById(R.id.tv_detail_acara);
            ivAcara = itemView.findViewById(R.id.iv_acara);
        }
    }
}
