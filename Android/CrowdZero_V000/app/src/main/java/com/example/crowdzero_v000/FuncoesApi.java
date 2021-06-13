package com.example.crowdzero_v000;

import android.content.Context;
import android.util.Log;

import androidx.core.content.ContextCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class FuncoesApi {
    //static String urlGeral ="http://pint2021.herokuapp.com";
    static String urlGeral = "http://192.168.1.78";

    static class FuncoesReports{
        static void criarNovoReportOutdoorOutrosUtil(
                Context getAppContext,
                String descReport, int nivelDensidade, int idLocal, int idOutroUtil,
                final ListaReportsInstituicaoActivity.volleycallback VCB) throws JSONException {

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
}
