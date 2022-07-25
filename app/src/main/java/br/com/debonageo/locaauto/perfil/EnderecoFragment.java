package br.com.debonageo.locaauto.perfil;

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

public class EnderecoFragment extends Fragment {

    private EditText etCEP;
    private EditText etLogradouro;
    private EditText etEndereco;
    private EditText etNumero;
    private EditText etBairro;
    private EditText etCidade;
    private EditText etEstado;

    public EnderecoFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v =  inflater.inflate(R.layout.perfil_endereco_fragment_editar, container, false);

        etCEP = v.findViewById(R.id.editTextCEP);
        etLogradouro = v.findViewById(R.id.editTextLogradouro);
        etEndereco = v.findViewById(R.id.editTextEndereco);
        etNumero = v.findViewById(R.id.editTextNumeroEndereco);
        etBairro = v.findViewById(R.id.editTextBairro);
        etCidade = v.findViewById(R.id.editTextCidade);
        etEstado = v.findViewById(R.id.editTextEstadoEndereco);

        DatabaseHelper databaseHelper = new DatabaseHelper(getActivity());

        Perfil p = databaseHelper.getLogin();

        etCEP.setText(p.getCep());
        etLogradouro.setText(p.getLogradouro());
        etEndereco.setText(p.getEndereco());
        etNumero.setText(p.getNumero());
        etBairro.setText(p.getBairro());
        etCidade.setText(p.getCidade());
        etEstado.setText(p.getEstado());

        Button salvar = v.findViewById(R.id.buttonSalvarEndereco);

        salvar.setOnClickListener(view -> salvar());

        return v;
    }

    private void salvar() {

        if (etCEP.getText().toString().isEmpty()) {
            Toast.makeText(getActivity(), R.string.informar_cep, Toast.LENGTH_LONG).show();
        } else if (etLogradouro.getText().toString().isEmpty()) {
            Toast.makeText(getActivity(), R.string.informar_logradouro, Toast.LENGTH_LONG).show();
        } else if (etEndereco.getText().toString().isEmpty()) {
            Toast.makeText(getActivity(), R.string.informar_endereco, Toast.LENGTH_LONG).show();
        } else if (etNumero.getText().toString().isEmpty()) {
            Toast.makeText(getActivity(), R.string.informar_numero_endereco, Toast.LENGTH_LONG).show();
        } else if (etBairro.getText().toString().isEmpty()) {
            Toast.makeText(getActivity(), R.string.informar_bairro, Toast.LENGTH_LONG).show();
        } else if (etCidade.getText().toString().isEmpty()) {
            Toast.makeText(getActivity(), R.string.informar_cidade, Toast.LENGTH_LONG).show();
        } else if (etEstado.getText().toString().isEmpty()) {
            Toast.makeText(getActivity(), R.string.informar_estado_endereco, Toast.LENGTH_LONG).show();
        } else {

            DatabaseHelper databaseHelper = new DatabaseHelper(getActivity());

            Perfil p = databaseHelper.getLogin();

            p.setCep(etCEP.getText().toString());
            p.setLogradouro(etLogradouro.getText().toString());
            p.setEndereco(etEndereco.getText().toString());
            p.setNumero(etNumero.getText().toString());
            p.setBairro(etBairro.getText().toString());
            p.setCidade(etCidade.getText().toString());
            p.setEstado(etEstado.getText().toString());

            databaseHelper.updatePerfilEndereco(p);

            Toast.makeText(getActivity(), R.string.endereco_salvo, Toast.LENGTH_LONG).show();

            getActivity()
                    .getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frame_main, new MainFragment())
                    .commit();
        }
    }

}