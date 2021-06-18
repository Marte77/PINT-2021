package com.example.crowdzero_v000;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.loader.content.AsyncTaskLoader;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;


public class FuncoesApi {

    public interface volleycallback{
        void onSuccess(JSONObject jsonObject) throws JSONException;
        void onError(JSONObject jsonObjectErr) throws JSONException;
    }
    //static String urlGeral ="http://pint2021.herokuapp.com";
    static String urlGeral = "http://192.168.3.132:3000";

//todo: verificar se o status da resposta é 500, o status que esta na resposta ou no header da resposta
    public static class FuncoesReports{
        public static void criarNovoReportOutdoorOutrosUtil(
                final Context getAppContext,
                String descReport, int nivelDensidade, int idLocal, int idOutroUtil,
                final volleycallback VCB) throws JSONException {

            String url = urlGeral + "/Report/novo_report_outdoor_outros";
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
                            Toast.makeText(getAppContext,"Erro de conexão",Toast.LENGTH_LONG).show();
                            Log.i("pedido","ERRO: "+new String(error.networkResponse.data, StandardCharsets.UTF_8));

                            try {
                                VCB.onError(new JSONObject(new String(error.networkResponse.data, StandardCharsets.UTF_8)));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
            );
            request.add(jsonObjectRequest);
        }
    }

    public static class FuncoesLocais{
        public static void getTodosLocais(Context getAppContext, final volleycallback VCB){
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
                            try {
                                VCB.onError(new JSONObject(new String(error.networkResponse.data, StandardCharsets.UTF_8)));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            Log.i("pedido","ERRO: " + new String(error.networkResponse.data, StandardCharsets.UTF_8));
                        }
                    }
            );
            request.add(jsonObjectRequest);
        }

        public static void getLocalPorId(Context context, int idLocal, final volleycallback VCB){
            RequestQueue request = Volley.newRequestQueue(context);
            String url =urlGeral+"/Locais/getLocalPorId/"+idLocal;
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
                            try {
                                VCB.onError(new JSONObject(new String(error.networkResponse.data, StandardCharsets.UTF_8)));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            Log.i("pedido","ERRO: " + new String(error.networkResponse.data, StandardCharsets.UTF_8));
                        }
                    }
            );
            request.add(jsonObjectRequest);
        }
    }

    public static class FuncoesPessoas{
        public static void fazerLogin(Context context, String email, String password,final volleycallback VCB) throws JSONException {
            String url = urlGeral + "/Pessoas/login";
            RequestQueue request = Volley.newRequestQueue(context);
            JSONObject body = new JSONObject();
            body.put("Email", email);
            // TODO: 18/06/2021 encriptar a password
            body.put("Password", password);
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                    Request.Method.POST, url,body,
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
                            try {
                                VCB.onError(new JSONObject(new String(error.networkResponse.data, StandardCharsets.UTF_8)));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            Log.i("pedido","ERRO: " + new String(error.networkResponse.data, StandardCharsets.UTF_8));
                        }
                    }
            );
            request.add(jsonObjectRequest);
        }
    }

    static public void downloadImagem(Context context, String url, final ImageView imageView){
        RequestQueue request = Volley.newRequestQueue(context);
        ImageRequest imageRequest = new ImageRequest(url, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
                imageView.setImageBitmap(response);
            }
        }, imageView.getMaxWidth(), imageView.getMaxHeight(),
                null, null, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("pedido","ErroImagem: " +new String(error.networkResponse.data, StandardCharsets.UTF_8));
            }
        });
        request.add(imageRequest);
    }

    public interface volleyimagecallback{
        void onSuccess(Bitmap bitmap);
    }
    static public void downloadImagem(Context context, String url, final volleyimagecallback VCB){
        RequestQueue request = Volley.newRequestQueue(context);
        ImageRequest imageRequest = new ImageRequest(url, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
                VCB.onSuccess(response);
            }
        }, 0, 0,
                null, null, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("pedido","ErroImagem: "+ new String(error.networkResponse.data, StandardCharsets.UTF_8));
            }
        });
        request.add(imageRequest);
    }



}
