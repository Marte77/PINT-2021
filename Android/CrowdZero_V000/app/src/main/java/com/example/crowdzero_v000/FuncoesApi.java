package com.example.crowdzero_v000;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class FuncoesApi {
    public interface volleycallback{
        void onSuccess(JSONObject jsonObject) throws JSONException;
    }
    //static String urlGeral ="http://pint2021.herokuapp.com";
    static String urlGeral = "http://192.168.1.78";

    static class FuncoesReports{
        static void criarNovoReportOutdoorOutrosUtil(
                Context getAppContext,
                String descReport, int nivelDensidade, int idLocal, int idOutroUtil,
                final volleycallback VCB) throws JSONException {

            String url = urlGeral + "/Report/novo_report_outdoor_outros";
            final JSONObject resposta = new JSONObject();
            RequestQueue request = Volley.newRequestQueue(getAppContext);
            JSONObject bodyReq = new JSONObject();
            bodyReq.put("DescricaoReport",descReport);
            bodyReq.put("NivelDensidade",nivelDensidade);
            bodyReq.put("IDLocal",idLocal);
            bodyReq.put("idOutroUtil",idOutroUtil);

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                    Request.Method.POST, url, bodyReq,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                VCB.onSuccess(response);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            /*Log.i("pedido","Sucesso: " +response.toString());
                            boolean i = true;
                            try {
                                resposta.put("status",response.getInt("status"));
                                resposta.put("report",response.getJSONObject("Report"));
                                resposta.put("report",response.getJSONObject("ReportOut"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                                i= false;
                            }
                            if(i) {
                                VCB.onSuccess(resposta);
                            }*/
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.i("pedido","ERRO: " + error);
                        }
                    }
            );
            request.add(jsonObjectRequest);
        }
    }

    static class FuncoesLocais{
        static void getTodosLocais(Context getAppContext, final volleycallback VCB){
            RequestQueue request = Volley.newRequestQueue(getAppContext);
            String url =urlGeral+ "/Locais/listar";
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                    Request.Method.GET, url, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {

                                VCB.onSuccess(response);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.i("pedido","ERRO: " + error);
                        }
                    }
            );
            request.add(jsonObjectRequest);
        }
    }
/*
    static public Bitmap downloadImagem(String urlimagem){
        try{
            URL url = new URL(urlimagem);
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream inputStream= connection.getInputStream();
            return BitmapFactory.decodeStream(inputStream);
        }catch (IOException e){
            e.printStackTrace();
            return null;
        }
    }*/



}
