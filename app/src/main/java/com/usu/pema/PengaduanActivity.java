package com.usu.pema;

import android.content.Intent;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.ConsoleMessage;
import android.webkit.CookieManager;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.usu.pema.model.Pengaduan;

public class PengaduanActivity extends AppCompatActivity {

    private static String TAG = PengaduanActivity.class.getSimpleName();
    private ProgressBar progressBarAduan;
    private String postUrl;
    private EditText edtInputNimPengadu, edtInputJudulAduan, edtInputCatatanAduan ;
    private Button btnSend;
    private DatabaseReference databaseReference;

    private static final int NUMBER_OF_COMMENTS = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pengaduan);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        databaseReference = FirebaseDatabase.getInstance().getReference("pengaduan");

        progressBarAduan = (ProgressBar) findViewById(R.id.progress_bar_aduan);
        edtInputNimPengadu = findViewById(R.id.edt_input_nim_pengadu);
        edtInputJudulAduan = findViewById(R.id.edt_input_judul_aduan);
        edtInputCatatanAduan = findViewById(R.id.edt_input_catatan_aduan);
        btnSend = findViewById(R.id.btn_send);

        btnSend.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                progressBarAduan.setVisibility(View.VISIBLE);
                String nim = edtInputNimPengadu.getText().toString();
                String judulAduan = edtInputJudulAduan.getText().toString();
                String catatanAduan = edtInputCatatanAduan.getText().toString();

                writeNewAduan(nim, judulAduan, catatanAduan);

                progressBarAduan.setVisibility(View.GONE);

                Intent intent = new Intent(PengaduanActivity.this, MainActivity.class);
                finish();
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.fb_comments, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
//
//        if (item.getItemId() == R.id.action_refresh) {
//
//        }

        return super.onOptionsItemSelected(item);
    }

    private void writeNewAduan(String nim, String judul, String catatan) {

        Pengaduan pengaduan = new Pengaduan(nim, judul, catatan);

        String pengaduanId = databaseReference.push().getKey();
        databaseReference.child(pengaduanId).setValue(pengaduan);

    }
}
