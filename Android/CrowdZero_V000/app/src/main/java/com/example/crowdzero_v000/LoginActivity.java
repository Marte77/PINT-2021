package com.example.crowdzero_v000;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {


    TextInputEditText emailTxt,passwordTxt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        MaterialToolbar tb = findViewById(R.id.appbar_login);
        String titulo = "      "+getResources().getString(R.string.titulo_Login);
        tb.setTitle(titulo);

        emailTxt = findViewById(R.id.emailInputLogin);
        passwordTxt = findViewById(R.id.passwordInputLogin);




    }
    void irParaPaginalPrincipal(){
        Intent i = new Intent(getApplicationContext(),PaginaPrincipal.class);
        startActivity(i);
    }

    public void onClick(View view){
        switch (view.getId()){
            case R.id.botaoLogin2: {
                if(emailTxt.getText().toString().isEmpty() || passwordTxt.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(),"Preencha os campos todos!",Toast.LENGTH_SHORT).show();
                    return;
                }
                try{
                    FuncoesApi.FuncoesPessoas.fazerLogin(getApplicationContext(),
                            emailTxt.getText().toString(), passwordTxt.getText().toString(),
                            new FuncoesApi.volleycallback() {
                                @Override
                                public void onSuccess(JSONObject jsonObject) throws JSONException {
                                    if(!jsonObject.getBoolean("login")){
                                        return;
                                    }
                                    SharedPreferences sharedPreferences = getSharedPreferences("InfoPessoa", Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedPreferences.edit();

                                    JSONObject infoutil= jsonObject.getJSONObject("PessoaLogin");
                                    JSONObject infopessoa= infoutil.getJSONObject("Pessoa");
                                    if(jsonObject.getString("TipoPessoa").equals("Outros_Util")){
                                        editor.putInt("IDUtil",infoutil.getInt("ID_Outro_Util"));
                                    }else if(jsonObject.getString("TipoPessoa").equals("Util_Instituicao")){
                                        editor.putInt("IDUtil",infoutil.getInt("ID_Util"));
                                    }else{
                                        Toast.makeText(getApplicationContext(),"Contas admin não podem usar a app", Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                    editor.putString("TipoPessoa",jsonObject.getString("TipoPessoa"));
                                    editor.putInt("IDPessoa",infopessoa.getInt("IDPessoa"));
                                    editor.putString("Email",emailTxt.getText().toString());
                                    // TODO: 18/06/2021 encriptar password
                                    editor.putString("Password",passwordTxt.getText().toString());
                                    editor.putBoolean("SessaoIniciada",true);
                                    editor.apply();
                                    editor.commit();
                                    irParaPaginalPrincipal();
                                }

                                @Override
                                public void onError(JSONObject jsonObjectErr) throws JSONException {

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
}