package com.lucasbazan.fondodepantalla.CategoriasClienteFirebase;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lucasbazan.fondodepantalla.R;
import com.squareup.picasso.Picasso;

public class ViewHolderImgCatFElegida extends RecyclerView.ViewHolder {

    View mView;

    private ViewHolderImgCatFElegida.ClickListener mClickListener;

    public interface ClickListener {

        void onItemClick(View view, int position);
    }


    public void setOnClickListener(ViewHolderImgCatFElegida.ClickListener clickListener) {
        mClickListener = clickListener;
    }


    public ViewHolderImgCatFElegida(@NonNull View itemView) {
        super(itemView);
        mView = itemView;

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mClickListener.onItemClick(view,getBindingAdapterPosition());
            }
        });

    }

    public void SeteoCategoriaFElegida(Context context, String nombre, int vista, String imagen) {
        ImageView ImgCatFElegida;
        TextView NombreImg_Cat_Elegida, Vista_Img_Cat_Elegida;

        ImgCatFElegida = mView.findViewById(R.id.ImgCatFElegida);
        NombreImg_Cat_Elegida = mView.findViewById(R.id.NombreImg_Cat_Elegida);
        Vista_Img_Cat_Elegida = mView.findViewById(R.id.Vista_Img_Cat_Elegida);

        NombreImg_Cat_Elegida.setText(nombre);

        String VistaString = String.valueOf(vista);
        Vista_Img_Cat_Elegida.setText(VistaString);


        try {
            Picasso.get().load(imagen).placeholder(R.drawable.categoria).into(ImgCatFElegida);
        }
        catch (Exception e) {
            Picasso.get().load(R.drawable.categoria).into(ImgCatFElegida);
        }


    }

}
