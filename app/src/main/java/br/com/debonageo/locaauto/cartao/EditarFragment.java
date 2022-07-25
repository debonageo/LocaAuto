package br.com.debonageo.locaauto.cartao;

import android.app.AlertDialog;
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

public class EditarFragment extends Fragment {

    private EditText etNumero;
    private EditText etNome;
    private EditText etValidade;
    private EditText etCVV;

    private DatabaseHelper databaseHelper;

    private Cartao c;

    public EditarFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.cartao_fragment_editar, container, false);

        etNumero = v.findViewById(R.id.editTextNumeroCartao);
        etNome = v.findViewById(R.id.editTextNomeCartao);
        etValidade = v.findViewById(R.id.editTextValidadeCartao);
        etCVV = v.findViewById(R.id.editTextCVVCartao);

        Bundle b = getArguments();

        int idCartao = b.getInt("id");

        databaseHelper = new DatabaseHelper(getActivity());

        c = databaseHelper.getByIdCartao(idCartao);

        etNumero.setText(c.getNumero());
        etNome.setText(c.getNome());
        etValidade.setText(c.getValidade());
        etCVV.setText(c.getCvv());

        Button btnEditar = v.findViewById(R.id.buttonSalvarCartao);

        btnEditar.setOnClickListener(view -> editar(idCartao));

        Button btnExcluir = v.findViewById(R.id.buttonExcluirCartao);

        btnExcluir.setOnClickListener(view -> {

            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

            builder.setTitle(R.string.excluir_cartao);

            builder.setPositiveButton(R.string.sim, (dialogInterface, i) -> excluir(idCartao));

            builder.setNegativeButton(R.string.nao, (dialogInterface, i) -> {
                // Não faz nada
            });

            AlertDialog alertDialog = builder.create();

            alertDialog.show();
        });

        return v;
    }

    private void editar(int id) {
        if (etNumero.getText().toString().isEmpty()) {
            Toast.makeText(getActivity(), R.string.informar_numero_cartao, Toast.LENGTH_LONG).show();
        } else if (etNome.getText().toString().isEmpty()) {
            Toast.makeText(getActivity(), R.string.informar_nome_cartao, Toast.LENGTH_LONG).show();
        } else if (etValidade.getText().toString().isEmpty()) {
            Toast.makeText(getActivity(), R.string.informar_validade_cartao, Toast.LENGTH_LONG).show();
        } else if (etCVV.getText().toString().isEmpty()) {
            Toast.makeText(getActivity(), R.string.informar_cvv, Toast.LENGTH_LONG).show();
        } else {
            c = new Cartao();

            c.setId(id);
            c.setNumero(etNumero.getText().toString());
            c.setNome(etNome.getText().toString());
            c.setValidade(etValidade.getText().toString());
            c.setCvv(etCVV.getText().toString());

            databaseHelper.updateCartao(c);

            Toast.makeText(getActivity(), R.string.cartao_atualizado, Toast.LENGTH_LONG).show();

            getActivity()
                    .getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frame_main, new ListarFragment())
                    .commit();
        }
    }

    private void excluir (int id) {

        c = new Cartao();

        c.setId(id);

        databaseHelper.deleteCartao(c);

        Toast.makeText(getActivity(), R.string.cartao_excluido, Toast.LENGTH_LONG).show();

        getActivity()
                .getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_main, new ListarFragment())
                .commit();
    }
}