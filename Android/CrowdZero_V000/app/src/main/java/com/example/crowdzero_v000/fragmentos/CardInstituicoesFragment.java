package com.example.crowdzero_v000.fragmentos;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.crowdzero_v000.InstituicaoInformacoesActivity;
import com.example.crowdzero_v000.R;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CardInstituicoesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CardInstituicoesFragment extends Fragment {

                                             /* nome da instituicao e descricao da mesma*/
    public static CardInstituicoesFragment newInstance(String nome, String descricao) {

        CardInstituicoesFragment f = new CardInstituicoesFragment();

        Bundle b = new Bundle();
        b.putString("nome", nome);
        b.putString("descricao", descricao);
        f.setArguments(b);
        return f;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View inflatedView =  inflater.inflate(R.layout.fragment_card_instituicoes, container, false);

        ((TextView) inflatedView.findViewById(R.id.NomeTxtViewFrag)).setText(getArguments().getString("nome"));
        ((TextView) inflatedView.findViewById(R.id.DescTxtViewFrag)).setText(getArguments().getString("descricao"));
        ((Button)inflatedView.findViewById(R.id.DetalhesFragmentoBotao)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity().getApplicationContext(), InstituicaoInformacoesActivity.class);
                i.putExtra("opcaoEscolhida","Home");
                i.putExtra("opcaoEscolhidaItemID",-1);
                i.putExtra("nome",getArguments().getString("nome"));
                i.putExtra("descricao",getArguments().getString("descricao"));
                startActivity(i);
            }
        });
        return inflatedView;
    }


}