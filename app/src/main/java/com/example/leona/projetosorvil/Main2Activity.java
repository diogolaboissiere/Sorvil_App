package com.example.leona.projetosorvil;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.example.leona.projetosorvil.bancodedados.Conta;


public class Main2Activity extends AppCompatActivity { //Classe que representa a janela de cadastro de usuarios
    private EditText Login,Senha,NomeUsario; // Variaveis que representão a caixa de texto do login e senha

    private FirebaseAuth mAuth; //Variaves relacionadas a autentificação
    FirebaseUser user;          // Variaveis relacionadas autentificacao usuario

    FirebaseDatabase firebasedatabase; // Variaveis relacionadas ao banco de dados
    DatabaseReference databasereference;


    @Override
    protected void onCreate(Bundle savedInstanceState) { // Metodo que é executado quandoa janela é criada
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Login = (EditText)findViewById(R.id.editText5);
        Senha = (EditText)findViewById(R.id.editText7);
        NomeUsario = (EditText)findViewById(R.id.editText2);

        mAuth = FirebaseAuth.getInstance(); //Verifica qual usuario está logado
        user = mAuth.getCurrentUser();
        if(user == null)Toast.makeText(this, "Usuario nao autenticado", Toast.LENGTH_SHORT).show();

        firebasedatabase = FirebaseDatabase.getInstance();
        databasereference = firebasedatabase.getReference();
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        user = mAuth.getCurrentUser();

    }

    public void Cadastro(View view) {
        String login = Login.getText().toString();
        String senha = Senha.getText().toString();
        String nome = NomeUsario.getText().toString();


        firebasedatabase = FirebaseDatabase.getInstance();
        databasereference = firebasedatabase.getReference();

        mAuth.createUserWithEmailAndPassword(login, senha) //Metodo que cria a conta de usuario na firebase
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser(); //Variavel user recebe o "usuario" se a conta no firebase foi criada automaticamente


                            //Conta nova = new Conta(user.getUid(),NomeUsario.getText().toString(),Login.getText().toString()); //Extrutura que representa uma conta de usuario que irá ser insrida no banco de dados.

                            Conta nova = new Conta(user.getUid(),NomeUsario.getText().toString(),Login.getText().toString());

                            databasereference.child("Conta").child(user.getUid()).setValue(nova);

                            Toast.makeText(getApplicationContext(), "A conta foi criada com sucesso!",Toast.LENGTH_SHORT).show();

                            Intent it = new Intent(Main2Activity.this, MainActivity.class); //Referencia uma mudança de tela
                            startActivity(it); //Mudança de Tela



                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(getApplicationContext(), "Não foi possível crar a conta",Toast.LENGTH_SHORT).show();

                        }

                        // ...
                    }
                });

    }




    public void TesteUpload(){

        int i = 1;
        i = i+1;
        Toast.makeText(getApplicationContext(), "O valor de i é: " + i, Toast.LENGTH_LONG).show();
    }


}
