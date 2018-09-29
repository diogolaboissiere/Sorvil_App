package com.example.leona.projetosorvil;
import com.example.leona.projetosorvil.bancodedados.Livro;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CadastroLivro extends AppCompatActivity {
    private EditText Titulo,Autor,Ano;



    private FirebaseAuth mAuth; //Variaves relacionadas a autentificação
    FirebaseUser user;          // Variaveis relacionadas autentificacao usuario

    FirebaseDatabase firebasedatabase; // Variaveis relacionadas ao banco de dados
    DatabaseReference databasereference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_livro);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Titulo = (EditText)findViewById(R.id.editText3);
        Autor  = (EditText)findViewById(R.id.editText4);
        Ano  = (EditText)findViewById(R.id.editText6);
    }


    public void CadastrarLivro(View view){
        String referencia_livro;
        String referencia_usario;

        firebasedatabase = FirebaseDatabase.getInstance();
        databasereference = firebasedatabase.getReference();

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();



        referencia_livro = databasereference.push().getKey(); //Referencia a um livro que será inserido no banco de dados
        Livro novo = new Livro(Titulo.getText().toString(),Autor.getText().toString(),Ano.getText().toString(),referencia_livro);
        databasereference.child("Livros").child(referencia_livro).setValue(novo);

        databasereference.child("Conta").child(user.getUid()).child("Livros").child(referencia_livro).setValue(referencia_livro);
        Toast.makeText(getApplicationContext(), "O livro foi inserido!",Toast.LENGTH_SHORT).show();

        Intent it = new Intent(CadastroLivro.this, BoasVindas.class); //Referencia uma mudança de tela
        startActivity(it); //Pula para a proxima janela referenciada


    }

}
