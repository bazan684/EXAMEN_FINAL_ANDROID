package com.lucasbazan.fondodepantalla.CategoriasAdmin.SeriesA;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lucasbazan.fondodepantalla.R;
import com.squareup.picasso.Picasso;

public class ViewHolderSerie extends RecyclerView.ViewHolder {

    View mView;

    private ViewHolderSerie.ClickListener mClickListener;

    public interface ClickListener {

        void onItemClick(View view, int position);
        void onItemLongClick(View view, int position);
    }


    public void setOnClickListener(ViewHolderSerie.ClickListener clickListener) {
        mClickListener = clickListener;
    }


    public ViewHolderSerie(@NonNull View itemView) {
        super(itemView);
        mView = itemView;

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mClickListener.onItemClick(view,getAdapterPosition());
            }
        });

        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                mClickListener.onItemLongClick(view, getAdapterPosition());
                return true;
            }
        });
    }

    public void SeteoSeries(Context context, String nombre, int vista, String imagen) {
        ImageView Imagen_Serie;
        TextView NombreImagen_Serie;
        TextView Vista_Serie;


        Imagen_Serie = mView.findViewById(R.id.Imagen_Serie);
        NombreImagen_Serie = mView.findViewById(R.id.NombreImagen_Serie);
        Vista_Serie = mView.findViewById(R.id.Vista_Serie);

        NombreImagen_Serie.setText(nombre);

        String VistaString = String.valueOf(vista);
        Vista_Serie.setText(VistaString);

        try {
            Picasso.get().load(imagen).placeholder(R.drawable.categoria).into(Imagen_Serie);
        }
        catch (Exception e) {
            Picasso.get().load(R.drawable.categoria).into(Imagen_Serie);
        }


    }

}
