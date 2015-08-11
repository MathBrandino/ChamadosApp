package br.com.caelum.chamadosapp.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import br.com.caelum.chamadosapp.R;
import br.com.caelum.chamadosapp.adapter.ChamadosAdapter;
import br.com.caelum.chamadosapp.dao.ChamadoDAO;
import br.com.caelum.chamadosapp.modelo.Chamado;


public class MainActivity extends AppCompatActivity {

    private android.support.design.widget.FloatingActionButton novoChamado;
    private ListView listaDeChamados;
    private List<Chamado> chamados;
    private ChamadoDAO dao;
    private ChamadosAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dao = new ChamadoDAO(this);

        populaActivity();

        abreNovoChamado();

        populaLista();


        listaDeChamados.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                final Chamado chamado = chamados.get(position);

                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("O que deseja fazer ?")
                        .setIcon(R.drawable.mobile)
                        .setPositiveButton("Enviar chamado", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                Intent enviarChamado = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                                        "mailto", "YourEmail@here.com", null));

                                enviarChamado.putExtra(Intent.EXTRA_SUBJECT, "Chamado de " + chamado.getNomeDoCliente());

                                enviarChamado.putExtra(Intent.EXTRA_TEXT, "Aparelho :  " + chamado.getAparelho()
                                        + "\n" + "Descricão :  " + chamado.getDescricao());


                                if (chamado.getCaminhoImagem() != null) {
                                    File file = new File(chamado.getCaminhoImagem());
                                    Uri uri = Uri.fromFile(file);

                                    enviarChamado.putExtra(Intent.EXTRA_STREAM, uri
                                    );
                                }

                                startActivity(enviarChamado);

                            }
                        })
                        .setNegativeButton("Deletar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ChamadoDAO dao = new ChamadoDAO(MainActivity.this);

                                dao.deletaChamado(chamado);

                                populaLista();
                            }
                        })
                        .setNeutralButton("Alterar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent edicao = new Intent(MainActivity.this, FormularioActivity.class);
                                edicao.putExtra("chamado", chamado);
                                startActivity(edicao);
                            }
                        })
                        .setCancelable(true)
                        .show();
            }
        });

    }

    private void populaLista() {

        chamados = dao.devolveLista();

        adapter = new ChamadosAdapter(chamados, this);

        listaDeChamados.setAdapter(adapter);
    }

    private void abreNovoChamado() {
        novoChamado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, FormularioActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        populaLista();
    }

    private void populaActivity() {
        novoChamado = (FloatingActionButton) findViewById(R.id.abrir_chamado);
        listaDeChamados = (ListView) findViewById(R.id.lista_chamados);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
