package br.com.debonageo.locaauto.data;

import android.content.Context;
import android.widget.Toast;

import br.com.debonageo.locaauto.data.model.LoggedInUser;
import br.com.debonageo.locaauto.database.DatabaseHelper;
import br.com.debonageo.locaauto.perfil.Perfil;

import java.io.IOException;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {

    private DatabaseHelper databaseHelper;

    public Result<LoggedInUser> login(String username, String password, Context context) {

        // Instancia o banco de dados caso ainda não esteja instanciado
        if (databaseHelper == null) {
            databaseHelper = new DatabaseHelper(context);
        }

        try {

            // Busca o perfil pelo CPF, caso exista
            Perfil p = databaseHelper.getByCpfPerfil(username);

            // Se não existir, cria a conta com o CPF e a senha inseridos
            if (p == null) {

                p = new Perfil();

                p.setSenha(password);
                p.setCpf(username);

                p.setId((int) databaseHelper.createPerfil(p));

                databaseHelper.setLogin(p);

                Toast.makeText(context, "Perfil criado", Toast.LENGTH_LONG).show();

                return new Result.Success<>(new LoggedInUser(String.valueOf(p.getId()), p.getCpf()));
            } else {
                // Se existir, testa se a confere, e logga o usuário
                if (p.getSenha().equals(password)) {

                    databaseHelper.setLogin(p);

                    return new Result.Success<>(new LoggedInUser(String.valueOf(p.getId()), p.getNome()));
                } else {
                    //Se não, retorna um erro
                    return new Result.Error(new IOException("Senha inválida"));
                }
            }
        } catch (Exception e) {
            return new Result.Error(new IOException("Error logging in", e));
        }
    }

    public void logout(Context context) {

        // Instancia o banco de dados caso ainda não esteja instanciado
        if (databaseHelper == null) {
            databaseHelper = new DatabaseHelper(context);
        }

        databaseHelper.logout();
    }
}