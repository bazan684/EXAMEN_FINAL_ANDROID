package com.lucasbazan.fondodepantalla.CategoriasAdmin.VideoJuegosA;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lucasbazan.fondodepantalla.R;
import com.squareup.picasso.Picasso;

public class ViewHolderVideojuegos extends RecyclerView.ViewHolder {

    View mView;

    private ViewHolderVideojuegos.ClickListener mClickListener;

    public interface ClickListener {

        void onItemClick(View view, int position);
        void onItemLongClick(View view, int position);
    }


    public void setOnClickListener(ViewHolderVideojuegos.ClickListener clickListener) {
        mClickListener = clickListener;
    }


    public ViewHolderVideojuegos(@NonNull View itemView) {
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

    public void SeteovideoJuegos(Context context, String nombre, int vista, String imagen) {
        ImageView ImagenVideoJuegos;
        TextView NombreImagen_VideoJuegos;
        TextView Vista_VideoJuegos;


        ImagenVideoJuegos = mView.findViewById(R.id.ImagenVideoJuegos);
        NombreImagen_VideoJuegos = mView.findViewById(R.id.NombreImagen_VideoJuegos);
        Vista_VideoJuegos = mView.findViewById(R.id.Vista_VideoJuegos);

        NombreImagen_VideoJuegos.setText(nombre);

        String VistaString = String.valueOf(vista);
        Vista_VideoJuegos.setText(VistaString);

        try {
            Picasso.get().load(imagen).placeholder(R.drawable.categoria).into(ImagenVideoJuegos);
        }
        catch (Exception e) {
            Picasso.get().load(R.drawable.categoria).into(ImagenVideoJuegos);
        }


    }

}
