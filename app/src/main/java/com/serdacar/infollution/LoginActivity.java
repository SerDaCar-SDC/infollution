package com.serdacar.infollution;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    static final String CLAVE_EMAIL = "EMAIL";
    private FirebaseAuth fbAuth;
    private FirebaseUser fbUser;
    EditText etEmail;
    EditText etPass;
    String email;
    String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getSupportActionBar().hide();

        etEmail = findViewById(R.id.etEmailLogin);
        etPass = findViewById(R.id.etPasswordLogin);

        fbAuth = FirebaseAuth.getInstance();
        fbUser = fbAuth.getCurrentUser();

        if (fbUser != null) {
            etEmail.setText(fbUser.getEmail());
        }

        String emailRegister = getIntent().getStringExtra(RegisterActivity.CLAVE_EMAIL);
        etEmail.setText(emailRegister);

        String emailFirst = getIntent().getStringExtra(FirstActivity.CLAVE_EMAIL);
        etEmail.setText(emailFirst);
    }

    public void accesoRegisterActivity(View view) {
        startActivity(new Intent(this, RegisterActivity.class));
        finish();
        overridePendingTransition(R.anim.left_in, R.anim.left_out);
    }

    public void acceder(View view) {
        if (validarDatos() == true) {
            fbAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(
                    this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                fbUser = fbAuth.getCurrentUser();
                                Intent i = new Intent(LoginActivity.this, FirstActivity.class);
                                i.putExtra(CLAVE_EMAIL, fbUser.getEmail());
                                startActivity(i);
                                finish();
                                overridePendingTransition(R.anim.left_in, R.anim.left_out);

                            } else {
                                Toast.makeText(LoginActivity.this, getString(R.string.toast_error_acceso), Toast.LENGTH_LONG).show();
                            }
                        }
                    }
            );
        }
    }

    private boolean validarDatos() {
        email = etEmail.getText().toString().trim();
        password = etPass.getText().toString().trim();

        boolean continuar;

        if (email.isEmpty() ||  password.isEmpty()) {
            Toast.makeText(this, getString(R.string.toast_msj_no_datos), Toast.LENGTH_LONG).show();
            continuar = false;

        } else {
            continuar = true;
        }
        return continuar;
    }
}
