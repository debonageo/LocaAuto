package br.com.debonageo.locaauto.perfil;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import br.com.debonageo.locaauto.R;
import br.com.debonageo.locaauto.cartao.ListarFragment;
import br.com.debonageo.locaauto.data.LoginDataSource;
import br.com.debonageo.locaauto.data.LoginRepository;
import br.com.debonageo.locaauto.ui.login.LoginFragment;

public class MainFragment extends Fragment {


    public MainFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.perfil_fragment_main, container, false);

        Button dados = v.findViewById(R.id.button_perfil_dados);
        Button endereco = v.findViewById(R.id.button_perfil_endereco);
        Button cnh = v.findViewById(R.id.button_perfil_cnh);
        Button cartao = v.findViewById(R.id.button_perfil_cartao);
        Button sair = v.findViewById(R.id.button_perfil_sair);

        Button inicio = getActivity().findViewById(R.id.button_inicio);
        Button reservas = getActivity().findViewById(R.id.button_reservas);
        Button perfil = getActivity().findViewById(R.id.button_perfil);

        // Ao loggar ou criar uma conta, este sera o fragment que o usuário verá.
        // Então, como ele estará logado, vindo da tela de login/ criar conta, torna os
        // botões de navegação visíveis
        inicio.setVisibility(View.VISIBLE);
        reservas.setVisibility(View.VISIBLE);
        perfil.setVisibility(View.VISIBLE);

        dados.setOnClickListener(view -> {
            requireActivity()
                    .getSupportFragmentManager()
                    .beginTransaction()
                    .setReorderingAllowed(true)
                    .addToBackStack("dados")
                    .replace(R.id.frame_main, new DadosFragment())
                    .commit();
        });
        endereco.setOnClickListener(view -> {
            requireActivity()
                    .getSupportFragmentManager()
                    .beginTransaction()
                    .setReorderingAllowed(true)
                    .addToBackStack("endereco")
                    .replace(R.id.frame_main, new EnderecoFragment())
                    .commit();
        });
        cnh.setOnClickListener(view -> {
            requireActivity()
                    .getSupportFragmentManager()
                    .beginTransaction()
                    .setReorderingAllowed(true)
                    .addToBackStack("cnh")
                    .replace(R.id.frame_main, new CNHFragment())
                    .commit();
        });
        cartao.setOnClickListener(view -> {
            requireActivity()
                    .getSupportFragmentManager()
                    .beginTransaction()
                    .setReorderingAllowed(true)
                    .addToBackStack("cartoes")
                    .replace(R.id.frame_main, new ListarFragment())
                    .commit();
        });

        sair.setOnClickListener(view -> {

            // Usuário clicou no botão sair(deslogar)
            // Desabilita os botões de navegação, realiza o logout no LoginRepository
            // e vai para a tela de login
            inicio.setVisibility(View.INVISIBLE);
            reservas.setVisibility(View.INVISIBLE);
            perfil.setVisibility(View.INVISIBLE);

            LoginRepository lr = LoginRepository.getInstance(new LoginDataSource());
            lr.logout(getContext());

            requireActivity()
                    .getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frame_main, new LoginFragment())
                    .commit();

        });

        return v;
    }
}