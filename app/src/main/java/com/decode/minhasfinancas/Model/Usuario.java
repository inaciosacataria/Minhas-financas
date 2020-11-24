package com.decode.minhasfinancas.Model;

import com.decode.minhasfinancas.Config.ConfigFirebase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;

public class Usuario {

    private String id;
    private String nome;
    private String email;
    private String password;
    private double despesa=0.00;
    private double receita=0.00;
    private DatabaseReference databaseReference;
    public Usuario() {
    }

    public void salvar(){
        databaseReference= ConfigFirebase.getDatabaseRef();
        databaseReference.child("usuario").child(this.getId()).setValue(this);
    }

    public double getDespesa() {
        return despesa;
    }

    public void setDespesa(double despesa) {
        this.despesa = despesa;
    }

    public double getReceita() {
        return receita;
    }

    public void setReceita(double receita) {
        this.receita = receita;
    }



    @Exclude
    public String getId() {
        return id;
    }
    @Exclude
    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
