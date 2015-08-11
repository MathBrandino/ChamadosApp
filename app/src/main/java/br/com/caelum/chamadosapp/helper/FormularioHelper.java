package br.com.caelum.chamadosapp.helper;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.widget.EditText;
import android.widget.ImageView;

import br.com.caelum.chamadosapp.R;
import br.com.caelum.chamadosapp.activity.FormularioActivity;
import br.com.caelum.chamadosapp.modelo.Chamado;

/**
 * Created by matheus on 10/08/15.
 */
public class FormularioHelper {

    private FormularioActivity activity;
    private EditText nomeCliente;
    private EditText aparelho;
    private EditText descricao;
    private ImageView fotoChamado;
    private android.support.design.widget.FloatingActionButton abreCamera;
    private Chamado chamado;

    public FormularioHelper(FormularioActivity activity) {
        this.activity = activity;

        nomeCliente = (EditText) activity.findViewById(R.id.nome_cliente);
        aparelho = (EditText) activity.findViewById(R.id.nome_aparelho);
        descricao = (EditText) activity.findViewById(R.id.descricao);
        fotoChamado = (ImageView) activity.findViewById(R.id.formulario_foto);
        abreCamera = (FloatingActionButton) activity.findViewById(R.id.tira_foto);

        chamado = new Chamado();
    }

    public Chamado pegaChamadoDoFormulario() {


        chamado.setNomeDoCliente(nomeCliente.getText().toString());
        chamado.setAparelho(aparelho.getText().toString());
        chamado.setDescricao(descricao.getText().toString());
        chamado.setCaminhoImagem((String) fotoChamado.getTag());

        return chamado;
    }

    private boolean validaNome() {

        if (nomeCliente.getText().toString().trim().isEmpty()) {
            android.support.design.widget.TextInputLayout layout = (TextInputLayout) activity.findViewById(R.id.layout_nome);

            layout.setError("Nome não pode ser vazio");
            return false;
        } else {
            return true;
        }
    }


    private boolean validaAparelho() {

        if (aparelho.getText().toString().trim().isEmpty()) {
            android.support.design.widget.TextInputLayout layout = (TextInputLayout) activity.findViewById(R.id.layout_aparelho);

            layout.setError("Aparelho não pode estar vazio");
            return false;
        } else {
            return true;
        }
    }


    private boolean validaDescricao() {

        if (descricao.getText().toString().trim().isEmpty()) {
            android.support.design.widget.TextInputLayout layout = (TextInputLayout) activity.findViewById(R.id.layout_descricao);

            layout.setError("Por favor coloque uma descricão válida");
            return false;
        } else {
            return true;
        }
    }

    public boolean validaFormulario() {

        if (validaNome()) {
            if (validaAparelho()) {
                if (validaDescricao()) {
                    return true;
                }
            }
        }
        return false;
    }

    public void colocaChamadoNoFormulario(Chamado chamado) {

        nomeCliente.setText(chamado.getNomeDoCliente());
        aparelho.setText(chamado.getAparelho());
        descricao.setText(chamado.getDescricao());

        if (chamado.getCaminhoImagem() != null) {
            fotoChamado.setImageURI(Uri.parse(chamado.getCaminhoImagem()));
        }
        this.chamado = chamado;

    }

    public void carregaImagem(String caminhoDaFoto) {

        Bitmap imagemFoto = BitmapFactory.decodeFile(caminhoDaFoto);

        Bitmap imagemFotoReduzida = Bitmap.createScaledBitmap(imagemFoto, imagemFoto.getWidth(), 250, true);

        fotoChamado.setScaleType(ImageView.ScaleType.FIT_XY);
        fotoChamado.setImageBitmap(imagemFotoReduzida);
        fotoChamado.setTag(caminhoDaFoto);

    }

    public FloatingActionButton getAbreCamera() {
        return abreCamera;
    }
}
