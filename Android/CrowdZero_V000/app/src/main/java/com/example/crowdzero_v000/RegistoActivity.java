package com.example.crowdzero_v000;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.drawerlayout.widget.DrawerLayout;

import com.example.crowdzero_v000.classesDeAjuda.FuncoesApi;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class RegistoActivity extends NavDrawerActivity{
    TextInputEditText emailIn, passwordIn,pNomeIn,uNomeIn, codPostalIn,localizacaoIn;
    Button cidadeBtn, dataNascBtn, fazerRegBtn, instituicaoBtn;
    String pertenceInstituicao="";//tem o nome da instituicao
    int idInstituicao=0;
    String dataNascimento;
    CheckBox pertenceInst;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registo);
        this.mudarNomeToolBar("Registo");
        int alturatb = this.tb.getLayoutParams().height;
        LinearLayout linearLayout = ((LinearLayout) findViewById(R.id.linearLayoutRegistoActivity));
        linearLayout.setPadding(linearLayout.getLeft(), alturatb, linearLayout.getRight(), linearLayout.getBottom());
        inicializacaoEListeners();
        dl.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
    }

    @Override //é feito este override de modo a que o utilizador nao aceda ao menu lateral no registo
    protected void abrirDrawer(View view) {
        //do nothing
    }

    private void inicializacaoEListeners() {
        emailIn = findViewById(R.id.emailInputRegisto);
        passwordIn = findViewById(R.id.passwordInputRegisto);
        pNomeIn = findViewById(R.id.PrimeiroNomeInputRegisto);
        uNomeIn = findViewById(R.id.UltimoNomeInputRegisto);
        cidadeBtn = findViewById(R.id.botaoCidadeRegisto);
        dataNascBtn = findViewById(R.id.botaoDataNascimentoRegisto);
        fazerRegBtn = findViewById(R.id.botaoFazerRegisto);
        instituicaoBtn = findViewById(R.id.botaoInstituicaoRegisto);
        pertenceInst = findViewById(R.id.pertenceInstCheckBoxReg);
        codPostalIn = findViewById(R.id.CodigoPostalInputRegisto);
        localizacaoIn = findViewById(R.id.LocalizacaoInputRegisto);

        //region cidadebtn listener
        cidadeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirCidadeDialog();
            }
        });
        //endregion

        //region dataNascBtn listener
        dataNascBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirCalendario();
            }
        });
        //endregion

        //region fazerRegBtn listener
        fazerRegBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fazerRegisto();

            }
        });
        //endregion

        //region instituicaobtn listener
        instituicaoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(instituicaoBtn.isEnabled()){
                    getInstituicoesEDialog();
                }else{
                    Toast.makeText(getApplicationContext(),"Tem de confirmar que pertence a uma instituição",Toast.LENGTH_LONG).show();
                }
            }
        });
        //endregion

        //region check box listener
        instituicaoBtn.setAlpha(0.4f);
        pertenceInst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pertenceInst.isChecked()) {
                    instituicaoBtn.setEnabled(true);
                    instituicaoBtn.setAlpha(1.0f);
                }
                else {
                    instituicaoBtn.setEnabled(false);
                    instituicaoBtn.setAlpha(0.4f);
                }
            }
        });
        //endregion
    }



    public void fazerRegisto() {
        if(localizacaoIn.getText().toString().isEmpty()||emailIn.getText().toString().isEmpty() || passwordIn.getText().toString().isEmpty()
                || pNomeIn.getText().toString().isEmpty() || uNomeIn.getText().toString().isEmpty()
                || opcaoCidade == -1 || dataNascBtn.getText().toString().isEmpty()
                || codPostalIn.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(),"Preencha os campos todos",Toast.LENGTH_LONG).show();
            return;
        }

        String passwordEncriptada = null;
        try {
            passwordEncriptada = FuncoesApi.encriptarString(passwordIn.getText().toString());
        } catch (Exception e) {
            e.printStackTrace();
            Log.i("testar","Erro Password:" + e.toString());
            return;
        }
        final String email = emailIn.getText().toString();
        String pNome = pNomeIn.getText().toString();
        String uNome = uNomeIn.getText().toString();
        String cidadeescolhida = cidades[opcaoCidade];
        String codpostal = codPostalIn.getText().toString();
        String localizacao = localizacaoIn.getText().toString();
        Log.i("testar",passwordEncriptada);
        if(pertenceInst.isChecked()) {
            //region criar util inst
            try {
                JSONObject body = new JSONObject();
                body.put("Email", email);
                body.put("Password", passwordEncriptada);
                body.put("Data_Nascimento",dataNascimento);
                body.put("Cidade",cidadeescolhida);
                body.put("Codigo_Postal",codpostal);
                body.put("UNome",uNome);
                body.put("PNome",pNome);
                body.put("InstituicaoIDInstituicao",idInstituicao);
                body.put("Localização", localizacao);
                FuncoesApi.FuncoesPessoas.criarUtilInstituicao(getApplicationContext(), body, new FuncoesApi.volleycallback() {
                    @Override
                    public void onSuccess(JSONObject jsonObject) throws JSONException {
                        Log.i("pedido","Sucesso util isnt "+jsonObject.toString());
                        comecarActivityPaginaPrincipal();
                        idPessoa = jsonObject.getJSONObject("Pessoa").getInt("IDPessoa");
                        idUtil= jsonObject.getJSONObject("Utilizador").getInt("ID_Util");
                        boolean isVerificado = jsonObject.getJSONObject("Utilizador").getBoolean("Verificado");
                        inserirSharedPreferences(true,isVerificado);
                        Toast.makeText(getApplicationContext(),"Registo feito com sucesso",Toast.LENGTH_LONG);
                        finish();
                    }

                    @Override
                    public void onError(JSONObject jsonObjectErr) throws JSONException {
                        Log.i("pedido",jsonObjectErr.toString());
                        Toast.makeText(getApplicationContext(),"Erro a fazer registo",Toast.LENGTH_LONG);
                    }
                });
            }catch (Exception e){
                e.printStackTrace();
            }
            //endregion
        }else {
            //region criar outro util
            try {
                JSONObject body = new JSONObject();
                body.put("Email", email);
                body.put("Password", passwordEncriptada);
                body.put("Data_Nascimento", dataNascimento);
                body.put("Cidade", cidadeescolhida);
                body.put("Codigo_Postal", codpostal);
                body.put("UNome", uNome);
                body.put("PNome", pNome);
                body.put("Localização", localizacao);
                body.put("InstituicaoIDInstituicao", idInstituicao);
                FuncoesApi.FuncoesPessoas.criarOutroUtil(getApplicationContext(), body, new FuncoesApi.volleycallback() {
                    @Override
                    public void onSuccess(JSONObject jsonObject) throws JSONException {
                        Log.i("pedido","Sucesso outro util "+jsonObject.toString());
                        comecarActivityPaginaPrincipal();
                        idPessoa = jsonObject.getJSONObject("Pessoa").getInt("IDPessoa");
                        idUtil= jsonObject.getJSONObject("Outros_Util").getInt("ID_Outro_Util");
                        inserirSharedPreferences(false,false);
                    }

                    @Override
                    public void onError(JSONObject jsonObjectErr) throws JSONException {
                        Log.i("pedido","Erro"+jsonObjectErr.toString());
                        if(jsonObjectErr.has("err")){
                            if(jsonObjectErr.getString("err").equals("Error: O email inserido ja existe")){
                                Toast.makeText(getApplicationContext(),"O email inserido já existe",Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
            //endregion
        }

    }

    int idPessoa=0, idUtil=0;
    private void inserirSharedPreferences( boolean isUtilInst, boolean isVerificado) throws JSONException {
        SharedPreferences sharedPreferences = getSharedPreferences("InfoPessoa", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        //editor.clear();
        if(!isUtilInst) {
            editor.putString("TipoPessoa", "Outro_Util");
        }
        else {
            editor.putString("TipoPessoa", "Util_Instituicao");
            editor.putBoolean("Verificado", isVerificado);
        }

        editor.putInt("IDUtil", idUtil);
        editor.putInt("IDPessoa", idPessoa);
        editor.putString("Email", emailIn.getText().toString());
        editor.putString("Password", passwordIn.getText().toString());
        editor.putBoolean("SessaoIniciada", true);
        editor.apply();
        editor.commit();
    }

    void comecarActivityPaginaPrincipal(){
        Intent i = new Intent(getApplicationContext(),PaginaPrincipal.class);
        startActivity(i);
    }

    /*
    * //vai pegar a lista de instituicoes e depois no callback abre um dialogo com as instituicoes todas
    * o utilizador escolhe uma delas e é guardado numa variavel
    * */
    void getInstituicoesEDialog(){
        FuncoesApi.FuncoesInstituicoes.getListaInstituicoes(getApplicationContext(), new FuncoesApi.volleycallback() {
            @Override
            public void onSuccess(JSONObject jsonObject) throws JSONException {
                int nInstituicoes = jsonObject.getJSONArray("Instituicoes").length();
                String[] listaInstituicoes = new String[nInstituicoes];
                for(int i = 0;i<nInstituicoes;i++){
                    listaInstituicoes[i] = jsonObject.getJSONArray("Instituicoes").getJSONObject(i).getString("Nome");
                }
                abrirInstituicoesDialog(listaInstituicoes);
            }
            @Override
            public void onError(JSONObject jsonObjectErr) throws JSONException {
            }
        });
    }
    public void abrirInstituicoesDialog(final String[]instituicoes){
        int opcao = 0;
        MaterialAlertDialogBuilder dialogBuilder= new MaterialAlertDialogBuilder(RegistoActivity.this);
        dialogBuilder.setTitle("Instituicão");
        //region neuutralbutton
        dialogBuilder.setNeutralButton(R.string.cancelar,new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
                idInstituicao=0;
                pertenceInstituicao ="";
            }
        });
        //endregion
        //region positivebutton
        dialogBuilder.setPositiveButton(R.string.selecionar, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Log.i("testar",pertenceInstituicao + " id"+idInstituicao);
            }
        });
        //endregion
        //region singlechoiceitems
        dialogBuilder.setSingleChoiceItems(instituicoes, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                pertenceInstituicao=instituicoes[i];
                idInstituicao=i+1;
                instituicaoBtn.setText(instituicoes[i]);
            }
        });
        //endregion
        dialogBuilder.show();
    }


    public void abrirCalendario(){
        MaterialDatePicker.Builder<Long> calendario = MaterialDatePicker.Builder.datePicker();
        calendario.setTitleText(R.string.data_nascimento_registo);

        final MaterialDatePicker<Long> datePicker = calendario.build();
        datePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Long>() {
            @Override
            public void onPositiveButtonClick(Long selection) {
                dataNascBtn.setText(datePicker.getHeaderText());
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
                Date date = new Date(selection);
                dataNascimento = dateFormat.format(date);
            }
        });

        datePicker.show(getSupportFragmentManager(), String.valueOf(R.string.data_nascimento_registo));
    }

    final String[] cidades = {"Aveiro","Beja","Braga","Bragança","Castelo Branco","Coimbra","Évora","Faro","Guarda","Leiria","Lisboa","Portalegre","Porto","Santarém","Setúbal","Viana do Castelo","Vila Real","Viseu"};
    int opcaoCidade = -1;
    public void abrirCidadeDialog(){


        final int opcao = -1;
        MaterialAlertDialogBuilder dialogo = new MaterialAlertDialogBuilder(RegistoActivity.this);
        dialogo.setTitle(R.string.cidade);
        dialogo.setNeutralButton(R.string.cancelar, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
                opcaoCidade = -1;
            }
        });
        dialogo.setPositiveButton(R.string.selecionar, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        dialogo.setSingleChoiceItems(cidades, opcao, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                cidadeBtn.setText(cidades[i]);
                opcaoCidade=i;
            }
        });
        dialogo.show();

    }
}