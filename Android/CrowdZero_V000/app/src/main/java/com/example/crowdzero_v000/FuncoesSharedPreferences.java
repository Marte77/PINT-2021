package com.example.crowdzero_v000;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.Map;

public class FuncoesSharedPreferences {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    /**
     * @param getSharedPrefs = getSharedPreferences("InfoPessoa", Context.MODE_PRIVATE);
     *
     */
    public FuncoesSharedPreferences(SharedPreferences getSharedPrefs){
        sharedPreferences = getSharedPrefs;
        editor = sharedPreferences.edit();
    }

    public void logarSharedPrefs(){
        Map<String,? > map = sharedPreferences.getAll();
        for(Map.Entry<String,?> a : map.entrySet())
            Log.i("testar",a.getKey() + " -> "+a.getValue());
    }

    /**
    * @return false se nao existir ou se nao estiver verificado
    * */
    public boolean getVerificacao(){
        return sharedPreferences.getBoolean("Verificado",false);
    }

    /**
     * @return "Outros_Util" ou "Util_Instituicao"
     * */
    public String getTipoPessoa(){
        return sharedPreferences.getString("TipoPessoa","Outros_Util");
    }

    /**
     * @return 0 se der erro
     * */
    public int getIDPessoa(){
        return sharedPreferences.getInt("IDPessoa",0);
    }

    /**
     * @return 0 se der erro
     * */
    public int getIDUtilizador(){
        return sharedPreferences.getInt("IDUtil",0);
    }

    /**
     * @return null se der erro
     * */
    public String getEmail(){
        return sharedPreferences.getString("Email",null);
    }

    /**
     * @return null se der erro
     * */
    public String getPassword(){
        return sharedPreferences.getString("Password",null);
    }

    /**
     * @return falso se nao estiver iniciada
     * */
    public boolean getSessaoIniciada(){
        return sharedPreferences.getBoolean("SessaoIniciada",false);
    }

    /**
     * @param sessaoIniciada  boolean
     * */
    public void setSessaoIniciada(boolean sessaoIniciada){
        editor.putBoolean("SessaoIniciada",sessaoIniciada);
        editor.apply();
        editor.commit();
    }

    /**
     * @param password  String
     * */
    public void setPassword(String password){
        editor.putString("Password",password);
        editor.apply();
        editor.commit();
    }

    /**
     * @param email  String
     * */
    public void setEmail(String email){
        editor.putString("Email",email);
        editor.apply();
        editor.commit();
    }

    /**
     * @param idUtilizador  int
     * */
    public void setIDUtilizador(int idUtilizador){
        editor.putInt("IDUtil",idUtilizador);
        editor.apply();
        editor.commit();
    }

    /**
     * @param idPessoa  int
     * */
    public void setIDPessoa(int idPessoa){
        editor.putInt("IDPessoa",idPessoa);
        editor.apply();
        editor.commit();
    }

    /**
     * @param tipoPessoa  String
     * */
    public void setTipoPessoa(String tipoPessoa){
        if(tipoPessoa.equals("Outros_Util")||tipoPessoa.equals("Util_Instituicao")) {
            editor.putString("TipoPessoa", tipoPessoa);
            editor.apply();
            editor.commit();
        }
    }

    /**
     * @param verificacao  boolean
     * */
    public void setVerificacao(boolean verificacao){
        editor.putBoolean("Verificado", verificacao);
        editor.apply();
        editor.commit();
    }
}
