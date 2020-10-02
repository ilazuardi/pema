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
import com.usu.pema.model.Mahasiswa;

public class SignupActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "SignUpActivity";
    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;

    private EditText edtInputNama, edtInputEmail, edtInputPassword;
    private Button btnSignup;
    private TextView tvLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        bindingView();

        btnSignup.setOnClickListener(this);
        tvLogin.setOnClickListener(this);
    }

    private void bindingView() {

        databaseReference = FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();

        edtInputNama = findViewById(R.id.edt_input_nama);
        edtInputEmail = findViewById(R.id.edt_input_email);
        edtInputPassword = findViewById(R.id.edt_input_password);
        btnSignup = findViewById(R.id.btn_signup);
        tvLogin = findViewById(R.id.tv_login);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.btn_signup :
                signUp();
                break;
            case R.id.tv_login :
                Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
                break;

        }
    }

    private void signUp() {

        Log.d(TAG, "signUp");
        if (!validateForm()) {
            return;
        }

        String nama = edtInputNama.getText().toString();
        String email = edtInputEmail.getText().toString();
        String password = edtInputPassword.getText().toString();

        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        Log.d(TAG, "createUser:onComplete:" + task.isSuccessful());
                        //hideProgressDialog();

                        if (task.isSuccessful()) {
                            onAuthSuccess(task.getResult().getUser());
                        } else {
                            Toast.makeText(SignupActivity.this, "Sign Up Failed",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void onAuthSuccess(FirebaseUser user) {

        String nama = edtInputNama.getText().toString();

        writeNewMahasiswa(user.getUid(), nama, user.getEmail());

        // Go to MainActivity
        startActivity(new Intent(SignupActivity.this, LoginActivity.class));
        finish();

    }

    private void writeNewMahasiswa(String uid, String nama, String email) {

        Mahasiswa mahasiswa = new Mahasiswa(nama, email);

        databaseReference.child("mahasiswa").child(uid).setValue(mahasiswa);
    }

    private boolean validateForm() {
        boolean result = true;

        if (TextUtils.isEmpty(edtInputNama.getText().toString())) {
            edtInputNama.setError("Required");
            result = false;
        } else {
            edtInputNama.setError(null);
        }

        if (TextUtils.isEmpty(edtInputEmail.getText().toString())) {
            edtInputEmail.setError("Required");
            result = false;
        } else {
            edtInputEmail.setError(null);
        }

        if (TextUtils.isEmpty(edtInputPassword.getText().toString())) {
            edtInputPassword.setError("Required");
            result = false;
        } else {
            edtInputPassword.setError(null);
        }

        return result;
    }
}
