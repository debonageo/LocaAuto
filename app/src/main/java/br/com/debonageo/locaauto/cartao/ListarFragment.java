package br.com.debonageo.locaauto.cartao;

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
        View v = inflater.inflate(R.layout.cartao_fragment_listar, container, false);

        DatabaseHelper databaseHelper = new DatabaseHelper(getActivity());

        ListView lv = v.findViewById(R.id.list_view_listar_cartao);

        databaseHelper.getAllCartao(getActivity(), lv);

        lv.setOnItemClickListener((adapterView, view, i, l) -> {

            TextView tvId = view.findViewById(R.id.textViewIdListarCartao);

            Bundle b = new Bundle();

            b.putInt("id", Integer.parseInt(tvId.getText().toString()));

            EditarFragment editar = new EditarFragment();

            FragmentTransaction ft = getActivity()
                    .getSupportFragmentManager()
                    .beginTransaction()
                    .setReorderingAllowed(true)
                    .addToBackStack("editar_cartao");

            editar.setArguments(b);

            ft.replace(R.id.frame_main, editar)
                    .commit();
        });

        Button adicionarCartao = v.findViewById(R.id.buttonAdicionarCartao2);

        adicionarCartao.setOnClickListener(view -> {
            requireActivity()
                    .getSupportFragmentManager()
                    .beginTransaction()
                    .setReorderingAllowed(true)
                    .addToBackStack("adicionar_cartao")
                    .replace(R.id.frame_main, new AdicionarFragment())
                    .commit();
        });

        return v;
    }
}