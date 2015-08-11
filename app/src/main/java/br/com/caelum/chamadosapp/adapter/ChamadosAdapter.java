package br.com.caelum.chamadosapp.adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import br.com.caelum.chamadosapp.R;
import br.com.caelum.chamadosapp.activity.MainActivity;
import br.com.caelum.chamadosapp.modelo.Chamado;

/**
 * Created by matheus on 10/08/15.
 */
public class ChamadosAdapter extends BaseAdapter {

    private List<Chamado> chamados;
    private MainActivity activity;

    public ChamadosAdapter(List<Chamado> chamados, MainActivity activity) {
        this.chamados = chamados;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return chamados.size();
    }

    @Override
    public Object getItem(int position) {
        return chamados.get(position);
    }

    @Override
    public long getItemId(int position) {
        return chamados.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;

        final Chamado chamado = (Chamado) getItem(position);

        if (convertView != null) {
            view = convertView;
        } else {
            view = View.inflate(activity, R.layout.item_chamado, null);
        }

        criaView(view, chamado);

        return view;
    }

    private void criaView(View view, Chamado chamado) {


        ViewHolder holder = new ViewHolder(view);

        ImageView imagemAparelho = holder.imagemAparelho;

        if (chamado.getCaminhoImagem() != null) {
            Bitmap bitmap = BitmapFactory.decodeFile(chamado.getCaminhoImagem());

            Bitmap bitmapReduzido = Bitmap.createScaledBitmap(bitmap, 200, 200, true);

            imagemAparelho.setImageBitmap(bitmapReduzido);
        }

        TextView aparelho = holder.aparelho;
        aparelho.setText(chamado.getAparelho());

        TextView descricao = holder.descricao;
        descricao.setText(chamado.getDescricao());
    }

    private class ViewHolder {
        ImageView imagemAparelho;
        TextView aparelho;
        TextView descricao;

        public ViewHolder(View view) {
            aparelho = (TextView) view.findViewById(R.id.item_nome_aparelho);
            descricao = (TextView) view.findViewById(R.id.item_descricao);
            imagemAparelho = (ImageView) view.findViewById(R.id.foto_chamado);
        }
    }
}
