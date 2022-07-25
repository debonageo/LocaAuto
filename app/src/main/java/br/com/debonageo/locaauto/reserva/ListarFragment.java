package br.com.debonageo.locaauto.reserva;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import br.com.debonageo.locaauto.R;
import br.com.debonageo.locaauto.database.DatabaseHelper;

public class ListarFragment extends Fragment {

    public ListarFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.reserva_fragment_listar, container, false);

        DatabaseHelper databaseHelper = new DatabaseHelper(getActivity());

        ListView lv = v.findViewById(R.id.list_view_listar_reserva);

        databaseHelper.getAllReserva(getActivity(), lv);

        lv.setOnItemClickListener((adapterView, view, i, l) -> {

            TextView tvId = view.findViewById(R.id.textViewIdListarReserva);

            Bundle b = new Bundle();

            b.putInt("id", Integer.parseInt(tvId.getText().toString()));

            EditarFragment editar = new EditarFragment();

            FragmentTransaction ft = getActivity()
                    .getSupportFragmentManager()
                    .beginTransaction()
                    .setReorderingAllowed(true)
                    .addToBackStack("editar_reserva");

            editar.setArguments(b);

            ft.replace(R.id.frame_main, editar)
                    .commit();
        });


        Button criarReserva = v.findViewById(R.id.buttonCriarReserva);

        if (databaseHelper.getAllCartao().size() == 0) {

            TextView aviso = v.findViewById(R.id.listarReservaCriarCartaoAviso);
            aviso.setVisibility(View.VISIBLE);

            criarReserva.setEnabled(false);
        }

        criarReserva.setOnClickListener(view -> {
            requireActivity()
                    .getSupportFragmentManager()
                    .beginTransaction()
                    .setReorderingAllowed(true)
                    .addToBackStack("criar_reserva")
                    .replace(R.id.frame_main, new CriarFragment())
                    .commit();
        });

        return v;
    }
}