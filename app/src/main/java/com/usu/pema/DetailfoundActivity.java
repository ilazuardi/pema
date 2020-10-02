package com.usu.pema;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class DetailfoundActivity extends AppCompatActivity {

    ImageView ivDetailLostfound;
    TextView tvJudulDetailLostfound, tvDateDetailLostfound, tvKontakDetailLostfound, tvDetailDetailLostfound;

    private void bindingView() {

        ivDetailLostfound = findViewById(R.id.iv_detail_lostfound);
        tvJudulDetailLostfound = findViewById(R.id.tv_judul_detail_lostfound);
        tvDateDetailLostfound = findViewById(R.id.tv_date_detail_lostfound);
        tvKontakDetailLostfound = findViewById(R.id.tv_kontak_detail_lostfound);
        tvDetailDetailLostfound = findViewById(R.id.tv_detail_detail_lostfound);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailfound);

        bindingView();

        Intent i = this.getIntent();
        String judul = i.getExtras().getString("JUDUL_KEY");
        String kontak = i.getExtras().getString("KONTAK_KEY");
        String date = i.getExtras().getString("DATE_KEY");
        String detail = i.getExtras().getString("DETAIL_KEY");
        String image = i.getExtras().getString("IMAGE_KEY");

        Picasso.with(this)
                .load(image)
                .fit()
                .centerCrop()
                .placeholder(R.drawable.logokabinetambilperan)
                .into(ivDetailLostfound);
        if (judul == null) {
            tvJudulDetailLostfound.setText("-");
        } else
            tvJudulDetailLostfound.setText(judul);
        if (kontak == null) {
            tvKontakDetailLostfound.setText("-");
        } else
            tvKontakDetailLostfound.setText(kontak);
        if (detail == null) {
            tvDetailDetailLostfound.setText("-");
        } else
            tvDetailDetailLostfound.setText(detail);
        if (date == null) {
            tvDateDetailLostfound.setText("-");
        } else
            tvDateDetailLostfound.setText(date);
    }
}
