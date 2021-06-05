package com.example.crowdzero_v000.fragmentos;

import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.crowdzero_v000.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;


public class FragmentModalBottomSheet extends BottomSheetDialogFragment {
    private int widthFragmento;
    private String nomeLocal;
    protected TextView txtViewNomeInstituicao;
    protected ImageView imgViewImagemInstituicao;
    protected ImageButton imgBtnReport,imgBtnHistorico, imgBtnInfo;
    public FragmentModalBottomSheet(String nomelocal){
        nomeLocal=nomelocal;
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
        imgViewImagemInstituicao = v.findViewById(R.id.imagemInstituicaoModalBottomSheet);
        imgBtnReport = v.findViewById(R.id.imgBtnReportsModalBottomSheet);
        imgBtnHistorico = v.findViewById(R.id.imgBtnHistoricoModalBottomSheet);
        imgBtnInfo = v.findViewById(R.id.imgBtnInfoModalBottomSheet);

        imgBtnReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"ola",Toast.LENGTH_LONG).show();
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
                Toast.makeText(getContext(),"ola",Toast.LENGTH_LONG).show();
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
                lp = imgViewImagemInstituicao.getLayoutParams();
                lp.width =(widthFragmento/4);
                imgViewImagemInstituicao.setLayoutParams(lp);

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