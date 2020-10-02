package com.usu.pema;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "LoginActivity";

    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;
    private EditText edtEmailTerdaftar, edtPasswordTerdaftar;
    private Button btnLogin;
    private TextView tvSignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        bindingView();

        btnLogin.setOnClickListener(this);
        tvSignup.setOnClickListener(this);


    }

    private void bindingView() {

        databaseReference = FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();

        edtEmailTerdaftar = findViewById(R.id.edt_email_terdaftar);
        edtPasswordTerdaftar = findViewById(R.id.edt_password_terdaftar);
        btnLogin = findViewById(R.id.btn_login);
        tvSignup = findViewById(R.id.tv_signup);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.btn_login :
                signIn();
                break;
            case R.id.tv_signup :
                Intent intentSignup = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intentSignup);
                break;

        }
    }

    private boolean validateForm() {

        boolean result = true;
        if (TextUtils.isEmpty(edtEmailTerdaftar.getText().toString())) {
            edtEmailTerdaftar.setError("Required");
            result = false;
        } else {
            edtEmailTerdaftar.setError(null);
        }

        if (TextUtils.isEmpty(edtPasswordTerdaftar.getText().toString())) {
            edtPasswordTerdaftar.setError("Required");
            result = false;
        } else {
            edtPasswordTerdaftar.setError(null);
        }

        return result;
    }

    private void signIn() {

        Log.d(TAG, "signIn");
        if (!validateForm()) {
            return;
        }

        String email = edtEmailTerdaftar.getText().toString();
        String password = edtPasswordTerdaftar.getText().toString();

        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signIn:onComplete:" + task.isSuccessful());

                        if (task.isSuccessful()) {
                            onAuthSuccess(task.getResult().getUser());
                        } else {
                            Toast.makeText(LoginActivity.this, "Email atau Password anda salah",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void onAuthSuccess(FirebaseUser user) {

        Intent intent = new Intent(LoginActivity.this, UploadActivity.class);
        startActivity(intent);
        finish();
    }
}
