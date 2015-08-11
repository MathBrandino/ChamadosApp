package br.com.caelum.chamadosapp.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.io.File;

import br.com.caelum.chamadosapp.R;
import br.com.caelum.chamadosapp.dao.ChamadoDAO;
import br.com.caelum.chamadosapp.helper.FormularioHelper;
import br.com.caelum.chamadosapp.modelo.Chamado;

/**
 * Created by matheus on 10/08/15.
 */
public class FormularioActivity extends AppCompatActivity {

    private final int code = 123;
    private FormularioHelper helper;
    private String caminhoDaFoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.formulario_activity);

        helper = new FormularioHelper(this);

        verificaSeEhEdicaoESetaCampos();

        iniciaCamera();
    }

    private void iniciaCamera() {

        FloatingActionButton tiraFoto = helper.getAbreCamera();

        tiraFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                caminhoDaFoto = getExternalFilesDir(null) + "/" + System.currentTimeMillis() + ".jpg";

                Uri localFoto = Uri.fromFile(new File(caminhoDaFoto));

                camera.putExtra(MediaStore.EXTRA_OUTPUT, localFoto);

                startActivityForResult(camera, code);
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == code) {
            if (resultCode == this.RESULT_OK) {
                helper.carregaImagem(caminhoDaFoto);
            } else {
                caminhoDaFoto = null;
            }
        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("caminho", caminhoDaFoto);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        caminhoDaFoto = (String) savedInstanceState.getSerializable("caminho");
    }

    private void verificaSeEhEdicaoESetaCampos() {
        Intent intent = getIntent();

        if (intent.hasExtra("chamado")){
            Chamado chamado = (Chamado) intent.getSerializableExtra("chamado");

            helper.colocaChamadoNoFormulario(chamado);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_formulario, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.salvar_chamado) {

            if (helper.validaFormulario()) {

                Chamado chamado = helper.pegaChamadoDoFormulario();

                insereOuAlteraChamado(chamado);

                finish();
                return true;
            }

        }

        return super.onOptionsItemSelected(item);
    }

    private void insereOuAlteraChamado(Chamado chamado) {
        ChamadoDAO dao = new ChamadoDAO(this);

        if (chamado.getId() == null){
            dao.insereChamado(chamado);
        } else {
            dao.alteraChamado(chamado);
        }
    }
}

