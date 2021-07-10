package com.example.crowdzero_v000.fragmentos;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.crowdzero_v000.InstituicaoInformacoesActivity;
import com.example.crowdzero_v000.ListaReportsInstituicaoActivity;
import com.example.crowdzero_v000.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;


public class FragmentModalBottomSheet extends BottomSheetDialogFragment {
    private int widthFragmento;
    private String nomeLocal;
    private int idlocal;
    private Bitmap bitmapImg;
    private String descricao;
    //private Context context;
    //private String urlImagem;
    protected TextView txtViewNomeInstituicao;
    //protected ImageView imgViewImagemInstituicao;
    protected ImageButton imgBtnReport,imgBtnHistorico, imgBtnInfo;
    public FragmentModalBottomSheet(String nomelocal, int idlocal_, final Context context/*, Bitmap bitmap*/){
        nomeLocal=nomelocal;
        idlocal = idlocal_;

    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_modal_bottom_sheet,container,false);



        getWidthFragmento(v);
        inicializarVarsEOnClickListeners(v);



        txtViewNomeInstituicao.setText(Html.fromHtml("<h2>"+nomeLocal+"</h2>"));

        return v;
    }

    private void inicializarVarsEOnClickListeners(View v) {
        txtViewNomeInstituicao = v.findViewById(R.id.textViewBottomSheetNomeInstituicao);
        //imgViewImagemInstituicao = v.findViewById(R.id.imagemInstituicaoModalBottomSheet);
        imgBtnReport = v.findViewById(R.id.imgBtnReportsModalBottomSheet);
        imgBtnHistorico = v.findViewById(R.id.imgBtnHistoricoModalBottomSheet);
        imgBtnInfo = v.findViewById(R.id.imgBtnInfoModalBottomSheet);


        imgBtnReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getContext(),"ola",Toast.LENGTH_LONG).show();
                Intent i = new Intent(getActivity(), ListaReportsInstituicaoActivity.class);
                i.putExtra("opcaoEscolhida","Home");
                i.putExtra("opcaoEscolhidaItemID",-1);
                i.putExtra("nome",nomeLocal);
                i.putExtra("descricao",descricao);
                i.putExtra("idlocal",idlocal);
                startActivity(i);
            }
        });
        imgBtnHistorico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"ola",Toast.LENGTH_LONG).show();
            }
        });
        imgBtnInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity().getApplicationContext(), InstituicaoInformacoesActivity.class);
                i.putExtra("opcaoEscolhida","Home");
                i.putExtra("opcaoEscolhidaItemID",-1);
                //i.putExtra("nome",nomeLocal);
                //i.putExtra("descricao",descricao);
                //i.putExtra("urlImagem",urlImagem);
                i.putExtra("idlocal",idlocal);
                i.putExtra("mapa",true);
                startActivity(i);
            }
        });
    }


    public void getWidthFragmento(final View v) {
        v.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                widthFragmento = ((LinearLayout)v.findViewById(R.id.linearLayoutModalBottomSheetOutside)).getWidth();

                ViewGroup.LayoutParams lp = txtViewNomeInstituicao.getLayoutParams();
                lp.width=(widthFragmento/3)*2;
                txtViewNomeInstituicao.setLayoutParams(lp);
                //lp = imgViewImagemInstituicao.getLayoutParams();
                //lp.width =(widthFragmento/4);
                //imgViewImagemInstituicao.setLayoutParams(lp);

                LinearLayout llInfo,llHistorico,llReport;
                llInfo = v.findViewById(R.id.linearLayoutInfoModalBottomSheet);
                llHistorico = v.findViewById(R.id.linearLayoutHistoricoModalBottomSheet);
                llReport = v.findViewById(R.id.linearLayoutReportsModalBottomSheet);

                lp = llInfo.getLayoutParams();
                lp.width = (widthFragmento/3);
                llInfo.setLayoutParams(lp);
                lp = llHistorico.getLayoutParams();
                lp.width = (widthFragmento/3);
                llHistorico.setLayoutParams(lp);
                lp = llReport.getLayoutParams();
                lp.width = (widthFragmento/3);
                llReport.setLayoutParams(lp);



            }
        });

    }


}