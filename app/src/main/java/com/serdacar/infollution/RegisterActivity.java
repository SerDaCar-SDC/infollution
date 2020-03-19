package com.serdacar.infollution;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity {
    static final String CLAVE_EMAIL = "EMAIL";

    private FirebaseAuth fbAuth;
    private FirebaseUser fbUser;
    EditText etEmail;
    EditText etPass;
    EditText etUser;
    String email;
    String password;
    String user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        getSupportActionBar().hide();

        etUser = findViewById(R.id.etUserRegistro);
        etEmail = findViewById(R.id.etEmailRegistro);
        etPass = findViewById(R.id.etPasswordRegistro);

        fbAuth = FirebaseAuth.getInstance();
        fbUser = fbAuth.getCurrentUser();

        if (fbUser != null) {
            etEmail.setText(fbUser.getEmail());
        }
    }

    public void registrar(View view) {
        // Comprobamos si los datos han sido introducidos
        if (validarDatos() == true) {
            // Se crea el usuario en Firebase con el correo y la contraseña recuperados en el
            // método validarDatos()
            fbAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(
                    this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                fbUser = fbAuth.getCurrentUser();
                                Toast.makeText(RegisterActivity.this, getString(R.string.toast_user_registrado), Toast.LENGTH_LONG).show();

                            } else {
                                Toast.makeText(RegisterActivity.this, getString(R.string.toast_user_no_registrado), Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        }
    }

    private boolean validarDatos() {
        // Método para comprobar que los campos no estén vacíos
        user = etUser.getText().toString().trim();
        email = etEmail.getText().toString().trim();
        password = etPass.getText().toString().trim();
        boolean continuar;

        if (user.isEmpty() || email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, getString(R.string.toast_msj_no_datos), Toast.LENGTH_LONG).show();
            continuar = false;

        } else {
            continuar = true;
        }
        return continuar;
    }


    public void accesoLoginActivity(View view) {
        // Método que volverá al login desde registro colocando el correo en el editText
        // etEmailLogin y así el usuario no tenga que volver a introducirlo
        email = etEmail.getText().toString().trim();
        Intent i = new Intent(this, LoginActivity.class);
        i.putExtra(CLAVE_EMAIL, email);
        startActivity(i);
        finish();
        overridePendingTransition(R.anim.right_in, R.anim.right_out);
    }
}
