package com.example.leona.projetosorvil;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.leona.projetosorvil.bancodedados.Livro;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class Buscalivros extends AppCompatActivity {
    private RecyclerView listagem;
    private ConstraintLayout layoutContentMain;
    private LivroAdapter livroAdapter;
    private List<Livro> dados;
    private EditText pesquisa;

    private FirebaseAuth mAuth; //Variaves relacionadas a autentificação
    FirebaseUser user;          // Variaveis relacionadas autentificacao usuario

    FirebaseDatabase firebasedatabase; // Variaveis relacionadas ao banco de dados
    DatabaseReference databasereference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscalivros);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        pesquisa = (EditText)findViewById(R.id.editText8);
        dados = new ArrayList<Livro>();

        InicializaComponetes();

        listagem = (RecyclerView)findViewById(R.id.listalivros);
        listagem.setHasFixedSize(true);
        layoutContentMain = (ConstraintLayout)findViewById(R.id.layout1);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        listagem.setLayoutManager(linearLayoutManager);
        livroAdapter = new LivroAdapter(dados);
        listagem.setAdapter(livroAdapter);


    }

    public void InicializaComponetes(){
        firebasedatabase = FirebaseDatabase.getInstance();
        databasereference = firebasedatabase.getReference();

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
    }

    public void Buscar(View view){
        CarregarLivros(pesquisa.getText().toString());
    }

    public void CarregarLivros(String texto){
        Query query;

        if(texto.equals("")) query = databasereference.child("Livros").orderByChild("titulo");
        else{
            query = databasereference.child("Livros").orderByChild("titulo").startAt(texto).endAt(texto+"\uf8ff");
        }

        dados.clear();
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int i = 0;
                for(DataSnapshot objSnapshot:dataSnapshot.getChildren()){
                    Livro aux = objSnapshot.getValue(Livro.class);
                    dados.add(aux);
                }
                livroAdapter = new LivroAdapter(dados);
                listagem.setAdapter(livroAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void SelecionarLivro(View view){
        Intent it = new Intent(Buscalivros.this, ConsultaLivro.class); //Referencia uma mudança de tela
        startActivity(it); //Pula para a proxima janela referenciada
    }


}
