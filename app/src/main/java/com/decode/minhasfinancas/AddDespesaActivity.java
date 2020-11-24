package com.decode.minhasfinancas;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.decode.minhasfinancas.Config.ConfigFirebase;
import com.decode.minhasfinancas.Model.Movimentacao;
import com.decode.minhasfinancas.Model.Usuario;
import com.decode.minhasfinancas.helper.CriptoBase64;
import com.decode.minhasfinancas.helper.DataHelper;
import com.github.clans.fab.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class AddDespesaActivity extends AppCompatActivity {

    FirebaseAuth firebaseAuth= ConfigFirebase.getAuth();
    DatabaseReference databaseReference=ConfigFirebase.getDatabaseRef();
    TextInputEditText editTextDataDespesa,editCategoriaDespesa,editDescricaoDespesa;
    EditText editValorDespesa; FloatingActionButton btnConcluirDespesa;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_despesa);
        getSupportActionBar().setTitle("Despesa");
        getSupportActionBar().setElevation(0);

        recuperarUser();

        editValorDespesa=findViewById(R.id.editValorDespesa);
        editTextDataDespesa=findViewById(R.id.editDataDespesa);
        editCategoriaDespesa=findViewById(R.id.editCategoriaDespesa);
        editDescricaoDespesa=findViewById(R.id.editDescricaoDespesa);;

        editTextDataDespesa.setText(DataHelper.getData());
    }
    public void add(View view){

        String valorDespesa= editValorDespesa.getText().toString();
        String data= editTextDataDespesa.getText().toString();
        String categoria= editCategoriaDespesa.getText().toString();
        String descricao= editDescricaoDespesa.getText().toString();


        if(!valorDespesa.isEmpty()){
            double valor=Double.parseDouble(valorDespesa);
            if(!data.isEmpty()){
                if(!categoria.isEmpty()){
                    if(!descricao.isEmpty()){
                        Movimentacao movimentacao= new Movimentacao(data,categoria,descricao,"Despesa",valor);
                        movimentacao.salvar();
                        recuperarUser();
                        double despesa= movimentacao.getValor()+usuario.getDespesa();

                        //
                        String emailUsuario =firebaseAuth.getCurrentUser().getEmail();
                        String idUsuario = CriptoBase64.Encriptar( emailUsuario );
                        DatabaseReference usuarioRef = databaseReference.child("usuario").child( idUsuario ).child("despesa");
                        usuarioRef.setValue(despesa);

                        finish();

                    }else{
                        Toast.makeText(getApplicationContext(),"Preencha a descricao",Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getApplicationContext(),"Preencha a categoria",Toast.LENGTH_SHORT).show();
                }
            }else{
                Toast.makeText(getApplicationContext(),"Preencha a data",Toast.LENGTH_SHORT).show();
            }
        }else {
            Toast.makeText(getApplicationContext(),"Preencha o valor de despesa",Toast.LENGTH_SHORT).show();
        }
    }
    Usuario usuario;
    public void recuperarUser(){





        String emailUsuario =firebaseAuth.getCurrentUser().getEmail();
        String idUsuario = CriptoBase64.Encriptar( emailUsuario );
        DatabaseReference usuarioRef = databaseReference.child("usuario").child( idUsuario );
        DatabaseReference valorRef=databaseReference;
        usuarioRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                usuario = dataSnapshot.getValue( Usuario.class );

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
}}
