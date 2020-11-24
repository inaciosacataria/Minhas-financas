package com.decode.minhasfinancas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.decode.minhasfinancas.Config.ConfigFirebase;
import com.decode.minhasfinancas.Model.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;

public class LoginActivity extends AppCompatActivity {

    FirebaseAuth auth;
    TextInputEditText editEmail,editPassword;
    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editEmail=findViewById(R.id.editEmailLogin);
        editPassword=findViewById(R.id.editPasswordLogin);

        btnLogin=findViewById(R.id.btnEntrar);

    }

    final Context c= this;
    public void Entrar(View view){
        String email= editEmail.getText().toString();
        String password=editPassword.getText().toString();
        auth= ConfigFirebase.getAuth();
        if(!email.isEmpty()){
            if(!password.isEmpty()){
                auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){

                            Intent intent = new Intent(c,PrincipalActivity.class);
                            startActivity(intent);
                            finish();
                        }else {
                            String excessao ="";
                            try {
                                throw task.getException();
                            }catch (FirebaseAuthInvalidCredentialsException e) {
                                excessao = "Email e senha nao correspodem";
                            }catch (FirebaseAuthInvalidUserException e){
                                excessao="Usuario nao existe!";
                            }catch (Exception e){
                              excessao="erro ao logar";
                            }
                            Toast.makeText(getApplicationContext(),excessao,Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }
    }
}
