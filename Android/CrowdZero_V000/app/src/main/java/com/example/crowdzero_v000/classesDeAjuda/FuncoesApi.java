package com.example.crowdzero_v000.classesDeAjuda;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
public class FuncoesApi {

    public interface volleycallback{
        void onSuccess(JSONObject jsonObject) throws JSONException;
        void onError(JSONObject jsonObjectErr) throws JSONException;
    }
    static String urlGeral ="http://pint2021.herokuapp.com";
    //static String urlGeral = "http://192.168.3.132:3000";

    public static class FuncoesReports {
        public static void criarNovoReportOutdoorOutrosUtil(
                final Context getAppContext,
                String descReport, int nivelDensidade, int idLocal, int idOutroUtil,
                final volleycallback VCB) throws JSONException {

            String url = urlGeral + "/Report/novo_report_outdoor_outros";
            RequestQueue request = Volley.newRequestQueue(getAppContext);
            JSONObject bodyReq = new JSONObject();
            bodyReq.put("DescricaoReport", descReport);
            bodyReq.put("NivelDensidade", nivelDensidade);
            bodyReq.put("IDLocal", idLocal);
            bodyReq.put("idOutroUtil", idOutroUtil);
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
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            try {
                                VCB.onError(new JSONObject(new String(error.networkResponse.data, StandardCharsets.UTF_8)));
                                Log.i("pedido","ERRO: " + new String(error.networkResponse.data, StandardCharsets.UTF_8));
                            } catch (Exception e ) {
                                e.printStackTrace();
                                Log.i("pedido","Catch ERRO: "+ e);
                            }
                        }
                    }
            );
            request.add(jsonObjectRequest);
        }

        /**
         * @param tipoTempo: = hh - horas, mm - minutos, dd - dias
         * @param tempo:     int da quantidade de tipoTempo
         */
        public static void getListaReportsOutdoor(final Context context, int idLocal, String tipoTempo, int tempo, final volleycallback VCB) throws JSONException {
            String url = urlGeral + "/Report/get_lista_reports_local/" + idLocal;
            RequestQueue request = Volley.newRequestQueue(context);
            JSONObject body = new JSONObject();
            body.put("tempo", tempo);
            body.put("tipoTempo", tipoTempo);
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                    Request.Method.PUT, url, body, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    //Log.i("pedido",response.toString());
                    try {
                        VCB.onSuccess(response);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    try {
                        VCB.onError(new JSONObject(new String(error.networkResponse.data, StandardCharsets.UTF_8)));
                        Log.i("pedido","ERRO: " + new String(error.networkResponse.data, StandardCharsets.UTF_8));
                    } catch (Exception e ) {
                        e.printStackTrace();
                        Log.i("pedido","Catch ERRO: "+ e);
                    }
                }
            }
            );
            request.add(jsonObjectRequest);
        }

        public static void getDensidadeMediaPorLocal( final Context context, int idLocal, String tipoTempo, int tempo,final volleycallback VCB) throws JSONException {
            String url = urlGeral + "/Report/get_densidade_media_local/"+idLocal;
            RequestQueue request = Volley.newRequestQueue(context);
            JSONObject body = new JSONObject();
            body.put("tempo",tempo);
            body.put("tipoTempo",tipoTempo);
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                    Request.Method.PUT, url, body, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    //Log.i("pedido",response.toString());
                    try {
                        VCB.onSuccess(response);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    try {
                        VCB.onError(new JSONObject(new String(error.networkResponse.data, StandardCharsets.UTF_8)));
                        Log.i("pedido","ERRO: " + new String(error.networkResponse.data, StandardCharsets.UTF_8));
                    } catch (Exception e ) {
                        e.printStackTrace();
                        Log.i("pedido","Catch ERRO: "+ e);
                    }
                }
            }
            );
            request.add(jsonObjectRequest);
        }

        public static void criarNovoReportOutdoorUtilInst(final Context context, String descricao, int nivelDensidade, int idlocal, int idUtilInst, final volleycallback VCB) throws JSONException{
            String url = urlGeral + "/Report/novo_report_outdoor_utilsInstituicao";
            RequestQueue request = Volley.newRequestQueue(context);
            JSONObject bodyReq = new JSONObject();
            bodyReq.put("DescricaoReport", descricao);
            bodyReq.put("NivelDensidade", nivelDensidade);
            bodyReq.put("IDLocal", idlocal);
            bodyReq.put("idUtilInst", idUtilInst);
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
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            try {
                                VCB.onError(new JSONObject(new String(error.networkResponse.data, StandardCharsets.UTF_8)));
                                Log.i("pedido","ERRO: " + new String(error.networkResponse.data, StandardCharsets.UTF_8));
                            } catch (Exception e ) {
                                e.printStackTrace();
                                Log.i("pedido","Catch ERRO: "+ e);
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
                                Log.i("pedido","ERRO: " + new String(error.networkResponse.data, StandardCharsets.UTF_8));
                            } catch (Exception e ) {
                                e.printStackTrace();
                                Log.i("pedido","Catch ERRO: "+ e);
                            }
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
                                Log.i("pedido","ERRO: " + new String(error.networkResponse.data, StandardCharsets.UTF_8));
                            } catch (Exception e ) {
                                e.printStackTrace();
                                Log.i("pedido","Catch ERRO: "+ e);
                            }
                        }
                    }
            );
            request.add(jsonObjectRequest);
        }

    }

    public static class FuncoesPessoas{
        public static void fazerLogin(final Context context, String email, String password, final volleycallback VCB) throws JSONException {
            String url = urlGeral + "/Pessoas/login";
            RequestQueue request = Volley.newRequestQueue(context);
            JSONObject body = new JSONObject();
            body.put("Email", email);
            body.put("Password", password);
            final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
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
                                Log.i("pedido","ERRO: " + new String(error.networkResponse.data, StandardCharsets.UTF_8));
                            } catch (Exception e ) {
                                e.printStackTrace();
                                Log.i("pedido","Catch ERRO: "+ e);
                                Toast.makeText(context,"Erro de conexão",Toast.LENGTH_LONG).show();
                            }
                        }
                    }
            );
            request.add(jsonObjectRequest);
        }

        public static void criarUtilInstituicao(final Context context,
                                                JSONObject body, final volleycallback VCB) throws JSONException {
            String url = urlGeral + "/Pessoas/createUtil_Instituicao";
            RequestQueue request = Volley.newRequestQueue(context);
            /*JSONObject body = new JSONObject();
            body.put("Email", email);
            body.put("Password", password);
            body.put("Data_Nascimento",dataNasc);
            body.put("Cidade",cidade);
            body.put("Codigo_Postal",codPostal);
            body.put("UNome",uNome);
            body.put("PNome",pNome);
            body.put("InstituicaoIDInstituicao",idInst);*/


            final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
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
                                Log.i("pedido","ERRO: " + new String(error.networkResponse.data, StandardCharsets.UTF_8));
                            } catch (Exception e ) {
                                e.printStackTrace();
                                Log.i("pedido","Catch ERRO: "+ e);
                                Toast.makeText(context,"Erro de conexão",Toast.LENGTH_LONG).show();
                            }
                        }
                    }
            );
            request.add(jsonObjectRequest);
        }
        public static void criarOutroUtil(final Context context,JSONObject body, final volleycallback VCB) throws JSONException {
            String url = urlGeral + "/Pessoas/createOutros_Util";
            RequestQueue request = Volley.newRequestQueue(context);
            /*JSONObject body = new JSONObject();
            body.put("Email", email);
            body.put("Password", password);
            body.put("Data_Nascimento",dataNasc);
            body.put("Cidade",cidade);
            body.put("Codigo_Postal",codPostal);
            body.put("UNome",uNome);
            body.put("PNome",pNome);
            body.put("Localizacao",cidade);*/
            final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
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
                                Log.i("pedido","ERRO: " + new String(error.networkResponse.data, StandardCharsets.UTF_8));
                            } catch (Exception e ) {
                                e.printStackTrace();
                                Log.i("pedido","Catch ERRO: "+ e);
                                Toast.makeText(context,"Erro de conexão",Toast.LENGTH_LONG).show();
                            }
                        }
                    }
            );
            request.add(jsonObjectRequest);
        }

        public static void verificarSeJaFoiVerificado(final Context context, int idUtilEmpresa,final volleycallback VCB){
            String url = urlGeral+"/Pessoas/ver_se_util_esta_verificado/"+idUtilEmpresa;
            RequestQueue request = Volley.newRequestQueue(context);
            final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                    Request.Method.GET, url,null,
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
                                Log.i("pedido","ERRO: " + new String(error.networkResponse.data, StandardCharsets.UTF_8));
                            } catch (Exception e ) {
                                e.printStackTrace();
                                Log.i("pedido","Catch ERRO: "+ e);
                                Toast.makeText(context,"Erro de conexão",Toast.LENGTH_LONG).show();
                            }
                        }
                    }
            );
            request.add(jsonObjectRequest);
        }

        //region criar utilizador (ler comentario aqui dentro)
        /**  estas funcoes sao exatamente iguais as duas de cima, apenas muda que as outras ja veem com o body feito
         * so nao removi para o caso de voltar a precisar ou se quiser ver alguma cena
            */
        public static void criarUtilInstituicao(final Context context, String email, String password, String codPostal, String pNome, String uNome, String dataNasc, int idInst, String cidade, final volleycallback VCB) throws JSONException {
            String url = urlGeral + "/Pessoas/createUtil_Instituicao";
            RequestQueue request = Volley.newRequestQueue(context);
            JSONObject body = new JSONObject();
            body.put("Email", email);
            body.put("Password", password);
            body.put("Data_Nascimento",dataNasc);
            body.put("Cidade",cidade);
            body.put("Codigo_Postal",codPostal);
            body.put("UNome",uNome);
            body.put("PNome",pNome);
            body.put("InstituicaoIDInstituicao",idInst);


            final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
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
                                Log.i("pedido","ERRO: " + new String(error.networkResponse.data, StandardCharsets.UTF_8));
                            } catch (Exception e ) {
                                e.printStackTrace();
                                Log.i("pedido","Catch ERRO: "+ e);
                                Toast.makeText(context,"Erro de conexão",Toast.LENGTH_LONG).show();
                            }
                        }
                    }
            );
            request.add(jsonObjectRequest);
        }
        public static void criarOutroUtil(final Context context, String email, String password, String codPostal,
                                          String pNome, String uNome, String dataNasc,
                                          String cidade, final volleycallback VCB) throws JSONException {
            String url = urlGeral + "/Pessoas/createOutros_Util";
            RequestQueue request = Volley.newRequestQueue(context);
            JSONObject body = new JSONObject();
            body.put("Email", email);
            body.put("Password", password);
            body.put("Data_Nascimento",dataNasc);
            body.put("Cidade",cidade);
            body.put("Codigo_Postal",codPostal);
            body.put("UNome",uNome);
            body.put("PNome",pNome);
            body.put("Localizacao",cidade);



            final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
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
                                Log.i("pedido","ERRO: " + new String(error.networkResponse.data, StandardCharsets.UTF_8));
                            } catch (Exception e ) {
                                e.printStackTrace();
                                Log.i("pedido","Catch ERRO: "+ e);
                                Toast.makeText(context,"Erro de conexão",Toast.LENGTH_LONG).show();
                            }
                        }
                    }
            );
            request.add(jsonObjectRequest);
        }
        //endregion

        //region estas funcoes deviam estar na FuncoesReports mas so dei conta disso demasiado tarde lol
        public static void avaliarReport(Context context,
                                         int like,int dislike,
                                         int idreport,int idPessoa ,final volleycallback VCB) throws JSONException {
            String url = urlGeral + "/LikeDislike/novo_likedislike";
            RequestQueue request = Volley.newRequestQueue(context);
            JSONObject body = new JSONObject();
            body.put("Like", like);
            body.put("Dislike", dislike);
            body.put("IDPessoa",idPessoa);
            body.put("IDReport",idreport);
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                    Request.Method.POST, url, body, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    //Log.i("pedido",response.toString());
                    try {
                        VCB.onSuccess(response);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    try {
                        VCB.onError(new JSONObject(new String(error.networkResponse.data, StandardCharsets.UTF_8)));
                        Log.i("pedido", "ERRO: " + new String(error.networkResponse.data, StandardCharsets.UTF_8));
                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.i("pedido", "Catch ERRO: " + e);
                        try {
                            VCB.onError(new JSONObject(new String("Erro catch")));
                        } catch (JSONException jsonException) {
                            jsonException.printStackTrace();
                        }
                    }

                }
            }
            );
            request.add(jsonObjectRequest);
        }
        public static void removerInteracaoReport(Context context, int idreport,int idPessoa ,final volleycallback VCB) throws JSONException {
            String url = urlGeral + "/LikeDislike/remover_likedislike";
            RequestQueue request = Volley.newRequestQueue(context);
            JSONObject body = new JSONObject();
            body.put("IDPessoa",idPessoa);
            body.put("IDReport",idreport);
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                    Request.Method.POST, url, body, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    //Log.i("pedido",response.toString());
                    try {
                        VCB.onSuccess(response);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    try {
                        VCB.onError(new JSONObject(new String(error.networkResponse.data, StandardCharsets.UTF_8)));
                        Log.i("pedido","ERRO: " + new String(error.networkResponse.data, StandardCharsets.UTF_8));
                    } catch (Exception e ) {
                        e.printStackTrace();
                        Log.i("pedido","Catch ERRO: "+ e);
                    }
                }
            }
            );
            request.add(jsonObjectRequest);
        }

        public static void verificarSeExisteInteracao(Context context, int idreport,int idPessoa ,final volleycallback VCB) throws JSONException{
            String url = urlGeral + "/LikeDislike/verificar_se_existe_interacao";
            RequestQueue request = Volley.newRequestQueue(context);
            JSONObject body = new JSONObject();
            body.put("IDPessoa",idPessoa);
            body.put("IDReport",idreport);
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                    Request.Method.POST, url, body, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    //Log.i("pedido",response.toString());
                    try {
                        VCB.onSuccess(response);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    try {
                        VCB.onError(new JSONObject(new String(error.networkResponse.data, StandardCharsets.UTF_8)));
                        Log.i("pedido","ERRO: " + new String(error.networkResponse.data, StandardCharsets.UTF_8));
                    } catch (Exception e ) {
                        e.printStackTrace();
                        Log.i("pedido","Catch ERRO: "+ e);
                    }
                }
            }
            );
            request.add(jsonObjectRequest);
        }
        //endregion
    }

    public static class FuncoesInstituicoes{
        public static void getListaInstituicoes(final Context context, final volleycallback VCB){
            String url = urlGeral + "/Instituicao/get_instituicoes";
            RequestQueue request = Volley.newRequestQueue(context);
            final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                    Request.Method.GET, url,null,
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
                                Log.i("pedido","ERRO: " + new String(error.networkResponse.data, StandardCharsets.UTF_8));
                            } catch (Exception e ) {
                                e.printStackTrace();
                                Log.i("pedido","Catch ERRO: "+ e);
                                Toast.makeText(context,"Erro de conexão",Toast.LENGTH_LONG).show();
                            }
                        }
                    }
            );
            request.add(jsonObjectRequest);
        }
    }


    @Deprecated
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

    public static String encriptarString(String input) throws Exception {
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
            digest.update(input.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            for (int i=0; i<messageDigest.length; i++)
                hexString.append(Integer.toHexString(0xFF & messageDigest[i]));
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";

    }

}