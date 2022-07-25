package br.com.debonageo.locaauto.cartao;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import br.com.debonageo.locaauto.R;
import br.com.debonageo.locaauto.database.DatabaseHelper;

public class AdicionarFragment extends Fragment {

    private EditText etNumero;
    private EditText etNome;
    private EditText etValidade;
    private EditText etCVV;

    public AdicionarFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.cartao_fragment_adicionar, container, false);

        etNumero = v.findViewById(R.id.editTextNumeroCartao);
        etNome = v.findViewById(R.id.editTextNomeCartao);
        etValidade = v.findViewById(R.id.editTextValidadeCartao);
        etCVV = v.findViewById(R.id.editTextCVVCartao);

        Button btnAdicionar = v.findViewById(R.id.buttonAdicionarCartao);
        btnAdicionar.setOnClickListener(view -> adicionar());

        // Inflate the layout for this fragment
        return v;
    }

    private void adicionar() {
        if (etNumero.getText().toString().isEmpty()) {
            Toast.makeText(getActivity(), R.string.informar_numero_cartao, Toast.LENGTH_LONG).show();
        } else if (etNome.getText().toString().isEmpty()) {
            Toast.makeText(getActivity(), R.string.informar_nome_cartao, Toast.LENGTH_LONG).show();
        } else if (etValidade.getText().toString().isEmpty()) {
            Toast.makeText(getActivity(), R.string.informar_validade_cartao, Toast.LENGTH_LONG).show();
        } else if (etCVV.getText().toString().isEmpty()) {
            Toast.makeText(getActivity(), R.string.informar_cvv, Toast.LENGTH_LONG).show();
        } else {

            DatabaseHelper databaseHelper = new DatabaseHelper(getActivity());

            Cartao c = new Cartao();

            c.setNumero(etNumero.getText().toString());
            c.setNome(etNome.getText().toString());
            c.setValidade(etValidade.getText().toString());
            c.setCvv(etCVV.getText().toString());

            databaseHelper.createCartao(c);

            Toast.makeText(getActivity(), R.string.cartao_salvo, Toast.LENGTH_LONG).show();

            getActivity()
                    .getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frame_main, new ListarFragment())
                    .commit();
        }
    }
}