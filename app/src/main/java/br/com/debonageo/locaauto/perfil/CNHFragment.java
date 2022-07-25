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

public class CNHFragment extends Fragment {

    private EditText etNumero;
    private EditText etRegistro;
    private EditText etValidade;
    private EditText etEstado;

    public CNHFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v =  inflater.inflate(R.layout.perfil_cnh_fragment_editar, container, false);

        etNumero = v.findViewById(R.id.editTextNumeroCNH);
        etRegistro = v.findViewById(R.id.editTextRegistroCNH);
        etValidade = v.findViewById(R.id.editTextValidadeCNH);
        etEstado = v.findViewById(R.id.editTextEstadoCNH);

        DatabaseHelper databaseHelper = new DatabaseHelper(getActivity());

        Perfil p = databaseHelper.getLogin();

        etNumero.setText(p.getCnh());
        etRegistro.setText(p.getCnh_registro());
        etValidade.setText(p.getCnh_validade());
        etEstado.setText(p.getCnh_estado());

        Button salvar = v.findViewById(R.id.buttonSalvarCNH);

        salvar.setOnClickListener(view -> salvar());

        return v;
    }

    private void salvar() {

        if (etNumero.getText().toString().isEmpty()) {
            Toast.makeText(getActivity(), R.string.informar_numero_cnh, Toast.LENGTH_LONG).show();
        } else if (etRegistro.getText().toString().isEmpty()) {
            Toast.makeText(getActivity(), R.string.informar_registro_cnh, Toast.LENGTH_LONG).show();
        } else if (etValidade.getText().toString().isEmpty()) {
            Toast.makeText(getActivity(), R.string.informar_validade_cnh, Toast.LENGTH_LONG).show();
        } else if (etEstado.getText().toString().isEmpty()) {
            Toast.makeText(getActivity(), R.string.informar_estado_cnh, Toast.LENGTH_LONG).show();
        } else {

            DatabaseHelper databaseHelper = new DatabaseHelper(getActivity());

            Perfil p = databaseHelper.getLogin();

            p.setCnh(etNumero.getText().toString());
            p.setCnh_registro(etRegistro.getText().toString());
            p.setCnh_validade(etValidade.getText().toString());
            p.setCnh_estado(etEstado.getText().toString());

            databaseHelper.updatePerfilCNH(p);

            Toast.makeText(getActivity(), R.string.cnh_salva, Toast.LENGTH_LONG).show();

            getActivity()
                    .getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frame_main, new MainFragment())
                    .commit();
        }
    }

}