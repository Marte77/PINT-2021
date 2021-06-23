package com.example.crowdzero_v000;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {


    TextInputEditText emailTxt,passwordTxt;
    String activityAnterior;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        MaterialToolbar tb = findViewById(R.id.appbar_login);
        String titulo = "      "+getResources().getString(R.string.titulo_Login);
        tb.setTitle(titulo);
        activityAnterior = getIntent().getExtras().getString("activity");
        emailTxt = findViewById(R.id.emailInputLogin);
        passwordTxt = findViewById(R.id.passwordInputLogin);

        listenersClickParaErrorText();

    }

    @Override
    public void onBackPressed() {
        if(activityAnterior.equals("main")){
            super.onBackPressed();
        }else{
            return;
        }
    }

    private void listenersClickParaErrorText() {

        emailTxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(((TextInputLayout)findViewById(R.id.textinputEmail)).isErrorEnabled())
                    ((TextInputLayout)findViewById(R.id.textinputEmail)).setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        passwordTxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(((TextInputLayout)findViewById(R.id.textinputPassword)).isErrorEnabled())
                    ((TextInputLayout)findViewById(R.id.textinputPassword)).setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        TextView hyperlinksemreg2 = findViewById(R.id.hiperlink_Sem_registo2);
        hyperlinksemreg2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i  = new Intent(getApplicationContext(),RegistoActivity.class);
                startActivity(i);
            }
        });
    }

    void irParaPaginalPrincipal(){
        Intent i = new Intent(getApplicationContext(),PaginaPrincipal.class);
        startActivity(i);
    }

    /**
     * SharedPreferences sharedPreferences = getSharedPreferences("InfoPessoa", Context.MODE_PRIVATE);
     * SharedPreferences.Editor editor = sharedPreferences.edit();
     * Vai inserir nas shared preferences estes elementos
     * IDUtil
     * @TipoPessoa - Pode ser "Admin","Outros_Util","Util_Instituicao" - Admin nunca é usado na app
     * IDPessoa
     * Email
     * Password
     * SessaoIniciada - boolean
     * Verificado - boolean
     * */

    public void onClick(View view){
        switch (view.getId()){
            case R.id.botaoLogin2: {
                boolean inputsvazios = false;
                if(emailTxt.getText().toString().isEmpty()){
                    inputsvazios = true;
                    ((TextInputLayout)findViewById(R.id.textinputEmail)).setErrorEnabled(true);
                    ((TextInputLayout)findViewById(R.id.textinputEmail)).setError("Insira o email");
                }
                if( passwordTxt.getText().toString().isEmpty()){
                    inputsvazios = true;
                    ((TextInputLayout)findViewById(R.id.textinputPassword)).setErrorEnabled(true);
                    ((TextInputLayout)findViewById(R.id.textinputPassword)).setError("Insira a password");
                }
                if(inputsvazios)
                    return;

                try{
                    FuncoesApi.FuncoesPessoas.fazerLogin(getApplicationContext(),
                            emailTxt.getText().toString(), passwordTxt.getText().toString(),
                            new FuncoesApi.volleycallback() {
                                @Override
                                public void onSuccess(JSONObject jsonObject) throws JSONException {
                                    if(!jsonObject.getBoolean("login")){
                                        return;
                                    }
                                    if (colocarNasSharedPreferences(jsonObject)) return;
                                    irParaPaginalPrincipal();
                                }

                                @Override
                                public void onError(JSONObject jsonObjectErr) throws JSONException {
                                    if(jsonObjectErr.getString("err").equals("Email nao existe")){
                                        Toast.makeText(getApplicationContext(),"O Email inserido nao existe!",Toast.LENGTH_LONG).show();
                                    }else if(jsonObjectErr.getString("err").equals("Password Incorreta")){
                                        Toast.makeText(getApplicationContext(),"A password inserida está incorreta!",Toast.LENGTH_LONG).show();

                                    }
                                }
                            });
                }catch (JSONException e){
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),"Ocorreu um erro",Toast.LENGTH_SHORT).show();
                }

                break;
            }
        }
    }

    /**
     * SharedPreferences sharedPreferences = getSharedPreferences("InfoPessoa", Context.MODE_PRIVATE);
     * SharedPreferences.Editor editor = sharedPreferences.edit();
     * Vai inserir nas shared preferences estes elementos
     * IDUtil
     * @TipoPessoa - Pode ser "Admin","Outros_Util","Util_Instituicao" - Admin nunca é usado na app
     * IDPessoa
     * Email
     * Password
     * SessaoIniciada - boolean
     * Verificado - boolean
     * */

    private boolean colocarNasSharedPreferences(final JSONObject jsonObject) throws JSONException {
        SharedPreferences sharedPreferences = getSharedPreferences("InfoPessoa", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        JSONObject infoutil= jsonObject.getJSONObject("PessoaLogin");
        JSONObject infopessoa= infoutil.getJSONObject("Pessoa");
        if(jsonObject.getString("TipoPessoa").equals("Outros_Util")){
            editor.putInt("IDUtil",infoutil.getInt("ID_Outro_Util"));
        }else if(jsonObject.getString("TipoPessoa").equals("Util_Instituicao")){
            editor.putInt("IDUtil",infoutil.getInt("ID_Util"));
            editor.putBoolean("Verificado",infoutil.getBoolean("Verificado"));
        }else{
            Toast.makeText(getApplicationContext(),"Contas admin não podem usar a app", Toast.LENGTH_SHORT).show();
            return true;
        }
        editor.putString("TipoPessoa", jsonObject.getString("TipoPessoa"));
        editor.putInt("IDPessoa",infopessoa.getInt("IDPessoa"));
        editor.putString("Email",emailTxt.getText().toString());
        // TODO: 18/06/2021 encriptar password
        editor.putString("Password",passwordTxt.getText().toString());
        editor.putBoolean("SessaoIniciada",true);
        editor.apply();
        editor.commit();
        return false;
    }
}