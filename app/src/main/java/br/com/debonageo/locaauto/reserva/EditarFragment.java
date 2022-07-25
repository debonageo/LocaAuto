package br.com.debonageo.locaauto.reserva;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;

import br.com.debonageo.locaauto.R;
import br.com.debonageo.locaauto.carro.Carro;
import br.com.debonageo.locaauto.cartao.Cartao;
import br.com.debonageo.locaauto.database.DatabaseHelper;

public class EditarFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    private EditText etRetirada;
    private EditText etDevolucao;

    private Spinner spCarro;
    private Carro carroSelecionado;
    private ArrayAdapter<Carro> spCarroArrayAdapter; // Para saber qual spinner disparou o evento

    private Spinner spCartao;
    private Cartao cartaoSelecionado;
    private ArrayAdapter<Cartao> spCartaoArrayAdapter; // Para saber qual spinner disparou o evento

    private DatabaseHelper databaseHelper;

    private Reserva r;

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
        View v = inflater.inflate(R.layout.reserva_fragment_editar, container, false);

        etRetirada = v.findViewById(R.id.editTextRetirada);
        etDevolucao = v.findViewById(R.id.editTextDevolucao);
        spCarro = v.findViewById(R.id.spinnerCarroReserva);
        spCartao = v.findViewById(R.id.spinnerCartaoReserva);

        Bundle b = getArguments();

        int idReserva = b.getInt("id");

        databaseHelper = new DatabaseHelper(getActivity());

        r = databaseHelper.getByIdReserva(idReserva);

        etRetirada.setText(r.getRetirada());
        etDevolucao.setText(r.getDevolucao());

        Button btnEditar = v.findViewById(R.id.buttonAtualizarReserva);

        btnEditar.setOnClickListener(view -> editar(idReserva));

        Button btnCancelar = v.findViewById(R.id.buttonCancelarReserva);

        btnCancelar.setOnClickListener(view -> {

            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

            builder.setTitle(R.string.cancelar_reserva);

            builder.setPositiveButton(R.string.sim, (dialogInterface, i) -> excluir(idReserva));

            builder.setNegativeButton(R.string.nao, (dialogInterface, i) -> {
                // Não faz nada
            });

            AlertDialog alertDialog = builder.create();

            alertDialog.show();
        });

        ArrayList<Carro> carros = databaseHelper.getAllCarro();

        spCarroArrayAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, carros);
        spCarro.setAdapter(spCarroArrayAdapter);
        spCarro.setOnItemSelectedListener(this);

        for (int i = 0; i < carros.size(); i++) {
            if (carros.get(i).getId() == r.getId_carro()) {
                spCarro.setSelection(i);
            }
        }

        ArrayList<Cartao> cartoes = databaseHelper.getAllCartao();

        spCartaoArrayAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, cartoes);
        spCartao.setAdapter(spCartaoArrayAdapter);
        spCartao.setOnItemSelectedListener(this);

        for (int i = 0; i < cartoes.size(); i++) {
            if (cartoes.get(i).getId() == r.getId_cartao()) {
                spCartao.setSelection(i);
            }
        }

        return v;
    }

    private void editar(int id) {
        if (etRetirada.getText().toString().isEmpty()) {
            Toast.makeText(getActivity(), R.string.informar_retirada, Toast.LENGTH_LONG).show();
        } else if (etDevolucao.getText().toString().isEmpty()) {
            Toast.makeText(getActivity(), R.string.informar_devolucao, Toast.LENGTH_LONG).show();
        } else if (carroSelecionado == null) {
            Toast.makeText(getActivity(), R.string.informar_carro, Toast.LENGTH_LONG).show();
        } else if (cartaoSelecionado == null) {
            Toast.makeText(getActivity(), R.string.informar_cartao, Toast.LENGTH_LONG).show();
        } else {
            r = new Reserva();

            r.setId(id);
            r.setRetirada(etRetirada.getText().toString());
            r.setDevolucao(etDevolucao.getText().toString());
            r.setId_carro(carroSelecionado.getId());
            r.setId_cartao(cartaoSelecionado.getId());

            databaseHelper.updateReserva(r);

            Toast.makeText(getActivity(), R.string.reserva_atualizada, Toast.LENGTH_LONG).show();

            getActivity()
                    .getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frame_main, new ListarFragment())
                    .commit();
        }
    }

    private void excluir (int id) {

        r = new Reserva();

        r.setId(id);

        databaseHelper.deleteReserva(r);

        Toast.makeText(getActivity(), R.string.reserva_cancelada, Toast.LENGTH_LONG).show();

        getActivity()
                .getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_main, new ListarFragment())
                .commit();
    }

    // Implementa AdapterView.OnItemSelectedListener
    // Esta implementação sera usada tanto no spinner do Cartão quanto do Carro
    // Para saber qual spinner gerou o evento, comparamos com os adapters de cada spinner
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        if (parent.getAdapter() == spCarroArrayAdapter) {
            carroSelecionado = (Carro) parent.getItemAtPosition(pos);
        } else if (parent.getAdapter() == spCartaoArrayAdapter) {
            cartaoSelecionado = (Cartao) parent.getItemAtPosition(pos);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        if (parent.getAdapter() == spCarroArrayAdapter) {
            carroSelecionado = null;
        } else if (parent.getAdapter() == spCartaoArrayAdapter){
            cartaoSelecionado = null;
        }
    }

}