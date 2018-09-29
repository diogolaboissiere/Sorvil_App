package com.example.leona.projetosorvil;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class BoasVindas extends AppCompatActivity{
    private String nomeusario;
    private TextView texto;

    private FirebaseAuth mAuth; //Variaves relacionadas a autentificação
    FirebaseUser user;          // Variaveis relacionadas autentificacao usuario

    FirebaseDatabase firebasedatabase; // Variaveis relacionadas ao banco de dados
    DatabaseReference databasereference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boas_vindas);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mAuth = FirebaseAuth.getInstance(); //Verifica qual usuario está logado
        user = mAuth.getCurrentUser();
        if(user == null) Toast.makeText(this, "Usuario nao autenticado", Toast.LENGTH_SHORT).show();

        firebasedatabase = FirebaseDatabase.getInstance();  //Ligando o banco de dados
        databasereference = firebasedatabase.getReference();

        databasereference.child("Conta").child(user.getUid()).child("nome_usuario").addListenerForSingleValueEvent( //Metodo que tenta acessar um campo no banco de dados
                new ValueEventListener() {
                    String n1;
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {  //Se a tentiva de consulta for sucessida esta funcao é ativada
                        // I commented the code here to check whether its coming from this part but it continued to occur even after commenting.


                        nomeusario = (String) dataSnapshot.getValue().toString();

                        texto =  (TextView) findViewById(R.id.textView8);
                        texto.setText("Bem vindo Usuario "+nomeusario+"");
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {  //Se a tentativa de consulta nao for possivel esta funcao e chamada
                        Toast.makeText(getApplicationContext(), "Requisão falhou!", Toast.LENGTH_SHORT).show();
                    }
                });


        texto =  (TextView) findViewById(R.id.textView8); //Caixa de texto bem vindo usuario
        texto.setText("Bem vindo Usuario");

    }

    public void CadastroLivro(View view){
        Intent it = new Intent(BoasVindas.this, CadastroLivro.class); //Referencia uma mudança de tela
        startActivity(it); //Pula para a proxima janela referenciada
    }

    public void PesquisaLivro(View view){
        Intent it = new Intent(BoasVindas.this, Buscalivros.class); //Referencia uma mudança de tela
        startActivity(it); //Pula para a proxima janela referenciada
    }

    public void ListaLivros(View view){
        Intent it = new Intent(BoasVindas.this,Listalivros.class); //Referencia uma mudança de tela
        startActivity(it); //Pula para a proxima janela referenciada
    }
}
