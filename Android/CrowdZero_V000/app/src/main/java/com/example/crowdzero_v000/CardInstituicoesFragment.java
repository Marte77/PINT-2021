package com.example.crowdzero_v000;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v =  inflater.inflate(R.layout.fragment_card_instituicoes, container, false);

        ((TextView) v.findViewById(R.id.NomeTxtViewFrag)).setText(getArguments().getString("nome"));
        ((TextView) v.findViewById(R.id.DescTxtViewFrag)).setText(getArguments().getString("descricao"));
        return v;
    }
}