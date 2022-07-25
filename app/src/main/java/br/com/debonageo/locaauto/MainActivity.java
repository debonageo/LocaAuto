package br.com.debonageo.locaauto;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import br.com.debonageo.locaauto.database.DatabaseHelper;
import br.com.debonageo.locaauto.perfil.MainFragment;
import br.com.debonageo.locaauto.reserva.ListarFragment;
import br.com.debonageo.locaauto.ui.login.LoginFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Defini qual será o conteúdo da interface (arquivo XML da pasta RES)
        setContentView(R.layout.activity_main);

        // Instancia o banco de dados
        DatabaseHelper db = new DatabaseHelper(getApplicationContext());

        // Instancia os botões
        Button inicio = findViewById(R.id.button_inicio);
        Button reservas = findViewById(R.id.button_reservas);
        Button perfil = findViewById(R.id.button_perfil);

        if (db.getLogin() == null) {
            // Se o usuário está deslogado, esconde os botões de navegação e mostra tela de login/criar conta

            inicio.setVisibility(View.INVISIBLE);
            reservas.setVisibility(View.INVISIBLE);
            perfil.setVisibility(View.INVISIBLE);

            getSupportFragmentManager().beginTransaction().replace(R.id.frame_main, new LoginFragment()).commit();
        } else if (savedInstanceState == null) {
            // Se o usuário está logado, mostra o perfil
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_main, new MainFragment()).commit();
        }

        // Configura as ações dos botôes de navegação
        inicio.setOnClickListener(view -> {
            // Muda a tela com suporte ao botão voltar
            getSupportFragmentManager()
                    .beginTransaction()
                    .setReorderingAllowed(true)
                    .addToBackStack("inicio")
                    .replace(R.id.frame_main, new MainFragment())
                    .commit();
        });

        reservas.setOnClickListener(view -> {
            getSupportFragmentManager()
                    .beginTransaction()
                    .setReorderingAllowed(true)
                    .addToBackStack("reservas")
                    .replace(R.id.frame_main, new ListarFragment())
                    .commit();
        });

        perfil.setOnClickListener(view -> {
            getSupportFragmentManager()
                    .beginTransaction()
                    .setReorderingAllowed(true)
                    .addToBackStack("perfil")
                    .replace(R.id.frame_main, new MainFragment())
                    .commit();
        });

    }

}