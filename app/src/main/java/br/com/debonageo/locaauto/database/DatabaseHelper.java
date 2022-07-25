package br.com.debonageo.locaauto.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import java.util.ArrayList;

import br.com.debonageo.locaauto.R;
import br.com.debonageo.locaauto.carro.Carro;
import br.com.debonageo.locaauto.cartao.Cartao;
import br.com.debonageo.locaauto.perfil.Perfil;
import br.com.debonageo.locaauto.reserva.Reserva;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Definição das variáveis globais
    private static final String DATABASE_NAME = "locadora";

    private static final String TABLE_PERFIL = "perfil";
    private static final String TABLE_CARTAO = "cartao";
    private static final String TABLE_RESERVA = "reserva";
    private static final String TABLE_CARRO = "carro";
    private static final String TABLE_LOGIN = "login";

    private static final String CREATE_TABLE_PERFIL = "CREATE TABLE " + TABLE_PERFIL + " (" +
            "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "nome VARCHAR(100), " +
            "nascimento DATE, " +
            "telefone VARCHAR(20), " +
            "cpf VARCHAR(14), " +
            "senha VARCHAR(50), " +
            "cep VARCHAR(9), " +
            "logradouro VARCHAR(200), " +
            "endereco VARCHAR(200), " +
            "numero INTEGER, " +
            "bairro VARCHAR(50), " +
            "cidade VARCHAR(100), " +
            "estado VARCHAR(100), " +
            "cnh VARCHAR(100), " +
            "cnh_registro VARCHAR(100), " +
            "cnh_validade DATE, " +
            "cnh_estado VARCHAR(100))";

    private static final String CREATE_TABLE_CARTAO = "CREATE TABLE " + TABLE_CARTAO + "(" +
            "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "numero VARCHAR(40), " +
            "nome VARCHAR(100), " +
            "validade DATE, " +
            "cvv VARCHAR(4)," +
            "id_perfil INTEGER," +
            "CONSTRAINT fk_cartao_perfil FOREIGN KEY (id_perfil) REFERENCES perfil (_id))";

    private static final String CREATE_TABLE_RESERVA = "CREATE TABLE " + TABLE_RESERVA + "(" +
            "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "retirada DATE, " +
            "devolucao DATE, " +
            "id_carro INTEGER," +
            "id_perfil INTEGER," +
            "id_cartao INTEGER," +
            "CONSTRAINT fk_reserva_carro FOREIGN KEY (id_carro) REFERENCES carro (_id)," +
            "CONSTRAINT fk_reserva_perfil FOREIGN KEY (id_perfil) REFERENCES perfil (_id)," +
            "CONSTRAINT fk_reserva_cartao FOREIGN KEY (id_cartao) REFERENCES cartao (_id))";

    private static final String CREATE_TABLE_CARRO = "CREATE TABLE " + TABLE_CARRO + "(" +
            "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "nome VARCHAR(20))";

    private static final String CREATE_TABLE_LOGIN = "CREATE TABLE " + TABLE_LOGIN + "(" +
            "_id INTEGER PRIMARY KEY, " +
            "id_perfil INTEGER)";

    private static final String DROP_TABLE_PERFIL = "DROP TABLE IF EXISTS " + TABLE_PERFIL;
    private static final String DROP_TABLE_CARTAO = "DROP TABLE IF EXISTS " + TABLE_CARTAO;
    private static final String DROP_TABLE_RESERVA = "DROP TABLE IF EXISTS " + TABLE_RESERVA;
    private static final String DROP_TABLE_CARRO = "DROP TABLE IF EXISTS " + TABLE_CARRO;
    private static final String DROP_TABLE_LOGIN = "DROP TABLE IF EXISTS " + TABLE_LOGIN;

    // Método construtor da classe, passando por parâmetro a versão do banco de dados
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 6);
    }

    // Se ainda não existir o banco de dados, cria
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_PERFIL);
        db.execSQL(CREATE_TABLE_CARTAO);
        db.execSQL(CREATE_TABLE_RESERVA);
        db.execSQL(CREATE_TABLE_CARRO);
        db.execSQL(CREATE_TABLE_LOGIN);

        Carro c = new Carro();

        c.setNome("Ford Ka");
        createCarro(db, c);

        c.setNome("Fiat Toro");
        createCarro(db, c);

        c.setNome("Jeep Compass");
        createCarro(db, c);
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
    }

    // Se já existir e a versão for diferente, atualiza.
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_TABLE_PERFIL);
        db.execSQL(DROP_TABLE_CARTAO);
        db.execSQL(DROP_TABLE_RESERVA);
        db.execSQL(DROP_TABLE_CARRO);
        db.execSQL(DROP_TABLE_LOGIN);
        onCreate(db);
    }

    /* Início CRUD Perfil */

    public long createPerfil(Perfil p) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put("nome", p.getNome());
        cv.put("nascimento", p.getNascimento());
        cv.put("telefone", p.getTelefone());
        cv.put("cpf", p.getCpf());
        cv.put("senha", p.getSenha());

        cv.put("cep", p.getCep());
        cv.put("logradouro", p.getLogradouro());
        cv.put("endereco", p.getEndereco());
        cv.put("numero", p.getNumero());
        cv.put("bairro", p.getBairro());
        cv.put("cidade", p.getCidade());
        cv.put("estado", p.getEstado());

        cv.put("cnh", p.getCnh());
        cv.put("cnh_registro", p.getCnh_registro());
        cv.put("cnh_validade", p.getCnh_validade());
        cv.put("cnh_estado", p.getCnh_estado());

        long id = db.insert(TABLE_PERFIL, null, cv);

        db.close();

        return id;
    }

    public long updatePerfil(Perfil p) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put("nome", p.getNome());
        cv.put("nascimento", p.getNascimento());
        cv.put("telefone", p.getTelefone());
        cv.put("cpf", p.getCpf());
        cv.put("senha", p.getSenha());

        cv.put("cep", p.getCep());
        cv.put("logradouro", p.getLogradouro());
        cv.put("endereco", p.getEndereco());
        cv.put("numero", p.getNumero());
        cv.put("bairro", p.getBairro());
        cv.put("cidade", p.getCidade());
        cv.put("estado", p.getEstado());

        cv.put("cnh", p.getCnh());
        cv.put("cnh_registro", p.getCnh_registro());
        cv.put("cnh_validade", p.getCnh_validade());
        cv.put("cnh_estado", p.getCnh_estado());

        long id = db.update(TABLE_PERFIL, cv,
                "_id = ?", new String[]{String.valueOf(p.getId())});

        db.close();

        return id;
    }

    public long updatePerfilDadosPessoais(Perfil p) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put("nome", p.getNome());
        cv.put("nascimento", p.getNascimento());
        cv.put("telefone", p.getTelefone());
        cv.put("cpf", p.getCpf());
        cv.put("senha", p.getSenha());

        long id = db.update(TABLE_PERFIL, cv,
                "_id = ?", new String[]{String.valueOf(p.getId())});

        db.close();

        return id;
    }

    public long updatePerfilCNH(Perfil p) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put("cnh", p.getCnh());
        cv.put("cnh_registro", p.getCnh_registro());
        cv.put("cnh_validade", p.getCnh_validade());
        cv.put("cnh_estado", p.getCnh_estado());

        long id = db.update(TABLE_PERFIL, cv,
                "_id = ?", new String[]{String.valueOf(p.getId())});

        db.close();

        return id;
    }

    public long updatePerfilEndereco(Perfil p) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put("cep", p.getCep());
        cv.put("logradouro", p.getLogradouro());
        cv.put("endereco", p.getEndereco());
        cv.put("numero", p.getNumero());
        cv.put("bairro", p.getBairro());
        cv.put("cidade", p.getCidade());
        cv.put("estado", p.getEstado());

        long id = db.update(TABLE_PERFIL, cv,
                "_id = ?", new String[]{String.valueOf(p.getId())});

        db.close();

        return id;
    }

    public long deletePerfil(Perfil p) {
        SQLiteDatabase db = this.getWritableDatabase();
        long id = db.delete(TABLE_PERFIL, "_id = ?",
                new String[]{String.valueOf(p.getId())});
        db.close();
        return id;
    }

    public Perfil getByIdPerfil(int id) {

        SQLiteDatabase db = this.getReadableDatabase();

        String[] columns = {"_id", "nome", "nascimento", "telefone", "cpf", "senha", "cep", "logradouro", "endereco", "numero", "bairro", "cidade", "estado", "cnh", "cnh_registro", "cnh_validade", "cnh_estado"};
        String[] args = {String.valueOf(id)};

        Cursor data = db.query(TABLE_PERFIL, columns, "_id = ?", args,
                null, null, null);

        data.moveToFirst();

        Perfil m = new Perfil();

        m.setId(data.getInt(0));
        m.setNome(data.getString(1));
        m.setNascimento(data.getString(2));
        m.setTelefone(data.getString(3));
        m.setCpf(data.getString(4));
        m.setSenha(data.getString(5));
        m.setCep(data.getString(6));
        m.setLogradouro(data.getString(7));
        m.setEndereco(data.getString(8));
        m.setNumero(data.getString(9));
        m.setBairro(data.getString(10));
        m.setCidade(data.getString(11));
        m.setEstado(data.getString(12));
        m.setCnh(data.getString(13));
        m.setCnh_registro(data.getString(14));
        m.setCnh_validade(data.getString(15));
        m.setCnh_estado(data.getString(16));

        data.close();

        db.close();

        return m;
    }

    public Perfil getByCpfPerfil(String cpf) {

        SQLiteDatabase db = this.getReadableDatabase();

        String[] columns = {"_id", "nome", "cpf", "senha"};
        String[] args = {cpf};

        Cursor data = db.query(TABLE_PERFIL, columns, "cpf = ?", args,
                null, null, null);

        if (data.getCount() == 0) {
            return null;
        }

        data.moveToFirst();

        Perfil p = new Perfil();

        p.setId(data.getInt(0));
        p.setNome(data.getString(1));
        p.setCpf(data.getString(2));
        p.setSenha(data.getString(3));

        data.close();

        db.close();

        return p;
    }

    /* Fim CRUD Perfil */

    /* Início CRUD Cartão */

    public long createCartao(Cartao c) {

        Perfil p = getLogin();

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put("numero", c.getNumero());
        cv.put("nome", c.getNome());
        cv.put("validade", c.getValidade());
        cv.put("cvv", c.getCvv());
        cv.put("id_perfil", p.getId());

        long id = db.insert(TABLE_CARTAO, null, cv);

        db.close();

        return id;
    }

    public long updateCartao(Cartao c) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put("numero", c.getNumero());
        cv.put("nome", c.getNome());
        cv.put("validade", c.getValidade());
        cv.put("cvv", c.getCvv());

        long id = db.update(TABLE_CARTAO, cv, "_id = ?", new String[]{String.valueOf(c.getId())});

        db.close();

        return id;
    }

    public long deleteCartao(Cartao c) {

        SQLiteDatabase db = this.getWritableDatabase();

        long id = db.delete(TABLE_CARTAO, "_id = ?", new String[]{String.valueOf(c.getId())});

        db.close();

        return id;
    }

    public Cartao getByIdCartao(int id) {

        SQLiteDatabase db = this.getReadableDatabase();

        String[] columns = {"_id", "numero", "nome", "cvv"};

        Cursor data = db.query(TABLE_CARTAO, columns, "_id = ?", new String[]{String.valueOf(id)}, null, null, null);

        data.moveToFirst();

        Cartao c = new Cartao();

        c.setId(data.getInt(0));
        c.setNumero(data.getString(1));
        c.setNome(data.getString(2));
        c.setCvv(data.getString(3));

        data.close();

        db.close();

        return c;
    }

    public void getAllCartao(Context context, ListView lv) {

        Perfil p = getLogin();

        SQLiteDatabase db = this.getReadableDatabase();

        // Defini as colunas da consulta do SELECT
        String[] columns = {"_id", "nome", "numero"};

        // Recupera os valores do banco de dados
        Cursor data = db.query(TABLE_CARTAO, columns, "id_perfil = ?", new String[]{String.valueOf(p.getId())}, null, null, null);

        // Campos do XML de destino para os valores do BD
        int[] to = {R.id.textViewIdListarCartao, R.id.textViewNumeroListarCartao, R.id.textViewValidadeListarCartao};

        // Cria um adaptador utilizando o XML da pasta layout (cartao_item_list_view) como padrão, para mostrar em cada item as informações presentes no banco de dados
        SimpleCursorAdapter simpleCursorAdapter = new SimpleCursorAdapter(context, R.layout.cartao_item_list_view, data, columns, to, 0);

        // Seta o adaptador no ListView. Por isso, não há retorno, é apenas modificado a instância do ListView recebido por parâmetro.
        lv.setAdapter(simpleCursorAdapter);

        db.close();
    }

    public ArrayList<Cartao> getAllCartao() {

        Perfil p = getLogin();

        SQLiteDatabase db = this.getReadableDatabase();

        String[] columns = {"_id", "numero"};

        Cursor data = db.query(TABLE_CARTAO, columns, "id_perfil = ?", new String[]{String.valueOf(p.getId())}, null,
                null, "numero");

        ArrayList<Cartao> cartoes = new ArrayList<>();

        while (data.moveToNext()) {

            Cartao c = new Cartao();

            int idColumnIndex = data.getColumnIndex("_id");
            c.setId(data.getInt(idColumnIndex));

            int numeroColumnIndex = data.getColumnIndex("numero");
            c.setNumero(data.getString(numeroColumnIndex));

            cartoes.add(c);
        }

        db.close();

        return cartoes;
    }

    /* Fim CRUD Cartão */

    /* Início CRUD Reserva */

    public long createReserva(Reserva r) {

        Perfil p = getLogin();

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put("retirada", r.getRetirada());
        cv.put("devolucao", r.getDevolucao());
        cv.put("id_carro", r.getId_carro());
        cv.put("id_cartao", r.getId_cartao());
        cv.put("id_perfil", p.getId());

        long id = db.insert(TABLE_RESERVA, null, cv);

        db.close();

        return id;
    }

    public long updateReserva(Reserva r) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put("retirada", r.getRetirada());
        cv.put("devolucao", r.getDevolucao());
        cv.put("id_carro", r.getId_carro());
        cv.put("id_cartao", r.getId_cartao());

        long rows = db.update(TABLE_RESERVA, cv, "_id = ?", new String[]{String.valueOf(r.getId())});

        db.close();

        return rows;
    }

    public long deleteReserva(Reserva r) {

        SQLiteDatabase db = this.getWritableDatabase();

        long rows = db.delete(TABLE_RESERVA, "_id = ?", new String[]{String.valueOf(r.getId())});

        db.close();

        return rows;
    }

    public void getAllReserva(Context context, ListView lv) {

        Perfil p = getLogin();

        SQLiteDatabase db = this.getReadableDatabase();

        String[] columns = {"_id", "retirada"};

        Cursor data = db.query(TABLE_RESERVA, columns, "id_perfil = ?", new String[]{String.valueOf(p.getId())}, null,
                null, "retirada");

        int[] to = {R.id.textViewIdListarReserva, R.id.textViewRetiradaListarReserva};

        SimpleCursorAdapter simpleCursorAdapter = new SimpleCursorAdapter(context,
                R.layout.reserva_item_list_view, data, columns, to, 0);

        lv.setAdapter(simpleCursorAdapter);

        db.close();
    }

    public Reserva getByIdReserva(int id) {

        SQLiteDatabase db = this.getReadableDatabase();

        String[] columns = {"_id", "retirada", "devolucao", "id_carro", "id_cartao"};
        String[] args = {String.valueOf(id)};

        Cursor data = db.query(TABLE_RESERVA, columns, "_id = ?", args, null,
                null, null);

        data.moveToFirst();

        Reserva r = new Reserva();

        r.setId(data.getInt(0));
        r.setRetirada(data.getString(1));
        r.setDevolucao(data.getString(2));
        r.setId_carro(data.getInt(3));
        r.setId_cartao(data.getInt(4));

        data.close();

        db.close();

        return r;
    }

    /* Fim CRUD Reserva */

    /* Início CRUD Carro */

    private long createCarro(SQLiteDatabase db, Carro c) {

        ContentValues cv = new ContentValues();

        cv.put("nome", c.getNome());

        return db.insert(TABLE_CARRO, null, cv);
    }

    public ArrayList<Carro> getAllCarro() {

        SQLiteDatabase db = this.getReadableDatabase();

        String[] columns = {"_id", "nome"};

        Cursor data = db.query(TABLE_CARRO, columns, null, null, null,
                null, "nome");

        ArrayList<Carro> carros = new ArrayList<>();

        while (data.moveToNext()) {

            Carro c = new Carro();

            int idColumnIndex = data.getColumnIndex("_id");
            c.setId(data.getInt(idColumnIndex));

            int nameColumnIndex = data.getColumnIndex("nome");
            c.setNome(data.getString(nameColumnIndex));

            carros.add(c);
        }

        db.close();

        return carros;
    }

    /* Fim CRUD Carro */

    /* Início CRUD Login */

    public void setLogin(Perfil p) {

        SQLiteDatabase db = getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put("_id", 1);
        cv.put("id_perfil", p.getId());

        db.insert(TABLE_LOGIN, null, cv);

        db.close();
    }

    public Perfil getLogin() {

        SQLiteDatabase db = this.getReadableDatabase();

        String[] columns = {"_id", "id_perfil"};
        String[] args = {"1"};

        Cursor data = db.query(TABLE_LOGIN, columns, "_id = ?", args, null,
                null, null);

        if (data.getCount() == 0) {
            return null;
        }

        data.moveToFirst();

        int idPerfil = data.getInt(1);

        data.close();
        db.close();

        return getByIdPerfil(idPerfil);
    }

    public void logout() {

        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_LOGIN, null, null);

        db.close();

    }

    /* Fim CRUD Login */

}
