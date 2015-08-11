package br.com.caelum.chamadosapp.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import br.com.caelum.chamadosapp.modelo.Chamado;

/**
 * Created by matheus on 10/08/15.
 */
public class ChamadoDAO extends SQLiteOpenHelper {

    private final static String NOME_BANCO = "ChamadosApp";
    private final static int VERSAO = 1;
    private final static String TABELA_CHAMADO = "Chamados";
    private final String APARELHO = "aparelho";
    private final String ID = "id";
    private final String CLIENTE = "cliente";
    private final String DESCRICAO = "descricao";
    private final String CAMINHO_IMAGEM = "caminhoImagem";

    public ChamadoDAO(Context context){
        super(context, NOME_BANCO, null, VERSAO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql;

        sql = "CREATE TABLE " + TABELA_CHAMADO +
                "(id INTEGER PRIMARY KEY," +
                " "+ CLIENTE +" TEXT NOT NULL," +
                " "+ APARELHO+ " TEXT NOT NULL," +
                " "+ CAMINHO_IMAGEM +" TEXT," +
                " " + DESCRICAO +" TEXT NOT NULL" +
                ");";

        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insereChamado(Chamado chamado){

        ContentValues values = new ContentValues();

        values.put(CLIENTE, chamado.getNomeDoCliente());
        values.put(APARELHO, chamado.getAparelho());
        values.put(DESCRICAO, chamado.getDescricao());
        values.put(CAMINHO_IMAGEM, chamado.getCaminhoImagem());

        getWritableDatabase().insert(TABELA_CHAMADO, null, values);
    }

    public void alteraChamado(Chamado chamado){

        ContentValues values = new ContentValues();

        values.put(CLIENTE, chamado.getNomeDoCliente());
        values.put(APARELHO, chamado.getAparelho());
        values.put(DESCRICAO, chamado.getDescricao());
        values.put(CAMINHO_IMAGEM, chamado.getCaminhoImagem());

        String args[] = { String.valueOf(chamado.getId()) };

        getWritableDatabase().update(TABELA_CHAMADO, values, "id=?", args);
    }

    public List<Chamado> devolveLista(){

        List<Chamado> chamados = new ArrayList<>();

        String sql = "SELECT * FROM " + TABELA_CHAMADO +" ;";

        Cursor cursor = getWritableDatabase().rawQuery(sql, null);

        while (cursor.moveToNext()){

            Chamado chamado = new Chamado();

            chamado.setId(cursor.getLong(cursor.getColumnIndex(ID)));
            chamado.setAparelho(cursor.getString(cursor.getColumnIndex(APARELHO)));
            chamado.setNomeDoCliente(cursor.getString(cursor.getColumnIndex(CLIENTE)));
            chamado.setCaminhoImagem(cursor.getString(cursor.getColumnIndex(CAMINHO_IMAGEM)));
            chamado.setDescricao(cursor.getString(cursor.getColumnIndex(DESCRICAO)));

            chamados.add(chamado);
        }

        return chamados;
    }

    public void deletaChamado(Chamado chamado){

        String[] args = {String.valueOf(chamado.getId())};

        getWritableDatabase().delete(TABELA_CHAMADO, "id=?", args);
    }
}
