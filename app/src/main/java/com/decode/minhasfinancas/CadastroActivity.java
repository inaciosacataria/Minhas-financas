package com.decode.minhasfinancas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.decode.minhasfinancas.Config.ConfigFirebase;
import com.decode.minhasfinancas.Model.Usuario;
import com.decode.minhasfinancas.helper.CriptoBase64;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

public class CadastroActivity extends AppCompatActivity {

    private  TextInputEditText editNome,editEmail,editPassword;
    private Button btnCadastro;

     private  FirebaseAuth auth;
    //DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

         editNome=findViewById(R.id.editNome);
        editEmail=findViewById(R.id.editEmailLogin);
        editPassword=findViewById(R.id.editPasswordLogin);
        btnCadastro=findViewById(R.id.btnCadastrar);


        btnCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String nome=editNome.getText().toString();
                String email=editEmail.getText().toString();
                String password=editPassword.getText().toString();


                if(!nome.isEmpty()){

                    if(!email.isEmpty()){

                        if(!password.isEmpty()){
                            String id= CriptoBase64.Encriptar(email);

                            u= new Usuario();
                            u.setEmail(email);
                            u.setNome(nome);
                            u.setPassword(password);
                            u.setId(id);

                            u.salvar();

                            Autenticacao(u);
                        }else{
                            Toast.makeText(getApplicationContext()," Preencha o Passwords",Toast.LENGTH_SHORT).show();
                        }

                    }else{
                        Toast.makeText(getApplicationContext(),"Preencha o Email",Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getApplicationContext(),"Preencha o Nome",Toast.LENGTH_SHORT).show();

                }
            }
        });



    }
    Usuario u;
    Context c=this;
    private void Autenticacao (Usuario u) {
        auth = ConfigFirebase.getAuth();
        auth.createUserWithEmailAndPassword(u.getEmail(), u.getPassword())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            Intent intent = new Intent(c,PrincipalActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            String execao = "";
                            try {
                                throw task.getException();
                            } catch (FirebaseAuthWeakPasswordException e) {
                                execao = "Escolha uma palavra-passe mais forte";
                            } catch (FirebaseAuthUserCollisionException e) {
                                execao = "Ja possui uma conta";
                            } catch (FirebaseAuthInvalidCredentialsException e) {
                                execao = "digite um email valido";
                            } catch (Exception e) {
                                execao = "Erro ao cadastrar";
                            }
                            Toast.makeText(getApplicationContext(),execao,Toast.LENGTH_SHORT).show();

                        }
                    }
                });


    }

}
