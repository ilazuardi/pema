package com.usu.pema;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.usu.pema.model.Acara;
import com.usu.pema.model.Lostfound;

import java.text.SimpleDateFormat;
import java.util.Date;

public class UploadActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;

    Button btnChooseImage, btnUpload;
    EditText edtFileName, edtDetail, edtKontakPenemu;
    ImageView ivChooseImage;
    TextView tvBackHome;
    ProgressBar pb;

    private Uri mImageUri;

    private StorageReference mStorageReference;
    private DatabaseReference mDatabaseReference;

    private StorageTask mUploadTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        bindingView();

        mStorageReference = FirebaseStorage.getInstance().getReference("lostfound");
        mDatabaseReference = FirebaseDatabase.getInstance().getReference("lostfound");

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mUploadTask != null && mUploadTask.isInProgress()) {
                    Toast.makeText(UploadActivity.this, "Upload On Progress", Toast.LENGTH_SHORT).show();
                }
                else {
                    uploadFile();
                }
            }
        });

        btnChooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFileChooser();
            }
        });
        tvBackHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(UploadActivity.this, MainActivity.class);
                startActivity(intent1);
                finish();
            }
        });
    }

    private void bindingView() {

        btnChooseImage = findViewById(R.id.btn_choose_image);
        btnUpload = findViewById(R.id.btn_upload);
        edtFileName = findViewById(R.id.edt_file_name);
        edtDetail = findViewById(R.id.edt_detail);
        edtKontakPenemu = findViewById(R.id.edt_kontak_penemu);
        ivChooseImage = findViewById(R.id.iv_choose_image);
        tvBackHome = findViewById(R.id.tv_back_home);
        pb = findViewById(R.id.pb);

    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            mImageUri = data.getData();

            Picasso.with(this).load(mImageUri).into(ivChooseImage);
        }
    }

    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    private void uploadFile() {
        if (mImageUri != null) {
            final StorageReference fileReference = mStorageReference.child(System.currentTimeMillis() + "." + getFileExtension(mImageUri));
            final String pathStorage = String.valueOf(fileReference);
            pb.setVisibility(View.VISIBLE);
            pb.setIndeterminate(true);
            mUploadTask = fileReference.putFile(mImageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    pb.setVisibility(View.VISIBLE);
                                    pb.setIndeterminate(false);
                                    pb.setProgress(0);
                                }
                            }, 500);

                            Toast.makeText(UploadActivity.this, "Upload Successful", Toast.LENGTH_SHORT).show();

                            if (taskSnapshot.getMetadata() != null) {
                                if (taskSnapshot.getMetadata().getReference() != null) {
                                    Task<Uri> result = taskSnapshot.getStorage().getDownloadUrl();
                                    result.addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            String imageUrl = uri.toString();
                                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");
                                            String datePost = simpleDateFormat.format(new Date());
                                            Lostfound lostfound = new Lostfound(edtFileName.getText().toString(),
                                                    edtKontakPenemu.getText().toString(),
                                                    edtDetail.getText().toString(),
                                                    imageUrl,
                                                    datePost);

                                            String lostFoundId = mDatabaseReference.push().getKey();
                                            mDatabaseReference.child(lostFoundId).setValue(lostfound);
                                        }
                                    });
                                }
                            }
                            pb.setVisibility(View.GONE);

                            moveHome();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(UploadActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                            pb.setProgress((int) progress);
                        }
                    });
        }
        else {
            Toast.makeText(this, "No File Selected", Toast.LENGTH_SHORT).show();
        }

    }

    private void moveHome() {
        Intent intent2 = new Intent(UploadActivity.this, MainActivity.class);
        startActivity(intent2);
        finish();

    }
}