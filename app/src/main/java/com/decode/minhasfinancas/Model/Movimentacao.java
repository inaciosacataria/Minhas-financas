package com.decode.minhasfinancas.Model;

import com.decode.minhasfinancas.Config.ConfigFirebase;
import com.decode.minhasfinancas.helper.CriptoBase64;
import com.decode.minhasfinancas.helper.DataHelper;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;

public class Movimentacao {

    private String data,categoria,descricao,key,tipoDeMov="Receita";
    private double valor;

    public Movimentacao(String data, String categoria, String descricao, String tipoDeMov,double valor) {
        this.data = data;
        this.categoria = categoria;
        this.descricao = descricao;
        this.tipoDeMov = tipoDeMov;
        this.valor = valor;

    }
//hello
    public Movimentacao() {
    }

    public void salvar(){
        DatabaseReference databaseReference= ConfigFirebase.getDatabaseRef();
        FirebaseAuth firebaseAuth=ConfigFirebase.getAuth();

        String idUsuario= CriptoBase64.Encriptar(firebaseAuth.getCurrentUser().getEmail());
        String tratamentoDeDataParaONo= DataHelper.dataEmStringParaNos(this.getData());
        databaseReference.child("Movimentacao")
                        .child(idUsuario)
                        .child(tratamentoDeDataParaONo)
                        .push()
                        .setValue(this);
    }

    @Exclude
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getTipoDeMov() {
        return tipoDeMov;
    }

    public void setTipoDeMov(String tipoDeMov) {
        this.tipoDeMov = tipoDeMov;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }
}
