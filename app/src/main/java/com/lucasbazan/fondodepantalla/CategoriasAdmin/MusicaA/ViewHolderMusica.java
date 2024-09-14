package com.lucasbazan.fondodepantalla.CategoriasAdmin.MusicaA;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.lucasbazan.fondodepantalla.R;
import com.squareup.picasso.Picasso;

public class ViewHolderMusica extends RecyclerView.ViewHolder {

    View mView;

    private ViewHolderMusica.ClickListener mClickListener;

    public interface ClickListener {

        void onItemClick(View view, int position);
        void onItemLongClick(View view, int position);
    }


    public void setOnClickListener(ViewHolderMusica.ClickListener clickListener) {
        mClickListener = clickListener;
    }


    public ViewHolderMusica(@NonNull View itemView) {
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

    public void SeteoMusica(Context context, String nombre, int vista, String imagen) {
        ImageView Imagen_Musica;
        TextView NombreImagen_Musica;
        TextView Vista_Musica;


        Imagen_Musica = mView.findViewById(R.id.Imagen_Musica);
        NombreImagen_Musica = mView.findViewById(R.id.NombreImagen_Musica);
        Vista_Musica = mView.findViewById(R.id.Vista_Musica);

        NombreImagen_Musica.setText(nombre);

        String VistaString = String.valueOf(vista);
        Vista_Musica.setText(VistaString);

        try {
            Picasso.get().load(imagen).placeholder(R.drawable.categoria).into(Imagen_Musica);
        }
        catch (Exception e) {
            Picasso.get().load(R.drawable.categoria).into(Imagen_Musica);
        }


    }

}
