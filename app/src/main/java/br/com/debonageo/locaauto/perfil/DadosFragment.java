package br.com.debonageo.locaauto.perfil;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import br.com.debonageo.locaauto.R;
import br.com.debonageo.locaauto.database.DatabaseHelper;

public class DadosFragment extends Fragment {

    private EditText etNome;
    private EditText etDataNascimento;
    private EditText etTelefone;
    private EditText etCPF;

    public DadosFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v =  inflater.inflate(R.layout.perfil_dados_fragment_editar, container, false);

        etNome = v.findViewById(R.id.editTextNomePerfil);
        etDataNascimento = v.findViewById(R.id.editTextDataNascimento);
        etTelefone = v.findViewById(R.id.editTextTelefone);
        etCPF = v.findViewById(R.id.editTextCPF);

        DatabaseHelper databaseHelper = new DatabaseHelper(getActivity());

        Perfil p = databaseHelper.getLogin();

        etNome.setText(p.getNome());
        etDataNascimento.setText(p.getNascimento());
        etTelefone.setText(p.getTelefone());
        etCPF.setText(p.getCpf());

        Button salvar = v.findViewById(R.id.buttonSalvarDadosPessoais);

        salvar.setOnClickListener(view -> salvar());


        return v;
    }

    private void salvar() {

        if (etNome.getText().toString().isEmpty()) {
            Toast.makeText(getActivity(), R.string.informar_nome, Toast.LENGTH_LONG).show();
        } else if (etDataNascimento.getText().toString().isEmpty()) {
            Toast.makeText(getActivity(), R.string.informar_nascimento, Toast.LENGTH_LONG).show();
        } else if (etTelefone.getText().toString().isEmpty()) {
            Toast.makeText(getActivity(), R.string.informar_telefone, Toast.LENGTH_LONG).show();
        } else if (etCPF.getText().toString().isEmpty()) {
            Toast.makeText(getActivity(), R.string.informar_cpf, Toast.LENGTH_LONG).show();
        } else {

            DatabaseHelper databaseHelper = new DatabaseHelper(getActivity());

            Perfil p = databaseHelper.getLogin();

            p.setNome(etNome.getText().toString());
            p.setNascimento(etDataNascimento.getText().toString());
            p.setTelefone(etTelefone.getText().toString());
            p.setCpf(etCPF.getText().toString());

            databaseHelper.updatePerfilDadosPessoais(p);

            Toast.makeText(getActivity(), R.string.dados_salvos, Toast.LENGTH_LONG).show();

            getActivity()
                    .getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frame_main, new MainFragment())
                    .commit();
        }
    }

}